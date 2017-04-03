package repository;

import domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iDiot on 4/3/17.
 */
@Repository
public class PersonRepositoryMongoImpl implements PersonRepository{
    @Autowired
    private MongoOperations mongoOperations;

    public void save(Person person) {
        mongoOperations.save(person);
    }

    public List<Person> findByKey(String id) {
        return mongoOperations.find(Query.query(Criteria.where("id").is(id)), Person.class);
    }

    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
}
