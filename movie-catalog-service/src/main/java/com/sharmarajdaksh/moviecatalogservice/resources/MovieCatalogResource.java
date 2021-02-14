package com.sharmarajdaksh.moviecatalogservice.resources;

import com.sharmarajdaksh.moviecatalogservice.models.CatalogItem;
import com.sharmarajdaksh.moviecatalogservice.models.Movie;
import com.sharmarajdaksh.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        WebClient.Builder builder = WebClient.builder();

        // Get all rated movie IDs
        List<Rating> ratings = List.of(
                new Rating("1234", 4),
                new Rating("5678", 3)
        );

        // For each movie ID, call the movie-info-service
        return ratings.stream().map(rating -> {
                    Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8081/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();

                    return new CatalogItem(movie.getName(), "Test", rating.getRating());
                }
        ).collect(Collectors.toList());
    }
}
