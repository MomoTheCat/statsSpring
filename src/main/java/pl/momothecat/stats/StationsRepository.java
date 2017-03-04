package pl.momothecat.stats;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.momothecat.stats.model.SimpleStation;

/**
 * Created by szymon on 04.03.2017.
 */

public interface StationsRepository extends MongoRepository<SimpleStation, String> {

    SimpleStation findByName(String firstName);
    SimpleStation findByIdNetwork(String networkId);

}