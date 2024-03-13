package com.rpcdemo.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceRegistry {
    private static final String BASE_PATH = "/services";
    @Autowired
    private CuratorFramework client;

    public void registerService(String serviceName, String serviceAddress) throws Exception {
        String servicePath = BASE_PATH + "/" + serviceName;
        if (client.checkExists().forPath(servicePath) == null) {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(servicePath);
        }

        String addressNode = servicePath + "/address-" + serviceAddress;
        client.create().withMode(CreateMode.EPHEMERAL).forPath(addressNode);
        System.out.println("Registered service at: " + addressNode);
    }

}
