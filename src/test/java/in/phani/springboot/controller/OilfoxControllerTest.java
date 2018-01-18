package in.phani.springboot.controller;

import in.phani.springboot.service.OilfoxService;
import in.phani.springboot.pojo.AlertMethod;
import in.phani.springboot.pojo.Customer;
import in.phani.springboot.pojo.Oilfox;
import in.phani.springboot.pojo.OilfoxData;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.util.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = OilfoxController.class)
public class OilfoxControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private OilfoxService oilfoxService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testRegister() throws Exception {

    Customer customer = Customer.builder().email("abc@def.com").sapId("sapId").firstname("firstname")
        .lastname("lastname").build();
    Oilfox oilfox = Oilfox.builder().alertMethod(AlertMethod.EMAIL).level(100).name("someName").oilFoxId("123").build();

    OilfoxData oilfoxData = OilfoxData.builder().oilfoxes(Lists.newArrayList(oilfox)).customer(customer).build();
    String jsonString = objectMapper.writeValueAsString(oilfoxData);

    /*ArgumentCaptor<OilfoxData> oilfoxDataArgumentCaptor = ArgumentCaptor.forClass(OilfoxData.class);
    doAnswer(invocation -> oilfoxDataArgumentCaptor.getValue()).when(oilfoxService).registerOilfoxData(oilfoxDataArgumentCaptor.capture());*/
    Mockito.when(oilfoxService.registerOilfoxData(Matchers.any(OilfoxData.class))).thenReturn(oilfoxData);

    mvc.perform(MockMvcRequestBuilders.post("/oilfox/register")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(jsonString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customer.email", is("abc@def.com")))
        .andExpect(jsonPath("$.oilfoxes[0].level", is(100)));
  }
}
