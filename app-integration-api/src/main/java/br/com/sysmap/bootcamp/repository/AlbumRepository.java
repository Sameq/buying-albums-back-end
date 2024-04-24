package br.com.sysmap.bootcamp.repository;

import br.com.sysmap.bootcamp.domain.AlbumModel;
import br.com.sysmap.bootcamp.domain.entities.Album;
import br.com.sysmap.bootcamp.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByUser(User user);

    Album deleteAlbumById(Long id);
}
