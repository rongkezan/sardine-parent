package sardine.aes.domain.event;

import lombok.*;

import java.math.BigDecimal;

@Data
public class CandlestickEvent {

  private String ch;

  private Long ts;

  private Candlestick candlestick;

  @Data
  private static class Candlestick {

    private Long id;

    private BigDecimal amount;

    private BigDecimal count;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private BigDecimal vol;
  }
}
