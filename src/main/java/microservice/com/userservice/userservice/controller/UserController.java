package microservice.com.userservice.userservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import microservice.com.userservice.userservice.model.Users;
import microservice.com.userservice.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users users){
       Users users1 = userService.saveUser(users);
       return ResponseEntity.status(HttpStatus.CREATED).body(users1);
    }

    @GetMapping("/{userId}")
    //@CircuitBreaker(name="ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    //@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<Users> getOneUser(@PathVariable String userId){
        logger.info("Fetch single user info");
        Users user = userService.getUsers(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> allUser = userService.getAllUsers();
        return ResponseEntity.ok(allUser);
    }


    int retryCount=1;
    public ResponseEntity<Users> ratingHotelFallback(String userId, Exception ex){
        ex.printStackTrace();
        //logger.info("Service is DOWN ,fallback is executed: "+ ex.getMessage());
        logger.info("Get single User Handler: UserController");
        logger.info("retry count: " + retryCount);
        retryCount++;
        Users users = Users.builder()
                .email("harish@gmail.com")
                .name("Harish")
                .about("This is dummy dummy data created because the service is down")
                .build();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
