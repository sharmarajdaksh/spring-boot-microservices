package com.sharmarajdaksh.moviecatalogservice.services;

import com.sharmarajdaksh.moviecatalogservice.models.Rating;
import com.sharmarajdaksh.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RatingsData {

    @Autowired
    RestTemplate restTemplate;

    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratingsData/users/" + userId, UserRating.class);
    }

    public UserRating getFallbackRating() {
        List<Rating> ratings = List.of(new Rating("300", 4));
        return new UserRating(ratings);
    }
}
