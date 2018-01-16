package in.phani.springboot.service;

import in.phani.springboot.OilfoxService;
import in.phani.springboot.OilfoxServiceImpl;
import in.phani.springboot.pojo.AlertMethod;
import in.phani.springboot.pojo.Customer;
import in.phani.springboot.pojo.Oilfox;
import in.phani.springboot.pojo.OilfoxData;
import in.phani.springboot.repository.OilfoxRepository;
import in.phani.springboot.service.MongoConfiguration;
import static com.lordofthejars.nosqlunit.core.LoadStrategyEnum.CLEAN_INSERT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by panikiran on 16.11.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfiguration.class})
@UsingDataSet(locations = "/imports/oilfoxdata.json", loadStrategy = CLEAN_INSERT)
public class OilfoxServiceTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Rule
  public MongoDbRule mongoDbRule = MongoConfiguration.getSpringMongoDbRule();

  @Autowired
  private OilfoxRepository oilfoxRepository;

  private OilfoxService oilfoxService;

  @Before
  public void setUp() {
    oilfoxService = new OilfoxServiceImpl(oilfoxRepository);
  }


  @Test
  public void testFakeMongoSetUp() {
    List<OilfoxData> oilFoxesData = oilfoxRepository.findAll();
    assertEquals(2, oilFoxesData.size());
    assertTrue(Arrays.asList("1111", "2222").contains(oilFoxesData.get(0).getCustomer().getSapId()));
  }

  @Test
  public void testForRegister() {
    Oilfox oilfox = Oilfox.builder().level(200).name("newoilfox").oilFoxId("3").alertMethod(AlertMethod.SMS).build();
    Customer customer = Customer.builder().firstname("fgh").lastname("ijk").phoneNumber("7890").email("fgh@ijk.com").sapId("3333").build();

    OilfoxData oilfoxData = OilfoxData.builder().customer(customer).oilfoxes(Lists.newArrayList(oilfox)).build();

    oilfoxService.registerOilfoxData(oilfoxData);
    assertEquals(3, oilfoxRepository.findAll().size());
  }

  @Test
  public void testForSecondDeviceRegister() {
    Oilfox oilfox = Oilfox.builder().level(100).name("myoilfox2").oilFoxId("2").alertMethod(AlertMethod.EMAIL).build();
    Customer customer = Customer.builder().firstname("abcc").lastname("deff").phoneNumber("34567").email("abc@def.com").sapId("1111").build();

    OilfoxData oilfoxData = OilfoxData.builder().customer(customer).oilfoxes(Lists.newArrayList(oilfox)).build();

    assertEquals(1, oilfoxRepository.findByCustomer_SapId("1111").get().getOilfoxes().size());

    oilfoxService.registerOilfoxData(oilfoxData);

    assertEquals(2, oilfoxRepository.findAll().size());
    assertEquals(2, oilfoxRepository.findByCustomer_SapId("1111").get().getOilfoxes().size());
  }

  @Test
  public void testForDelete() {
    Oilfox oilfox = Oilfox.builder().level(100).name("myoilfox2").oilFoxId("2").alertMethod(AlertMethod.EMAIL).build();
    Customer customer = Customer.builder().firstname("abcc").lastname("deff").phoneNumber("34567").email("abc@def.com").sapId("1111").build();

    OilfoxData oilfoxData = OilfoxData.builder().customer(customer).oilfoxes(Lists.newArrayList(oilfox)).build();
    oilfoxService.registerOilfoxData(oilfoxData);
    assertEquals(2, oilfoxRepository.findByCustomer_SapId("1111").get().getOilfoxes().size());

    int count = oilfoxService.deleteOilfoxId("1");
    assertEquals(1, count);
    List<Oilfox> oilfoxes = oilfoxRepository.findByCustomer_SapId("1111").get().getOilfoxes();
    assertEquals(1, oilfoxes.size());
    assertEquals("2", oilfoxes.get(0).getOilFoxId());

    count = oilfoxService.deleteOilfoxId("2");

    assertEquals(1, count);
    assertFalse(oilfoxRepository.findByCustomer_SapId("1111").isPresent());

  }
}
