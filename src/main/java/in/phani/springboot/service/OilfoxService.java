package in.phani.springboot.service;

import in.phani.springboot.pojo.OilfoxData;
import in.phani.springboot.pojo.QueryObject;

import java.util.List;
import java.util.Optional;

/**
 * Created by panikiran on 16.11.17.
 */
public interface OilfoxService {

  OilfoxData registerOilfoxData(OilfoxData oilfoxData);

  Optional<OilfoxData> findOilfoxBySapId(String sapId);

  int deleteOilfoxId(String oilfoxId);

  Optional<List<OilfoxData>> findByDsl(QueryObject queryObject);
}
