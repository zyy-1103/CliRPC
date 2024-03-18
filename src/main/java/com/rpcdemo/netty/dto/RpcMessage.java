package com.rpcdemo.netty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RpcMessage {
    private byte type;
    private Object data;
}
