package com.tiv.log.monitor.sdk.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.tiv.log.monitor.sdk.model.LogMessage;
import com.tiv.log.monitor.sdk.push.LogPushService;
import com.tiv.log.monitor.sdk.push.impl.RedisLogPushServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomAppender<E> extends AppenderBase<E> {

    /**
     * 应用名
     */
    private String applicationName;

    /**
     * 包名,只采集指定包下的日志
     */
    private String packageName;

    /**
     * redis host
     */
    private String host;

    /**
     * redis port
     */
    private int port;

    /**
     * 日志推送服务
     */
    private final LogPushService logPushService = new RedisLogPushServiceImpl();

    @Override
    protected void append(E eventObject) {
        // 初始化
        logPushService.init(host, port);

        // 获取日志
        if (!(eventObject instanceof ILoggingEvent)) {
            return;
        }
        ILoggingEvent event = (ILoggingEvent) eventObject;

        String className = "unknown";
        String methodName = "unknown";
        StackTraceElement[] callerData = event.getCallerData();
        if (callerData != null && callerData.length > 0) {
            StackTraceElement stackTraceElement = callerData[0];
            className = stackTraceElement.getClassName();
            methodName = stackTraceElement.getMethodName();
        }

        if (!className.startsWith(packageName)) {
            return;
        }

        // 构造日志消息
        LogMessage logMessage = new LogMessage(applicationName, className, methodName, Arrays.asList(event.getFormattedMessage().split(" ")));
        // 推送日志消息
        logPushService.push(logMessage);
    }

}
