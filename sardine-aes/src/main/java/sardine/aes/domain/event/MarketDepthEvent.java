package sardine.aes.domain.event;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MarketDepthEvent {

  private String ch;

  private Long ts;

  private MarketDepth depth;

  @Data
  private static class MarketDepth {

    private long version;

    private Long ts;

    private List<PriceLevel> bids;

    private List<PriceLevel> asks;

    @Data
    private static class PriceLevel {

      private BigDecimal price;

      private BigDecimal amount;
    }
  }
}
