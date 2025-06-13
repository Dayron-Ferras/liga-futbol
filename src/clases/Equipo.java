package clases;
public class Equipo {
    private String nombreEquipo;
    private int campeonatosParticipados;
    private int campeonatosGanados;
    private String mascota;
    private String provincia;

    // Getters y setters
    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }
    public int getCampeonatosParticipados() { return campeonatosParticipados; }
    public void setCampeonatosParticipados(int campeonatosParticipados) { this.campeonatosParticipados = campeonatosParticipados; }
    public int getCampeonatosGanados() { return campeonatosGanados; }
    public void setCampeonatosGanados(int campeonatosGanados) { this.campeonatosGanados = campeonatosGanados; }
    public String getMascota() { return mascota; }
    public void setMascota(String mascota) { this.mascota = mascota; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
}