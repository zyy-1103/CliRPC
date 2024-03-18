package com.rpcdemo.netty.dto;

import lombok.*;
import org.yaml.snakeyaml.events.Event;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 967283726033110749L;
    private int ID;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
}
