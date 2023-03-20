package com.nontrust.authwithspringsecurity.api.v1.user;

import com.nontrust.authwithspringsecurity.api.v1.repository.UsersRepository;
import com.nontrust.authwithspringsecurity.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Users JPA 테스트")
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserJpaTest {

    @Autowired
    private UsersRepository usersRepository;

    @DisplayName("테스트 User DB 생성")
    @Timeout(3)
    @BeforeEach
    public void setUsers(){
        log.warn("Save User");
        LinkedList<Users> insertUsersList = IntStream.rangeClosed(1, 100)
                .mapToObj((i) -> new Users("TestUser"+i, "password"+i))
                .collect(Collectors.toCollection(LinkedList::new));
        usersRepository.saveAll(insertUsersList);

        assertTrue(insertUsersList.size() == 100);
    }

    @DisplayName("테스트 DB 삭제")
    @Timeout(3)
    @AfterEach
    public void deleteUsers(){
        log.warn("Delete user");
        LinkedList<Users> deleteUserList = IntStream.rangeClosed(1, 100)
                .mapToObj((i) -> usersRepository.findByEmailAndPassword("TestUser"+i, "password"+i).get())
                .collect(Collectors.toCollection(LinkedList::new));

        assertTrue(deleteUserList.size() == 100);
        usersRepository.deleteAllInBatch(deleteUserList);
    }

    @Test
    public void selectUsers(){
        log.warn("Select user");
        LinkedList<Users> usersList = IntStream.rangeClosed(1, 100)
                .mapToObj((i) -> usersRepository.findByEmailAndPassword("TestUser"+i, "password"+i).get())
                .collect(Collectors.toCollection(LinkedList::new));

        assertTrue(usersList.size() == 100);
    }
}

