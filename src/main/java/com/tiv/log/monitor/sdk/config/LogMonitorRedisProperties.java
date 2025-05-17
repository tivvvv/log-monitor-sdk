package com.tiv.log.monitor.sdk.config;

/**
 * 日志监控redis配置属性
 */
public interface LogMonitorRedisProperties {

    /**
     * 连接池的大小
     */
    int poolSize = 64;

    /**
     * 连接池的最小空闲连接数
     */
    int minIdleSize = 10;

    /**
     * 连接的最大空闲时间/ms
     */
    int idleTimeout = 10000;

    /**
     * 连接超时时间/ms
     */
    int connectTimeout = 10000;

    /**
     * 连接重试次数
     */
    int retryAttempts = 3;

    /**
     * 连接重试间隔/ms
     */
    int retryInterval = 1000;

    /**
     * 定期检查连接可用性间隔/ms
     */
    int pingInterval = 0;

    /**
     * 是否保持长连接
     */
    boolean keepAlive = true;

}
