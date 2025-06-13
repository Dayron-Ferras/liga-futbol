package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Estadio;
import logica.manejarBD;

public class EstadioService {

    // Create usando función de la base de datos
    public boolean createEstadio(Estadio e) {
        String sql = "{ call inserta_estadio(?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, e.getNombre());
            cs.setInt(2, e.getCapacidadMax());
            cs.setInt(3, e.getAudienciaTotal());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Estadio", ex);
            return false;
        }
    }

    // Leer todos
    public List<Estadio> getAllEstadios() {
        List<Estadio> lista = new ArrayList<>();
        String sql = "SELECT * FROM estadio";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToEstadio(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando estadios", e);
        }
        return lista;
    }

    // Leer uno
    public Estadio getEstadioByNombre(String nombre) {
        String sql = "SELECT * FROM estadio WHERE nombre = ?";
        Estadio e = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    e = mapResultSetToEstadio(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo estadio", ex);
        }
        return e;
    }

    // Update usando función de la base de datos
    public boolean updateEstadio(String nombreOriginal, String nuevoNombre, int capacidadMax, int audienciaTotal) {
        String sql = "{ call actualiza_estadio(?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombreOriginal);
            cs.setString(2, nuevoNombre);
            cs.setInt(3, capacidadMax);
            cs.setInt(4, audienciaTotal);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando Estadio", ex);
            return false;
        }
    }

    // Delete usando función de la base de datos
    public boolean deleteEstadio(String nombre) {
        String sql = "{ call elimina_estadio(?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombre);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error eliminando Estadio", ex);
            return false;
        }
    }

    private Estadio mapResultSetToEstadio(ResultSet rs) throws SQLException {
        Estadio e = new Estadio();
        e.setNombre(rs.getString("nombre"));
        e.setCapacidadMax(rs.getInt("capacidad_max"));
        e.setAudienciaTotal(rs.getInt("audiencia_total"));
        return e;
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}