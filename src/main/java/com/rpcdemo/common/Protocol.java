package com.rpcdemo.common;

import org.yaml.snakeyaml.events.Event;

// 消息传输协议
public class Protocol {

    // 消息类型
    public static final int REQUEST_TYPE = 1;
    public static final int RESPONSE_TYPE = 2;

    // 记录类型的字段为byte类型（1字节）
    public static final int TYPE_SIZE = 1;

    // 记录长度的字段为int型（4字节）
    public static final int MESSAGE_SIZE = 4;

    public static final int ID_SIZE = 4;

    // 信息头所占字节数
    public static final int HEAD_SIZE = TYPE_SIZE + MESSAGE_SIZE + ID_SIZE;

    public static final String BASE_PACKAGE = "com.rpcdemo.";
}
