package unit.provider;

import static org.assertj.core.api.Assertions.assertThat;
import conf.DatastoreProvider;

import org.junit.Test;

import java.util.UUID;

import ninja.utils.MockNinjaProperties;
import ninja.utils.NinjaProperties;

public class DatastoreProviderTest {

  @Test
  public void useInMemoryDbHasUUIDDatabaseName() throws Exception {
    NinjaProperties properties =
        MockNinjaProperties
            .create("ninja.mongodb.useInMemory", "true", "ninja.morphia.package", "");
    DatastoreProvider datastore = new DatastoreProvider(properties);

    assertThat(UUID.fromString(datastore.get().getDB().getName())).isNotNull();
  }

}
