package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Equipo;
import logica.manejarBD;

public class EquipoService {

    // Create usando función de la base de datos
    public boolean createEquipo(Equipo e) {
        String sql = "{ call inserta_equipo(?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, e.getNombreEquipo());
            cs.setInt(2, e.getCampeonatosParticipados());
            cs.setInt(3, e.getCampeonatosGanados());
            cs.setString(4, e.getMascota());
            cs.setString(5, e.getProvincia());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Equipo", ex);
            return false;
        }
    }

    
    
    // Leer todos
    public List<Equipo> getAllEquipos() {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipo";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToEquipo(rs));
            }
        } catch (SQLException e) {
            handleSQLException("Error listando equipos", e);
        }
        return lista;
    }

    // Leer uno
    public Equipo getEquipoById(String nombreEquipo) {
        String sql = "SELECT * FROM equipo WHERE nombre_equipo = ?";
        Equipo e = null;
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreEquipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    e = mapResultSetToEquipo(rs);
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo equipo", ex);
        }
        return e;
    }

    // Update usando función de la base de datos
    public boolean updateEquipo(Equipo e, String nombreOriginal) {
        String sql = "{ call actualiza_equipo(?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombreOriginal); // nombre_original
            cs.setInt(2, e.getCampeonatosParticipados());
            cs.setInt(3, e.getCampeonatosGanados());
            cs.setString(4, e.getMascota());
            cs.setString(5, e.getProvincia());
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error actualizando Equipo", ex);
            return false;
        }
    }

    // Delete usando función de la base de datos
    public boolean deleteEquipo(String nombreEquipo) {
        String sql = "{ call elimina_equipo(?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombreEquipo);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error eliminando Equipo", ex);
            return false;
        }
    }

    private Equipo mapResultSetToEquipo(ResultSet rs) throws SQLException {
        Equipo e = new Equipo();
        e.setNombreEquipo(rs.getString("nombre_equipo"));
        e.setCampeonatosParticipados(rs.getInt("campeonatos_participados"));
        e.setCampeonatosGanados(rs.getInt("campeonatos_ganados"));
        e.setMascota(rs.getString("mascota"));
        e.setProvincia(rs.getString("provincia"));
        return e;
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}