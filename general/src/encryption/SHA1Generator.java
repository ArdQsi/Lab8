package encryption;

import exceptions.EncryptionException;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;


public class SHA1Generator {
    public static String hash(String text) throws EncryptionException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = messageDigest.digest(text.getBytes());
            return Hex.encodeHexString(bytes);
        } catch (Exception e) {
            throw new EncryptionException();
        }
    }
}
