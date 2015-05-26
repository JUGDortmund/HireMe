package integration.provider;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mongodb.morphia.Datastore;

import ninja.NinjaTest;

public class DatastoreProviderTest extends NinjaTest {

  @Test
  public void multipleDatastoreResolvingsReturnSameDatastore() throws Exception {
    final Datastore datastore = getInjector().getInstance(Datastore.class);

    assertThat(datastore).isSameAs(getInjector().getInstance(Datastore.class));
  }
}
