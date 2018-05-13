package pt.ulisboa.tecnico.cmu.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pt.ulisboa.tecnico.cmu.models.Code;
import pt.ulisboa.tecnico.cmu.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    @Override
    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);

    Optional<User> findByCode(Code code);

    List<User> findAllByOrderByScoresDesc();
}
