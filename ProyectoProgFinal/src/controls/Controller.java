package controls;

import encriptadoaes.AES;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import obje.actualizar;
import obje.usuario;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;


public class Controller {
    @FXML
    private TextField txtNombregrupo;
    @FXML
    private Button btnasistencia;
    @FXML
    private Button btnusuario;
    @FXML
    private Button btncrear;

    @FXML
    private Button btnmod;

    @FXML
    private TextField txtidusers;

    @FXML
    private Button btndelete;
    @FXML
    private AnchorPane menuanchor;
    @FXML
    private Button btnlogin;
    @FXML
    private Button btngrupos;
    @FXML
    private TextField txtusuariologin;
    @FXML
    private TextField txtpasswordlogin;
    @FXML
    private TableView tableview;
    @FXML
    private AnchorPane anchorCrear;
    @FXML
    private AnchorPane anchorReal;
    @FXML
    private AnchorPane anchorlogin;
    @FXML
    private Button btnmenucrear;
    @FXML
    private TextField txtusuarioCrear;
    @FXML
    private TextField txtpasswordCrear;
    @FXML
    private TextField txtmailCrear;
    @FXML
    private TextField txtnombreasist;
    @FXML
    private TextField txtdniasist;
    @FXML
    private DatePicker dpfecharegist;
    @FXML
    private Button btnRegistAsist;
    @FXML
    private Button btnAtrasAsist;
    @FXML
    private AnchorPane anchorRegistAsist;
    @FXML
    private AnchorPane anchorGrupos;
    @FXML
    private TableView tbPermisos;
    @FXML
    private Button btnGrupoagregar;
    @FXML
    private Button btnGrupoatras;
    @FXML
    private Button btnusuarioBaja;
    @FXML
    private Button btnusuarioMod;
    @FXML
    private Button btncreargrupo;
    @FXML
    private ComboBox cmbgrupocrear;
    @FXML
    private TableView tbcrearusuario;
    @FXML
    private TextField txtidgrupocrear;
    @FXML
    private AnchorPane anchorMod;
    @FXML
    private TextField txtusuarioMod;
    @FXML
    private TextField txtpasswordMod;
    @FXML
    private TextField txtmailMod;
    @FXML
    private TableView tbusuarioMod;
    @FXML
    private Button btnaccionMod;
    @FXML
    private Button btnaccionCancelarMod;
    @FXML
    private TextField txtgrupoMod;
   @FXML
   private Button btnaccionEliminar;
   @FXML
   private TextField txtusuarioEliminar;
   @FXML
   private AnchorPane anchorBorrar;
    @FXML private TableColumn<usuario,Number> clmid;
    @FXML private TableColumn<usuario,String> clmusuario;
    @FXML private TableColumn<usuario,String> clmpassword;
    @FXML private TableColumn<usuario,String> clmmail;
    @FXML private TableColumn<usuario,Number> clmidgrupo;

    private ArrayList<String> caract = new ArrayList<>();
    private ArrayList<String> grupoPermisos = new ArrayList<>();


    private String objetodefinido;
    private String pass1;
    private Integer idusers;
    private String pass2;
    private String mailencryp;
    private String mailDecryp;
    private String grupoPermiso;
    private int id_grupo;
    private int idgrupoFiltrar;
    private String grupocrear;
    private ObservableList<usuario> listausu;
    private int[] permisos= new int[9];


    public void initialize() throws SQLException {
        anchorReal.setVisible(true);
        anchorlogin.setVisible(true);
        menuanchor.setVisible(false);
        anchorCrear.setVisible(false);
        anchorMod.setVisible(false);
        txtidusers.setDisable(true);
        //anchorBorrar.setVisible(false);
        objetodefinido = "asistencias";
        cargaLista();
        desactivarBotones();



    }
    public void desactivarBotones(){
        btnmenucrear.setVisible(false);
        btnusuario.setVisible(false);
        btngrupos.setVisible(false);
        btncreargrupo.setVisible(false);
        btnRegistAsist.setVisible(false);
        btnusuarioBaja.setVisible(false);
        btnusuarioMod.setVisible(false);
        btnasistencia.setVisible(false);

    }

    public void btnlogin(){
        encriptar encrip= new encriptar();
        pass2=encrip.encriptado(txtpasswordlogin.getText());
        validarlogin(txtusuariologin.getText(),pass2);

        //menuanchor.setVisible(true);
      //anchorlogin.setVisible(false);

    }
    public void btnmenucrear() throws SQLException{
        anchorCrear.setVisible(true);
        anchorlogin.setVisible(false);
        menuanchor.setVisible(false);
        listar("grupos");
        gruposcrear();
    }
    public void btncreargrupo() throws SQLException{

        anchorGrupos.setVisible(true);
        menuanchor.setVisible(false);
        tbcrearusuario.getItems().clear();
        listar("permisos");
        cargaPermisos();


    }
    public void btngrupoAgregar()throws SQLException{
        //cargaPermisos();
       crearGrupo();

    }
    public void btngrupoatras() throws SQLException{
        anchorGrupos.setVisible(false);
        menuanchor.setVisible(true);
        tableview.getItems().clear();
        tableview.getColumns().clear();

    }
    public void btnusuario() throws SQLException{
        listar("users");
        cargaLista();
    }
    public void btngrupos() throws SQLException{
        listar("grupos");
        cargaLista();
    }

    public void btnasistencia() throws  SQLException{
        tableview.getItems().clear();
        listar("asistencias");
        cargaLista();
    }

    public void btnatrasCrear(){
        txtusuarioCrear.setText("");
        txtpasswordCrear.setText("");
        txtmailCrear.setText("");
        anchorCrear.setVisible(false);
        menuanchor.setVisible(true);
        anchorlogin.setVisible(false);

    }
    public void btnRegistAsist(){
        menuanchor.setVisible(false);
        anchorRegistAsist.setVisible(true);
        dpfecharegist.setValue(LocalDate.now());
        dpfecharegist.setDisable(true);
    }
    public void btnaccionregist(){
       if (txtnombreasist.getText().isEmpty() || txtnombreasist.getText().length()<5){
           mostraralerta("error al grabar","el nombre no puede estar vacio o ser menos de 5 caracteres");
           txtnombreasist.requestFocus();
           return;
       }
       if (!isnumeric(txtdniasist.getText())){
           mostraralerta("error","el dni debe ser numerico");
           txtdniasist.requestFocus();
           return;
       }
       if (txtdniasist.getText().length()!=8){
           mostraralerta("error","el dni debe contener 8 caracteres ");
           txtdniasist.requestFocus();
           return;
       }
       InsertarAsistencia();

    }
    public void btnAtrasAsist(){


        anchorRegistAsist.setVisible(false);
        menuanchor.setVisible(true);
    }


    public void btncreate () throws SQLException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
       //usuario usr = new usuario(0,txtusuarioCrear.getText(),txtpasswordCrear.getText());
        if (txtusuarioCrear.getText().equals("") || txtusuarioCrear.getText().length()<5){
            mostraralerta("error","el usuario debe estar completo y debe contener mas de 5 caracteres");
            txtusuarioCrear.requestFocus();
            return;
        }
        if (txtpasswordCrear.getText().equals("") || txtpasswordCrear.getText().length()<8){
            mostraralerta("error","la contrasenia debe estar completa y debe contener mas de 8 caracteres");
            txtpasswordCrear.requestFocus();
            return;
        }
        encriptar encr = new encriptar();
        encriptadoaes.AES aes = new encriptadoaes.AES();
        pass1=encr.encriptado(txtpasswordCrear.getText());
        mailencryp= aes.encriptar(txtmailCrear.getText());
        Insertarusuario();
        anchorCrear.setVisible(false);
        menuanchor.setVisible(true);
        anchorlogin.setVisible(false);
    }


    public void btnusuarioMod() throws SQLException{
        menuanchor.setVisible(false);
        anchorMod.setVisible(true);
       //btnaccionMod.setDisable(true);
       // listar("users");
       // btnBuscarMod();
        //creargrilla();
       //.cargarUsuarioMod();
        listausu = FXCollections.observableArrayList();
        usuario.llenarInformacionAlumnos(listausu);
        tbusuarioMod.setItems(listausu);
        clmid.setCellValueFactory(new PropertyValueFactory<usuario, Number>("idusers"));
        clmusuario.setCellValueFactory(new PropertyValueFactory<usuario, String>("usuario"));
        clmpassword.setCellValueFactory(new PropertyValueFactory<usuario, String>("password"));
        clmmail.setCellValueFactory(new PropertyValueFactory<usuario, String>("mail"));
        clmidgrupo.setCellValueFactory(new PropertyValueFactory<usuario, Number>("idgrupo"));
        gestionarEventos();


    }
    public void setidUsuario(int idusers) throws SQLException{
            Connection Con= null;
            this.idusers= idusers;
            AES aes = new AES();
            try{
                Con = controls.bdcfg.getConnection();
                PreparedStatement ps1 = Con.prepareStatement("select idusers,usuario,password,mail,idgrupo from users where idusers='"+idusers+"");
                ResultSet rs1=ps1.executeQuery();

                while (rs1.next()){
                    txtusuarioMod.setText(rs1.getString("usuario"));
                    txtpasswordMod.setText(rs1.getString("password"));
                    txtmailMod.setText(aes.desencriptar(rs1.getString("mail")));

                }
            }catch (ClassNotFoundException e){
                System.out.println(e);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
    }
    public void btnCancelarMod(){
        anchorMod.setVisible(false);
        menuanchor.setVisible(true);
    }
    public void btnusuarioBaja(){
        anchorBorrar.setVisible(true);
        menuanchor.setVisible(false);
    }
    public void btnCancelarBorrar(){
        anchorBorrar.setVisible(false);
        menuanchor.setVisible(true);
    }
    public void btnaccionEliminar(){
        try {
            eliminarRegistro();
            tbusuarioMod.getItems().clear();
            usuario.llenarInformacionAlumnos(listausu);
            tbusuarioMod.setItems(listausu);
        }catch (Exception e){
            mostraralerta("error","error al borrar");
        }
    }
    public void eliminarRegistro() {

        Connection Con = null;
        try {
            Con = controls.bdcfg.getConnection();
            PreparedStatement ps1 = Con.prepareStatement("DELETE FROM users WHERE idusers= ?");
            ps1.setString(1,txtidusers.getText());
            ps1.execute();
            mostrarInfo("info","se pudo eliminar el usuario");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet btnBuscarMod() throws SQLException{

        Connection Con = null   ;
        try {
            Con =  controls.bdcfg.getConnection();
            System.out.println(Con.isClosed());
            PreparedStatement ps1=Con.prepareStatement("select * from users where usuario='"+txtusuarioMod.getText()+"'");
            ResultSet rs1=ps1.executeQuery();
            return rs1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public void btnaccionMod(){

       try{
           modificarUsuario();
           tbusuarioMod.getItems().clear();
           usuario.llenarInformacionAlumnos(listausu);
           tbusuarioMod.setItems(listausu);
       }catch (Exception e){
           mostraralerta("error","error al actualizar");
       }


    }
    public void modificarUsuario(){
        usuario usu = new usuario(Integer.valueOf(txtidusers.getText()),txtusuarioMod.getText(),txtpasswordMod.getText(),txtmailMod.getText(),Integer.valueOf(txtgrupoMod.getText()));
       Integer id = Integer.parseInt(txtidusers.getText());
       AES aes = new AES();
        Connection Con=null;
        encriptar encr = new encriptar();
        try {
            Con = controls.bdcfg.getConnection();
            PreparedStatement ps1= Con.prepareStatement("update users set usuario=?, password=?, mail=?, idgrupo=? where idusers=?");
            ps1.setString(1,txtusuarioMod.getText());
            ps1.setString(2,encr.encriptado(txtpasswordMod.getText()));
            ps1.setString(3,aes.encriptar(txtmailMod.getText()));
            ps1.setString(4,txtgrupoMod.getText());
            ps1.setInt(5,id);
            ps1.executeUpdate();
            mostrarInfo("actualizar","salio todo joya ");
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    public ResultSet listar (String objeto){
        Connection Con = null   ;
        try {
            Con =  controls.bdcfg.getConnection();
            System.out.println(Con.isClosed());
            PreparedStatement ps1=Con.prepareStatement("select * from "+objeto );
            ResultSet rs1=ps1.executeQuery();
            return rs1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            //  try { Con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void cargaLista() throws SQLException {
        // Model.Listados U = new .Listados();

        ResultSet rs = listar(objetodefinido);


        tableview.getColumns().clear();
        tableview.getItems().clear();


        System.out.println(        rs.getMetaData().getColumnCount());

        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tableview.getColumns().addAll(col);
            // System.out.println("Column ["+i+"] ");

            ObservableList<ObservableList> data;
            data = FXCollections.observableArrayList();


            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int n = 1; n <= rs.getMetaData().getColumnCount(); n++) {
                    row.add(rs.getString(n));
                }

                data.add(row);

            }
            tableview.getItems().addAll(data);
            //data = null;

        }

    }
    public void cargaPermisos() throws SQLException {
        // Model.Listados U = new .Listados();

        ResultSet rs = listar(objetodefinido);


        tbPermisos.getColumns().clear();
        tbPermisos.getItems().clear();
        tbPermisos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        System.out.println(        rs.getMetaData().getColumnCount());

        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tbPermisos.getColumns().addAll(col);
            // System.out.println("Column ["+i+"] ");

            ObservableList<ObservableList> data;
            data = FXCollections.observableArrayList();


            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int n = 1; n <= rs.getMetaData().getColumnCount(); n++) {
                    row.add(rs.getString(n));
                }

                data.add(row);

            }
            tbPermisos.getItems().addAll(data);
            //data = null;

        }

    }
    public void gruposcrear() throws SQLException {
        // Model.Listados U = new .Listados();

        ResultSet rs = listar(objetodefinido);


        tbcrearusuario.getColumns().clear();
        tbcrearusuario.getItems().clear();
       // tbcrearusuario.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



        System.out.println(        rs.getMetaData().getColumnCount());

        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tbcrearusuario.getColumns().addAll(col);
            // System.out.println("Column ["+i+"] ");

            ObservableList<ObservableList> data;
            data = FXCollections.observableArrayList();


            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int n = 1; n <= rs.getMetaData().getColumnCount(); n++) {
                    row.add(rs.getString(n));
                }

                data.add(row);

            }
            tbcrearusuario.getItems().addAll(data);
            //data = null;

        }

    }
    public void crearGrupo() throws SQLException{
        grupoPermiso = tbPermisos.getSelectionModel().getSelectedItems().toString();

        for (int i=0;i<grupoPermiso.length();i++){
            caract.add(i,String.valueOf(grupoPermiso.charAt(i)));
            System.out.println(caract.get(i));
        }

        for (int i=0;i<caract.size();i++){

            if (isnumeric(caract.get(i))){
                grupoPermisos.add(caract.get(i));
            }
        }
        Connection Con = null;
        ResultSet rs;

        try {
            Con= controls.bdcfg.getConnection();
            PreparedStatement ps1=Con.prepareStatement("insert into grupos (nombregrupo) value('"+txtNombregrupo.getText()+"');");
            ps1.execute();
        }catch (ClassNotFoundException e){e.printStackTrace();
            System.out.println("rompe parte1");}


       try{
           Con = controls.bdcfg.getConnection();
           PreparedStatement ps1= Con.prepareStatement("select idgrupo from grupos where nombregrupo = '"+txtNombregrupo.getText()+"'");
           rs = ps1.executeQuery();
           while(rs.next()){
               rs.getRow();
               id_grupo = rs.getInt(1);
               System.out.println("id_grupo"+id_grupo);

           }
       }catch (ClassNotFoundException e){e.printStackTrace();
           System.out.println(" rompe parte 2");}

       for (int i=0;i< grupoPermisos.size();i++){

          try {
              Con = controls.bdcfg.getConnection();
              PreparedStatement ps2 = Con.prepareStatement("insert into grupo_permisos (idgrupo,idpermiso) values ('" + id_grupo + "','" + grupoPermisos.get(i) + "')");
              ps2.execute();
          }catch (ClassNotFoundException ex){ex.printStackTrace();
              System.out.println("rompe parte 3");}


       }

    }
    public void filtrarPermisos(int id_grupo) throws SQLException{
        ResultSet rs;
        Connection Con = null;
        try{
            Con = controls.bdcfg.getConnection();
            PreparedStatement ps1= Con.prepareStatement("select idpermiso from grupo_permisos where idgrupo='"+id_grupo+"'");
            rs=ps1.executeQuery();


            while (rs.next()){
                rs.getRow();
                permisos[rs.getRow()-1] = rs.getInt(1);
                System.out.println(permisos[rs.getRow()-1]);

            }

        }catch (ClassNotFoundException e){e.printStackTrace();}

        for (int i=0;i<permisos.length;i++){
            switch (permisos[i]){
                case 1: btnmenucrear.setVisible(true);
                    break;
                case 2: btnusuarioBaja.setVisible(true);
                    break;
                case 3: btnasistencia.setVisible(true);
                    break;
                case 4: btnRegistAsist.setVisible(true);
                    break;
                case 5: btncreargrupo.setVisible(true);
                    break;
                case 6: btnusuarioMod.setVisible(true);
                    break;
                case 7: btngrupos.setVisible(true);
                    break;
                case 8: btnusuario.setVisible(true);
                    break;

            }
        }


    }

    public void Insertarusuario(){
        Connection Con = null   ;
       // idgrupoFiltrar = tbcrearusuario.getSelectionModel().getSelected;
        try {

            Con =  controls.bdcfg.getConnection();
            System.out.println(Con.isClosed());
            PreparedStatement ps1=Con.prepareStatement("Insert into users (usuario,password,mail,idgrupo) values (?,?,?,?)" );
            ps1.setString(1,txtusuarioCrear.getText());
            ps1.setString(2,pass1);
            ps1.setString(3,mailencryp);
            ps1.setString(4,txtidgrupocrear.getText());
            ps1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { Con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void InsertarAsistencia(){
        Connection Con = null   ;
        try {

            Con =  controls.bdcfg.getConnection();
            System.out.println(Con.isClosed());
            PreparedStatement ps1=Con.prepareStatement("Insert into asistencias (nombreprof,dniprof,fecha) values (?,?,?)" );
            ps1.setString(1,txtnombreasist.getText());
            ps1.setString(2,txtdniasist.getText());
            ps1.setString(3,dpfecharegist.getValue().toString());
            ps1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { Con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void mostraralerta(String tit, String msj){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(tit);
        alerta.setContentText(msj);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
    public void mostrarInfo(String tit,String msj){
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle(tit);
        alert1.setContentText(msj);
        alert1.setHeaderText(null);
        alert1.showAndWait();
    }


    @FXML
    private void DefineObjeto(ActionEvent ae)  throws SQLException{

        try {
            Button BTN = (Button) ae.getSource();
            objetodefinido= BTN.idProperty().getValue();
            System.out.println(objetodefinido);
            cargaLista();
        }
        catch (Exception E)
        {
            System.out.println(E.getMessage());
        }
    }
    public static boolean isnumeric( String dato){

       try {
           Integer.parseInt(dato);
           return true;
       }catch (NumberFormatException e){
           return false;
       }
    }



    public boolean validarlogin(String usuario,String pass2){
        Connection Con = null   ;
        ResultSet rs;
        //usuario=txtusuariologin.getText();
       // encriptar encrip= new encriptar();
       // pass2=encrip.encriptado(txtpasswordlogin.getText());
        try {

            Con =  controls.bdcfg.getConnection();
            System.out.println(Con.isClosed());
            PreparedStatement ps1=Con.prepareStatement("select idgrupo from users where usuario=? and password=?" );
            ps1.setString(1,usuario);
            ps1.setString(2,pass2);

            rs=ps1.executeQuery();


            if (rs.next()){
                menuanchor.setVisible(true);
                anchorlogin.setVisible(false);
                filtrarPermisos(rs.getInt(1));

                return true;
            }
            mostraralerta("error","datos incorrectos");
            return false;

        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }
    public void cargarUsuarioMod() throws SQLException {
        // Model.Listados U = new .Listados();

        ResultSet rs = listar("users");


        tbusuarioMod.getColumns().clear();
        tbusuarioMod.getItems().clear();
        //tbusuarioMod.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        System.out.println(        rs.getMetaData().getColumnCount());

        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tbusuarioMod.getColumns().addAll(col);
            // System.out.println("Column ["+i+"] ");

            ObservableList<ObservableList> data;
            data = FXCollections.observableArrayList();


            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int n = 1; n <= rs.getMetaData().getColumnCount(); n++) {
                    row.add(rs.getString(n));
                }

                data.add(row);

            }
            tbusuarioMod.getItems().addAll(data);
            //data = null;

        }

    }
    /*public void creargrilla(){
        tbusuarioMod.getColumns().clear();
        tbusuarioMod.getItems().clear();
        TableColumn col1= new TableColumn("idusers");
        col1.setCellValueFactory(new PropertyValueFactory<>("idusers"));
        tbusuarioMod.getColumns().add(col1);
        TableColumn col2= new TableColumn("usuario");
        tbusuarioMod.getColumns().add(col2);
        col2.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        TableColumn col3= new TableColumn("password");
        tbusuarioMod.getColumns().add(col3);
        col3.setCellValueFactory(new PropertyValueFactory<>("password"));
        TableColumn col4= new TableColumn("mail");
        col4.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tbusuarioMod.getColumns().add(col4);
        TableColumn col5= new TableColumn("idgrupo");
        col5.setCellValueFactory(new PropertyValueFactory<>("idgrupo"));
        tbusuarioMod.getColumns().add(col5);
        List<usuario> listUsu = usuario.listusu();
        ObservableList<usuario> Lista =FXCollections.observableArrayList(listUsu);
        tbusuarioMod.setItems(Lista);
    }*/

    public void gestionarEventos(){
        AES aes = new AES();
        tbusuarioMod.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<usuario>() {
                    @Override
                    public void changed(ObservableValue<? extends usuario> arg0,
                                        usuario valorAnterior, usuario valorSeleccionado) {


                        if (valorSeleccionado!=null){
                            txtidusers.setText(String.valueOf(valorSeleccionado.getIdusers()));
                            txtusuarioMod.setText(valorSeleccionado.getUsuario());
                            txtpasswordMod.setText("");
                            txtmailMod.setText(valorSeleccionado.getMail());
                            txtgrupoMod.setText(String.valueOf(valorSeleccionado.getIdgrupo()));

                    }
                    }

                }
        );
    }


}
