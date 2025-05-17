package com.tiv.log.monitor.sdk.push;

import com.tiv.log.monitor.sdk.model.LogMessage;

/**
 * 日志推送服务
 */
public interface LogPushService {

    /**
     * 初始化
     *
     * @param host
     * @param port
     */
    void init(String host, int port);

    /**
     * 推送日志
     *
     * @param logMessage
     */
    void push(LogMessage logMessage);

}
