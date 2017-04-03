package repository;

import com.mongodb.*;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.process.runtime.Network;
import domain.Person;
import junit.framework.TestCase;
import org.hamcrest.core.Is;
import org.junit.*;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by iDiot on 4/3/17.
 */
public class PersonRepositoryMongoImplTest {
    private static final String LOCALHOST = "127.0.0.1";
    private static final String DB_NAME = "itest";
    private static final int MONGO_TEST_PORT = 27028;

    private static MongodProcess mongoProcess;
    private static Mongo mongo;
    private static MongodExecutable mongodExecutable;

    private MongoTemplate template;

    private PersonRepositoryMongoImpl repoImpl;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        MongodStarter starter = MongodStarter.getDefaultInstance();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(LOCALHOST, MONGO_TEST_PORT, Network.localhostIsIPv6()))
                .build();


        mongodExecutable = starter.prepare(mongodConfig);

        mongoProcess = mongodExecutable.start();

        mongo = new MongoClient(LOCALHOST, MONGO_TEST_PORT);
        mongo.getDB(DB_NAME);
    }

    @AfterClass
    public static void shutdownDB() throws InterruptedException {
        mongo.close();
        mongoProcess.stop();
    }

    @Before
    public void setUp() throws Exception {
        repoImpl = new PersonRepositoryMongoImpl();
        template = new MongoTemplate(mongo, DB_NAME);
        repoImpl.setMongoOperations(template);
    }

    @After
    public void tearDown() throws Exception {
        template.dropCollection(Person.class);
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

        List<Person> persons = repoImpl.findByKey("1234");
        assertThat(persons.size(), Is.is(1));
        assertThat(persons.get(0), Is.is(new Person("1234")));

    }

}