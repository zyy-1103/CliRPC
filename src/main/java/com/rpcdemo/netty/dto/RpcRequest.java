package com.rpcdemo.netty.dto;

import lombok.*;
import org.yaml.snakeyaml.events.Event;

import java.io.Serializable;

@Data
public class RpcRequest implements Serializable {
    public RpcRequest(String interfaceName, String methodName, Object[] parameters, Class<?>[] paramTypes) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameters = parameters;
        this.paramTypes = paramTypes;
    }

    private static final long serialVersionUID = 967283726033110749L;
    private int ID;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
}
