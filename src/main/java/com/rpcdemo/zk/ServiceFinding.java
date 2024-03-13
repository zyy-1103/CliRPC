package com.rpcdemo.zk;

import com.rpcdemo.hook.LoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceFinding {
    private static final String BASE_PATH = "/services";
    @Autowired
    private CuratorFramework client;

    public String findingService(String serviceName, LoadBalance balance) throws Exception {
        List<String> strings = client.getChildren().forPath(serviceName);
        return balance.balance(strings);
    }
}
