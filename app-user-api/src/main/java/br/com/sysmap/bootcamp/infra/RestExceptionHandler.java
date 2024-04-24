package br.com.sysmap.bootcamp.infra;

import br.com.sysmap.bootcamp.infra.exceptions.CredentialsInvalid;
import br.com.sysmap.bootcamp.infra.exceptions.ExistingUser;
import br.com.sysmap.bootcamp.infra.exceptions.ExistingWallet;
import br.com.sysmap.bootcamp.infra.exceptions.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<String> userNotFoundHandler(UserNotFound exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @ExceptionHandler(ExistingUser.class)
    private ResponseEntity<String> userExisting(ExistingUser exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User existing");
    }

    @ExceptionHandler(CredentialsInvalid.class)
    private ResponseEntity<String> CredentialsInvalid(CredentialsInvalid exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Crendentials invalid");
    }

    @ExceptionHandler(ExistingWallet.class)
    private ResponseEntity<String> ExistingWallet(ExistingWallet exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet existing");
    }

}
