package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.EstadisticasPartido;
import logica.manejarBD;

public class EstadisticasPartidoService {

    public boolean createEstadisticasPartido(EstadisticasPartido ep) {
        String sql = "{ call inserta_estadistica_partido(?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, ep.getIdPartido());
            cs.setString(2, ep.getIdFutbolista());
            cs.setInt(3, ep.getGoles());
            cs.setInt(4, ep.getAsistencias());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando EstadisticasPartido", ex);
            return false;
        }
    }

    public boolean updateEstadisticasPartido(String idPartido, String idFutbolista, int goles, int asistencias, int minutosJugados) {
        String sql = "{ call actualiza_estadisticas_partido(?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idPartido);
            cs.setString(2, idFutbolista);
            cs.setInt(3, goles);
            cs.setInt(4, asistencias);
            cs.setInt(5, minutosJugados);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando EstadisticasPartido", ex);
            return false;
        }
    }

    public boolean deleteEstadisticasPartido(String idPartido, String idFutbolista) {
        String sql = "DELETE FROM estadisticas_partido WHERE id_partido = ? AND id_futbolista = ?";
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idPartido);
            pstmt.setString(2, idFutbolista);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error eliminando EstadisticasPartido", ex);
            return false;
        }
    }

    public EstadisticasPartido getEstadisticasPartidoById(String idPartido, String idFutbolista) {
        String sql = "SELECT * FROM estadisticas_partido WHERE id_partido = ? AND id_futbolista = ?";
        EstadisticasPartido ep = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idPartido);
            pstmt.setString(2, idFutbolista);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ep = mapResultSetToEstadisticasPartido(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo EstadisticasPartido", ex);
        }
        return ep;
    }

    public List<EstadisticasPartido> getAllEstadisticasPartido() {
        List<EstadisticasPartido> lista = new ArrayList<>();
        String sql = "SELECT * FROM estadisticas_partido";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToEstadisticasPartido(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando EstadisticasPartido", e);
        }
        return lista;
    }

    private EstadisticasPartido mapResultSetToEstadisticasPartido(ResultSet rs) throws SQLException {
        EstadisticasPartido ep = new EstadisticasPartido();
        ep.setIdPartido(rs.getString("id_partido"));
        ep.setIdFutbolista(rs.getString("id_futbolista"));
        ep.setGoles(rs.getInt("goles"));
        ep.setAsistencias(rs.getInt("asistencias"));
        return ep;
    }
    public List<EstadisticasPartido> obtenerInfoPartidoEquipo(String idPartido, String idEquipo) {
        List<EstadisticasPartido> resultado = new ArrayList<>();
        String sql = "SELECT * FROM public.obtener_info_partido_equipo(?, ?)";
        try (Connection conn = manejarBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPartido);
            ps.setString(2, idEquipo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EstadisticasPartido info = new EstadisticasPartido();
                    info.setIdPartido(rs.getString("id_partido"));
                    info.setIdFutbolista(rs.getString("id_futbolista"));
                    info.setGoles(rs.getInt("goles"));
                    info.setAsistencias(rs.getInt("asistencias"));
                    resultado.add(info);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}