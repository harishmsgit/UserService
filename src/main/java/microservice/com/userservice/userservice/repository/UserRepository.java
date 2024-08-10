package microservice.com.userservice.userservice.repository;

import microservice.com.userservice.userservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
}
