package com.example.person;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Artifact for JAX-RS
 *
 * @author jshea
 */

// Jersey 1.x version
public class PersonApplication extends Application {
   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> empty = new HashSet<Class<?>>();

   public PersonApplication() {
      singletons.add(new PersonResource());
   }

   @Override
   public Set<Class<?>> getClasses() {
      return empty;
   }

   @Override
   public Set<Object> getSingletons() {
      return singletons;
   }
}
