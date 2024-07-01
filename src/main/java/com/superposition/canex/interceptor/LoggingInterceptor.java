package com.superposition.canex.interceptor;

import com.superposition.canex.domain.entity.AccessLog;
import com.superposition.canex.domain.mapper.AccessLogMapper;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private final AccessLogMapper accessLogMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ipAddress = request.getRemoteAddr();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        LocalDateTime now = LocalDateTime.now();

        log.info("IP Address: {} | URI: {} | Method: {} | Timestamp: {}", ipAddress, uri, method, now);

        AccessLog accessLog = AccessLog.builder()
                .ipAddress(ipAddress)
                .uri(uri)
                .method(method)
                .now(now)
                .build();
        accessLogMapper.save(accessLog);

        return true;
    }
}
