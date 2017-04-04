package com.kuldeep.examples.repository;

import com.mongodb.Mongo;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import com.kuldeep.examples.domain.Person;
import org.hamcrest.core.Is;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * Created by iDiot on 4/3/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-application-context.xml"})
public class PersonRepositoryMongoImplTest {
    private static final String LOCALHOST = "127.0.0.1";
    private static final String DB_NAME = "itest";
    private static final int MONGO_TEST_PORT = 27028;

    private static MongodProcess mongoProcess;
    private static Mongo mongo;
    private static MongodExecutable mongodExecutable;

    private MongoTemplate template;

    @Autowired
    private PersonRepository repoImpl;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {


        MongodStarter starter = MongodStarter.getDefaultInstance();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(LOCALHOST, MONGO_TEST_PORT, Network.localhostIsIPv6()))
                .build();


        mongodExecutable = starter.prepare(mongodConfig);

        mongoProcess = mongodExecutable.start();

        /*mongo = new MongoClient(LOCALHOST, MONGO_TEST_PORT);
        mongo.getDB(DB_NAME);*/

//        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

    }

    @AfterClass
    public static void shutdownDB() throws InterruptedException {
//        mongo.close();
        mongoProcess.stop();
    }


    @Test
    public void save() throws Exception {

       Person person = new Person("1234");
        person.setFirstName("Kuldeep");
        person.setMiddleName("Singh");
        person.setLastName("Sengar");
        repoImpl.save(person);

    }

    @Test
    public void findByKey() throws Exception {
        Person person = new Person("1234");
        person.setFirstName("Kuldeep");
        person.setLastName("Sengar");
        repoImpl.save(person);

        List<Person> persons = repoImpl.findByEmployeeId("1234");
        assertThat(persons.size(), Is.is(1));
        assertThat(persons.get(0), Is.is(new Person("1234")));

    }

}