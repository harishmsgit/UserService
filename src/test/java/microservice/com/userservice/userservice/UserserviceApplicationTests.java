package microservice.com.userservice.userservice;

import microservice.com.userservice.userservice.feignWebClient.RatingService;
import microservice.com.userservice.userservice.model.Rating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class UserserviceApplicationTests {

	@Autowired
	RatingService ratingService;

	@Test
	void contextLoads() {
	}

	@Test
	public void createRating_Test(){
		Rating rating = Rating.builder().rating(10).userId("").hotelId("").feedback("Best hotel").build();
		ResponseEntity<Rating> ratingResponseEntity = ratingService.createRating(rating);
		//ratingResponseEntity.getBody();
		System.out.println("New Rating created");
	}

}
