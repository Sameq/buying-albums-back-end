package br.com.sysmap.bootcamp.repository;

import br.com.sysmap.bootcamp.domain.User;
import br.com.sysmap.bootcamp.dto.UserDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    //sei que é inútil fazer testes unitários para o repositorio, mas eu quis fazer para aprender mais
    @Autowired
    EntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    private User createUser(UserDTO userDTO){
        User newUser = new User(userDTO);
        this.entityManager.persist(newUser);
        return newUser;
    }

    @Test
    void findById() {
    }

    @Test
    void findByEmailCase2() {
        Optional<User> user = this.userRepository.findByEmail("te@gmail");
        assertThat(user.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should get User from DB when user not exists")
    void findByEmailCase1() {
        Optional<User> user = this.userRepository.findByEmail("teste@gmail");
        assertThat(user.isPresent()).isTrue();
    }


    @Test
    @DisplayName("Should get User from DB when user not exists")
    void findByPasswordCase2() {
        Optional<User> foundUser = this.userRepository.findByPassword("321");
        assertThat(foundUser.isEmpty()).isTrue();
    }
    @Test
    @DisplayName("Should get User successfully from DB")
    void findByPasswordCase1() {
        UserDTO data = new UserDTO("teste","teste@gmail", "123");
        this.createUser(data);
        Optional<User> foundUser = this.userRepository.findByPassword("123");
        assertThat(foundUser.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should get User from DB when user not exists")
    void findByNameCase2() {
        UserDTO data = new UserDTO("teste2","teste@gmail", "123");
        assertNull(this.userRepository.findByName(data.name()));
    }

    @Test
    @DisplayName("Should get User successfully from DB")
    void findByNameCase1() {
        UserDTO data = new UserDTO("teste","teste@gmail", "123");
        this.createUser(data);

        User foundUser = this.userRepository.findByName("teste");
        assertNotNull(foundUser);
    }


}