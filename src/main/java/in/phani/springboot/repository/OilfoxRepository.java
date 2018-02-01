package in.phani.springboot.repository;

import in.phani.springboot.pojo.OilfoxData;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by panikiran on 15.11.17.
 */
@Repository
public interface OilfoxRepository extends MongoRepository<OilfoxData, String>, QueryDslPredicateExecutor<OilfoxData> {

  @SuppressWarnings({"checkstyle:methodname", "PMD.MethodNamingConventions"})
  Optional<OilfoxData> findByCustomer_SapId(final String sapId);

  @Query("{'oilfoxes.oilFoxId' : ?0}")
  Optional<OilfoxData> findByOilFoxId(final String oilFoxId);
}
