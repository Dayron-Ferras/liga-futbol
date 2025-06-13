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

import clases.Portero;
import logica.manejarBD;

public class PorteroService {

    public boolean createPorteroCompleto(Portero p, String nombre, int numero, int edad, String idEquipo, int partidosJugados, int goles, int asistencias, double promedioGoles) {
        String sql = "{ call inserta_portero_completo(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, p.getIdFutbolista());
            cs.setString(2, nombre);
            cs.setInt(3, numero);
            cs.setInt(4, edad);
            cs.setString(5, idEquipo);
            cs.setInt(6, partidosJugados);
            cs.setInt(7, goles);
            cs.setInt(8, asistencias);
            cs.setBigDecimal(9, BigDecimal.valueOf(promedioGoles));
            cs.setInt(10, p.getAtajadas());
            cs.setInt(11, p.getGolesEncajados());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Portero", ex);
            return false;
        }
    }

    public boolean updatePortero(String idFutbolista, int atajadas, int golesEncajados) {
        String sql = "{ call actualiza_portero(?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idFutbolista);
            cs.setInt(2, atajadas);
            cs.setInt(3, golesEncajados);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando Portero", ex);
            return false;
        }
    }

    public boolean eliminarPortero(String idFutbolista) {
        String sql = "{ call eliminar_portero(?) }";
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

    public Portero getPorteroById(String idFutbolista) {
        String sql = "SELECT * FROM portero WHERE id_futbolista = ?";
        Portero p = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idFutbolista);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    p = mapResultSetToPortero(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo Portero", ex);
        }
        return p;
    }

    public List<Portero> getAllPorteros() {
        List<Portero> lista = new ArrayList<>();
        String sql = "SELECT * FROM portero";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToPortero(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando porteros", e);
        }
        return lista;
    }

    private Portero mapResultSetToPortero(ResultSet rs) throws SQLException {
        Portero p = new Portero();
        p.setIdFutbolista(rs.getString("id_futbolista"));
        p.setAtajadas(rs.getInt("atajadas"));
        p.setGolesEncajados(rs.getInt("goles_encajados"));
        return p;
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}