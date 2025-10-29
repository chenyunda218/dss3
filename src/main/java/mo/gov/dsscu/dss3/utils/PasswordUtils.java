package mo.gov.dsscu.dss3.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

public class PasswordUtils {
  private static final int SALT_LENGTH = 16;

  public static String generateSalt() {
    return generateRandomString(SALT_LENGTH);
  }

  public static String hashPassword(String password, String salt) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      byte[] hashBytes = md.digest(password.getBytes());
      BigInteger number = new BigInteger(1, hashBytes);
      StringBuilder hexString = new StringBuilder(number.toString(16));
      while (hexString.length() < 32) {
        hexString.insert(0, '0');
      }
      return hexString.toString();
    } catch (Exception e) {
      return "";
    }
  }

  public static boolean verifyPassword(String password, String storedHash, String salt) {
    return hashPassword(password, salt).equals(storedHash);
  }

  public static String generateRandomString(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder sb = new StringBuilder(length);
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(characters.length());
      sb.append(characters.charAt(index));
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    String salt = generateSalt();
    String password = hashPassword("helloworld", salt);
    System.out.println(password);
    System.out.println(salt);
  }

}
