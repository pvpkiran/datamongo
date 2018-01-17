package in.phani.springboot.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by panikiran on 15.11.17.
 */
@Document(collection = "OilFox")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
@Setter
@SuppressWarnings({"checkstyle:membername"})
public class OilfoxData {

  @Id
  private ObjectId _id; //NOPMD

  private List<Oilfox> oilfoxes;

  @NotNull
  @Valid
  private Customer customer;
}
