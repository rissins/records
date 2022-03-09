package com.rissins.records.utils.requestlimit;

import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class Throttle2 {
    public static final int TEM_MINUTE = 599990;
    public static final int MAX_COUNT = 20;
    public static final int FIVE_SECOND = 5990;

    private static boolean resultValue = true;
    private static int count = 0;
    private static RateLimiter rateLimiter;
    private static long startTime = System.currentTimeMillis();
    private static Map<Long, Boolean> statusByUser = new HashMap<>();
    private static Map<Integer, Boolean> validationByUser = new HashMap<>();


    public boolean setting(Long eventId) {
        statusByUser.put(eventId, true);
        rateLimiter = RateLimiter.create(2);

        if (rateLimiter.tryAcquire()) {
            if (statusByUser.get(eventId)) {
                count++;
                if (count >= MAX_COUNT) {
                    count = MAX_COUNT;
                    log.info("카운트 횟수 초과됨 count = {}", count);
                    statusByUser.replace(eventId, false);
                }
                if (System.currentTimeMillis() - startTime > TEM_MINUTE) {
                    count = 0;
                    startTime = System.currentTimeMillis();
                }
            } else {
                if (System.currentTimeMillis() - startTime > TEM_MINUTE) {
                    count = 0;
                    startTime = System.currentTimeMillis();
                    statusByUser.replace(eventId, true);
                }
            }
            log.info("resultValue = {} 상태", resultValue);
        }
        return statusByUser.get(eventId);
    }
}
