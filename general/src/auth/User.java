package auth;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import encryption.SHA1Generator;
import exceptions.EncryptionException;
import org.apache.commons.codec.binary.Hex;

public class User implements Serializable {

    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        try {
            this.password = SHA1Generator.hash(password);
        } catch (EncryptionException e) {
            this.password = null;
        }
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return getLogin();
    }

}
