package br.com.sysmap.bootcamp.controller;

import br.com.sysmap.bootcamp.domain.AlbumModel;
import br.com.sysmap.bootcamp.domain.entities.Album;
import br.com.sysmap.bootcamp.service.AlbumService;
import br.com.sysmap.bootcamp.service.integration.SportifyApi;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Operation(summary ="Search album", description = "Endpoint to find album")
    @GetMapping("/all/{search}")
    public ResponseEntity<List<AlbumModel>> getAlbums(@PathVariable String search) throws IOException, ParseException, SpotifyWebApiException {
        return ResponseEntity.ok(this.albumService.getAlbumModels(search));
    }

    @Operation(summary ="Buy album", description = "Endpoint to buy album")
    @PostMapping("/sale")
    public ResponseEntity<?> saleAlbum(@RequestBody Album album){
        return ResponseEntity.ok(this.albumService.saveAlbum(album));
    }

    @Operation(summary ="Collection album by user", description = "Endpoint to get collection album")
    @GetMapping("/my-collection")
    public ResponseEntity<List<Album>> collectionAlbums(){
        return ResponseEntity.ok(this.albumService.getAlbums());
    }

    @Operation(summary ="Delete user", description = "Endpoint to get collection album")
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Album> deleteAlbum(@PathVariable(value = "id") Integer id){
        this.albumService.deleteAlbum();
        return ResponseEntity.ok().build();
    }
}
