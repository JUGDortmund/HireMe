package conf;

import java.util.UUID;

import javax.inject.Inject;

import ninja.utils.NinjaProperties;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.github.fakemongo.Fongo;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import mongodb.MongoDB;

@Singleton
public class DatastoreProvider implements Provider<Datastore> {

    private static final String MORPHIA_PACKAGE = "ninja.morphia.package";
    private NinjaProperties properties;
    private Datastore datastore;

    @Inject
    public DatastoreProvider(NinjaProperties properties, MongoDB mongoDB) {
        this.properties = properties;
        if (properties.isProd()) {
            datastore = mongoDB.getDatastore();
        } else {
            datastore = initiateDatestore();
        }
    }

    public Datastore get() {
        return datastore;
    }

    private Datastore initiateDatestore() {
        String dbName = UUID.randomUUID().toString();
        Fongo fongo = new Fongo(dbName);
        Morphia morphia = new Morphia();
        morphia.mapPackage(properties.get(MORPHIA_PACKAGE));
        return morphia.createDatastore(fongo.getMongo(), dbName);
    }
}

