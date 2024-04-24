package br.com.sysmap.bootcamp.service;

import br.com.sysmap.bootcamp.domain.User;
import br.com.sysmap.bootcamp.domain.Wallet;
import br.com.sysmap.bootcamp.dto.WalletDto;
import br.com.sysmap.bootcamp.infra.exceptions.ExistingUser;
import br.com.sysmap.bootcamp.infra.exceptions.ExistingWallet;
import br.com.sysmap.bootcamp.infra.exceptions.UserNotFound;
import br.com.sysmap.bootcamp.repository.UserRepository;
import br.com.sysmap.bootcamp.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private  Map<DayOfWeek, Long> points;


    public void debit(WalletDto walletDto) {
        Optional<User> optionalUser = this.userRepository.findByEmail(walletDto.getEmail());
        User user = optionalUser.orElseThrow(() -> new UserNotFound("User not found"));

        Wallet wallet = this.walletRepository.findByUser(user);

        wallet.setBalance(wallet.getBalance().subtract(walletDto.getValue()));

        Long points = calculatePoints();
        wallet.setPoints(wallet.getPoints() + points);

        walletRepository.save(wallet);
    }

    public Long calculatePoints(){
        Map<DayOfWeek, Long> pointsMap = Map.of(
                DayOfWeek.SUNDAY, 25L,
                DayOfWeek.MONDAY, 7L,
                DayOfWeek.TUESDAY, 6L,
                DayOfWeek.WEDNESDAY, 2L,
                DayOfWeek.THURSDAY, 10L,
                DayOfWeek.FRIDAY, 15L,
                DayOfWeek.SATURDAY, 20L
        );
        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
        Long points = pointsMap.getOrDefault(dayOfWeek, 0L);

        return points;
    }

    public void saveWallet(Wallet wallet){
        this.walletRepository.save(wallet);
    }
    public Wallet addFromWallet(User user, BigDecimal value){
        Wallet wallet = getWallet(user);
        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal newBalance = currentBalance.add(value);
        wallet.setBalance(newBalance);
       return this.walletRepository.save(wallet);
    }

    public Wallet getWallet(User user){
        return this.walletRepository.findByUser(user);
    }

}
