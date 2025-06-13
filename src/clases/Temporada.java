
package clases;
import java.util.Date;

public class Temporada {
    private int idTemporada;
    private Date fechaInicio;
    private Date fechaFin;

    // Getters y setters
    public int getIdTemporada() { return idTemporada; }
    public void setIdTemporada(int idTemporada) { this.idTemporada = idTemporada; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFinal) { this.fechaFin = fechaFinal; }
}