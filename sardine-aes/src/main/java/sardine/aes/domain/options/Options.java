package sardine.aes.domain.options;

import sardine.aes.domain.enums.ExchangeEnum;

/**
 * @author keith
 */
public interface Options {

  String getApiKey();

  String getSecretKey();

  ExchangeEnum getExchange();

  String getRestHost();

  String getWebSocketHost();

  boolean isWebSocketAutoConnect();
}
