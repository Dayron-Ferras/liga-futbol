package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.InformacionPartido;
import logica.manejarBD;

public class InformacionPartidoService {

    // Create (no hay función, se usa insert directo)
    public boolean createInformacionPartido(InformacionPartido ip) {
        String sql = "INSERT INTO informacion_partido (id_partido, equipo_local, equipo_visitante, goles_local, goles_visitante, asistencias_local, asistencias_visitante) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ip.getIdPartido());
            ps.setString(2, ip.getEquipoLocal());
            ps.setString(3, ip.getEquipoVisitante());
            ps.setInt(4, ip.getGolesLocal());
            ps.setInt(5, ip.getGolesVisitante());
            ps.setInt(6, ip.getAsistenciasLocal());
            ps.setInt(7, ip.getAsistenciasVisitante());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            handleSQLException("Error creando InformacionPartido", ex);
            return false;
        }
    }

    // Read all
    public List<InformacionPartido> getAllInformacionPartido() {
        List<InformacionPartido> lista = new ArrayList<>();
        String sql = "SELECT * FROM informacion_partido";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToInformacionPartido(rs));
            }
        } catch (SQLException ex) {
            handleSQLException("Error listando InformacionPartido", ex);
        }
        return lista;
    }

    // Read one
    public InformacionPartido getInformacionPartidoById(String idPartido) {
        String sql = "SELECT * FROM informacion_partido WHERE id_partido = ?";
        InformacionPartido ip = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPartido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ip = mapResultSetToInformacionPartido(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo InformacionPartido", ex);
        }
        return ip;
    }

    // Delete (no hay función, se usa delete directo)
    public boolean deleteInformacionPartido(String idPartido) {
        String sql = "DELETE FROM informacion_partido WHERE id_partido = ?";
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPartido);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            handleSQLException("Error eliminando InformacionPartido", ex);
            return false;
        }
    }

    private InformacionPartido mapResultSetToInformacionPartido(ResultSet rs) throws SQLException {
        InformacionPartido ip = new InformacionPartido();
        ip.setIdPartido(rs.getString("id_partido"));
        ip.setEquipoLocal(rs.getString("equipo_local"));
        ip.setEquipoVisitante(rs.getString("equipo_visitante"));
        ip.setGolesLocal(rs.getInt("goles_local"));
        ip.setGolesVisitante(rs.getInt("goles_visitante"));
        ip.setAsistenciasLocal(rs.getInt("asistencias_local"));
        ip.setAsistenciasVisitante(rs.getInt("asistencias_visitante"));
        return ip;
    }

    private void handleSQLException(String message, SQLException ex) {
        System.err.println(message);
        ex.printStackTrace();
    }
}