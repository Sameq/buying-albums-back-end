package br.com.sysmap.bootcamp.domain.listeners;

import br.com.sysmap.bootcamp.dto.WalletDto;
import br.com.sysmap.bootcamp.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RequiredArgsConstructor
@RabbitListener(queues = "WalletQueue")
public class WalletListener {
    @Autowired
    private WalletService walletService;

    @RabbitHandler
    public  void receive(WalletDto walletDto){
        System.out.println("CHEGOU NO RECEIVE: " + walletDto.getEmail());
        this.walletService.debit(walletDto);
//        log.info("debiting wallet: {}", walletDto);
    }
}
