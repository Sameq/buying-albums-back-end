package br.com.sysmap.bootcamp.service.integration;

import br.com.sysmap.bootcamp.domain.AlbumModel;
import br.com.sysmap.bootcamp.domain.mapper.AlbumMapper;
import com.neovisionaries.i18n.CountryCode;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class SportifyApi {

    private se.michaelthelin.spotify.SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("69b268e3412e4a1ca50c182d3dfc5f11")
            .setClientSecret("8c55f7f1555846e5b54b2a858ac1bc64")
            .build();

    public List<AlbumModel> getAlbums(String seach) throws IOException, ParseException, SpotifyWebApiException {

        spotifyApi.setAccessToken(getToken());

        return AlbumMapper.INSTANCE.toModel(spotifyApi.searchAlbums(seach).market(CountryCode.BR)
                .limit(30)
                .build().execute()
                .getItems())
                .stream().peek(album -> album.setValue(BigDecimal.valueOf((Math.random() * ((100.00 - 12.00) + 1)) + 12.00)
                        .setScale(2, BigDecimal.ROUND_HALF_UP))).toList();
    }

    public String getToken() throws IOException, ParseException, SpotifyWebApiException {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        return clientCredentialsRequest.execute().getAccessToken();
    }

}
