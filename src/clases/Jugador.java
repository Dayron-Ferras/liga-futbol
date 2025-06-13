package clases;

public class Jugador {
   
    
    // Datos de Jugador
	private String IdFutbolista;
    private String posicion;
    private int partidosJugados;
    private int goles;
    private int asistencias;
    private double promedioGoles;
    
    
	public Jugador(String idFutbolista, String posicion, int partidosJugados, int goles, int asistencias,
			double promedioGoles) {
		super();
		IdFutbolista = idFutbolista;
		this.posicion = posicion;
		this.partidosJugados = partidosJugados;
		this.goles = goles;
		this.asistencias = asistencias;
		this.promedioGoles = promedioGoles;
	}
	public String getPosicion() {
		return posicion;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
	public int getPartidosJugados() {
		return partidosJugados;
	}
	public void setPartidosJugados(int partidosJugados) {
		this.partidosJugados = partidosJugados;
	}
	public int getGoles() {
		return goles;
	}
	public void setGoles(int goles) {
		this.goles = goles;
	}
	public int getAsistencias() {
		return asistencias;
	}
	public void setAsistencias(int asistencias) {
		this.asistencias = asistencias;
	}
	public double getPromedioGoles() {
		return promedioGoles;
	}
	public void setPromedioGoles(double bigDecimal) {
		this.promedioGoles = bigDecimal;
	}
	public String getIdFutbolista() {
		return IdFutbolista;
	}
	public void setIdFutbolista(String idFutbolista) {
		IdFutbolista = idFutbolista;
	}
    
   
   
	
}