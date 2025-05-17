package com.tiv.log.monitor.sdk.push.impl;

import com.tiv.log.monitor.sdk.config.LogMonitorRedisProperties;
import com.tiv.log.monitor.sdk.model.LogMessage;
import com.tiv.log.monitor.sdk.push.LogPushService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

/**
 * redis日志推送服务实现
 */
@Slf4j
public class RedisLogPushServiceImpl implements LogPushService {

    private RedissonClient redissonClient;

    private static final String LOG_MONITOR_SDK_TOPIC = "log-monitor-sdk-topic";

    @Override
    public synchronized void init(String host, int port) {
        if (redissonClient != null && !redissonClient.isShutdown()) {
            return;
        }

        Config config = new Config();
        config.setCodec(JsonJacksonCodec.INSTANCE);
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setConnectionPoolSize(LogMonitorRedisProperties.poolSize)
                .setConnectionMinimumIdleSize(LogMonitorRedisProperties.minIdleSize)
                .setIdleConnectionTimeout(LogMonitorRedisProperties.idleTimeout)
                .setConnectTimeout(LogMonitorRedisProperties.connectTimeout)
                .setRetryAttempts(LogMonitorRedisProperties.retryAttempts)
                .setRetryInterval(LogMonitorRedisProperties.retryInterval)
                .setPingConnectionInterval(LogMonitorRedisProperties.pingInterval)
                .setKeepAlive(LogMonitorRedisProperties.keepAlive);

        this.redissonClient = Redisson.create(config);
    }

    @Override
    public void push(LogMessage logMessage) {
        try {
            RTopic topic = redissonClient.getTopic(LOG_MONITOR_SDK_TOPIC);
            topic.publish(logMessage);
        } catch (Exception e) {
            log.error("推送日志消息失败", e);
        }
    }

}
