package com.sharmarajdaksh.moviecatalogservice.services;

import com.sharmarajdaksh.moviecatalogservice.models.CatalogItem;
import com.sharmarajdaksh.moviecatalogservice.models.Movie;
import com.sharmarajdaksh.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CircuitBreakerFactory circuitBreakerFactory;

    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallbackCatalog() {
        return new CatalogItem("No movie", "", 0);
    }
}

