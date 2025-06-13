package clases;
import java.util.Date;

public class Partido {
    private String idPartido;
    private String resultado;
    private Integer idTemporada;
    private Date fecha;

    // Getters y setters
    public String getIdPartido() { return idPartido; }
    public void setIdPartido(String idPartido) { this.idPartido = idPartido; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public Integer getIdTemporada() { return idTemporada; }
    public void setIdTemporada(Integer idTemporada) { this.idTemporada = idTemporada; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}