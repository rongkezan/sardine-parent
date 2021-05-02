package sardine.aes.domain.request;

import lombok.*;
import sardine.aes.domain.enums.CandlestickIntervalEnum;

import java.util.List;

@Data
@Builder
public class SubCandlestickRequest {

  private List<String> symbols;

  private CandlestickIntervalEnum interval;
}
