package engineer.thesis;

import engineer.thesis.core.service.Implementation.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public class PatientControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientService service;
/*
    @Test
    public void getAllPatients_Test() {

        mockAuthenticatedUser(buildTestUserWithRole(UserRole.PATIENT));

        PatientDTO2 patient = new PatientDTO2();
        patient.setInsuranceNumber("696969");

        List<PatientDTO2> allPatients = Collections.singletonList(patient);

        given(service.getAllPatientsShort()).willReturn(allPatients);

        try {
            Boolean doesContain = mvc.perform(MockMvcRequestBuilders.get("/patients")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn()
                    .getResponse()
                    .getContentAsString()
                    .contains("\"insuranceNumber\":\"" + "696969" + "\"");
            assertThat(doesContain).isEqualTo(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
}
