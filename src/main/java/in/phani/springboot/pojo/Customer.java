package in.phani.springboot.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by panikiran on 15.11.17.
 */
@Builder
@Getter
@Setter
public class Customer {

  private String firstname;

  private String lastname;

  @NotNull
  private String sapId;

  @NotNull
  private String email;

  private String phoneNumber;
}
