package com.onlinebookstore.model;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import javafx.util.Pair;

public class User {
	private String firstName, lastName, email, userRole, password;
	private int userId;
	private static Connection con = null;

	public User() {
	}

	// Constructor to create a new person.
	public User(String firstName, String lastName, String email, String userRole) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRole = userRole;
	}

	// Constructor to create a new person.
	public User(String firstName, String lastName, String email,
			String userRole, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRole = userRole;
		this.password = password;
	}

	// Constructor to create a new person.
	public User(int userId, String firstName, String lastName, String email,
			String userRole) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRole = userRole;
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getUserRole() {
		return userRole;
	}

	public int getUserId() {
		return userId;
	}

	public String toString() {
		return getFirstName() + " " + getLastName() + " " + getEmail() + " "
				+ userRole;
	}

	public static boolean verifyEmployeeID(String employeeID, String firstName,
			String lastName) {
		boolean result = false;
		PreparedStatement pstmt = null;

		con = getConnection();

		if (con != null) {
			String query = "SELECT * FROM employees WHERE binary first_name=? and binary last_name=? and binary employee_id=?;";
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, firstName);
				pstmt.setString(2, lastName);
				pstmt.setString(3, employeeID);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					result = true;
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		return result;
	}

	public static User verifyUserLoginCredentials(String loginEmail,
			String loginPassword) {
		String userSalt = getUserSalt(loginEmail);

		if (userSalt != null) {
			// If loginEmail exists in the users table
			String hashedLoginPassword = getSecurePasswordSHA512(loginPassword,
					userSalt.getBytes());

			return verifyPassword(loginEmail, hashedLoginPassword);
		}
		System.out.format("email: %s is invalid\n", loginEmail);
		return null;
	}

	public static User verifyPassword(String loginEmail,
			String hashedLoginPassword) {
		User userLogingIn = null;

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String getUser = "SELECT * FROM users WHERE binary email=? and binary hashed_password=?;";

			try {
				pstmt = con.prepareStatement(getUser);
				pstmt.setString(1, loginEmail);
				pstmt.setString(2, hashedLoginPassword);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) { // rs should return one row
					int userId = rs.getInt("user_id");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String email = rs.getString("email");
					String userRole = rs.getString("user_role");

					userLogingIn = new User(userId, firstName, lastName, email,
							userRole);
					System.out.println("Returning an existing User");
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

		return userLogingIn;
	}

	public static String getUserSalt(String email) {
		PreparedStatement pstmt = null;
		String userSalt = null;

		con = getConnection();

		if (con != null) {
			String getUserSalt = "SELECT salt FROM users WHERE binary email=?;";

			try {
				pstmt = con.prepareStatement(getUserSalt);
				pstmt.setString(1, email);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) { // returns only one row
					userSalt = rs.getString("salt");
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return userSalt;
	}

	public static boolean queryEmailExistance(String email, String query) {
		PreparedStatement pstmt = null;
		boolean result = true;

		con = getConnection();

		if (con != null) {
			// String query = "SELECT email FROM customers WHERE email=?;";
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, email);

				ResultSet rs = pstmt.executeQuery();

				if (!rs.next()) { // email does not already exist in the
					result = false; // database
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		return result;
	}

	public static boolean emailAlreadyExists(String email) {
		PreparedStatement pstmt = null;
		boolean result = false;

		con = getConnection();

		if (con != null) {
			String query = "SELECT email FROM users WHERE binary email=?;";
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, email);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) { // email already exist in the database
					result = true;
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		return result;
	}

	public static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	/***
	 * Return a new secure hashed password using SHA512 algorithm.
	 * 
	 * @param passwordToHash
	 * @param salt
	 **/
	public static String getSecurePasswordSHA512(String passwordToHash,
			byte[] salt) {
		String securePassword = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] newBytes = md.digest(passwordToHash.getBytes());
			securePassword = bytesToHex(newBytes);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return securePassword;
	}

	/***
	 * Returns an array of size 2 containing a string that is hashed and a
	 * string version of the salt used to create the hashed password for a
	 * specific user. Otherwise, return an array of size two containing an error
	 * message: ["Error", "Cannot get hashed password and salt"].
	 * 
	 * @param password
	 *            that customer entered.
	 **/
	public static String[] getSecurePasswordAndSalt(String passwordToHash) {
		String[] result = new String[2];

		try {
			byte[] salt = User.getSalt();
			String hashedPassword = User.getSecurePasswordSHA512(
					passwordToHash, salt);

			result[0] = hashedPassword;
			result[1] = new String(salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new String[] { "Error",
					"Cannot get secure password and salt." };
		}

		return result;
	}

	/***
	 * Returns a new string version of the byte array. Converts a 16 bytes array
	 * into hexadecimal string of length 128.
	 * 
	 * @param bytes
	 *            a byte array of a hashed password.
	 **/
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/***
	 * Connect to the local database for development purposes. The database must
	 * be named "onlinebookstore". The username is "root" with the password
	 * "admin".
	 **/
	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/onlinebookstore";
		String username = "root";
		String password = "admin";

		// Return existing connection after first call
		if (con != null) {
			return con;
		}

		System.out.println("Getting local connection...");
		try {
			Class.forName("com.mysql.jdbc.Driver");

			System.out.println("Getting database connection");

			con = DriverManager.getConnection(url, username, password);
			if (con != null)
				System.out.println("Connected");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

	public static boolean closeConnection() {
		System.out.println("Closing connection...");

		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		System.out.println("Closed");
		return true;
	}

	public static boolean addNewUser(User newUser) {
		// Get hashed password and salt as a string 2-tuple
		String[] hashedPasswordAndSalt = getSecurePasswordAndSalt(newUser
				.getPassword());

		if (hashedPasswordAndSalt[0].equals("Error")) {
			return false;
		}

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String insertNewCustomer = "INSERT INTO users (first_name, last_name, email, hashed_password, salt, user_role) VALUES (?, ?, ?, ?, ?, ?);";
			try {
				pstmt = con.prepareStatement(insertNewCustomer);
				pstmt.setString(1, newUser.getFirstName());
				pstmt.setString(2, newUser.getLastName());
				pstmt.setString(3, newUser.getEmail());
				pstmt.setString(4, hashedPasswordAndSalt[0]); // hashed password
				pstmt.setString(5, hashedPasswordAndSalt[1]); // salt
				pstmt.setString(6, newUser.getUserRole());
				pstmt.executeUpdate();

				if (pstmt != null)
					pstmt.close();

				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		return false;
	}

	/**
	 * Return users in the users table that have an attribute that have a
	 * substring match with the query.
	 */
	public static ArrayList<User> getUserSearch(String query) {
		ArrayList<User> allUsers = new ArrayList<User>();

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String getAllUsers = "SELECT first_name, last_name, email, user_role FROM users WHERE (first_name like ?) or (last_name like ?) or (user_role like ?);";
				pstmt = con.prepareStatement(getAllUsers);
				pstmt.setString(1, "%" + query + "%");
				pstmt.setString(2, "%" + query + "%");
				pstmt.setString(3, "%" + query + "%");

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String email = rs.getString("email");
					String userRole = rs.getString("user_role");

					allUsers.add(new User(firstName, lastName, email, userRole));
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<User>();
			}
		}
		return allUsers;
	}

	public static ArrayList<User> getUsers() {
		ArrayList<User> allUsers = new ArrayList<User>();

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String getAllUsers = "SELECT first_name, last_name, email, user_role FROM users;";
				pstmt = con.prepareStatement(getAllUsers);

				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String email = rs.getString("email");
					String userRole = rs.getString("user_role");

					allUsers.add(new User(firstName, lastName, email, userRole));
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<User>();
			}
		}
		return allUsers;
	}

	public static ArrayList<Item> getCart(int userId) {
		ArrayList<Item> shoppingCart = new ArrayList<Item>();

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String getShoppingCart = "SELECT book_id, quantity FROM carts WHERE user_id=? ORDER BY date_created ASC;";
				pstmt = con.prepareStatement(getShoppingCart);
				pstmt.setInt(1, userId);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					int bookId = rs.getInt("book_id");
					int quantity = rs.getInt("quantity");

					Book book = Book.getBookById(bookId);
					shoppingCart.add(new Item(book, quantity));
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<Item>();
			}
		}
		return shoppingCart;
	}

	public static void addItems(ArrayList<Item> items, int userId) {

		for (Item item : items) {
			con = getConnection();
			int qnty = 0;

			// Check if item already exist, if it does, get the quantity.
			PreparedStatement pstmt = null;
			if (con != null) {
				try {
					String getQuantity = "SELECT quantity FROM carts WHERE user_id=? and book_id=?;";
					pstmt = con.prepareStatement(getQuantity);
					pstmt.setInt(1, userId);
					pstmt.setInt(2, item.getBook().getBookId());
					ResultSet rs = pstmt.executeQuery();

					if (rs.next()) {
						qnty = rs.getInt("quantity");
					}

					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			// Use qnty to update or insert
			String query = "";
			if (qnty != 0) {
				query = "UPDATE carts SET quantity = ? WHERE user_id=? and book_id=?;";
			} else {
				query = "INSERT INTO carts VALUES (?, ?, ?, NOW());";
			}

			con = getConnection();

			pstmt = null;
			if (con != null) {
				try {
					pstmt = con.prepareStatement(query);
					// UPDATE
					if (qnty != 0) {
						pstmt.setInt(1, qnty + item.getQuantity());
						pstmt.setInt(2, userId);
						pstmt.setInt(3, item.getBook().getBookId());
					} else { // INSERT
						pstmt.setInt(1, userId);
						pstmt.setInt(2, item.getBook().getBookId());
						pstmt.setInt(3, item.getQuantity());
					}

					pstmt.executeUpdate();

					if (pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void addBookToCart(int userId, int bookId) {
		con = getConnection();
		int qnty = 0;

		// Check if item already exist, if it does, get the quantity.
		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String getQuantity = "SELECT quantity FROM carts WHERE user_id=? and book_id=?;";
				pstmt = con.prepareStatement(getQuantity);
				pstmt.setInt(1, userId);
				pstmt.setInt(2, bookId);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					qnty = rs.getInt("quantity");
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Use qnty to update or insert
		String query = "";
		if (qnty != 0) {
			query = "UPDATE carts SET quantity = ? WHERE user_id=? and book_id=?;";
		} else {
			query = "INSERT INTO carts VALUES (?, ?, ?, NOW());";
		}

		con = getConnection();

		pstmt = null;
		if (con != null) {
			try {
				pstmt = con.prepareStatement(query);
				// UPDATE
				if (qnty != 0) {
					pstmt.setInt(1, qnty + 1);
					pstmt.setInt(2, userId);
					pstmt.setInt(3, bookId);
				} else { // INSERT
					pstmt.setInt(1, userId);
					pstmt.setInt(2, bookId);
					pstmt.setInt(3, 1);
				}

				pstmt.executeUpdate();

				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void updateCart(int userId, int bookId, int quantity) {
		String query = "";
		if (quantity > 0) {
			query = "UPDATE carts SET quantity=? WHERE user_id=? and book_id=?;";
		} else {
			query = "DELETE FROM carts WHERE user_id=? and book_id=?;";
		}

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				pstmt = con.prepareStatement(query);
				// UPDATE
				if (quantity > 0) {
					pstmt.setInt(1, quantity);
					pstmt.setInt(2, userId);
					pstmt.setInt(3, bookId);
				} else { // DELETE
					pstmt.setInt(1, userId);
					pstmt.setInt(2, bookId);
				}

				pstmt.executeUpdate();

				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static Pair<Integer, String> getAddress(int userId) {
		con = getConnection();

		PreparedStatement pstmt = null;
		Pair<Integer, String> userAddress = null;
		if (con != null) {
			try {

				String getMostRecentlyUsedAddress = "SELECT * FROM addresses WHERE user_id=? ORDER BY date_added DESC;";

				pstmt = con.prepareStatement(getMostRecentlyUsedAddress);
				pstmt.setInt(1, userId);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					int addressId = rs.getInt("address_id");
					String address1 = rs.getString("address1");
					String address2 = rs.getString("address2");

					if (address2 == null) {
						address2 = "";
					} else {
						address2 = address2 + "<br>";
					}
					String address = address1.toUpperCase() + "<br>"
							+ address2.toUpperCase()
							+ rs.getString("city").toUpperCase() + ", "
							+ rs.getString("state").toUpperCase() + " "
							+ rs.getString("zip").toUpperCase();
					userAddress = new Pair<Integer, String>(addressId, address);
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return userAddress;
	}

	public static void addNewAddress(int userId, String address,
			String address2, String city, String state, int zip) {
		con = getConnection();

		// place Order
		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String addAddress = "INSERT INTO addresses (user_id, address1, address2, city, state, zip, date_added) VALUES (?,?,?,?,?,?, NOW());";
				pstmt = con.prepareStatement(addAddress);
				pstmt.setInt(1, userId);
				pstmt.setString(2, address);
				pstmt.setString(3, address2);
				pstmt.setString(4, city);
				pstmt.setString(5, state);
				pstmt.setInt(6, zip);

				pstmt.executeUpdate();

				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Pair<Integer, String> placeOrder(int userId, int addressId,
			BigDecimal subTotal) {
		con = getConnection();

		// place Order
		PreparedStatement pstmt = null;
		String hash = UUID.randomUUID().toString();
		if (con != null) {
			try {
				String placeOrder = "INSERT INTO orders (hash, total, date_ordered, address_id, user_id) VALUES (?,?, NOW(),?,?);";
				pstmt = con.prepareStatement(placeOrder);
				pstmt.setString(1, hash);
				pstmt.setBigDecimal(2, subTotal);
				pstmt.setInt(3, addressId);
				pstmt.setInt(4, userId);

				pstmt.executeUpdate();

				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Get userId below
		con = getConnection();

		int order_id = -1;
		pstmt = null;
		if (con != null) {
			try {
				String query = "SELECT order_id FROM orders WHERE hash=?;";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, hash);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					order_id = rs.getInt("order_id");
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new Pair<>(order_id, hash);
	}

	public static void emptyCart(int userId, ArrayList<Item> shoppingCart) {
		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String emptyCart = "DELETE FROM carts WHERE user_id=?;";
			try {
				pstmt = con.prepareStatement(emptyCart);
				pstmt.setInt(1, userId);

				pstmt.executeUpdate();

				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void placeBookOrders(int orderId, ArrayList<Item> shoppingCart) {
		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String placeBookOrders = "INSERT INTO orders_books (order_id, book_id, quantity) VALUES (?,?,?);";
				pstmt = con.prepareStatement(placeBookOrders);

				for (Item item : shoppingCart) {
					pstmt.setInt(1, orderId);
					pstmt.setInt(2, item.getBook().getBookId());
					pstmt.setInt(3, item.getQuantity());

					pstmt.executeUpdate();
				}

				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void placePayment(int orderId, String transactionId) {
		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String placePayment = "INSERT INTO payments (order_id, transaction_id, date_created) VALUES (?,?, NOW());";
				pstmt = con.prepareStatement(placePayment);

				pstmt.setInt(1, orderId);
				pstmt.setString(2, transactionId);

				pstmt.executeUpdate();

				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void updateStock(ArrayList<Item> shoppingCart) {
		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String updateStock = "UPDATE books SET stock = stock - ? WHERE book_id=?;";
			try {
				pstmt = con.prepareStatement(updateStock);

				for (Item item : shoppingCart) {
					pstmt = con.prepareStatement(updateStock);
					pstmt.setInt(1, item.getQuantity());
					pstmt.setInt(2, item.getBook().getBookId());

					pstmt.executeUpdate();
				}

				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static String formatAddress(String address1, String address2,
			String city, String state, int zip) {

		if (address2 == null) {
			address2 = "";
		} else {
			address2 = address2 + "<br>";
		}
		String address = address1.toUpperCase() + "<br>"
				+ address2.toUpperCase() + city.toUpperCase() + ", "
				+ state.toUpperCase() + " " + zip;

		return address;

	}

	public static String formatDate(String date) {
		// Remove trailing fractional seconds
		String d = date.substring(0, 19);

		LocalDate formatedDate = LocalDate.parse(d,
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return formatedDate.format(DateTimeFormatter
				.ofPattern("EEEE, MMMM dd, yyyy"));
	}

	public static Order getOrderDetails(String hash) {
		con = getConnection();

		Order order = null;
		PreparedStatement pstmt = null;

		if (con != null) {
			try {
				String query = "SELECT * "
						+ "FROM orders, orders_books, addresses "
						+ "WHERE orders.order_id = orders_books.order_id "
						+ "AND orders.address_id = addresses.address_id "
						+ "AND orders.hash = ?;";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, hash);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					order = new Order(new ArrayList<Item>(),
							rs.getBigDecimal("total"),
							formatDate(rs.getString("date_ordered")), null,
							hash);
					order.setShippingAddress(User.formatAddress(
							rs.getString("address1"), rs.getString("address2"),
							rs.getString("city"), rs.getString("state"),
							rs.getInt("zip")));
				}

				rs.previous();

				while (rs.next()) {
					Book book = Book.getBookById(rs.getInt("book_id"));
					Item item = new Item(book, rs.getInt("quantity"));
					order.addItemOrdered(item);
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return order;
	}

	public static ArrayList<Order> getOrderHistory(int orderId) {
		con = getConnection();
		System.out.println("hello");
		ArrayList<Order> orderList = new ArrayList<Order>();
		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String query = "SELECT * "
						+ "FROM orders, orders_books, addresses "
						+ "WHERE orders.order_id = orders_books.order_id "
						+ "AND orders.address_id = addresses.address_id "
						+ "AND orders.user_id = ? " + "ORDER BY date_ordered "
						+ "DESC, " + "hash DESC;";

				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, orderId);

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					System.out.println("bye");
					String prevHash = rs.getString("hash");

					Order order = new Order(new ArrayList<Item>(),
							rs.getBigDecimal("total"),
							formatDate(rs.getString("date_ordered")), null,
							prevHash);

					Book book = Book.getBookById(rs.getInt("book_id"));
					Item item = new Item(book, rs.getInt("quantity"));
					order.addItemOrdered(item);

					order.setShippingAddress(User.formatAddress(
							rs.getString("address1"), rs.getString("address2"),
							rs.getString("city"), rs.getString("state"),
							rs.getInt("zip")));

					while (rs.next()) {
						String currHash = rs.getString("hash");
						if (prevHash.equals(currHash)) {
							Book b = Book.getBookById(rs.getInt("book_id"));
							Item i = new Item(b, rs.getInt("quantity"));
							// The current book belongs to the same order
							order.addItemOrdered(i);
						} else {
							break;
						}
					}

					orderList.add(order);

					rs.previous();

				}
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return orderList;
	}

	public static BigDecimal getOrdersTotal() {
		con = getConnection();
		BigDecimal total = new BigDecimal("-1");

		// Check if item already exist, if it does, get the quantity.
		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String getTotal = "SELECT " + "SUM(total) as revenue "
						+ "FROM orders;";
				pstmt = con.prepareStatement(getTotal);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					total = rs.getBigDecimal("revenue");
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return total;
	}

	public static void main(String args[]) {
		try {
			System.out.println(Integer.parseInt(null));
		} catch (NumberFormatException e) {

		}
		// addNewAddress(29, "4748 Flower West Dr.", "Studio #5", "Box House",
		// "ND", 49499);
		// System.out.println(getOrdersTotal());
		// for (Order order : getOrderHistory(113)) {
		// System.out.println(order);
		// }
		// System.out
		// .println(getOrderDetails("61b2d54d-f671-4d5f-ab25-40bcf6ea21bb"));
		// LocalDate.now().plusDays(5)
		// .format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy"));
		// String str = "2016-03-04 11:30:40";
		// DateTimeFormatter formatter =
		// DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		// LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		// System.out.println(dateTime.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")));
		// LocalDate date = LocalDate.parse(str,
		// DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		// System.out.println(date.format(DateTimeFormatter
		// .ofPattern("EEEE, MMMM dd, yyyy")));
	}
}
