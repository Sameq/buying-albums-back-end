package br.com.sysmap.bootcamp.service;

import br.com.sysmap.bootcamp.domain.AlbumModel;
import br.com.sysmap.bootcamp.domain.entities.Album;
import br.com.sysmap.bootcamp.domain.entities.User;
import br.com.sysmap.bootcamp.dto.WalletDto;
import br.com.sysmap.bootcamp.repository.AlbumRepository;
import br.com.sysmap.bootcamp.service.integration.SportifyApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class AlbumService {
    private final Queue queue;
    private final RabbitTemplate rabbitTemplate;
    private final SportifyApi sportifyApi;
    private final UserService userService;
    private final AlbumRepository albumRepository;

    public List<AlbumModel> getAlbumModels(String search) throws IOException, ParseException, SpotifyWebApiException {
        return this.sportifyApi.getAlbums(search);
    }

    public List<Album> getAlbums(){
        User user = getUser();
        return this.albumRepository.findAllByUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Album saveAlbum(Album album){
        album.setUser(getUser());
        Album albumSaved = this.albumRepository.save(album);
        WalletDto walletDto = new WalletDto(albumSaved.getUser().getEmail(), albumSaved.getValue());

        this.rabbitTemplate.convertAndSend(queue.getName(), walletDto);

        return albumSaved;
    }

    public void deleteAlbum(){
        User user = getUser();
        this.albumRepository.deleteAlbumById(user.getId());
    }

    private User getUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userService.findByEmail(username);
    }

}
