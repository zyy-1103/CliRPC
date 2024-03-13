package com.rpcdemo.netty.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUID = -5819461361735001986L;
    private String ID;
    private T data;
}
