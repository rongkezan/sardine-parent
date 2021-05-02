package sardine.aes.domain.event;

import lombok.*;

import java.math.BigDecimal;

@Data
public class MarketDetailEvent {
  private String ch;

  private Long ts;

  private MarketDetail detail;

  @Data
  private static class MarketDetail {

    private Long id;

    private BigDecimal open;

    private BigDecimal close;

    private BigDecimal low;

    private BigDecimal high;

    private Long count;

    private BigDecimal vol;

    private BigDecimal amount;

    private Long version;
  }
}
