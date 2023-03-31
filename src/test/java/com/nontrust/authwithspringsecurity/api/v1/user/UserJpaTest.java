package com.nontrust.authwithspringsecurity.api.v1.user;

import com.nontrust.authwithspringsecurity.api.v1.enums.Role;
import com.nontrust.authwithspringsecurity.api.v1.repository.UsersRepository;
import com.nontrust.authwithspringsecurity.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Users JPA 테스트")
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class UserJpaTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("테스트 User DB 생성")
    @Timeout(3)
    @BeforeEach
    public void setUsers() {
        log.warn("Save User");

        // given
        LinkedList<Users> insertUsersList = IntStream.rangeClosed(1, 100)
//                .mapToObj((i) -> Users("TestUser"+i, "password"+i))
                .mapToObj((i) -> Users
                        .builder()
                            .email("TestUser"+i)
                            .password(passwordEncoder.encode("password"+i))
                            .role(Collections.singletonList(Role.USER))
                        .build()
                ).collect(Collectors.toCollection(LinkedList::new));
        usersRepository.saveAll(insertUsersList);

        assertEquals(insertUsersList.size(), 100);
    }

    @DisplayName("테스트 DB 삭제")
    @Timeout(3)
    @AfterEach
    public void deleteUsers() {
        // given at  @BeforeEach
        log.warn("Delete user");

        // when
        LinkedList<Users> deleteUserList = IntStream.rangeClosed(1, 100)
                .mapToObj((i) -> usersRepository.findByEmail("TestUser"+i).get())
                .collect(Collectors.toCollection(LinkedList::new));

        // then
        assertEquals(deleteUserList.size(), 100);

        // close
        usersRepository.deleteAllInBatch(deleteUserList);
    }

    @DisplayName("유저 선택 테스트")
    @Test
    public void selectUsers() {
        // given at  @BeforeEach
        log.warn("Select users");

        // when
        LinkedList<Users> usersList = IntStream.rangeClosed(1, 100)
                .mapToObj((i) -> usersRepository.findByEmail("TestUser"+i).get())
                .collect(Collectors.toCollection(LinkedList::new));

        // then
        assertEquals(usersList.size(), 100);
    }
    @DisplayName("유저 롤 테스트")
    @Test
    public void setUsersRole() {
        // given at @BeforeEach
        log.warn("Set Role Users");
        log.warn("Role Common User 1-50");

        // when
        // default user Role
        LinkedList<Users> commonUsersList = IntStream.rangeClosed(1, 50)
                .mapToObj((i) -> usersRepository.findByEmail("TestUser"+i).get())
                .collect(Collectors.toCollection(LinkedList::new));

        log.warn("commonUsersList {} ", commonUsersList.getFirst().getRole());

        usersRepository.saveAll(commonUsersList);

        log.warn("Role Manager User 51-90");
        // Manager Role Update
        LinkedList<Users> managerUsersList = IntStream.rangeClosed(51, 90)
                .mapToObj((i) -> usersRepository.findByEmail("TestUser"+i).get())
                .map((i) -> i.addRole(Role.MANAGER))
                .collect(Collectors.toCollection(LinkedList::new));

        usersRepository.saveAll(managerUsersList);

        log.warn("Role Admin User 51-90");
        // Admin Role Update
        LinkedList<Users> adminUsersList = IntStream.rangeClosed(91, 100)
                .mapToObj((i) -> usersRepository.findByEmail("TestUser"+i).get())
                .map((i) -> i.addRole(Role.MANAGER))
                .map((i) -> i.addRole(Role.ADMIN))
                .collect(Collectors.toCollection(LinkedList::new));

        usersRepository.saveAll(adminUsersList);

        Optional<Users> commonUsers =  usersRepository.findByEmail("TestUser1");
        Optional<Users> managerUsers =  usersRepository.findByEmail("TestUser51");
        Optional<Users> adminUsers =  usersRepository.findByEmail("TestUser91");


        // then
        // test common user Role
        assertTrue(commonUsers.get().hasRole(Role.USER));
        assertFalse(commonUsers.get().hasRole(Role.MANAGER));
        assertFalse(commonUsers.get().hasRole(Role.ADMIN));

        // test manager user Role
        assertTrue(managerUsers.get().hasRole(Role.USER));
        assertTrue(managerUsers.get().hasRole(Role.MANAGER));
        assertFalse(managerUsers.get().hasRole(Role.ADMIN));

        // test admin user Role
        assertTrue(adminUsers.get().hasRole(Role.USER));
        assertTrue(adminUsers.get().hasRole(Role.MANAGER));
        assertTrue(adminUsers.get().hasRole(Role.ADMIN));

    }


    @Configuration
    public class SecurityConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            String saltKey = "leaked_salt";

            return new BCryptPasswordEncoder(10, new SecureRandom(saltKey.getBytes()));
        }
    }
}

