package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Partido;
import logica.manejarBD;

public class PartidoService {

    public boolean createPartido(String idPartido, java.sql.Date fecha, String resultado, int idTemporada, String nombreEstadio,
                                 String equipoLocal, String equipoVisitante, int golesLocal, int golesVisitante, int asistenciasLocal, int asistenciasVisitante) {
        String sql = "{ call inserta_partido(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idPartido);
            cs.setDate(2, fecha);
            cs.setString(3, resultado);
            cs.setInt(4, idTemporada);
            cs.
            setString(5, nombreEstadio);
            cs.setString(6, equipoLocal);
            cs.setString(7, equipoVisitante);
            cs.setInt(8, golesLocal);
            cs.setInt(9, golesVisitante);
            cs.setInt(10, asistenciasLocal);
            cs.setInt(11, asistenciasVisitante);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Partido", ex);
            return false;
        }
    }

    public boolean updatePartido(String idPartido, java.sql.Date fecha, String resultado, int idTemporada,
                                 String nuevoEstadio, String equipoLocal, String equipoVisitante, int golesLocal, int golesVisitante,
                                 int asistenciasLocal, int asistenciasVisitante) {
        String sql = "{ call actualiza_partido(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idPartido);
            cs.setDate(2, fecha);
            cs.setString(3, resultado);
            cs.setInt(4, idTemporada);
            cs.setString(5, nuevoEstadio);
            cs.setString(6, equipoLocal);
            cs.setString(7, equipoVisitante);
            cs.setInt(8, golesLocal);
            cs.setInt(9, golesVisitante);
            cs.setInt(10, asistenciasLocal);
            cs.setInt(11, asistenciasVisitante);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando Partido", ex);
            return false;
        }
    }

    public boolean deletePartido(String idPartido) {
        String sql = "{ call elimina_partido(?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idPartido);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error eliminando Partido", ex);
            return false;
        }
    }

    public Partido getPartidoById(String idPartido) {
        String sql = "SELECT * FROM partido WHERE id_partido = ?";
        Partido p = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idPartido);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    p = mapResultSetToPartido(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo Partido", ex);
        }
        return p;
    }

    public List<Partido> getAllPartidos() {
        List<Partido> lista = new ArrayList<>();
        String sql = "SELECT * FROM partido";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToPartido(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando partidos", e);
        }
        return lista;
    }
   

    private Partido mapResultSetToPartido(ResultSet rs) throws SQLException {
        Partido p = new Partido();
        p.setIdPartido(rs.getString("id_partido"));
        p.setResultado(rs.getString("resultado"));
        p.setIdTemporada(rs.getInt("id_temporada"));
        p.setFecha(rs.getDate("fecha"));
        return p;
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}