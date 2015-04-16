package integration.provider;

import ninja.NinjaTest;

import org.junit.Test;
import org.mongodb.morphia.Datastore;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DatastoreProviderTest extends NinjaTest {

    @Test
    public void testModeHasUUIDDatabase() throws Exception {
        assertThat(UUID.fromString(getInjector().getInstance(Datastore.class).getDB().getName())).isNotNull();
    }

    @Test
    public void multipleDatastoreResolvingsReturnSameDatastore() throws Exception {
        final Datastore datastore = getInjector().getInstance(Datastore.class);

        assertThat(datastore).isSameAs(getInjector().getInstance(Datastore.class));
    }
}
