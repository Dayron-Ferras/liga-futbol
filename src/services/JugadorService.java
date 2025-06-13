 package services;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import clases.Jugador;
import logica.manejarBD;

public class JugadorService {

    // Create usando función de la base de datos
    public boolean createJugadorCompleto(Jugador jc) {
        String sql = "{ call inserta_jugador_completo(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            // Parámetros básicos
            cs.setString(1, jc.getIdFutbolista());
            cs.setString(6, jc.getPosicion());
            cs.setInt(7, jc.getPartidosJugados());
            cs.setInt(8, jc.getGoles());
            cs.setInt(9, jc.getAsistencias());
            cs.setBigDecimal(10, BigDecimal.valueOf(jc.getPromedioGoles()));
            
           
            
            cs.execute();
            return true;

        } catch (SQLException ex) {
            handleSQLException("Error creando Jugador Completo", ex);
            return false;
        }
    }

    // Leer todos los jugadores
    
    public boolean actualizarGolesYAsistencias(String idFutbolista, int goles, int asistencias)  {
        String sql = "UPDATE public.jugador SET goles = ?, asistencias = ? WHERE id_futbolista = ?";
        int filas =0;
        try (Connection conn = manejarBD.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql) ){
            ps.setInt(1, goles);
            ps.setInt(2, asistencias);
            ps.setString(3, idFutbolista);
             filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return filas > 0;
		}}
    
    public Jugador getJugadorById(String idFutbolista) {
        String sql = "SELECT * FROM jugador WHERE id_futbolista = ?";
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idFutbolista);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Jugador(
                        rs.getString("id_futbolista"),
                        rs.getString("posicion"),
                        rs.getInt("partidos_jugados"),
                        rs.getInt("goles"),
                        rs.getInt("asistencias"),
                    rs.getBigDecimal("promedio_goles").doubleValue()   
                    );
                }
            }
        } catch (SQLException ex) {
            handleSQLException("Error leyendo jugador", ex);
        }
        return null;
    }
    
   
    
    
    public boolean updateJugadorCompleto(Jugador jugador) {
        String sqlFutbolista = "UPDATE futbolista SET nombre=?, numero=?, edad=?, id_equipo=? WHERE id_futbolista=?";
        String sqlJugador = "UPDATE jugador SET posicion=?, partidos_jugados=?, goles=?, asistencias=?, promedio_goles=? WHERE id_futbolista=?";
        try (Connection conn = manejarBD.getConnection()) {
            conn.setAutoCommit(false);
            try (
                PreparedStatement psFut = conn.prepareStatement(sqlFutbolista);
                PreparedStatement psJug = conn.prepareStatement(sqlJugador)
            ) {
               
                psFut.setString(5, jugador.getIdFutbolista());
                psFut.executeUpdate();

                // Actualiza datos en jugador
                psJug.setString(1, jugador.getPosicion());
                psJug.setInt(2, jugador.getPartidosJugados());
                psJug.setInt(3, jugador.getGoles());
                psJug.setInt(4, jugador.getAsistencias());
                psJug.setBigDecimal(5, BigDecimal.valueOf(jugador.getPromedioGoles()));
                psJug.setString(6, jugador.getIdFutbolista());
                psJug.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException ex) {
                conn.rollback();
                handleSQLException("Error actualizando datos generales del jugador", ex);
                return false;
            }
        } catch (SQLException ex) {
            handleSQLException("Error de conexión al actualizar jugador", ex);
            return false;
        }
    }

    
    
    
    // Actualizar jugador
    public boolean updateJugador(Jugador jugador) {
        String sql = "{ call actualiza_jugador_posicion(?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try (Connection conn = manejarBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, jugador.getIdFutbolista());
            cs.setString(2, jugador.getPosicion());
           

            cs.execute();
            return true;
        } catch (SQLException ex) {
            handleSQLException("Error actualizando estadísticas jugador", ex);
            return false;
        }
    }

    // ... (otros métodos iguales, como getJugadorById, createJugadorCompleto, etc.)

   
    // Eliminar jugador
    public boolean eliminarJugador(String idFutbolista) {
        String sql = "SELECT eliminar_jugador(?)";
        try (
            Connection conn = manejarBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, idFutbolista);
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    // Método auxiliar para crear un Jugador desde ResultSet
   
    // Manejo de excepciones
    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}