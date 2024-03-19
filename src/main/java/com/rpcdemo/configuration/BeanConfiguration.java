package com.rpcdemo.configuration;

import com.rpcdemo.netty.server.NettyServer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Value("zk.address")
    String address;
    @Bean
    public CuratorFramework framework() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(address, new ExponentialBackoffRetry(1000, 3));
        curatorFramework.start();
        return curatorFramework;
    }

    @Bean
    public NettyServer server() throws Exception {
        NettyServer nettyServer = new NettyServer();
        nettyServer.run();
        return nettyServer;
    }
}
