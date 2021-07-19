package com.sardine.common.httpclient;

import com.sardine.common.util.JacksonUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author keith
 */
public class TestDemo {

    private static RestTemplate restTemplate;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Integer> list = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 1; i++) {
            new Thread(() ->  {
                list.add(exec());
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println("数量:" + list);
    }

    private static int exec(){
        String json = "{\n" +
                "    \"productGroup\": \"SwapU\",\n" +
                "    \"isHistoryOrder\": \"0\",\n" +
                "    \"delegateType\": \"2\"\n" +
                "}";
        HttpRequest request = HttpRequest.post("http://127.0.0.1:8801/api/app/contract/query/orders")
                .addHeader("x-uuid", "b5772789-7328-4eb8-ad4a-e0bdfe0e257a")
                .contentType(ContentType.APPLICATION_JSON)
                .bodyJson(json);
        HttpResult result = request.execute();
        String data = result.getData();
        Map<String, Map<String, List<String>>> map = JacksonUtils.toBean(data, Map.class);
        System.out.println(map);
        return map.get("data").get("data").size();
    }
}
