package pl.momothecat.stats.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pl.momothecat.stats.model.SimpleStation;

/**
 * Created by szymon on 04.03.2017.
 */
@Repository
public class StationsRepositoryTemplate {

    @Autowired
    MongoTemplate mongoTemplate;

    public void pushMethod(String objectId, Object... object) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where(SimpleStation.ID_NETWORK).is(objectId)),
                new Update().pushAll(SimpleStation.EXTRAS, object), SimpleStation.class);
    }

    public boolean ifExistsNetworkId(String objectId) {
        return mongoTemplate.exists(
                Query.query(Criteria.where(SimpleStation.ID_NETWORK).is(objectId)),
                SimpleStation.class);
    }
}
