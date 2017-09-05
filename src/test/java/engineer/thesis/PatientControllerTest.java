package engineer.thesis;

import engineer.thesis.core.service.Implementation.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientService service;
/*
    @Test
    public void getAllPatients_Test() {

        mockAuthenticatedUser(buildTestUserWithRole(UserRole.PATIENT));

        PatientDTO patient = new PatientDTO();
        patient.setInsuranceNumber("696969");

        List<PatientDTO> allPatients = Collections.singletonList(patient);

        given(service.getAllPatients()).willReturn(allPatients);

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
