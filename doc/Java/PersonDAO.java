package gov.nasa.jpl.ebis.person.model;

import gov.nasa.jpl.ebis.person.PersonException;
import gov.nasa.jpl.nbs.util.JPLDBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonDAO {
   private static final Logger logger = Logger.getLogger(PersonDAO.class.getName());

   // TODO - Set the schema to your user name. If you changed the schema in PersonDDL.sql
   //        to your username then this project just might work.
   private static final String yourUserName = "jshea";


   /**
    * Get one individual based upon a unique identifier.
    *
    * @param id
    * @return
    * @throws PersonException
    */
   public static Person getPerson(int id) throws PersonException {
      Person p = null;
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         conn = JPLDBConnection.getConnection();
         ps = conn.prepareStatement(
                 "select * " +
                 "from apps." + yourUserName + "_angularcrud " +
                 "where id = ?" +
                 "order by lastname, firstname");
         ps.setLong(1, id);
         rs = ps.executeQuery();

         if (rs.next()) {
            p = new Person();

            p.setId(rs.getInt("id"));
            p.setFirstName(rs.getString("firstName"));
            p.setLastName(rs.getString("lastName"));
         }
      }
      catch (SQLException sqlEx) {
         logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         throw new PersonException(sqlEx.getMessage(), sqlEx);
      }
      finally {
         // Cleanup after ourselves
         try {
            if (rs != null)   { rs.close(); }
            if (ps != null)   { ps.close(); }
            if (conn != null) { conn.close(); }
         }
         catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         }
      }
      return p;
   }


   /**
    * Get all data. Warning, returned results can be big.
    *
    * @return
    * @throws PersonException
    */
   public static List<Person> getAll() throws PersonException {
      List<Person> entryList = new ArrayList<Person>();
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         conn = JPLDBConnection.getConnection();
         ps = conn.prepareStatement(
                 "select * " +
                 "from apps." + yourUserName + "_angularcrud " +
                 "order by lastname, firstname");
         rs = ps.executeQuery();

         while (rs.next()) {
            Person p = new Person();

            p.setId(rs.getInt(1));
            p.setFirstName(rs.getString("firstName"));
            p.setLastName(rs.getString("lastName"));

            entryList.add(p);
         }
      }
      catch (SQLException sqlEx) {
         logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         throw new PersonException(sqlEx.getMessage(), sqlEx);
      }
      finally {
         // Cleanup after ourselves
         try {
            if (rs != null)   { rs.close(); }
            if (ps != null)   { ps.close(); }
            if (conn != null) { conn.close(); }
         }
         catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         }
      }
      return entryList;
   }


   /**
    * Perform a search/retrieval based upon user specified criteria. A person object
    * is used to hold search criteria. Any populated values are used to perform a
    * search for that data item being equal to the provided value.
    *
    * Multiple search criteria are "and'd" together. That is, all criteria must
    * be matched for the person to be retrieved.
    *
    * Not currently implemented in this example.
    *
    * @param searchCriteria
    * @return
    * @throws PersonException
    */
   public static List<Person> find(Person searchCriteria) throws PersonException {
      List<Person> entryList = new ArrayList<Person>();
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      // Build a where clause based upon values present in the searchCriteria object.
      String whereClause = buildWhereClause(searchCriteria);

      try {
         conn = JPLDBConnection.getConnection();
         ps = conn.prepareStatement(
                 "select * " +
                 "from apps." + yourUserName + "_angularcrud " +
                 (whereClause.length() > 0 ? "where " + whereClause : "") +
                 "order by lastname, firstname");
         rs = ps.executeQuery();

         while (rs.next()) {
            Person p = new Person();

            p.setId(rs.getInt("id"));
            p.setFirstName(rs.getString("firstName"));
            p.setLastName(rs.getString("lastName"));

            entryList.add(p);
         }
      }
      catch (SQLException sqlEx) {
         logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         throw new PersonException(sqlEx.getMessage(), sqlEx);
      }
      finally {
         // Cleanup our JDBC objects
         try {
            if (rs != null)   { rs.close(); }
            if (ps != null)   { ps.close(); }
            if (conn != null) { conn.close(); }
         }
         catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         }
      }
      return entryList;
   }


   /**
    * Add a new person.
    *
    * @param p
    * @return
    * @throws PersonException
    */
   public static Person add(Person p) throws PersonException {

      if (p == null) {
         throw new PersonException("Person persistence failed. Cannot persist a null Person");
      }

      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      Person newUser = null;

      try {
         conn = JPLDBConnection.getConnection();
         ps = conn.prepareStatement(
                 "insert into apps." + yourUserName + "_angularcrud " +
                 "(id, firstName, lastName) " +
                 "values (apps." + yourUserName + "_angularcrud_seq.nextval,?,?)", Statement.RETURN_GENERATED_KEYS);

         ps.setString( 1, p.getFirstName());
         ps.setString( 2, p.getLastName());

         int rowCount = ps.executeUpdate();

         if (rowCount < 1) {
            throw new SQLException("Person persistence failed.");
         }
         else {
            conn.commit();

            Statement s = conn.createStatement();
            rs = s.executeQuery("select apps." + yourUserName + "_angularcrud_seq.currval from dual");
            int newId = 0;
            while (rs.next()) {
               newId = rs.getInt(1);
            }
            if (newId != 0) {
               newUser = getPerson(newId);
            }
         }
      }
      catch (SQLException sqlEx) {
         try {
            conn.rollback();
         }
         catch (SQLException e) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), e);
            throw new PersonException(sqlEx.getMessage(),e);
         }
         logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         throw new PersonException(sqlEx.getMessage(),sqlEx);
      }
      finally {
         // Cleanup our JDBC objects
         try {
            if (rs != null)   { rs.close(); }
            if (ps != null)   { ps.close(); }
            if (conn != null) { conn.close(); }
         }
         catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         }
      }
      return newUser;
   }


   /**
    * Add a list of people. Not currently used in this example.
    *
    * @param personList
    * @throws PersonException
    */
   public static void add(List<Person> personList) throws PersonException {

      if (personList == null) {
         throw new PersonException("Person persistence failed. Cannon persist a null list");
      }

      for (Person p : personList) {
         add(p);
      }
   }


   /**
    * Update a person by replacing all values with the passed in values.
    *
    * @param p
    * @throws PersonException
    */
   public static void update(Person p) throws PersonException {

      if (p == null || p.getId() == null || p.getId() <= 0) {
         throw new PersonException("Person persistence failed. Cannot persist a null Person");
      }

      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         conn = JPLDBConnection.getConnection();
         ps = conn.prepareStatement(
                 "update apps." + yourUserName + "_angularcrud set " +
                 "firstName=?, " +
                 "lastName=? " +
                 "where id=?");

         ps.setString( 1, p.getFirstName());
         ps.setString( 2, p.getLastName());
         ps.setLong(   3, p.getId());

         int rowCount = ps.executeUpdate();

         if (rowCount < 1) {
            throw new SQLException("Person persistence failed.");
         }
         conn.commit();
      }
      catch (SQLException sqlEx) {
         try {
            conn.rollback();
         }
         catch (SQLException e) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
            throw new PersonException(sqlEx.getMessage(), sqlEx);
         }
         logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         throw new PersonException(sqlEx.getMessage(), sqlEx);
      }
      finally {
         // Cleanup our JDBC objects
         try {
            if (rs != null)   { rs.close(); }
            if (ps != null)   { ps.close(); }
            if (conn != null) { conn.close(); }
         }
         catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         }
      }
   }


   /**
    * Delete one person based on their unique key.
    *
    * @param id
    * @return
    * @throws PersonException
    */
   public static boolean delete(int id) throws PersonException {
      boolean retValue = false;

      if (id == 0) {
         throw new PersonException("Person deletion failed. Cannot delete a null id");
      }

      Connection conn = null;
      PreparedStatement ps = null;

      try {
         conn = JPLDBConnection.getConnection();
         ps = conn.prepareStatement("delete from apps." + yourUserName + "_angularcrud where id = ?");
         ps.setLong(1, id);

         int rowCount = ps.executeUpdate();

         if (rowCount > 0) {
            retValue = true;
         }
         conn.commit();
      }
      catch (SQLException sqlEx) {
         try {
            conn.rollback();
         }
         catch (SQLException e) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
            throw new PersonException(sqlEx.getMessage(), sqlEx);
         }
         logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         throw new PersonException(sqlEx.getMessage(), sqlEx);
      }
      finally {
         // Cleanup after ourselves
         try {
            if (ps != null)   { ps.close(); }
            if (conn != null) { conn.close(); }
         }
         catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         }
      }
      return retValue;
   }


   /**
    * Delete all person data from the data store.
    *
    * @throws PersonException
    */
   public static void deleteAll() throws PersonException {
      Connection conn = null;
      PreparedStatement ps = null;

      try {
         conn = JPLDBConnection.getConnection();
         ps = conn.prepareStatement("delete from apps." + yourUserName + "_angularcrud");

         ps.executeUpdate();

         conn.commit();
      }
      catch (SQLException sqlEx) {
         try {
            conn.rollback();
         }
         catch (SQLException e) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
            throw new PersonException(e.getMessage(), e);
         }
         logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         throw new PersonException(sqlEx.getMessage(), sqlEx);
      }
      finally {
         try {
            if (ps != null)   { ps.close(); }
            if (conn != null) { conn.close(); }
         }
         catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
         }
      }
   }


   /**
    * Take the values present in the searchCriteria and create a SQL where
    * clause.
    *
    * @param searchCriteria
    * @return
    */
   private static String buildWhereClause(Person searchCriteria) {
      StringBuilder sb = new StringBuilder();

      if (searchCriteria.getLastName() != null && !searchCriteria.getLastName().equals("")) {
         if (sb.length() != 0) { sb.append(" and "); }
         sb.append("lower(lastname) like '").append(searchCriteria.getLastName().toLowerCase()).append("%'");
      }

      if (searchCriteria.getFirstName() != null && !searchCriteria.getFirstName().equals("")) {
         if (sb.length() != 0) { sb.append(" and "); }
         sb.append("lower(firstname) like '").append(searchCriteria.getFirstName().toLowerCase()).append("%'");
      }

      return sb.toString();
   }

}
