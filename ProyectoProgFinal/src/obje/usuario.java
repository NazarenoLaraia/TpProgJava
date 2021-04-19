package obje;

import encriptadoaes.AES;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.IllegalCharsetNameException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class usuario {
    private final SimpleIntegerProperty idusers;
    private final SimpleStringProperty usuario;
    private final SimpleStringProperty password;
    private final SimpleStringProperty mail;
     private final SimpleIntegerProperty idgrupo;

    public int getIdgrupo() {
        return idgrupo.get();
    }

    public SimpleIntegerProperty idgrupoProperty() {
        return idgrupo;
    }

    public void setIdgrupo(int idgrupo) {
        this.idgrupo.set(idgrupo);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public SimpleStringProperty usuarioProperty() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getIdusers() {
        return idusers.get();
    }

    public SimpleIntegerProperty idusersProperty() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers.set(idusers);
    }

    public String getMail() {
        return mail.get();
    }

    public SimpleStringProperty mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

     public usuario(Integer idusers, String usuario, String password, String mail, Integer idgrupo) {
           this.idusers = new SimpleIntegerProperty(idusers);
            this.usuario = new SimpleStringProperty(usuario);
            this.password = new SimpleStringProperty(password);
            this.mail = new SimpleStringProperty(mail);
            this.idgrupo = new SimpleIntegerProperty(idgrupo);
        }
/*
    public usuario(SimpleIntegerProperty id, SimpleStringProperty usuario, SimpleStringProperty password, SimpleStringProperty mail, SimpleIntegerProperty idgrupo) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.mail = mail;
        this.idgrupo = idgrupo;
    }

   /*
  public usuario(){
     this.id = new SimpleIntegerProperty(0);
     this.usuario = new SimpleStringProperty("");
     this.password = new SimpleStringProperty("");
     this.mail = new SimpleStringProperty("");
     this.idgrupo = new SimpleIntegerProperty(0);
 }*/

  /* public static  List<usuario> listusu () {
       Connection Con = null;
       List<usuario> listUsu = new ArrayList<>();
       encriptadoaes.AES aes = new encriptadoaes.AES();
       try{
           Con = controls.bdcfg.getConnection();
           PreparedStatement ps1 = Con.prepareStatement("SELECT * from users");
           ResultSet rs1= ps1.executeQuery();
           while (rs1.next()){
               obje.usuario usu = new usuario();
               usu.setId(rs1.getInt("idusers"));
               usu.setUsuario(rs1.getString("usuario"));
               usu.setPassword(rs1.getString("password"));
               usu.setMail(aes.desencriptar(rs1.getString("mail"),"naza"));
               usu.setIdgrupo(rs1.getInt("idgrupo"));
              listUsu.add(usu);
           }

       }catch (SQLException |ClassNotFoundException e){
           System.out.println(e);

       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       } catch (InvalidKeyException e) {
           e.printStackTrace();
       } catch (NoSuchPaddingException e) {
           e.printStackTrace();
       } catch (BadPaddingException e) {
           e.printStackTrace();
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       } catch (IllegalBlockSizeException e) {
           e.printStackTrace();
       }
        return listUsu;
   }*/
    public static void llenarInformacionAlumnos( ObservableList<usuario> lista){
            Connection Con = null;
        encriptadoaes.AES aes = new AES();

        try {
           Con= controls.bdcfg.getConnection();
           PreparedStatement ps1 = Con.prepareStatement("SELECT " +
                   " idusers ," +
                   " usuario ," +
                   "password ,"+
                   " mail, " +
                   " idgrupo" +
                   " FROM " +
                   "users");
           ResultSet rs1=ps1.executeQuery();
            while (rs1.next()){
                lista.add(
                        new usuario(rs1.getInt("idusers"), rs1.getString("usuario"), rs1.getString("password"),aes.desencriptar(rs1.getString("mail")) , rs1.getInt("idgrupo"))
                        )
                ;

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
