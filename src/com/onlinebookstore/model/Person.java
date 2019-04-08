package com.onlinebookstore.model;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.xml.bind.DatatypeConverter;

public abstract class Person {
	private String firstName, lastName, email, hashedPassword, salt;

	public Person() {
	}

	// Constructor to create a new person.
	public Person(String firstName, String lastName, String email,
			String password, String salt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.hashedPassword = password;
		this.salt = salt;
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

	public String getHashedPassword() {
		return hashedPassword;
	}

	public String getPersonSalt() {
		return salt;
	}

	/**
	 * Return a random byte array of size 16.
	 * 
	 * @throws NoSuchAlgorithmException
	 * */
	public static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	/**
	 * Return a new secure hashed password using SHA512 algorithm.
	 * 
	 * @param passwordToHash
	 * @param salt
	 */
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

	/**
	 * Returns an array of size 2 containing a string that is hashed and a
	 * string version of the salt used to create the hashed password for a
	 * specific user. Otherwise, return an array of size two containing an error
	 * message: ["Error", "Cannot get hashed password and salt"].
	 * 
	 * @param password
	 *            that customer entered.
	 */
	public static String[] getSecurePasswordAndSalt(String passwordToHash) {
		String[] result = new String[2];

		try {
			byte[] salt = Person.getSalt();
			String hashedPassword = Person.getSecurePasswordSHA512(
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

	/**
	 * Returns a new string version of the byte array. Converts a 16 bytes array
	 * into hexadecimal string of length 128.
	 * 
	 * @param bytes
	 *            a byte array of a hashed password.
	 */
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

	public static void main(String args[]) throws NoSuchAlgorithmException {
		// String pass = "green";
		// System.out.println(pass);
		// byte[] salt = getSalt();
		// System.out.println(salt);
		// System.out.println(new String(salt));
		// String hash = (getSecurePasswordSHA512(pass, salt));
		// System.out.println(hash);
		// String slt = new String(salt);
		// System.out.println(slt);
		// byte[] bslt = slt.getBytes();
		// System.out.println(bslt);
		// String hash2 = getSecurePasswordSHA512(pass, (bslt));
		// System.out.println(hash2);
		// System.out.println(hash.equals(hash2));

	}
}
