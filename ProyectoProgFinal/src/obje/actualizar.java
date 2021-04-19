package obje;

import controls.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class actualizar {


/*

    @FXML private TableColumn<Alumno,Number> clmDni;
    @FXML private TableColumn<Alumno,String> clmNombre;
    @FXML private TableColumn<Alumno,String> clmApellido;
    @FXML private TableColumn<Alumno,String> clmSexo;
    @FXML private TableColumn<Alumno,Number> clmcarrera;

    //Componentes GUI
    @FXML private TextField txtdni;
    @FXML private TextField txtnombre;
    @FXML private TextField txtapellido;
    @FXML private RadioButton rbtFemenino;
    @FXML private RadioButton rbtMasculino;
    @FXML private ComboBox cmbcarrera;
    @FXML private Label dniObli;
    @FXML private Label nombreObli;
    @FXML private Label apeObli;
    @FXML private Button btnguardar;
    @FXML private Button btneliminar;
    @FXML private Button btnmodificar;
    private boolean puedeGuardar;
    @FXML
    private AnchorPane FormArea;

    @FXML private TableView<Alumno> tblViewAlumnos;

    //Colecciones
    private ObservableList<Alumno> listaAlumnos;
    private Conexion conexion;

    ObservableList<String> Carreras = FXCollections.observableArrayList("Analista de Sistemas", "Diseño Industrial", "Seguridad e Higiene","Enfermería");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnmodificar.setVisible(false);
        btneliminar.setVisible(false);
        //Instanciamos primero la conexion
        conexion = new Conexion();
        conexion.establecerConexion();

        //Inicializar listas
        listaAlumnos = FXCollections.observableArrayList();
        cmbcarrera.setValue("Analista de Sistemas");
        cmbcarrera.setItems(Carreras);

        //Llenar listas
        Alumno.llenarInformacionAlumnos(conexion.getConnection(), listaAlumnos);

        //Enlazar TableView con el alumno
        tblViewAlumnos.setItems(listaAlumnos);

        //Enlazar columnas con atributos
        clmDni.setCellValueFactory(new PropertyValueFactory<Alumno, Number>("dnialumno"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        clmApellido.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido"));
        clmSexo.setCellValueFactory(new PropertyValueFactory<Alumno, String>("sexo"));
        clmcarrera.setCellValueFactory(new PropertyValueFactory<Alumno, Number>("carreraAlumno"));
        gestionarEventos();
        conexion.cerrarConexion();
    }

    public void gestionarEventos(){
        tblViewAlumnos.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Alumno>() {
                    @Override
                    public void changed(ObservableValue<? extends Alumno> arg0,
                                        Alumno valorAnterior, Alumno valorSeleccionado) {
                        btnmodificar.setVisible(true);
                        btneliminar.setVisible(true);
                        btnguardar.setVisible(false);
                        dniObli.setVisible(false);
                        nombreObli.setVisible(false);
                        apeObli.setVisible(false);

                        if (valorSeleccionado!=null){
                            txtdni.setText(String.valueOf(valorSeleccionado.getDnialumno()));
                            txtnombre.setText(valorSeleccionado.getNombre());
                            txtapellido.setText(valorSeleccionado.getApellido());
                            cmbcarrera.setValue(valorSeleccionado.getcarreraalumno());
                            if (valorSeleccionado.getSexo().equals("F")){
                                rbtFemenino.setSelected(true);
                                rbtMasculino.setSelected(false);
                            }else if (valorSeleccionado.getSexo().equals("M")){
                                rbtFemenino.setSelected(false);
                                rbtMasculino.setSelected(true);
                            }
                        }
                    }

                }
        );
    }


    @FXML
    public void guardarRegistro() throws IOException {

        dniObli.setVisible(false);
        nombreObli.setVisible(false);
        apeObli.setVisible(false);
        puedeGuardar = true;

        validarDNI();
        validarNombre();
        validarApellido();

        if (puedeGuardar == false) {

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Alta de Alumno");
            mensaje.setContentText("Verifique los campos marcados en rojo");
            mensaje.setHeaderText("No se pudo guardar:");
            mensaje.show();

        } else {

            //Crear una nueva instancia del tipo Alumno
            Alumno a = new Alumno(
                    Integer.valueOf(txtdni.getText()), //
                    txtnombre.getText(),
                    txtapellido.getText(),
                    rbtFemenino.isSelected() ? "F" : "M",
                    cmbcarrera.getValue().toString());

            //Llamar al metodo guardarRegistro de la clase Alumno
            conexion.establecerConexion();
            int resultado = a.guardarRegistro(conexion.getConnection());

            if (resultado == 1) {
                //Agregamos el objeto
                listaAlumnos.add(a);
                //JDK 8u>40
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("Registro agregado");
                mensaje.setContentText("El registro ha sido agregado exitosamente");
                mensaje.setHeaderText("Resultado:");
                mensaje.show();
                limpiarComponentes();
                Parent fxml = FXMLLoader.load(getClass().getResource("../AltaAlumno/abmAlumnos.fxml"));
                FormArea.getChildren().setAll(fxml);
            }
            conexion.cerrarConexion();
        }
    }

    @FXML
    public void actualizarRegistro() throws IOException {

        dniObli.setVisible(false);
        nombreObli.setVisible(false);
        apeObli.setVisible(false);
        puedeGuardar = true;

        validarDNI();
        validarNombre();
        validarApellido();

        if (puedeGuardar == false) {

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Alta de Alumno");
            mensaje.setContentText("Verifique los campos marcados en rojo");
            mensaje.setHeaderText("No se pudo guardar:");
            mensaje.show();

        } else {

            //Crear una nueva instancia del tipo Alumno
            Alumno a = new Alumno(
                    Integer.valueOf(txtdni.getText()), //
                    txtnombre.getText(),
                    txtapellido.getText(),
                    rbtFemenino.isSelected() ? "F" : "M",
                    cmbcarrera.getValue().toString());
            conexion.establecerConexion();
            int resultado = a.actualizarRegistro(conexion.getConnection());
            conexion.cerrarConexion();
            if (resultado == 1) {
                //Agregamos el objeto
                //listaAlumnos.add(a);
                listaAlumnos.set(tblViewAlumnos.getSelectionModel().getSelectedIndex(), a);
                //JDK 8u>40
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("Registro actualizado");
                mensaje.setContentText("El registro ha sido actualizado exitosamente");
                mensaje.setHeaderText("Resultado:");
                mensaje.show();
                limpiarComponentes();
                btnmodificar.setVisible(false);
                Parent fxml = FXMLLoader.load(getClass().getResource("../AltaAlumno/abmAlumnos.fxml"));
                FormArea.getChildren().setAll(fxml);
            }
        }
    }


    @FXML
    public void eliminarRegistro() throws IOException {
        conexion.establecerConexion();
        int resultado = tblViewAlumnos.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1){
            listaAlumnos.remove(tblViewAlumnos.getSelectionModel().getSelectedIndex());
            //JDK 8u>40
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            btneliminar.setVisible(false);
            Parent fxml = FXMLLoader.load(getClass().getResource("../AltaAlumno/abmAlumnos.fxml"));
            FormArea.getChildren().setAll(fxml);
            mensaje.setTitle("Registro Eliminado");
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }

    @FXML
    public void limpiarComponentes(){
        txtdni.setText(null);
        txtnombre.setText(null);
        txtapellido.setText(null);
        rbtFemenino.setSelected(false);
        rbtMasculino.setSelected(false);
    }


    @FXML
    public void Minimize (MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void Close (MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    @FXML
    public void validarDNI() {

        if (txtdni.getText().equals("")) {
            dniObli.setVisible(true);
            dniObli.setText("* Debe completar este campo");
            puedeGuardar = false;
        } else if (!isNumeric(txtdni.getText())) {
            dniObli.setVisible(true);
            dniObli.setText("* El campo debe ser numérico");
            puedeGuardar = false;
        }
    }

    @FXML
    public void validarNombre() {

        if (txtnombre.getText().equals("")) {
            nombreObli.setVisible(true);
            nombreObli.setText("* Debe completar este campo");
            puedeGuardar = false;
        } else if (isNumeric(txtnombre.getText())) {
            nombreObli.setVisible(true);
            nombreObli.setText("* El campo no puede contener números");
            puedeGuardar = false;
        }
    }

    @FXML
    public void validarApellido() {

        if (txtapellido.getText().equals("")) {
            apeObli.setVisible(true);
            apeObli.setText("* Debe completar este campo");
            puedeGuardar = false;
        } else if (isNumeric(txtapellido.getText())) {
            apeObli.setVisible(true);
            apeObli.setText("* El campo no puede contener números");
            puedeGuardar = false;
        }
    }
*/


}
