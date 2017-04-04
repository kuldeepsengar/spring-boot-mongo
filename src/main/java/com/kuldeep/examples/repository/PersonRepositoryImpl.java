package com.kuldeep.examples.repository;

import com.kuldeep.examples.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by iDiot on 4/4/17.
 */
@Component
public class PersonRepositoryImpl implements PersonRepository{

    @Autowired
    private MongoOperations mongoOperations;

    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public void save(Person person) {
        mongoOperations.save(person);
    }

    public List<Person> findByEmployeeId(String id) {
        return mongoOperations.find(Query.query(Criteria.where("id").is(id)),Person.class);
    }
}
