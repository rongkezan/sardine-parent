package sardine.webflux;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author keith
 */
@RestController
public class TestController {

    @RequestMapping(value = "/sse", produces = "text/event-stream;charset=utf-8")
    public String sse() {
        return "data:" + LocalDateTime.now().toString() + "\n\n";
    }
}
