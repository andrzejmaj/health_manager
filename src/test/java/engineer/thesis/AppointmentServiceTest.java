package engineer.thesis;

import engineer.thesis.core.repository.AppointmentRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.TimeSlotRepository;
import engineer.thesis.core.service.Implementation.AppointmentService;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
public class AppointmentServiceTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Autowired
    private AppointmentService appointmentService;

    @MockBean
    private AppointmentRepository appointmentRepository;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private TimeSlotRepository timeSlotRepository;

    @TestConfiguration
    static class AppointmentServiceTestContextConfiguration {
        @Bean
        public AppointmentService appointmentService() {
            return new AppointmentService();
        }
    }
/*
    @Before
	public void setUp() {
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

		when(patientRepository.findAll()).thenReturn(Collections.singletonList(patient));
		when(patientRepository.findOne(ID)).thenReturn(patient);

		Doctor doctor = new Doctor(new User(), "oncologist");
		TimeSlot timeSlot1 = new TimeSlot(LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC),
				LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC), doctor);
		timeSlot1.setId(1L);
		TimeSlot timeSlot2 = new TimeSlot(LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC),
				LocalDateTime.ofEpochSecond(2L, 0, ZoneOffset.UTC), doctor);
		timeSlot2.setId(2L);
		Appointment appointment = new Appointment(patient, timeSlot1, 1, false, "");

		when(appointmentRepository.findByTimeSlotId(timeSlot1.getId())).thenReturn(appointment);
		when(appointmentRepository.findByTimeSlotId(timeSlot2.getId())).thenReturn(null);
		when(timeSlotRepository.findOne(timeSlot1.getId())).thenReturn(timeSlot1);
		when(timeSlotRepository.findOne(timeSlot2.getId())).thenReturn(timeSlot2);
	}

	@Test
	public void existsOnExistingAppointmentReturnsTrue_Test() {
		AppointmentDTO appointmentDTO = new AppointmentDTO(0L, 1L, false, 1, "");
		assertThat(appointmentService.exists(appointmentDTO)).isEqualTo(true);

		appointmentDTO = new AppointmentDTO(100L, 1L, true, 12, "aaa");
		assertThat(appointmentService.exists(appointmentDTO)).isEqualTo(true);
	}

	@Test
	public void existsOnNonExistingAppointmentReturnsFalse_Test() {
		AppointmentDTO appointmentDTO = new AppointmentDTO(0L, 2L, false, 1, "");
		assertThat(appointmentService.exists(appointmentDTO)).isEqualTo(false);
	}

	@Test
	public void createNewAppointment_Test() {
		AppointmentDTO appointmentDTO = new AppointmentDTO(0L, 2L, false, 1, "");
		appointmentService.saveDoctor(appointmentDTO, 1L);
		verify(appointmentRepository, never()).delete(any(Appointment.class));
		verify(appointmentRepository, times(1)).saveDoctor(any(Appointment.class));
	}

	@Test
	public void changeAppointment_Test() {
		AppointmentDTO appointmentDTO = new AppointmentDTO(0L, 1L, false, 1, "");
		appointmentService.saveDoctor(appointmentDTO, 1L);
		verify(appointmentRepository, times(1)).delete(any(Appointment.class));
		verify(appointmentRepository, times(1)).saveDoctor(any(Appointment.class));
	}
*/
}
