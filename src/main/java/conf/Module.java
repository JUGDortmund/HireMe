/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package conf;

import org.mongodb.morphia.Datastore;

import services.TagService;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

@Singleton
public class Module extends AbstractModule {

  private EventBus eventBus = new EventBus("Default EventBus");

  protected void configure() {
    bind(Datastore.class).toProvider(DatastoreProvider.class);
    bind(EventBus.class).toInstance(eventBus);
    bind(TagService.class).asEagerSingleton();
    bind(ScheduledResourceCleanUp.class);

    bindListener(Matchers.any(), new TypeListener() {
      @Override
      public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        encounter.register((InjectionListener<I>) eventBus::register);
      }
    });
    bindListener(Matchers.any(), new LoggerTypeListener());
  }

}
