package com.sharmarajdaksh.ratingsdataservice.resources;

import com.sharmarajdaksh.ratingsdataservice.models.Rating;
import com.sharmarajdaksh.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ratingsData")
public class RatingsResource {
    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @GetMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        return new UserRating(List.of(
                new Rating("100", 4),
                new Rating("200", 3)
        ));
    }

}
