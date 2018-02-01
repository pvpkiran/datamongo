package in.phani.springboot.controller;

import in.phani.springboot.pojo.OilfoxData;
import in.phani.springboot.pojo.QueryObject;
import in.phani.springboot.service.OilfoxService;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by panikiran on 15.11.17.
 */
@RestController
@RequestMapping("/oilfox")
@Api(value = "Oilfox", tags = "Oilfox Registration", description = "Endpoints for oilfox registration",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class OilfoxController {

  private static final String REGISTER = "/register";
  private static final String FIND_BY_SAPID = "/customer/{sapId}";
  private static final String DELETE_BY_OILFOXID = "/{oilfoxId}";
  private static final String GET_BY_DSL = "/dsl";

  private final OilfoxService oilfoxService;

  @Autowired
  public OilfoxController(final OilfoxService oilfoxService) {
    this.oilfoxService = oilfoxService;
  }

  @PostMapping(REGISTER)
  public ResponseEntity<OilfoxData> registerOilFoxData(@RequestBody @Valid final OilfoxData oilfoxData) {
    final OilfoxData savedObject = oilfoxService.registerOilfoxData(oilfoxData);

    return ResponseEntity.ok(savedObject);
  }

  @GetMapping(FIND_BY_SAPID)
  public ResponseEntity<OilfoxData> findBySapId(@PathVariable final String sapId) {
    return oilfoxService.findOilfoxBySapId(sapId).map(ResponseEntity::ok).orElseGet(this::notFound);
  }

  @DeleteMapping(DELETE_BY_OILFOXID)
  public ResponseEntity<Integer> deleteByOilFoxId(@PathVariable final String oilfoxId) {
    return ResponseEntity.ok(oilfoxService.deleteOilfoxId(oilfoxId));
  }

  @PostMapping(GET_BY_DSL)
  public ResponseEntity<List<OilfoxData>> getByDsl(@RequestBody final QueryObject queryObject) {
    return oilfoxService.findByDsl(queryObject).map(ResponseEntity::ok).orElseGet(this::notFound);
  }

  private <T> ResponseEntity<T> notFound() {
    return ResponseEntity.notFound().build();
  }
}
