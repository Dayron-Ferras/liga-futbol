package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Entrenador;
import logica.manejarBD;

public class EntrenadorService {

    // Create usando función de la base de datos
    public boolean createEntrenador(Entrenador e) {
        String sql = "{ call inserta_entrenador(?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, e.getIdFutbolista());
            cs.setInt(2, e.getAniosExp());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Entrenador", ex);
            return false;
        }
    }

    public boolean crearEntrenadorCompleto(Entrenador entrenador, String nombre, int numero, int edad, String idEquipo) {
        String sql = "{ call crear_entrenador_completo(?, ?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, entrenador.getIdFutbolista());
            cs.setString(2, nombre);
            cs.setInt(3, numero);
            cs.setInt(4, edad);
            cs.setString(5, idEquipo);
            cs.setInt(6, entrenador.getAniosExp());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando entrenador completo", ex);
            return false;
        }
    }
    
    // Leer todos
    public List<Entrenador> getAllEntrenadores() {
        List<Entrenador> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenador";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToEntrenador(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando entrenadores", e);
        }
        return lista;
    }

    // Leer uno
    public Entrenador getEntrenadorById(String idFutbolista) {
        String sql = "SELECT * FROM entrenador WHERE id_futbolista = ?";
        Entrenador e = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idFutbolista);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    e = mapResultSetToEntrenador(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo entrenador", ex);
        }
        return e;
    }

    // Update usando función de la base de datos
    public boolean updateEntrenador(String idFutbolista, int aniosExp) {
        String sql = "{ call actualiza_entrenador(?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idFutbolista);
            cs.setInt(2, aniosExp);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando Entrenador", ex);
            return false;
        }
    }

    // Delete usando función de la base de datos
    public boolean deleteEntrenador(String idFutbolista) {
        String sql = "{ call elimina_entrenador(?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idFutbolista);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error eliminando Entrenador", ex);
            return false;
        }
    }

    private Entrenador mapResultSetToEntrenador(ResultSet rs) throws SQLException {
        Entrenador e = new Entrenador();
        e.setIdFutbolista(rs.getString("id_futbolista"));
        e.setAniosExp(rs.getInt("a�os_exp"));
        return e;
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}