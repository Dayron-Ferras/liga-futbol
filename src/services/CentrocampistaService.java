package services;

import java.sql.CallableStatement;import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import logica.*;
import clases.Centrocampista;

public class CentrocampistaService {

    public boolean createCentrocampistaCompleto(Centrocampista c, String nombre, int numero, int edad, String idEquipo, int partidosJugados, int goles, int asistencias, double promedioGoles) {
        String sql = "{ call inserta_centrocampista_completo(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, c.getIdFutbolista());
            cs.setString(2, nombre);
            cs.setInt(3, numero);
            cs.setInt(4, edad);
            cs.setString(5, idEquipo);
            cs.setInt(6, partidosJugados);
            cs.setInt(7, goles);
            cs.setInt(8, asistencias);
            cs.setBigDecimal(9, BigDecimal.valueOf(promedioGoles));
            cs.setInt(10, c.getPasesCompletos());
            cs.setInt(11, c.getIntercepciones());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Centrocampista", ex);
            return false;
        }
    }

    public boolean updateCentrocampista(String idFutbolista, int pasesCompletos, int intercepciones) {
        String sql = "{ call actualiza_centrocampista(?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idFutbolista);
            cs.setInt(2, pasesCompletos);
            cs.setInt(3, intercepciones);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando Centrocampista", ex);
            return false;
        }
    }

    public boolean eliminarCentrocampista(String idFutbolista) {
        String sql = "{ call eliminar_centrocampista(?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, idFutbolista);
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public Centrocampista getCentrocampistaById(String idFutbolista) {
        String sql = "SELECT * FROM centrocampista WHERE id_futbolista = ?";
        Centrocampista c = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idFutbolista);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    c = mapResultSetToCentrocampista(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo Centrocampista", ex);
        }
        return c;
    }

    public List<Centrocampista> getAllCentrocampistas() {
        List<Centrocampista> lista = new ArrayList<>();
        String sql = "SELECT * FROM centrocampista";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToCentrocampista(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando Centrocampistas", e);
        }
        return lista;
    }

    private Centrocampista mapResultSetToCentrocampista(ResultSet rs) throws SQLException {
        Centrocampista c = new Centrocampista();
        c.setIdFutbolista(rs.getString("id_futbolista"));
        c.setPasesCompletos(rs.getInt("pases_completos"));
        c.setIntercepciones(rs.getInt("intercepciones"));
        return c;
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}