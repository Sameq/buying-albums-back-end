package br.com.sysmap.bootcamp.service;

import br.com.sysmap.bootcamp.domain.User;
import br.com.sysmap.bootcamp.domain.Wallet;
import br.com.sysmap.bootcamp.dto.AuthDto;
import br.com.sysmap.bootcamp.dto.UserDTO;
import br.com.sysmap.bootcamp.infra.exceptions.CredentialsInvalid;
import br.com.sysmap.bootcamp.infra.exceptions.ExistingUser;
import br.com.sysmap.bootcamp.infra.exceptions.UserNotFound;
import br.com.sysmap.bootcamp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;

    public User saveUser(User user) {
        Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new ExistingUser();
        }
        user = user.toBuilder().password(this.passwordEncoder.encode(user.getPassword())).build();
        user = this.userRepository.save(user);
        Wallet wallet = Wallet.builder()
                .balance(BigDecimal.ZERO)
                .points(0L)
                .lastUpdate(LocalDateTime.now())
                .user(user)
                .build();
        this.walletService.saveWallet(wallet);
        return user;
    }

    public List<User> getAllUsers() {
        try {
            return this.userRepository.findAll();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    public Optional<User> getUserId(Long id) {
        try {
            return this.userRepository.findById(id);
        }catch (UserNotFound e){
            throw new UserNotFound();
        }
    }

//    public User updateUser(UserDTO userDTO) {

//        User userExistent = userRepository.findById(id).orElse(null);
//        if (userExistent == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        userExistent.setEmail(userDTO.email());
//        userExistent.setName(userDTO.name());
//        userExistent.setPassword(userDTO.password());
//        return this.userRepository.save(userExistent);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = this.userRepository.findByPassword(username);
        return userOptional.map(user -> new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                new ArrayList<GrantedAuthority>())).orElseThrow(() -> new UsernameNotFoundException("User not Found" + username));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFound("User not found"));
    }

    public User findByName(String name){
        try {
            return this.userRepository.findByName(name);
        }catch (UserNotFound e){
            throw new UserNotFound();
        }
    }

    public AuthDto auth(AuthDto authDto) {

        User user = this.findByEmail(authDto.getEmail());

        if (user == null){
            throw new UserNotFound();
        }
        if (!this.passwordEncoder.matches(authDto.getPassword(), user.getPassword())) {
            throw new CredentialsInvalid();
        }
        StringBuilder password = new StringBuilder().append(user.getPassword()).append(":").append(user.getPassword());

        return AuthDto.builder().email(user.getEmail()).token(
                Base64.getEncoder().withoutPadding().encodeToString(password.toString().getBytes())
        ).id(user.getId()).build();
    }
}