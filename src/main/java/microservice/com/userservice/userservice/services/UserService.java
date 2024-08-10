package microservice.com.userservice.userservice.services;

import microservice.com.userservice.userservice.model.Users;

import java.util.List;

public interface UserService {

    Users saveUser(Users users);

    List<Users> getAllUsers();

    Users getUsers(String userId);


}
