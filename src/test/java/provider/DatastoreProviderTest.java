package provider;

import java.util.UUID;
import ninja.NinjaTest;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import static org.assertj.core.api.Assertions.assertThat;

public class DatastoreProviderTest extends NinjaTest {

	@Test
	public void testModeHasUUIDDatabase() throws Exception {
		assertThat(UUID.fromString(getInjector().getInstance(Datastore.class).getDB().getName())).isNotNull();
	}
}
