package com.tiv.log.monitor.sdk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 日志消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage {

    /**
     * 应用名
     */
    private String applicationName;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 日志列表
     */
    private List<String> logList;

}
