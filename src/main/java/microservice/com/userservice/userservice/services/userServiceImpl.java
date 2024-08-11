package microservice.com.userservice.userservice.services;

import microservice.com.userservice.userservice.exception.ResouceNotFoundException;
import microservice.com.userservice.userservice.feignWebClient.HotelService;
import microservice.com.userservice.userservice.model.Hotel;
import microservice.com.userservice.userservice.model.Rating;
import microservice.com.userservice.userservice.model.Users;
import microservice.com.userservice.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class userServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public Users saveUser(Users users) {

        String randomUserId = UUID.randomUUID().toString();
        users.setUserId(randomUserId);
        return userRepository.save(users);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getUsers(String userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResouceNotFoundException("User with given id is not found on server !! : " + userId));
        //String url = "http://RATING-SERVICE/ratings/users/";
        Rating[] ratingOfUsers = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{}", ratingOfUsers);
        List<Rating> ratings = Arrays.stream(ratingOfUsers).collect(Collectors.toList());
        List<Rating> ratingList = ratings.stream().map(rating -> {
            Hotel hotel = hotelService.getHotel(rating.getHotelId()); //Calling by FeignClient
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}





    /*@Override
    public Users getUsers(String userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResouceNotFoundException("User with given id is not found on server !! : " + userId));

        //http://localhost:8083/ratings/users/45b3bbbe-4c36-4f2e-b30a-a168727397ad
        //userService call to RatingService
        //ArrayList<Rating> ratingOfUsers = restTemplate.getForObject("http://localhost:8083/ratings/users/"+users.getUserId(), ArrayList.class);

        //above logic will not work because in Rating class have Hotel object.

        //localhost:8083 replace to service name
        String url = "http://RATING-SERVICE/ratings/users/";
        Rating[] ratingOfUsers = restTemplate.getForObject(url + user.getUserId(), Rating[].class);
        logger.info("{}", ratingOfUsers);
        List<Rating> ratings = Arrays.stream(ratingOfUsers).collect(Collectors.toList());
        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get hotel
            //http://localhost:8082/hotel/a849a625-bc84-4821-a94e-817f904efcfc
            //localhost:8082 replace to service name
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotel/"+rating.getHotelId(), Hotel.class);
            //Hotel hotel = forEntity.getBody();
            Hotel hotel = hotelService.getHotel(rating.getHotelId()); //Calling by FeignClient
            //logger.info("response status code: {} ", forEntity.getStatusCode());
            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());

        //users.setRatings(ratingOfUsers);
        user.setRatings(ratingList);
        return user;
    }
    }*/

