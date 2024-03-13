package com.rpcdemo.netty.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 967283726033110749L;
    private String ID;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
}
