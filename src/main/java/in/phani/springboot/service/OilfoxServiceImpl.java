package in.phani.springboot.service;

import in.phani.springboot.pojo.Oilfox;
import in.phani.springboot.pojo.OilfoxData;
import in.phani.springboot.pojo.QOilfoxData;
import in.phani.springboot.pojo.QueryObject;
import in.phani.springboot.repository.OilfoxRepository;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by panikiran on 16.11.17.
 */
@Service
public class OilfoxServiceImpl implements OilfoxService {

  private final OilfoxRepository oilfoxRepository;

  @Autowired
  public OilfoxServiceImpl(final OilfoxRepository oilfoxRepository) {
    this.oilfoxRepository = oilfoxRepository;
  }

  @Override
  public OilfoxData registerOilfoxData(final OilfoxData oilfoxData) {
    final Optional<OilfoxData> oilfoxDataOptional = oilfoxRepository.findByCustomer_SapId(oilfoxData.getCustomer().getSapId());
    return oilfoxDataOptional.map(oilfoxDataFound -> updateRecordInDbWithNewData(oilfoxData, oilfoxDataFound))
        .orElseGet(() -> oilfoxRepository.save(oilfoxData));
  }

  @Override
  public Optional<OilfoxData> findOilfoxBySapId(final String sapId) {
    return oilfoxRepository.findByCustomer_SapId(sapId);
  }

  @Override
  public int deleteOilfoxId(final String oilfoxId) {
    final Optional<OilfoxData> oilfoxDataOptional = oilfoxRepository.findByOilFoxId(oilfoxId);
    if (oilfoxDataOptional.isPresent()) {
      final OilfoxData oilfoxData = oilfoxDataOptional.get();
      if (oilfoxData.getOilfoxes().size() == 1) {
        oilfoxRepository.delete(oilfoxData);
      } else {
        deleteSpecificOilfoxFromDb(oilfoxId, oilfoxData);
      }

      return 1;
    }
    return 0;
  }

  @Override
  public Optional<List<OilfoxData>> findByDsl(QueryObject queryObject) {
    //new PathBuilderFactory().create(OilfoxData.class).get(QOilfoxData.oilfoxData.oilfoxes.any().oilFoxId).eq(queryObject.getOilfoxId());
    BooleanExpression predicate = QOilfoxData.oilfoxData.oilfoxes.any().oilFoxId.eq(queryObject.getOilfoxId());
    if(queryObject.getCustomerEmail() != null) {
      predicate = predicate.and(QOilfoxData.oilfoxData.customer.email.eq(queryObject.getCustomerEmail()));
    }
    List<OilfoxData> oilfoxData = Lists.newArrayList();

    oilfoxRepository.findAll(predicate).forEach(oilfoxData::add);
    return Optional.of(oilfoxData);
  }

  private OilfoxData updateRecordInDbWithNewData(final OilfoxData oilfoxData, final OilfoxData oilfoxDataFound) {
    oilfoxDataFound.getOilfoxes().addAll(oilfoxData.getOilfoxes());
    oilfoxDataFound.setCustomer(oilfoxData.getCustomer());
    return oilfoxRepository.save(oilfoxDataFound);
  }

  private void deleteSpecificOilfoxFromDb(final String oilfoxId, final OilfoxData oilfoxData) {
    final List<Oilfox> collect = oilfoxData.getOilfoxes()
        .stream()
        .filter(oilfox -> ! oilfox.getOilFoxId().equalsIgnoreCase(oilfoxId))
        .collect(Collectors.toList());

    oilfoxData.setOilfoxes(collect);

    oilfoxRepository.save(oilfoxData);
  }
}
