package sardine.aes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sardine.aes.domain.enums.CandlestickIntervalEnum;
import sardine.aes.domain.request.SubCandlestickRequest;
import sardine.aes.service.MarketService;
import sardine.aes.service.impl.HuobiMarketServiceImpl;

import java.util.Arrays;

/**
 * @author keith
 */
@RestController
@RequiredArgsConstructor
public class TestController {

    private final String a;

    @GetMapping("test")
    public void test(){
        System.out.println(a);
    }
}
