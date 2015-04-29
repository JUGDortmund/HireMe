package conf;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.UUID;

import javax.inject.Inject;

import ninja.utils.NinjaProperties;

@Singleton
public class DatastoreProvider implements Provider<Datastore> {

  private static final String MORPHIA_PACKAGE = "ninja.morphia.package";
  private static final String MONGODB_HOST = "ninja.mongodb.host";
  private static final String MONGODB_PORT = "ninja.mongodb.port";
  private static final String MONGODB_USER = "ninja.mongodb.user";
  private static final String MONGODB_PASS = "ninja.mongodb.pass";
  private static final String MONGODB_DBNAME = "ninja.mongodb.dbname";
  private static final String MONGODB_AUTHDB = "ninja.mongodb.authdb";
  private NinjaProperties properties;
  private MongoClient mongoClient;
  private Morphia morphia;
  private Datastore datastore;
  private String databaseName;


  @Inject
  public DatastoreProvider(NinjaProperties properties) throws UnknownHostException {
    this.properties = properties;
    if (properties.isProd()) {
      createMongoDBConnection();
    } else {
      initiateInMemoryDatestore();
    }
    String packageName = properties.get(MORPHIA_PACKAGE);

    mongoClient.dropDatabase(databaseName);
    morphia = new Morphia().mapPackage(packageName);
    datastore = morphia.createDatastore(mongoClient, databaseName);
  }

  private void createMongoDBConnection() throws UnknownHostException {
    String host = properties.get(MONGODB_HOST);
    int port = properties.getInteger(MONGODB_PORT);

    String username = properties.get(MONGODB_USER);
    String password = properties.get(MONGODB_PASS);
    String authdb = properties.get(MONGODB_AUTHDB);

    MongoCredential credentials = MongoCredential.createCredential(username,
                                                                      authdb,
                                                                      password.toCharArray());
    mongoClient = new MongoClient(new ServerAddress(host, port), Arrays.asList(credentials));
    databaseName = properties.get(MONGODB_DBNAME);
  }

  public Datastore get() {
    return datastore;
  }

  private void initiateInMemoryDatestore() {
    databaseName = UUID.randomUUID().toString();
    Fongo fongo = new Fongo(databaseName);
    mongoClient = fongo.getMongo();
  }
}

