package com.example.demo.scheduler;

import com.example.demo.entity.InvalidatedToken;
import com.example.demo.enums.ErrorCode;
import com.example.demo.exception.AppException;
import com.example.demo.repository.InvalidatedTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InvalidatedTokenScheduler {
    InvalidatedTokenRepository invalidatedTokenRepository;

    // run every 1 minute
    @Scheduled(cron = "0 * * * * ?")
    public void cleanUpFirstRecord() {
        log.info("Cron Start...");
        try {
            // Tìm bản ghi đầu tiên
            List<InvalidatedToken> tokens = invalidatedTokenRepository.findAllByMinExpiryTime();

            if (!tokens.isEmpty()) {
                InvalidatedToken tokenToDelete = tokens.getFirst();
                invalidatedTokenRepository.deleteById(tokenToDelete.getId());
                log.info("Delete Token with ID: {}", tokenToDelete.getId());
            } else {
                log.info("No Token is Deleted.");
            }
        } catch (AppException e) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
    }
}
