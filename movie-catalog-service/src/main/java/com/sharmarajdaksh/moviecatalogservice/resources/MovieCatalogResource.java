package com.sharmarajdaksh.moviecatalogservice.resources;

import com.sharmarajdaksh.moviecatalogservice.models.CatalogItem;
import com.sharmarajdaksh.moviecatalogservice.models.UserRating;
import com.sharmarajdaksh.moviecatalogservice.services.MovieInfo;
import com.sharmarajdaksh.moviecatalogservice.services.RatingsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    RatingsData ratingsData;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

        UserRating ratings = circuitBreaker.run(() -> ratingsData.getUserRating(userId), throwable -> ratingsData.getFallbackRating());


        // For each movie ID, call the movie-info-service
        return ratings.getUserRating().stream().map(rating ->
                circuitBreaker.run(() -> movieInfo.getCatalogItem(rating), throwable -> movieInfo.getFallbackCatalog())
        ).collect(Collectors.toList());
    }

    public List<CatalogItem> getFallbackCatalog() {
        return List.of(new CatalogItem("No movie", "", 0));
    }

}
