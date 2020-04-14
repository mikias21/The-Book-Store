package utils;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

// utility class for doing several common things
public class BookUtility{
    // class fields
    private static SecureRandom secureRandom = new SecureRandom();
    // alphabet string for hashing password
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuv";
    private static final int ITERATIONS = 1000; // number of iterations
    private static final int KEY_LENGTH = 256; // key length of the hashed password
    private static final int SALT_LENGTH = 30; // salt length for hashing password

    // method to get salt
    public static String getSalt(){
        StringBuilder stringBuilder = new StringBuilder(); //string builder object
        for (int i = 0 ; i < SALT_LENGTH ; i++){ // iterates 30 times
            //appends random alphabet character from the alphabet string into the stringBuilder object
            stringBuilder.append(ALPHABET.charAt(secureRandom.nextInt(ALPHABET.length())));
        }
        return stringBuilder.toString();
    }

    // hash the password and return as a byte takes password and salt as a parameter
    public static byte[] hash(char[] password , byte[] salt){
        // create PBEKeySpec for the password with the plane password and salt iterations and key lenfth
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password , salt , ITERATIONS , KEY_LENGTH);
        Arrays.fill(password , Character.MIN_VALUE);
        try {
            // creates Secretkey instance
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(pbeKeySpec).getEncoded(); // returns secret key with the pbeKeySpec provided from above
        } catch (Exception e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            pbeKeySpec.clearPassword(); // clears password
        }
    }

    //method generates secure password from the salt and plane password
    public static String generateSecurePassword(String password, String salt) {
        String returnValue = null;

        byte[] securePassword = hash(password.toCharArray(), salt.getBytes()); // hashes the password with the salt as byte data

        returnValue = Base64.getEncoder().encodeToString(securePassword); // set the return value to base64 encoded string

        return returnValue;
    }

    // method to get current time and date
    public static String getCurrentDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
        Date date = new Date();
        java.lang.String currentDate = dateFormat.format(date);
        return currentDate;
    }

    // verify plane password with hashed password specially during Logging in
    public static boolean verifyUserPassword(String providedPassword,
                                             String securedPassword, String salt)
    {
        boolean returnValue = false;

        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        // Check if two passwords are equal
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }
}
