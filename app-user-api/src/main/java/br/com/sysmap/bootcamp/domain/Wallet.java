package br.com.sysmap.bootcamp.domain;

import br.com.sysmap.bootcamp.dto.WalletDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "WALLET")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "points")
    private Long points;

    @Column(name = "lastUpdate")
    private LocalDateTime lastUpdate;

    @OneToOne
    private User user;



    //    public Wallet(WalletDto walletDto) {
//        this.balance = walletDto.getBalence();
//        this.points = walletDto.getPoints();
//        this.lastUpdate = walletDto.getLastUpdate();
//    }
}
