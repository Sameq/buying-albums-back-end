package br.com.sysmap.bootcamp.controller;

import br.com.sysmap.bootcamp.domain.User;
import br.com.sysmap.bootcamp.domain.Wallet;
import br.com.sysmap.bootcamp.repository.UserRepository;
import br.com.sysmap.bootcamp.service.UserService;
import br.com.sysmap.bootcamp.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class ControllerWallet {

    private final UserRepository userRepository;
    private final UserService userService;
    private final WalletService walletService;

    @Operation(summary = "Add credit in wallet the user", description = "Endpoint to add credit")
    @PostMapping("/credit/{value}")
    public ResponseEntity<?> credit(@PathVariable(value = "value") BigDecimal value){
        String name = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.findByName(name);
        return ResponseEntity.ok(this.walletService.addFromWallet(user, value));
    }
    @Operation(summary = "Get wallet the user", description = "Endpoint to get wallet user")
    @GetMapping("/getWallet")
    public ResponseEntity<Wallet> getWallet(){
        String name = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.findByName(name);
        Wallet wallet = this.walletService.getWallet(user);
        return ResponseEntity.ok().body(wallet);
    }
}
