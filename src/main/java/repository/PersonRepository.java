package repository;

import domain.Person;

import java.util.List;

/**
 * Created by iDiot on 4/3/17.
 */
public interface PersonRepository {
    void save(Person person);
    List<Person> findByKey(String id);

}
