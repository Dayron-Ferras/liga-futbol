package logica;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class manejarBD {

	private static String url = "jdbc:postgresql://localhost:5432/proyecto2";
	private static String usuario = "postgres";
	private static String contraseña = "12312312";
	
    public static Connection getConnection() throws SQLException {
    	return DriverManager.getConnection(url, usuario, contraseña);
    }
    
	
}


