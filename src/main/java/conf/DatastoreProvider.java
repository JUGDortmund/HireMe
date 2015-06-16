package conf;

import com.github.fakemongo.Fongo;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import ninja.utils.NinjaProperties;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.inject.Inject;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.UUID;

@Singleton
public class DatastoreProvider implements Provider<Datastore> {

  private static final String MORPHIA_PACKAGE = "ninja.morphia.package";
  private static final String MONGODB_HOST = "ninja.mongodb.host";
  private static final String MONGODB_PORT = "ninja.mongodb.port";
  private static final String MONGODB_USER = "ninja.mongodb.user";
  private static final String MONGODB_PASS = "ninja.mongodb.pass";
  private static final String MONGODB_DBNAME = "ninja.mongodb.dbname";
  private static final String MONGODB_AUTHDB = "ninja.mongodb.authdb";
  private static final String MONGODB_USE_IN_MEMORY = "ninja.mongodb.useInMemory";
  private static final String MONGODB_INITIALIZE_DB = "ninja.mongodb.initializedb";
  private static final String DEFAULT_MORPHIA_PACKAGE = "model";

  private NinjaProperties properties;
  private MongoClient mongoClient;
  private Morphia morphia;
  private Datastore datastore;
  private String databaseName;

  @Inject
  public DatastoreProvider(NinjaProperties properties) throws UnknownHostException {
    this.properties = properties;

    if (properties.getBooleanWithDefault(MONGODB_USE_IN_MEMORY, true)) {
      initiateInMemoryDatastore();
    } else {
      createMongoDBConnection();
    }
    String packageName = properties.getWithDefault(MORPHIA_PACKAGE, DEFAULT_MORPHIA_PACKAGE);

    morphia = new Morphia().mapPackage(packageName);
    datastore = morphia.createDatastore(mongoClient, databaseName);
  }

  private void createMongoDBConnection() throws UnknownHostException {
    String host = properties.get(MONGODB_HOST);
    int port = properties.getInteger(MONGODB_PORT);

    String username = properties.get(MONGODB_USER);
    String password = properties.get(MONGODB_PASS);
    String authdb = properties.get(MONGODB_AUTHDB);

    MongoCredential credentials =
        MongoCredential.createCredential(username, authdb, password.toCharArray());
    mongoClient = new MongoClient(new ServerAddress(host, port), Arrays.asList(credentials));
    databaseName = properties.get(MONGODB_DBNAME);
    if (properties.getBooleanWithDefault(MONGODB_INITIALIZE_DB, false)) {
      mongoClient.dropDatabase(databaseName);
    }
  }

  public Datastore get() {
    return datastore;
  }

  private void initiateInMemoryDatastore() {
    databaseName = UUID.randomUUID().toString();
    Fongo fongo = new Fongo(databaseName);
    mongoClient = fongo.getMongo();
  }
}
