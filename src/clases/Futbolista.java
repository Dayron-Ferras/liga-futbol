package clases;
public class Futbolista {
    private String idFutbolista;
    private String nombre;
    private Integer numero;
    private Integer edad;
    private String tipo;
    private String idEquipo;

   
	// Getters y setters
    public String getIdFutbolista() { return idFutbolista; }
    public void setIdFutbolista(String idFutbolista) { this.idFutbolista = idFutbolista; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getIdEquipo() { return idEquipo; }
    public void setIdEquipo(String idEquipo) { this.idEquipo = idEquipo; }
}