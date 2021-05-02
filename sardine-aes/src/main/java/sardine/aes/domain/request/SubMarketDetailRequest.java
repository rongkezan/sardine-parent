package sardine.aes.domain.request;


import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubMarketDetailRequest {

  private List<String> symbols;

}
