import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * PasswordUtil — simple but secure password hashing with SHA-256 + random salt.
 *
 * Format stored in DB:  BASE64(salt) + "$" + BASE64(SHA256(salt + password))
 *
 * For production, consider upgrading to BCrypt via a library like
 * org.mindrot:jbcrypt — just add the jar to WEB-INF/lib.
 */
public class PasswordUtil {

    private static final int SALT_BYTES = 16;

    /**
     * Hashes a plain-text password with a random salt.
     * @return the hash string to store in the database
     */
    public static String hash(String plainPassword) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);

        byte[] hash = sha256(salt, plainPassword);
        return Base64.getEncoder().encodeToString(salt) + "$" +
               Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Verifies a plain-text password against a stored hash.
     */
    public static boolean verify(String plainPassword, String storedHash) {
        if (storedHash == null || !storedHash.contains("$")) return false;
        String[] parts = storedHash.split("\\$", 2);
        if (parts.length != 2) return false;
        try {
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] expected = Base64.getDecoder().decode(parts[1]);
            byte[] actual   = sha256(salt, plainPassword);
            return MessageDigest.isEqual(expected, actual); // constant-time compare
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] sha256(byte[] salt, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            md.update(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
