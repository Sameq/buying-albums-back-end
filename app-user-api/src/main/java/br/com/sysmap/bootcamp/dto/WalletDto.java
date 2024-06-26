package br.com.sysmap.bootcamp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class WalletDto implements Serializable {
//    private String teste;
    private String email;
    private BigDecimal value;

    public WalletDto(String email, BigDecimal value) {
        this.email = email;
        this.value = value;
    }

    public WalletDto() {
    }
}
