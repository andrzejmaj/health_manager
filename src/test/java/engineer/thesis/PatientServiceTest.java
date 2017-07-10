package engineer.thesis;

import engineer.thesis.model.Patient;
import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.User;
import engineer.thesis.model.dto.PatientDTO;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.repository.PatientRepository;
import engineer.thesis.repository.PersonalDetailsRepository;
import engineer.thesis.service.IPatientService;
import engineer.thesis.service.IUserService;
import engineer.thesis.service.PatientService;
import engineer.thesis.service.PersonalDetailsService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PatientServiceTest {

    @TestConfiguration
    static class PatientServiceTestContextConfiguration {
        @Bean
        public PatientService patientService() {
            return new PatientService();
        }

        @Bean
        public PersonalDetailsService personalDetailsService() {
            return new PersonalDetailsService();
        }
    }

    @Autowired PatientService patientService;

    @Autowired
    PersonalDetailsService personalDetailsService;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PersonalDetailsRepository personalDetailsRepository;

    @MockBean
    private IUserService userService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {

        // TODO: 03/07/2017
        // make a little bit better test data
        // just a lil'

        Long ID = 1L;
        String NAME = "John";
        String LAST_NAME = "Smith";
        String PESEL = "1234567890";

        Patient patient = new Patient();
        patient.setId(ID);

        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setPesel("1234567890");
        PersonalDetails emergencyContact = new PersonalDetails();
        personalDetails.setFirstName(NAME);
        personalDetails.setLastName(LAST_NAME);
        User user = new User();

        user.setPersonalDetails(personalDetails);
        patient.setUser(user);
        patient.setEmergencyContact(emergencyContact);


        Mockito.when(patientRepository.findAll()).thenReturn(Collections.singletonList(patient));
        Mockito.when(patientRepository.findOne(ID)).thenReturn(patient);
        Mockito.when(patientRepository.findByUser_PersonalDetails_Pesel(PESEL)).thenReturn(patient);
        Mockito.when(patientRepository.findByUser_PersonalDetails_LastNameLike(LAST_NAME)).thenReturn(Collections.singletonList(patient));
        Mockito.when(patientRepository.save(Matchers.any(Patient.class))).thenReturn(patient);
        Mockito.when(userService.findByEmail("mail@mail.pl")).thenReturn(Optional.of(user));
    }

    @Test
    public void findAllPatients_Test() {
        List<PatientDTO> patients = patientService.getAllPatients();
        assertThat(patients.size()).isEqualTo(1);
    }

    @Test
    public void findById_Test() {
        Long ID = 1L;

        String EXPECTED_NAME = "John";

        PatientDTO patient = patientService.findById(ID);
        assertThat(patient.getPersonalDetails().getFirstName()).isEqualTo(EXPECTED_NAME);
    }

    @Test
    public void findById_ExceptionTest() {
        Long ERROR_ID = 2L;

        exception.expect(NoSuchElementException.class);
        patientService.findById(ERROR_ID);
    }

    @Test
    public void findByPesel_Test() {
        String PESEL = "1234567890";

        String EXPECTED_NAME = "John";

        PatientDTO patient = patientService.findByPesel(PESEL);
        assertThat(patient.getPersonalDetails().getFirstName()).isEqualTo(EXPECTED_NAME);
    }

    @Test
    public void findByPesel_ExceptionTest() {
        String ERROR_PESEL = "0987654321";

        exception.expect(NoSuchElementException.class);
        patientService.findByPesel(ERROR_PESEL);
    }

    @Test
    public void findByLastName_Test() {
        String LAST_NAME = "Smith";

        List<PatientDTO> patient = patientService.findPatientsByLastName(LAST_NAME);
        assertThat(patient.size()).isEqualTo(1);
    }

    @Test
    public void saveNewPatient_Test() {
        String LAST_NAME = "Smith";

        PatientDTO expectedPatientDTO = new PatientDTO();
        PersonalDetailDTO expectedPersonalDetailDTO = new PersonalDetailDTO();
        expectedPatientDTO.setEmergencyContact(new PersonalDetailDTO());
        expectedPersonalDetailDTO.setLastName(LAST_NAME);
        expectedPatientDTO.setPersonalDetails(expectedPersonalDetailDTO);

        PatientDTO patient = patientService.saveNewPatient(expectedPatientDTO, "mail@mail.pl");
        assertThat(patient.getPersonalDetails().getLastName()).isEqualTo(expectedPersonalDetailDTO.getLastName());
    }

}
