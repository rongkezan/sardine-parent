package sardine.aes.domain.request;

import lombok.*;
import sardine.aes.domain.enums.DepthStepEnum;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubMarketDepthRequest {

  private List<String> symbols;

  private DepthStepEnum step;
}
