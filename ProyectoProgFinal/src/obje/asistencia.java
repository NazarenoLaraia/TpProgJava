package obje;

import javafx.scene.control.DatePicker;

public class asistencia {
    private Integer id;
    private String nombreprof;
    private Integer dniprof;
    private DatePicker fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreprof() {
        return nombreprof;
    }

    public void setNombreprof(String nombreprof) {
        this.nombreprof = nombreprof;
    }

    public Integer getDniprof() {
        return dniprof;
    }

    public void setDniprof(Integer dniprof) {
        this.dniprof = dniprof;
    }

    public DatePicker getFecha() {
        return fecha;
    }

    public void setFecha(DatePicker fecha) {
        this.fecha = fecha;
    }
}
