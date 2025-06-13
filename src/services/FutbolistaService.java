package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Futbolista;
import logica.manejarBD;

public class FutbolistaService {

    // Create usando función de la base de datos
    public boolean createFutbolista(Futbolista f) {
        String sql = "{ call inserta_futbolista(?, ?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, f.getIdFutbolista());
            cs.setString(2, f.getNombre());
            cs.setInt(3, f.getNumero());
            cs.setInt(4, f.getEdad());
            cs.setString(5, f.getTipo());
            cs.setString(6, f.getIdEquipo());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Futbolista", ex);
            return false;
        }
    }

    // Leer todos
    public List<Futbolista> getAllFutbolistas() {
        List<Futbolista> lista = new ArrayList<>();
        String sql = "SELECT * FROM futbolista";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToFutbolista(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando futbolistas", e);
        }
        return lista;
    }

    // Leer uno
    public Futbolista getFutbolistaById(String idFutbolista) {
        String sql = "SELECT * FROM futbolista WHERE id_futbolista = ?";
        Futbolista f = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idFutbolista);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    f = mapResultSetToFutbolista(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo futbolista", ex);
        }
        return f;
    }

    // Update usando función de la base de datos
    public boolean updateFutbolista(Futbolista f) {
        String sql = "{ call actualiza_futbolista(?, ?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, f.getIdFutbolista());
            cs.setString(2, f.getNombre());
            cs.setInt(3, f.getNumero());
            cs.setInt(4, f.getEdad());
            cs.setString(5, f.getTipo());
            cs.setString(6, f.getIdEquipo());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando Futbolista", ex);
            return false;
        }
    }

    // Delete usando función de la base de datos
    public boolean deleteFutbolista(String idFutbolista) {
        String sql = "{ call elimina_futbolista(?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idFutbolista);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error eliminando Futbolista", ex);
            return false;
        }
    }

    private Futbolista mapResultSetToFutbolista(ResultSet rs) throws SQLException {
        Futbolista f = new Futbolista();
        f.setIdFutbolista(rs.getString("id_futbolista"));
        f.setNombre(rs.getString("nombre"));
        f.setNumero(rs.getInt("numero"));
        f.setEdad(rs.getInt("edad"));
        f.setTipo(rs.getString("tipo"));
        f.setIdEquipo(rs.getString("id_equipo"));
        return f;
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}