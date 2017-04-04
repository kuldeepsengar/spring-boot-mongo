package com.kuldeep.examples.repository;

import com.kuldeep.examples.domain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iDiot on 4/3/17.
 */
@Repository
public interface PersonRepository extends MongoRepository<Person,String>{
    Person save(Person person);
    List<Person> findByEmployeeId(String id);

}
