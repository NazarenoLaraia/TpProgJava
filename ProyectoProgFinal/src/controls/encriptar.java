package controls;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class encriptar {

    public String encriptado(String password) {
        String password1 = password;
        byte[] newPassword = null;
        try {
            newPassword = MessageDigest.getInstance("SHA").digest(password.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        String encriptado = Base64.getEncoder().encodeToString(newPassword);
        System.out.println(encriptado);
        return encriptado;
    }
}


