package in.phani.springboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryObject {

  private String oilfoxId;
  private String oilfoxName;
  private String customerSapId;
  private String customerEmail;
}
