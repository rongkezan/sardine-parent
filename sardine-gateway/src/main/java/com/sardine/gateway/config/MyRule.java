//package com.sardine.gateway.config;
//
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.AbstractLoadBalancerRule;
//import com.netflix.loadbalancer.Server;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import java.util.List;
//
//@Qualifier
//public class MyRule extends AbstractLoadBalancerRule {
//
//    @Override
//    public void initWithNiwsConfig(IClientConfig iClientConfig) {
//
//    }
//
//    @Override
//    public Server choose(Object o) {
//        List<Server> reachableServers = this.getLoadBalancer().getReachableServers();
//        return reachableServers.get(0);
//    }
//}
