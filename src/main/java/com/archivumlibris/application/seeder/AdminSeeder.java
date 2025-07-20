package com.archivumlibris.application.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.archivumlibris.application.service.user.UserService;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.domain.model.user.UserRole;
import com.archivumlibris.domain.port.out.user.UserRepositoryPort;
import com.archivumlibris.shared.config.AdminConfig;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserService userService;
    private final AdminConfig adminConfig;
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    public AdminSeeder(
            UserService userService,
            AdminConfig adminConfig,
            UserRepositoryPort userRepositoryPort,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.adminConfig = adminConfig;
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        try {
            if (userService.findByEmail(adminConfig.getEmail()).isEmpty()) {
                User admin = new User(
                    null,
                    "Admin",
                    adminConfig.getEmail(),
                    passwordEncoder.encode(adminConfig.getPassword()),
                    UserRole.ADMIN,
                    false
                );

                userRepositoryPort.save(admin);
                log.info("ADMIN user created successfully with email: {}", adminConfig.getEmail());
            } else {
                log.info("ADMIN user already exists with email: {}", adminConfig.getEmail());
            }
        } catch (Exception e) {
            log.error("Error creating ADMIN user: {}", e.getMessage());
        }
    }
}