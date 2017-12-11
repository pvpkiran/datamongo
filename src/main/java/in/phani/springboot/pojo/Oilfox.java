package in.phani.springboot.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by panikiran on 15.11.17.
 */
@Getter
@Setter
@Builder
public class Oilfox {

  private String name;

  @NotNull
  private String oilFoxId;

  private int level;

  private AlertMethod alertMethod;
}
