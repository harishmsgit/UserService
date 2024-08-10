package microservice.com.userservice.userservice.feignWebClient;

import jakarta.ws.rs.DELETE;
import microservice.com.userservice.userservice.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="RATING-SERVICE")
public interface RatingService {

    @PostMapping("/ratings")
    public ResponseEntity<Rating> createRating(Rating values);

    @PostMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable("ratingId") String ratingId, Rating values);

    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable("ratingId") String ratingId);

}
