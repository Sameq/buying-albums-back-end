package br.com.sysmap.bootcamp.repository;

import br.com.sysmap.bootcamp.domain.User;
import br.com.sysmap.bootcamp.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findByUser(User user);
}
