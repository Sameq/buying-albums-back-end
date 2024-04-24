package br.com.sysmap.bootcamp.service;

import br.com.sysmap.bootcamp.domain.User;
import br.com.sysmap.bootcamp.domain.Wallet;
import br.com.sysmap.bootcamp.dto.WalletDto;
import br.com.sysmap.bootcamp.repository.UserRepository;
import br.com.sysmap.bootcamp.repository.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest

class WalletServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;
    @InjectMocks
    private WalletService walletService;


    @Test
    @DisplayName("Metadata for when the user buys an album will be deducted from the wallet")
    void debitShouldUpdateWalletBalanceAndPoints() {
        User user = User.builder().build();
        user.setEmail("teste@hotmail.com");

        WalletDto walletDto = new WalletDto();
        walletDto.setEmail("teste@hotmail.com");
        walletDto.setValue(BigDecimal.TEN);

        Wallet wallet = Wallet.builder().build();
        wallet.setBalance(BigDecimal.valueOf(100));
        wallet.setUser(user);
        wallet.setPoints(0l);

        walletService.debit(walletDto);

        verify(walletRepository).save(wallet);
        assertEquals(BigDecimal.valueOf(90), wallet.getBalance());

        Long expectedPoints = walletService.calculatePoints();
        assertEquals(expectedPoints, wallet.getPoints());
    }

    @Test
    @DisplayName("Metadata to clacular the user's points")
    void calculatePoints() {
        Map<DayOfWeek, Long> expectedPointsMap = Map.of(
                DayOfWeek.SUNDAY, 25L,
                DayOfWeek.MONDAY, 7L,
                DayOfWeek.TUESDAY, 6L,
                DayOfWeek.WEDNESDAY, 2L,
                DayOfWeek.THURSDAY, 10L,
                DayOfWeek.FRIDAY, 15L,
                DayOfWeek.SATURDAY, 20L
        );
        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
    }

    @Test
    @DisplayName("Save User's wallet")
    void saveWalletCallsRepositorySaveMethod() {

        User user = User.builder().build();
        user.setName("teste");
        user.setEmail("teste@hotmail.com");
        user.setPassword("teste");

        Wallet wallet = Wallet.builder().build();
        wallet.setLastUpdate(LocalDateTime.now());
        wallet.setBalance(BigDecimal.valueOf(100));
        wallet.setPoints(0l);
        wallet.setUser(user);

        this.walletService.saveWallet(wallet);

        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    @DisplayName("Add money to your wallet")
    void addFromWalletUpdatesBalanceAndCallsRepositorySaveMethod() {
        User user = User.builder().build();
        user.setName("teste");
        user.setPassword("teste");
        user.setEmail("teste@hotmail");
        user.setId(1l);

        Wallet wallet = Wallet.builder().build();
        wallet.setLastUpdate(LocalDateTime.now());
        wallet.setPoints(0l);
        wallet.setBalance(BigDecimal.valueOf(100l));
        wallet.setUser(user);

        when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        when(walletRepository.findByUser(user)).thenReturn(wallet);

        Wallet updatedWallet = walletService.addFromWallet(user, wallet.getBalance());
        verify(walletRepository, times(1)).save(updatedWallet);

        BigDecimal expectedBalance = wallet.getBalance().add(wallet.getBalance());
        assertEquals(expectedBalance, updatedWallet.getBalance());
    }

    @Test
    @DisplayName("Search User wallet")
    void getWalletReturnsCorrectWallet() {

        User user = User.builder().build();
        user.setName("teste");
        user.setPassword("teste");
        user.setEmail("teste@hotmail");
        user.setId(1l);

        Wallet wallet = Wallet.builder().build();
        wallet.setLastUpdate(LocalDateTime.now());
        wallet.setPoints(0l);
        wallet.setBalance(BigDecimal.valueOf(100l));
        wallet.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(walletRepository.findByUser(user)).thenReturn(wallet);

        Wallet retrievedWallet = walletService.getWallet(user);
        verify(walletRepository).findByUser(user);
        assertEquals(wallet, retrievedWallet);
    }
}