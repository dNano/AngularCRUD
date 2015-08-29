package gov.nasa.jpl.ebis.person;

/**
 * Custom error for handling Person business errors.
 */
public class PersonException extends Exception {
   private static final long serialVersionUID = 1L;
    
   public PersonException() { }


   public PersonException(String msg) {
      super(msg);
   }

   public PersonException(String msg, Exception e) {
      super(msg, e);
   }

}
