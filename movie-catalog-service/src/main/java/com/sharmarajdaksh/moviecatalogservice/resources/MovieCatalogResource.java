package com.sharmarajdaksh.moviecatalogservice.resources;

import com.sharmarajdaksh.moviecatalogservice.models.CatalogItem;
import com.sharmarajdaksh.moviecatalogservice.models.Movie;
import com.sharmarajdaksh.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        // Get all rated movie IDs
        // Address is resolved via Eureka
        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsData/users/" + userId, UserRating.class);

        // For each movie ID, call the movie-info-service
        return ratings.getUserRating().stream().map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                }
        ).collect(Collectors.toList());
    }
}
