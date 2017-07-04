package engineer.thesis;

import engineer.thesis.model.UserRole;
import engineer.thesis.model.dto.PatientDTO;
import engineer.thesis.service.PatientService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class PatientControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientService service;

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
}
