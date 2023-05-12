package com.sardine.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class ElasticsearchController {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @GetMapping("get")
    public Order get() throws IOException {
        GetResponse<Order> response = elasticsearchClient.get(g -> g.index("order").id("1"), Order.class);
        if (response.found()) {
            return response.source();
        } else {
            return null;
        }
    }

    @GetMapping("search")
    public List<Order> search() throws IOException {
        String searchText = "牙";
        SearchResponse<Order> response = elasticsearchClient.search(s -> s
                        .index("order")
                        .query(q -> q
                                .match(t -> t
                                        .field("name")
                                        .query(searchText)
                                )
                        )
                , Order.class
        );
        TotalHits total = response.hits().total();
        boolean isExactResult = total.relation() == TotalHitsRelation.Eq;
        if (isExactResult) {
            log.info("There are " + total.value() + " results");
        } else {
            log.info("There are more than " + total.value() + " results");
        }

        List<Hit<Order>> hits = response.hits().hits();
        List<Order> orders = new ArrayList<>();
        for (Hit<Order> hit : hits) {
            Order order = hit.source();
            orders.add(order);
        }
        return orders;
    }

    @GetMapping("index")
    public String index() throws IOException {
        Order order = new Order(1L, "牙刷");
        IndexResponse response = elasticsearchClient.index(i -> i
                .index("order")
                .id(order.getId().toString())
                .document(order)
        );
        return "Indexed with version" + response.version();
    }

    @GetMapping("bulk/index")
    public String bulkIndex() throws IOException {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(2L, "杯子"));
        orders.add(new Order(3L, "牙膏"));
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Order order : orders) {
            br.operations(op -> op.index(i -> i
                    .index("order")
                    .id(order.getId().toString())
                    .document(order)
            ));
        }
        BulkResponse response = elasticsearchClient.bulk(br.build());
        // Log errors, if any
        if (response.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item : response.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }
        return "Bulk indexed with version" + response.toString();
    }
}
