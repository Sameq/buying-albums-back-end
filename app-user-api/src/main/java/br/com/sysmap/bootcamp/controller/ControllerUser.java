package br.com.sysmap.bootcamp.controller;

import br.com.sysmap.bootcamp.domain.User;
import br.com.sysmap.bootcamp.domain.Wallet;
import br.com.sysmap.bootcamp.dto.AuthDto;
import br.com.sysmap.bootcamp.dto.UserDTO;
import br.com.sysmap.bootcamp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class ControllerUser {

    private final UserService userService;

    @Operation(summary = "Save user", description = "Endpoint to save a new user")
    @PostMapping("/create")
    public ResponseEntity<User> saveUser(@RequestBody UserDTO user){
            User newUser = new User(user);
            return ResponseEntity.ok(this.userService.saveUser(newUser));
    }
    @Operation(summary = "Authorization user token", description = "Endpoint to authorize user")
    @PostMapping("/auth")
    public ResponseEntity<AuthDto> auth(@RequestBody AuthDto authDto){
        return ResponseEntity.ok(this.userService.auth(authDto));
    }
    @Operation(summary = "Find user by id", description = "Endpoint to find user")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserId(@PathVariable Long id){
        return ResponseEntity.ok(this.userService.getUserId(id));
    }

    @PutMapping("/update")
    public ResponseEntity<User> updataUser(@RequestBody  UserDTO user ){
        return ResponseEntity.ok().build();
    }

}
