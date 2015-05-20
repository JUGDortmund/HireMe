package util;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Test;

import conf.Module;

import static org.assertj.core.api.Assertions.assertThat;

public class EventSystemTest {

  @Test
  public void eventBusIsInjectedEveryWhere() throws Exception {
    Injector injector = Guice.createInjector(new TestModule(), new Module());

    TestSubscriber subscriber = injector.getInstance(TestSubscriber.class);
    injector.getInstance(EventBus.class).post(new TestEvent("Test"));
    assertThat(subscriber.message).isEqualTo("Test");
  }

  private static class TestEvent {

    String message;

    public TestEvent(String message) {
      this.message = message;
    }
  }

  private static class TestSubscriber {

    String message;

    @Subscribe
    public void receiveEvent(TestEvent event) {
      message = event.message;
    }
  }
}
