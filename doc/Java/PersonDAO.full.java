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

/*
 * This is a full person/contact DAO. This is for reference as AngularCRUD only uses the first and last name.
 */
public class PersonDAO {
	private static final Logger logger = Logger.getLogger(PersonDAO.class.getName());
	// TODO - Set the schema to your user name. If you changed the schema in PersonDDL.sql
	//        to your username then this project just might work.
	private static final String yourUserName = "Your username";


	/**
	 * Get one individual based upon a unique identifier.
	 *
	 * @param userId
	 * @return
	 * @throws PersonException
	 */
	public static Person getPerson(int userId) throws PersonException {
		Person p = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = JPLDBConnection.getConnection();
			ps = conn.prepareStatement(
					  "select * " +
					  "from apps." + yourUserName + "_rest_example " +
					  "where userId = ?" +
					  "order by lastname, firstname");
			ps.setLong(1, userId);
			rs = ps.executeQuery();

			if (rs.next()) {
				p = new Person();

				p.setUserId(rs.getInt("userId"));
				p.setFirstName(rs.getString("firstName"));
				p.setLastName(rs.getString("lastName"));
				p.setAddress(rs.getString("address"));
				p.setCity(rs.getString("city"));
				p.setState(rs.getString("state"));
				p.setZip(rs.getString("zip"));
				p.setHomePhone(rs.getString("homePhone"));
				p.setWorkPhone(rs.getString("workPhone"));
				p.setMobilePhone(rs.getString("mobilePhone"));
				p.setEmail(rs.getString("email"));
				p.setBirthdayDate(rs.getDate("birthday"));
				p.setChildren(rs.getInt("children"));
			}
		}
		catch (SQLException sqlEx) {
			logger.log(Level.SEVERE, sqlEx.getMessage(), sqlEx);
			throw new PersonException(sqlEx.getMessage(), sqlEx);
		}
		finally {
			// Cleanup after ourselves
			try {
				if (rs != null)	{ rs.close(); }
				if (ps != null)	{ ps.close(); }
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
					  "from apps." + yourUserName + "_rest_example " +
					  "order by lastname, firstname");
			rs = ps.executeQuery();

			while (rs.next()) {
				Person p = new Person();

				p.setUserId(rs.getInt("userId"));
				p.setFirstName(rs.getString("firstName"));
				p.setLastName(rs.getString("lastName"));
				p.setAddress(rs.getString("address"));
				p.setCity(rs.getString("city"));
				p.setState(rs.getString("state"));
				p.setZip(rs.getString("zip"));
				p.setHomePhone(rs.getString("homePhone"));
				p.setWorkPhone(rs.getString("workPhone"));
				p.setMobilePhone(rs.getString("mobilePhone"));
				p.setEmail(rs.getString("email"));
				p.setBirthdayDate(rs.getDate("birthday"));
				p.setChildren(rs.getInt("children"));

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
				if (rs != null)	{ rs.close(); }
				if (ps != null)	{ ps.close(); }
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
					  "from apps." + yourUserName + "_rest_example " +
					  (whereClause.length() > 0 ? "where " + whereClause : "") +
					  "order by lastname, firstname");
			rs = ps.executeQuery();

			while (rs.next()) {
				Person p = new Person();

				p.setUserId(rs.getInt("userId"));
				p.setFirstName(rs.getString("firstName"));
				p.setLastName(rs.getString("lastName"));
				p.setAddress(rs.getString("address"));
				p.setCity(rs.getString("city"));
				p.setState(rs.getString("state"));
				p.setZip(rs.getString("zip"));
				p.setHomePhone(rs.getString("homePhone"));
				p.setWorkPhone(rs.getString("workPhone"));
				p.setMobilePhone(rs.getString("mobilePhone"));
				p.setEmail(rs.getString("email"));
				p.setBirthdayDate(rs.getDate("birthday"));
				p.setChildren(rs.getInt("children"));

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
				if (rs != null)	{ rs.close(); }
				if (ps != null)	{ ps.close(); }
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
					  "insert into apps." + yourUserName + "_rest_example (" +
					  "userId, firstName, lastName, " +
					  "address, city, state, zip, " +
					  "homePhone, workPhone, mobilePhone, " +
					  "email, birthday, children) " +
					  "values (apps." + yourUserName + "_rest_example_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString( 1, p.getFirstName());
			ps.setString( 2, p.getLastName());
			ps.setString( 3, p.getAddress());
			ps.setString( 4, p.getCity());
			ps.setString( 5, p.getState());
			ps.setString( 6, p.getZip());
			ps.setString( 7, p.getHomePhone());
			ps.setString( 8, p.getWorkPhone());
			ps.setString( 9, p.getMobilePhone());
			ps.setString(10, p.getEmail());
			ps.setDate(  11, new java.sql.Date(p.getBirthdayDate().getTime()));
			ps.setInt(   12, p.getChildren());

			int rowCount = ps.executeUpdate();

			if (rowCount < 1) {
				throw new SQLException("Person persistence failed.");
			}
			else {
				conn.commit();

				Statement s = conn.createStatement();
				rs = s.executeQuery("select apps." + yourUserName + "_rest_example_seq.currval from dual");
				int newUserId = 0;
				while (rs.next()) {
					newUserId = rs.getInt(1);
				}
				if (newUserId != 0) {
					newUser = getPerson(newUserId);
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
				if (rs != null)	{ rs.close(); }
				if (ps != null)	{ ps.close(); }
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

		if (p == null || p.getUserId() == null || p.getUserId() <= 0) {
			throw new PersonException("Person persistence failed. Cannot persist a null Person");
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = JPLDBConnection.getConnection();
			ps = conn.prepareStatement(
					  "update apps." + yourUserName + "_rest_example set " +
					  "firstName=?, " +
					  "lastName=?, " +
					  "address=?, " +
					  "city=?, " +
					  "state=?, " +
					  "zip=?, " +
					  "homePhone=?, " +
					  "workPhone=?, " +
					  "mobilePhone=?, " +
					  "email=?, " +
					  "birthday=?, " +
					  "children=? " +
					  "where userid=?");

			ps.setString( 1, p.getFirstName());
			ps.setString( 2, p.getLastName());
			ps.setString( 3, p.getAddress());
			ps.setString( 4, p.getCity());
			ps.setString( 5, p.getState());
			ps.setString( 6, p.getZip());
			ps.setString( 7, p.getHomePhone());
			ps.setString( 8, p.getWorkPhone());
			ps.setString( 9, p.getMobilePhone());
			ps.setString(10, p.getEmail());
			ps.setDate(  11, new java.sql.Date(p.getBirthdayDate().getTime()));
			ps.setInt(   12, p.getChildren());
			ps.setLong(  13, p.getUserId());

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
				if (rs != null)	{ rs.close(); }
				if (ps != null)	{ ps.close(); }
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
	 * @param userId
	 * @return
	 * @throws PersonException
	 */
	public static boolean delete(int userId) throws PersonException {
		boolean retValue = false;

		if (userId == 0) {
			throw new PersonException("Person deletion failed. Cannot delete a null id");
		}

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JPLDBConnection.getConnection();
			ps = conn.prepareStatement("delete from apps." + yourUserName + "_rest_example where userId = ?");
			ps.setLong(1, userId);

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
				if (ps != null)	{ ps.close(); }
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
			ps = conn.prepareStatement("delete from apps." + yourUserName + "_rest_example");

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
				if (ps != null)	{ ps.close(); }
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

		if (searchCriteria.getAddress() != null && !searchCriteria.getAddress().equals("")) {
			if (sb.length() != 0) { sb.append(" and "); }
			sb.append("lower(address) like '").append(searchCriteria.getAddress().toLowerCase()).append("%'");
		}

		if (searchCriteria.getCity() != null && !searchCriteria.getCity().equals("")) {
			if (sb.length() != 0) { sb.append(" and "); }
			sb.append("lower(city) like '").append(searchCriteria.getCity().toLowerCase()).append("%'");
		}

		if (searchCriteria.getState() != null && !searchCriteria.getState().equals("")) {
			if (sb.length() != 0) { sb.append(" and "); }
			sb.append("lower(state) = '").append(searchCriteria.getState().toLowerCase()).append("'");
		}

		if (searchCriteria.getZip() != null && !searchCriteria.getZip().equals("")) {
			if (sb.length() != 0) { sb.append(" and "); }
			sb.append("zip like '").append(searchCriteria.getZip()).append("%'");
		}

		if (searchCriteria.getHomePhone() != null && !searchCriteria.getHomePhone().equals("")) {
			if (sb.length() != 0) { sb.append(" and "); }
			sb.append("homephone like '%").append(searchCriteria.getHomePhone()).append("%'");
		}

		if (searchCriteria.getWorkPhone() != null && !searchCriteria.getWorkPhone().equals("")) {
			if (sb.length() != 0) { sb.append(" and "); }
			sb.append("workphone like '%").append(searchCriteria.getWorkPhone()).append("%'");
		}

		if (searchCriteria.getMobilePhone() != null && !searchCriteria.getMobilePhone().equals("")) {
			if (sb.length() != 0) { sb.append(" and "); }
			sb.append("mobilephone like '%").append(searchCriteria.getMobilePhone()).append("%'");
		}

		if (searchCriteria.getEmail() != null && !searchCriteria.getEmail().equals("")) {
			if (sb.length() != 0) { sb.append(" and "); }
			sb.append("lower(email) like '%").append(searchCriteria.getEmail().toLowerCase()).append("%'");
		}

		if (searchCriteria.getBirthday() != null ) {
			if (sb.length() != 0) { sb.append(" and "); }
			// TODO - finish this
			sb.append("birthday = '").append(searchCriteria.getBirthday()).append("'");
		}

		// Note, this won't return people with zero children (those smart childless bastards!)
		// Should be an Integer so we can have nulls as well as zeros.
		if (searchCriteria.getChildren() != 0) {
			if (sb.length() != 0) { sb.append(" and "); }
			sb.append("children = ").append(searchCriteria.getChildren());
		}

		return sb.toString();
	}

}
