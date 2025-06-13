package clases;
public class Delantero {
    private String idFutbolista;
    private int tirosPuerta;

    
    public Delantero(String idFutbolista, int tirosPuerta) {
		this.idFutbolista = idFutbolista;
		this.tirosPuerta = tirosPuerta;
	}
	public Delantero() {
		// TODO Auto-generated constructor stub
	}
	
    public String getIdFutbolista() { return idFutbolista; }
    public void setIdFutbolista(String idFutbolista) { this.idFutbolista = idFutbolista; }
    public int getTirosPuerta() { return tirosPuerta; }
    public void setTirosPuerta(int tirosPuerta) { this.tirosPuerta = tirosPuerta; }
}