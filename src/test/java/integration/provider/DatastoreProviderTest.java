package integration.provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import ninja.NinjaTest;

import org.junit.Test;
import org.mongodb.morphia.Datastore;

public class DatastoreProviderTest extends NinjaTest {

  @Test
  public void testModeHasUUIDDatabase() throws Exception {
    assertThat(UUID.fromString(getInjector().getInstance(Datastore.class).getDB().getName()))
        .isNotNull();
  }

  @Test
  public void multipleDatastoreResolvingsReturnSameDatastore() throws Exception {
    final Datastore datastore = getInjector().getInstance(Datastore.class);

    assertThat(datastore).isSameAs(getInjector().getInstance(NinjaTest.class));
  }
}
