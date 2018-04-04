package pt.ulisboa.tecnico.cmu.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.ulisboa.tecnico.cmu.models.Monument;

import java.util.Optional;

@Repository
public interface MonumentRepository extends MongoRepository<Monument, String> {

    @Override
    Optional<Monument> findById(String id);

    Optional<Monument> findBySsid(String ssid);
}
