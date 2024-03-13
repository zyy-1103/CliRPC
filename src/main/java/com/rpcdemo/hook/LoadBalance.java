package com.rpcdemo.hook;

import java.util.List;

/*
负载均衡算法接口，给用户预留的hook
 */
public interface LoadBalance {
    String balance(List<String> strings);
}
