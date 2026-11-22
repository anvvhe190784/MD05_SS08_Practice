package com.quickbite.practice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class PracticeApplicationTests {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void contextLoadsAndPersistsToPostgres() {
        AppUser user = new AppUser();
        user.setUsername("ci-test-user");
        AppUser saved = appUserRepository.save(user);

        assertNotNull(saved.getId());
        assertEquals("ci-test-user", appUserRepository.findById(saved.getId()).orElseThrow().getUsername());
    }
}
