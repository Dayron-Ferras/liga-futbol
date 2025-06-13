package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.EstadioPartido;
import logica.manejarBD;

public class EstadioPartidoService {

    // Create (no hay función específica, así que se usa el insert directo)
    public boolean createEstadioPartido(EstadioPartido ep) {
        String sql = "INSERT INTO estadio_partido (id_partido, nombre_estadio) VALUES (?, ?)";
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ep.getIdPartido());
            ps.setString(2, ep.getNombreEstadio());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            handleSQLException("Error creando EstadioPartido", ex);
            return false;
        }
    }

    // Read all
    public List<EstadioPartido> getAllEstadioPartidos() {
        List<EstadioPartido> lista = new ArrayList<>();
        String sql = "SELECT * FROM estadio_partido";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToEstadioPartido(rs));
            }
        } catch (SQLException ex) {
            handleSQLException("Error listando EstadioPartido", ex);
        }
        return lista;
    }

    // Read one
    public EstadioPartido getEstadioPartidoByKey(String idPartido, String nombreEstadio) {
        String sql = "SELECT * FROM estadio_partido WHERE id_partido = ? AND nombre_estadio = ?";
        EstadioPartido ep = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPartido);
            ps.setString(2, nombreEstadio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ep = mapResultSetToEstadioPartido(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo EstadioPartido", ex);
        }
        return ep;
    }

    // Delete (no hay función, se usa delete directo)
    public boolean deleteEstadioPartido(String idPartido, String nombreEstadio) {
        String sql = "DELETE FROM estadio_partido WHERE id_partido = ? AND nombre_estadio = ?";
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPartido);
            ps.setString(2, nombreEstadio);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            handleSQLException("Error eliminando EstadioPartido", ex);
            return false;
        }
    }

    private EstadioPartido mapResultSetToEstadioPartido(ResultSet rs) throws SQLException {
        EstadioPartido ep = new EstadioPartido();
        ep.setIdPartido(rs.getString("id_partido"));
        ep.setNombreEstadio(rs.getString("nombre_estadio"));
        return ep;
    }

    private void handleSQLException(String message, SQLException ex) {
        System.err.println(message);
        ex.printStackTrace();
    }
}