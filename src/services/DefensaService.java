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

import clases.Defensa;
import logica.manejarBD;

public class DefensaService {

    public boolean createDefensaCompleto(Defensa d, String nombre, int numero, int edad, String idEquipo, int partidosJugados, int goles, int asistencias, double promedioGoles) {
        String sql = "{ call inserta_defensa_completo(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
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
            cs.setInt(10, d.getEntradas());
            cs.setInt(11, d.getBloqueos());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Defensa", ex);
            return false;
        }
    }

    public boolean updateDefensa(String idFutbolista, int entradas, int bloqueos) {
        String sql = "{ call actualiza_defensa(?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idFutbolista);
            cs.setInt(2, entradas);
            cs.setInt(3, bloqueos);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando Defensa", ex);
            return false;
        }
    }

    public boolean eliminarDefensa(String idFutbolista) {
        String sql = "{ call eliminar_defensa(?) }";
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
    public Defensa getDefensaById(String idFutbolista) {
        String sql = "SELECT * FROM defensa WHERE id_futbolista = ?";
        Defensa d = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idFutbolista);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    d = mapResultSetToDefensa(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo Defensa", ex);
        }
        return d;
    }

    public List<Defensa> getAllDefensas() {
        List<Defensa> lista = new ArrayList<>();
        String sql = "SELECT * FROM defensa";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToDefensa(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando Defensas", e);
        }
        return lista;
    }

    private Defensa mapResultSetToDefensa(ResultSet rs) throws SQLException {
        Defensa d = new Defensa();
        d.setIdFutbolista(rs.getString("id_futbolista"));
        d.setEntradas(rs.getInt("entradas"));
        d.setBloqueos(rs.getInt("bloqueos"));
        return d;
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}