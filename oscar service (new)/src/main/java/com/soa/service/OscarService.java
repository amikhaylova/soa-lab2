package com.soa.service;

import com.soa.client.RestClient;
import com.soa.dto.MovieDTO;
import com.soa.enums.MovieGenre;

import javax.ws.rs.core.Response;
import java.util.List;

public class OscarService {

    private RestClient restClient;

    public OscarService() {
        restClient = new RestClient();
    }

    public void reallocateOscars(MovieGenre fromGenre, MovieGenre toGenre) {
        List<MovieDTO> from = restClient.getXmlMoviesByGenre(fromGenre.name()).getMovieList();
        List<MovieDTO> to = restClient.getXmlMoviesByGenre(toGenre.name()).getMovieList();

        Long overAllFromOscars = 0L;

        for (MovieDTO dto : from) {
            overAllFromOscars = overAllFromOscars + dto.getOscarsCount();
            dto.setOscarsCount(0L);
            Response response = restClient.updateMovie(dto);
        }

        while (overAllFromOscars > 0) {
            for (MovieDTO dto : to) {
                if (overAllFromOscars > 0) {
                    dto.setOscarsCount(dto.getOscarsCount() + 1);
                    restClient.updateMovie(dto);
                    overAllFromOscars--;
                } else break;
            }
        }
    }

    public void increaseOscars(Long duration, Integer oscars) {
        List<MovieDTO> movies = restClient.getXmlMoviesByDurationGreaterThan(duration).getMovieList();
        for (MovieDTO dto : movies) {
            dto.setOscarsCount(dto.getOscarsCount() + oscars);
            restClient.updateMovie(dto);
        }
    }

}
