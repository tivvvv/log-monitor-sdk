package com.tiv.log.monitor.sdk.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.tiv.log.monitor.sdk.model.LogMessage;
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

    @Override
    protected void append(E eventObject) {
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
    }

}
