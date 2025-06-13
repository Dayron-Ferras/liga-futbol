package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Temporada;
import logica.manejarBD;

public class TemporadaService {

    public boolean createTemporada(Temporada t) {
        String sql = "{ call inserta_temporada(?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, t.getIdTemporada());
            cs.setDate(2, new java.sql.Date(t.getFechaInicio().getTime()));
            cs.setDate(3, new java.sql.Date(t.getFechaFin().getTime()));
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Temporada", ex);
            return false;
        }
    }

    public boolean updateTemporada(int idTemporada, java.sql.Date fechaInicio, java.sql.Date fechaFin) {
        String sql = "{ call actualiza_temporada(?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idTemporada);
            cs.setDate(2, fechaInicio);
            cs.setDate(3, fechaFin);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando Temporada", ex);
            return false;
        }
    }

    public boolean deleteTemporada(int idTemporada) {
        String sql = "{ call elimina_temporada(?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idTemporada);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error eliminando Temporada", ex);
            return false;
        }
    }

    public Temporada getTemporadaById(int idTemporada) {
        String sql = "SELECT * FROM temporada WHERE id_temporada = ?";
        Temporada t = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idTemporada);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    t = mapResultSetToTemporada(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo Temporada", ex);
        }
        return t;
    }

    public List<Temporada> getAllTemporadas() {
        List<Temporada> lista = new ArrayList<>();
        String sql = "SELECT * FROM temporada";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToTemporada(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando temporadas", e);
        }
        return lista;
    }

    private Temporada mapResultSetToTemporada(ResultSet rs) throws SQLException {
        Temporada t = new Temporada();
        t.setIdTemporada(rs.getInt("id_temporada"));
        t.setFechaInicio(rs.getDate("fecha_inicio"));
        t.setFechaFin(rs.getDate("fecha_fin"));
        return t;
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}