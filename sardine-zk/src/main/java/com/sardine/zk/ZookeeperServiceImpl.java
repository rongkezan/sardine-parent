package com.sardine.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Zookeeper scheduling service.
 * For example, there has 2 servers do same scheduling job, but just
 * one has leadership, if one of them dead, another one will be the
 * leader continue do the job, and 2 servers will not do job at the
 * same time.
 *
 * @author keith
 */
@Slf4j
@Service
public class ZookeeperServiceImpl implements ZookeeperService {

    private static final String BASE_PATH = "/sardine";

    private LeaderLatch leaderLatch;

    @PostConstruct
    public void init() throws Exception {
        CuratorFramework curator = CuratorFrameworkFactory.builder()
                .namespace("sardine-curator")
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(new BoundedExponentialBackoffRetry(1_000, 16_000, Integer.MAX_VALUE))
                .build();
        // logging when curator change states.
        curator.getConnectionStateListenable().addListener((curatorFramework, connectionState) ->
            log.info("[CuratorFramework] State changed, now connection state is: {}.", connectionState));
        curator.start();
        curator.blockUntilConnected();
        log.info("[CuratorFramework] Client connected, start create leader latch.");

        leaderLatch = new LeaderLatch(curator, BASE_PATH);
        leaderLatch.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {
                log.info("[LeaderLatch] Got leadership.");
            }

            @Override
            public void notLeader() {
                log.info("[LeaderLatch] Lost leadership.");
            }
        });
        leaderLatch.start();
        log.info("[LeaderLatch] Started.");
    }

    @Override
    public boolean hasLeaderShip() {
        return leaderLatch.hasLeadership();
    }
}
