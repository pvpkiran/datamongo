package in.phani.springboot;

import in.phani.springboot.pojo.OilfoxData;

import java.util.Optional;

/**
 * Created by panikiran on 16.11.17.
 */
public interface OilfoxService {

  OilfoxData registerOilfoxData(OilfoxData oilfoxData);

  Optional<OilfoxData> findOilfoxBySapId(String sapId);

  int deleteOilfoxId(String oilfoxId);
}
