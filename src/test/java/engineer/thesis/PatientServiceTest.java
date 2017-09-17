package engineer.thesis;

import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.PersonalDetailsRepository;
import engineer.thesis.core.service.Implementation.PatientService;
import engineer.thesis.core.service.Implementation.PersonalDetailsService;
import engineer.thesis.core.service.Interface.IUserService;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PatientServiceTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Autowired PatientService patientService;

    @Autowired
    PersonalDetailsService personalDetailsService;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PersonalDetailsRepository personalDetailsRepository;

    @MockBean
    private IUserService userService;

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
/*
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
        Mockito.when(patientRepository.saveDoctor(Matchers.any(Patient.class))).thenReturn(patient);
        Mockito.when(userService.findByEmail("mail@mail.pl")).thenReturn(Optional.of(user));
    }

    @Test
    public void findAllPatients_Test() {
        List<PatientDTO2> patients = patientService.getAllPatientsShort();
        assertThat(patients.size()).isEqualTo(1);
    }

    @Test
    public void findById_Test() {
        Long ID = 1L;

        String EXPECTED_NAME = "John";

        PatientDTO2 patient = patientService.findById(ID);
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

        PatientDTO2 patient = patientService.findByPesel(PESEL);
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

        List<PatientDTO2> patient = patientService.findPatientsByLastName(LAST_NAME);
        assertThat(patient.size()).isEqualTo(1);
    }

    @Test
    public void saveNewPatient_Test() {
        String LAST_NAME = "Smith";

        PatientDTO2 expectedPatientDTO = new PatientDTO2();
        PersonalDetailDTO expectedPersonalDetailDTO = new PersonalDetailDTO();
        expectedPatientDTO.setEmergencyContact(new PersonalDetailDTO());
        expectedPersonalDetailDTO.setLastName(LAST_NAME);
        expectedPatientDTO.setPersonalDetails(expectedPersonalDetailDTO);

        PatientDTO2 patient = patientService.saveNewPatient(expectedPatientDTO, "mail@mail.pl");
        assertThat(patient.getPersonalDetails().getLastName()).isEqualTo(expectedPersonalDetailDTO.getLastName());
    }
*/
}
