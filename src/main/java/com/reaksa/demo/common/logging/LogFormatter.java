package com.reaksa.demo.common.logging;

import org.springframework.stereotype.Component;

@Component
public class LogFormatter {
    private static final String LOG_FORMAT = "%s:%s | target=%s | method=%s," +
            "startTime=%s,endTime=%s," +
            "executionTime=%sms";

    public String logRequest(String requestId, String target, String method, Long startTime) {
        return String.format(LOG_FORMAT,
                "REQUEST",
                requestId,
                target,
                method,
                startTime
                );
    }

    public String logResponse(String requestId, String target, String method, Long startTime,  Long endTime) {
        return String.format(LOG_FORMAT,
                "RESPONSE",
                requestId,
                target,
                method,
                endTime - startTime
                );
    }

    public String logError(String requestId, String target, String method, Long startTime,  Long endTime) {
        return String.format(LOG_FORMAT,
                "RESPONSE",
                requestId,
                target,
                method,
                endTime - startTime
                );
    }

}
