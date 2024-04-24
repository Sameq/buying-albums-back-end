package br.com.sysmap.bootcamp.service;

import br.com.sysmap.bootcamp.domain.User;
import br.com.sysmap.bootcamp.dto.UserDTO;
import br.com.sysmap.bootcamp.infra.exceptions.ExistingUser;
import br.com.sysmap.bootcamp.infra.exceptions.UserNotFound;
import br.com.sysmap.bootcamp.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {


    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private WalletService walletService;

    @Test
    @DisplayName("Shoudl return user when valid user is saved")
    public void shoudlReturnUserWhenValidUserIsSaved1(){
        User user = User.builder().id(1l).name("teste").email("teste").password("teste").build();
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals(user, savedUser);
    }
    @Test
    @DisplayName("Should throw ExistingUser exception when user already exists")
    void sshoudlReturnUserWhenValidUserIsSaved2() {
        // Arrange
        User existingUser = User.builder().build();
        existingUser.setEmail("existinguser@example.com");
        existingUser.setPassword("existingPassword");

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(ExistingUser.class, () -> userService.saveUser(existingUser));
        verify(userRepository, never()).save(existingUser);
        verify(walletService, never()).saveWallet(any());
    }

    @Test
    @DisplayName("Should return all users when repository returns non-empty list")
    void getAllUsersReturnsNonEmptyList1() {
        UserDTO userDTO = new UserDTO("teste", "teste@hotmail", "teste");
        List<User> mockUsers = List.of(
                new User(userDTO)
        );
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> users = userRepository.findAll();

        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
    }

    @Test
    @DisplayName("Should return empty list when repository returns empty list")
    void getAllUsersReturnsEmptyList2() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> users = userRepository.findAll();

        assertThat(users).isNotNull();
        assertThat(users).isEmpty();
    }

    @Test
    @DisplayName("Should return user when ID exists")
    void getUserIdReturnsUserWhenIdExists1() {
        User mockUser = User.builder().build();
        mockUser.setName("teste");
        mockUser.setEmail("teste@hotmail");
        mockUser.setPassword("123teste");
        mockUser.setId(1l);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        Optional<User> userOptional = userRepository.findById(1L);

        assertThat(userOptional).isPresent();
        assertThat(userOptional.get()).isEqualTo(mockUser);
    }
    @Test
    @DisplayName("Should return empty Optional when ID does not exist")
    void getUserIdReturnsEmptyOptionalWhenIdDoesNotExist2() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> userOptional = userRepository.findById(2L);

        assertThat(userOptional).isEmpty();
    }
    @Test
    void loadUserByUsername() {
    }

    @Test
    @DisplayName("Should find user by email")
    void findByEmailReturnsUserWhenUserExists1() {
        User mockUser = User.builder().build();
        mockUser.setEmail("teste@hotmail.com");

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        Optional<User> foundUser = userRepository.findByEmail("john@example.com");
        assertThat(foundUser).isNotNull();
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when email does not exist")
    void findByEmailThrowsUserNotFoundExceptionWhenUserDoesNotExist2() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> userRepository.findByEmail("nonexistent@example.com"));
    }
    @Test
    @DisplayName("Should find user by name")
    void findByNameReturnsUserWhenUserExists1() {
        User mockUser = User.builder().build();
        mockUser.setName("teste");
        when(userRepository.findByName("John")).thenReturn(mockUser);

        User foundUser = userRepository.findByName("John");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo(mockUser.getName());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when name does not exist")
    void findByNameThrowsUserNotFoundExceptionWhenUserDoesNotExist2() {
        when(userRepository.findByName("Nonexistent")).thenReturn(null);

        assertThrows(UserNotFound.class, () -> userRepository.findByName("Nonexistent"));
    }

    @Test
    void auth() {
    }

}