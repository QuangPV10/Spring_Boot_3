package com.example.demo.configuration;

import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

// class này dùng dđể kởi tạo account admin mỗi khi application run -> dùng ApplicationRunner

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(prefix = "spring", value = "datasource.driverClassName", havingValue = "org.hibernate.dialect.PostgreSQLDialect")
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        log.info("run applicationRunner");
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                Set<String> roles = new HashSet<>();

                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
//                        .roles(roles)
                        .build();
                userRepository.save(user);

                log.warn("Admin User has been created with default password: admin. Please change it");
            }
        };
    }
}
