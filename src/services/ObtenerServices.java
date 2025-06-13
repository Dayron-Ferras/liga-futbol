package services;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Centrocampista;
import clases.Defensa;
import clases.Delantero;
import clases.Entrenador;
import clases.Equipo;
import clases.Estadio;
import clases.EstadioPartido;
import clases.EstadisticasPartido;
import clases.Futbolista;
import clases.InformacionPartido;
import clases.Jugador;
import clases.Partido;
import clases.Portero;
import clases.TablaPosiciones;
import clases.Temporada;
import logica.manejarBD;

public class ObtenerServices {

    public List<Equipo> obtenerEquipos() {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipo";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Equipo e = new Equipo();
                e.setNombreEquipo(rs.getString("nombre_equipo"));
                e.setCampeonatosParticipados(rs.getInt("campeonatos_participados"));
                e.setCampeonatosGanados(rs.getInt("campeonatos_ganados"));
                e.setMascota(rs.getString("mascota"));
                e.setProvincia(rs.getString("provincia"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    
    public List<TablaPosiciones> obtenerTablaPosiciones(int idTemporada) throws SQLException {
        List<TablaPosiciones> lista = new ArrayList<>();

        String sql = "SELECT * FROM tu_funcion_tabla_posiciones(?)";
        try (Connection conn = manejarBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTemporada);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TablaPosiciones pos = new TablaPosiciones();
                    pos.setEquipo(rs.getString("equipo"));
                    pos.setPartidosJugados(rs.getInt("partidos_jugados"));
                    pos.setGanados(rs.getInt("ganados"));
                    pos.setEmpatados(rs.getInt("empatados"));
                    pos.setPerdidos(rs.getInt("perdidos"));
                    pos.setGolesFavor(rs.getInt("goles_favor"));
                    pos.setGolesContra(rs.getInt("goles_contra"));
                    pos.setDiferenciaGoles(rs.getInt("diferencia_goles"));
                    pos.setPuntos(rs.getInt("puntos"));
                    lista.add(pos);
                }
            }
        }
        return lista;
    }
    public List<Centrocampista> obtenerCentrocampistas() {
        List<Centrocampista> lista = new ArrayList<>();
        String sql = "SELECT * FROM centrocampista";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Centrocampista c = new Centrocampista();
                c.setIdFutbolista(rs.getString("id_futbolista"));
                c.setPasesCompletos(rs.getInt("pases_completos"));
                c.setIntercepciones(rs.getInt("intercepciones"));
                lista.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Defensa> obtenerDefensas() {
        List<Defensa> lista = new ArrayList<>();
        String sql = "SELECT * FROM defensa";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Defensa d = new Defensa();
                d.setIdFutbolista(rs.getString("id_futbolista"));
                d.setEntradas(rs.getInt("entradas"));
                d.setBloqueos(rs.getInt("bloqueos"));
                lista.add(d);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Delantero> obtenerDelanteros() {
        List<Delantero> lista = new ArrayList<>();
        String sql = "SELECT * FROM delantero";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Delantero d = new Delantero();
                d.setIdFutbolista(rs.getString("id_futbolista"));
                d.setTirosPuerta(rs.getInt("tiros_puerta"));
                lista.add(d);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Entrenador> obtenerEntrenadores() {
        List<Entrenador> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenador";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Entrenador e = new Entrenador();
                e.setIdFutbolista(rs.getString("id_futbolista"));
                e.setAniosExp(rs.getInt("a—os_exp"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Jugador> obtenerIdsEquipoTodosEstrellas() {
    	ArrayList<Jugador>j = new ArrayList<Jugador>();
        List<String> ids = new ArrayList<>();
        String sql = "SELECT * FROM equipo_todos_estrellas_ids()";

        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ids.add(rs.getString("id_futbolista"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(String i : ids) {
        	JugadorService c = new JugadorService();
        	Jugador ob = c.getJugadorById(i);
        	j.add(ob);
        }
        return j;
    }
    public List<Estadio> obtenerEstadios() {
        List<Estadio> lista = new ArrayList<>();
        String sql = "SELECT * FROM estadio";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Estadio e = new Estadio();
                e.setNombre(rs.getString("nombre"));
                e.setCapacidadMax(rs.getInt("capacidad_max"));
                e.setAudienciaTotal(rs.getInt("audiencia_total"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Futbolista> obtenerFutbolistas() {
        List<Futbolista> lista = new ArrayList<>();
        String sql = "SELECT * FROM futbolista";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Futbolista f = new Futbolista();
                f.setIdFutbolista(rs.getString("id_futbolista"));
                f.setNombre(rs.getString("nombre"));
                f.setNumero(rs.getInt("numero"));
                f.setEdad(rs.getInt("edad"));
                f.setTipo(rs.getString("tipo"));
                f.setIdEquipo(rs.getString("id_equipo"));
                lista.add(f);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    
    public Futbolista buscarFutbolista(String ID) {
        Futbolista f = null;  
        boolean encontrado = false;
        List<Futbolista> futbolistas = obtenerFutbolistas(); 
        for(int i = 0; i < futbolistas.size() && !encontrado; i++) {
            if(futbolistas.get(i).getIdFutbolista().equalsIgnoreCase(ID)) {
                f = futbolistas.get(i);
                encontrado = true;
            }
        }
        
        return f;
    }
    
    public ArrayList<Jugador> obtenerJugadores() {
        ArrayList<Jugador> lista = new ArrayList<>();
        String sql = "SELECT id_futbolista, posicion, partidos_jugados, goles, asistencias, promedio_goles FROM jugador";

        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Jugador j = new Jugador(
                    rs.getString("id_futbolista"),
                    rs.getString("posicion"),
                    rs.getInt("partidos_jugados"),
                    rs.getInt("goles"),
                    rs.getInt("asistencias"),
                    rs.getBigDecimal("promedio_goles").doubleValue()     
                );
                lista.add(j);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return lista;
    }
        
        
       
    
    
    
    public List<Partido> obtenerPartidos() {
        List<Partido> lista = new ArrayList<>();
        String sql = "SELECT * FROM partido";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Partido p = new Partido();
                p.setIdPartido(rs.getString("id_partido"));
                p.setResultado(rs.getString("resultado"));
                p.setIdTemporada(rs.getInt("id_temporada"));
                p.setFecha(rs.getDate("fecha"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    
    public List<Partido> obtenerPartidos2(String ID) {
    	List<Partido> lista = obtenerPartidos();
    	List<Partido> lista2 = new ArrayList<>();
    	int IDint = Integer.parseInt(ID);
    	for(Partido p : lista) {
    		if(p.getIdTemporada()==IDint)
    			lista2.add(p);
    	}
    	
    	return lista2;
    }

    public List<Portero> obtenerPorteros() {
        List<Portero> lista = new ArrayList<>();
        String sql = "SELECT * FROM portero";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Portero p = new Portero();
                p.setIdFutbolista(rs.getString("id_futbolista"));
                p.setAtajadas(rs.getInt("atajadas"));
                p.setGolesEncajados(rs.getInt("goles_encajados"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Temporada> obtenerTemporadas() {
        List<Temporada> lista = new ArrayList<>();
        String sql = "SELECT * FROM temporada";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Temporada t = new Temporada();
                t.setIdTemporada(rs.getInt("id_temporada"));
                t.setFechaInicio(rs.getDate("fecha_inicio"));
                t.setFechaFin(rs.getDate("fecha_fin"));
                lista.add(t);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<EstadisticasPartido> obtenerEstadisticasPartido() {
        List<EstadisticasPartido> lista = new ArrayList<>();
        String sql = "SELECT * FROM estadisticas_partido";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                EstadisticasPartido ep = new EstadisticasPartido();
                ep.setIdPartido(rs.getString("id_partido"));
                ep.setIdFutbolista(rs.getString("id_futbolista"));
                ep.setGoles(rs.getInt("goles"));
                ep.setAsistencias(rs.getInt("asistencias"));
                lista.add(ep);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<EstadioPartido> obtenerEstadioPartidos() {
        List<EstadioPartido> lista = new ArrayList<>();
        String sql = "SELECT * FROM estadio_partido";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                EstadioPartido ep = new EstadioPartido();
                ep.setIdPartido(rs.getString("id_partido"));
                ep.setNombreEstadio(rs.getString("nombre_estadio"));
                lista.add(ep);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<InformacionPartido> obtenerInformacionPartidos() {
        List<InformacionPartido> lista = new ArrayList<>();
        String sql = "SELECT * FROM informacion_partido";
        try (Connection conn = manejarBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                InformacionPartido ip = new InformacionPartido();
                ip.setIdPartido(rs.getString("id_partido"));
                ip.setEquipoLocal(rs.getString("equipo_local"));
                ip.setEquipoVisitante(rs.getString("equipo_visitante"));
                ip.setGolesLocal(rs.getInt("goles_local"));
                ip.setGolesVisitante(rs.getInt("goles_visitante"));
                ip.setAsistenciasLocal(rs.getInt("asistencias_local"));
                ip.setAsistenciasVisitante(rs.getInt("asistencias_visitante"));
                lista.add(ip);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    
   public List<InformacionPartido> Informacion(ArrayList<Partido>partidos){
	   List<InformacionPartido> inf =obtenerInformacionPartidos();
	   List<InformacionPartido> inf2 =new ArrayList<InformacionPartido>();
	   boolean stop = false;
	   for(Partido p: partidos) {
		   stop= false;
		   for(int i = 0 ; i<inf.size()&&!stop;i++) {
			   if(inf.get(i).getIdPartido().equals(p.getIdPartido())) {
				   inf2.add(inf.get(i));
				   stop=true;
			   }
		   }
	   }
	   
	   return inf2;
   }
}