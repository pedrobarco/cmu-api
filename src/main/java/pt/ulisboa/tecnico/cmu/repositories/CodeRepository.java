package pt.ulisboa.tecnico.cmu.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pt.ulisboa.tecnico.cmu.models.Code;

import java.util.Optional;

public interface CodeRepository extends MongoRepository<Code, String> {

    @Override
    Optional<Code> findById(String id);

    Optional<Code> findBySecret(String secret);
}
