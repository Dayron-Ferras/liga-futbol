package services;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Delantero;
import logica.manejarBD;

public class DelanteroService {

    public boolean createDelanteroCompleto(Delantero d, String nombre, int numero, int edad, String idEquipo, int partidosJugados, int goles, int asistencias, double promedioGoles) {
        String sql = "{ CALL inserta_delantero_completo(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, d.getIdFutbolista());
            cs.setString(2, nombre);
            cs.setInt(3, numero);
            cs.setInt(4, edad);
            cs.setString(5, idEquipo);
            cs.setInt(6, partidosJugados);
            cs.setInt(7, goles);
            cs.setInt(8, asistencias);
            cs.setBigDecimal(9, BigDecimal.valueOf(promedioGoles));
            cs.setInt(10, d.getTirosPuerta());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Delantero", ex);
            return false;
        }
    }

    public boolean updateDelantero(String idFutbolista, int tirosPuerta) {
        String sql = "{ call actualiza_delantero(?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idFutbolista);
            cs.setInt(2, tirosPuerta);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando Delantero", ex);
            return false;
        }
    }

    public boolean eliminarDelantero(String idFutbolista) {
        String sql = "{ call eliminar_delantero(?) }";
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

    public Delantero getDelanteroById(String idFutbolista) {
        String sql = "SELECT * FROM delantero WHERE id_futbolista = ?";
        Delantero d = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idFutbolista);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    d = mapResultSetToDelantero(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo Delantero", ex);
        }
        return d;
    }

    public List<Delantero> getAllDelanteros() {
        List<Delantero> lista = new ArrayList<>();
        String sql = "SELECT * FROM delantero";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToDelantero(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando Delanteros", e);
        }
        return lista;
    }

    private Delantero mapResultSetToDelantero(ResultSet rs) throws SQLException {
        Delantero d = new Delantero();
        d.setIdFutbolista(rs.getString("id_futbolista"));
        d.setTirosPuerta(rs.getInt("tiros_puerta"));
        return d;
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}