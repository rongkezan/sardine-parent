package sardine.aes.domain.event;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MarketTradeEvent {

  private String ch;

  private Long ts;

  private List<MarketTrade> list;

  @Data
  private static class MarketTrade {

    private String id;

    private Long tradeId;

    private BigDecimal price;

    private BigDecimal amount;

    private String direction;

    private Long ts;
  }
}
