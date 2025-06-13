package jframeessss;

import clases.Equipo;
import clases.Partido;
import clases.InformacionPartido;
import services.ObtenerServices;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TablaPosicionesDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public TablaPosicionesDialog(Frame parent) {
        super(parent, true);
        setUndecorated(true);

        Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int width = 900, height = 500;
        int x = screenBounds.x + (screenBounds.width - width) / 2;
        int y = screenBounds.y + (screenBounds.height - height) / 2;
        setBounds(x, y, width, height);

        @SuppressWarnings("serial")
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255,255,255,235));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        setContentPane(panel);

        // Botón cerrar
        HoverPressButton btnCerrar = new HoverPressButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnCerrar.setButtonColors(
            new Color(255, 70, 70, 180),
            new Color(220, 20, 60, 200),
            new Color(180, 0, 0, 220)
        );
        btnCerrar.setBounds(width-120, 18, 90, 36);
        btnCerrar.addActionListener(e -> dispose());
        panel.add(btnCerrar);

        // Botón estado equipo
        HoverPressButton btnEstado = new HoverPressButton("Estado equipo");
        btnEstado.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnEstado.setButtonColors(
                new Color(70, 180, 70, 180),
                new Color(30, 144, 60, 200),
                new Color(0, 128, 0, 220)
        );
        btnEstado.setBounds(width-260, 18, 120, 36);
        panel.add(btnEstado);

        // Obtener datos
        ObtenerServices o = new ObtenerServices();
        List<Equipo> equipos = o.obtenerEquipos();
        List<Partido> partidos = o.obtenerPartidos();
        List<InformacionPartido> infoPartidos = o.obtenerInformacionPartidos();

        // Calcular tabla de posiciones
        List<PosicionEquipo> tabla = calcularTablaPosiciones(equipos, partidos, infoPartidos);

        // Armar datos para JTable
        String[] col = {"#", "Equipo", "Pts", "PJ", "G", "E", "P", "GF", "GC", "DG"};
        Object[][] data = new Object[tabla.size()][col.length];
        for (int i = 0; i < tabla.size(); i++) {
            PosicionEquipo pe = tabla.get(i);
            data[i][0] = (i+1);
            data[i][1] = pe.getNombreEquipo();
            data[i][2] = pe.getPuntos();
            data[i][3] = pe.getPartidosJugados();
            data[i][4] = pe.getGanados();
            data[i][5] = pe.getEmpatados();
            data[i][6] = pe.getPerdidos();
            data[i][7] = pe.getGolesFavor();
            data[i][8] = pe.getGolesContra();
            data[i][9] = pe.getGolesFavor() - pe.getGolesContra();
        }
        @SuppressWarnings("serial")
        DefaultTableModel model = new DefaultTableModel(data, col) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tablaPos = new JTable(model);
        tablaPos.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        tablaPos.setRowHeight(30);
        tablaPos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        tablaPos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(tablaPos);
        scroll.setBounds(40, 70, width-80, height-100);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        panel.add(scroll);

        // Acción del botón Estado equipo
        btnEstado.addActionListener(e -> {
            int selectedRow = tablaPos.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un equipo de la tabla.");
                return;
            }
            String equipo = (String) tablaPos.getValueAt(selectedRow, 1);
            EstadoEquipo estado = calcularEstadoEquipo(equipo, partidos, infoPartidos);
            String mensaje = "Equipo: " + equipo +
                    "\n\nTotal:" +
                    "\n  Ganados: " + estado.totalGanados +
                    "\n  Empatados: " + estado.totalEmpatados +
                    "\n  Perdidos: " + estado.totalPerdidos +
                    "\n\nComo Local:" +
                    "\n  Ganados: " + estado.localGanados +
                    "\n  Empatados: " + estado.localEmpatados +
                    "\n  Perdidos: " + estado.localPerdidos +
                    "\n\nComo Visitante:" +
                    "\n  Ganados: " + estado.visitanteGanados +
                    "\n  Empatados: " + estado.visitanteEmpatados +
                    "\n  Perdidos: " + estado.visitantePerdidos;
            JOptionPane.showMessageDialog(this, mensaje, "Estado de " + equipo, JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static class PosicionEquipo {
        private String nombreEquipo;
        private int puntos, ganados, empatados, perdidos, golesFavor, golesContra, partidosJugados;

        public PosicionEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }
        // Getters y setters...
        public String getNombreEquipo() { return nombreEquipo; }
        public int getPuntos() { return puntos; }
        public int getGanados() { return ganados; }
        public int getEmpatados() { return empatados; }
        public int getPerdidos() { return perdidos; }
        public int getGolesFavor() { return golesFavor; }
        public int getGolesContra() { return golesContra; }
        public int getPartidosJugados() { return partidosJugados; }
        public void setPuntos(int p) { puntos = p; }
        public void setGanados(int g) { ganados = g; }
        public void setEmpatados(int e) { empatados = e; }
        public void setPerdidos(int p) { perdidos = p; }
        public void setGolesFavor(int gf) { golesFavor = gf; }
        public void setGolesContra(int gc) { golesContra = gc; }
        public void setPartidosJugados(int pj) { partidosJugados = pj; }
    }

    // NUEVO: clase para el estado del equipo
    public static class EstadoEquipo {
        int totalGanados = 0, totalEmpatados = 0, totalPerdidos = 0;
        int localGanados = 0, localEmpatados = 0, localPerdidos = 0;
        int visitanteGanados = 0, visitanteEmpatados = 0, visitantePerdidos = 0;
    }

    // Calcula el estado del equipo
    public static EstadoEquipo calcularEstadoEquipo(
            String nombreEquipo,
            List<Partido> partidos,
            List<InformacionPartido> infoPartidos
    ) {
        EstadoEquipo estado = new EstadoEquipo();
        Map<String, InformacionPartido> infoPorId = new HashMap<>();
        for (InformacionPartido ip : infoPartidos) infoPorId.put(ip.getIdPartido(), ip);

        for (Partido partido : partidos) {
            InformacionPartido ip = infoPorId.get(partido.getIdPartido());
            if (ip == null) continue;

            boolean esLocal = ip.getEquipoLocal().equals(nombreEquipo);
            boolean esVisitante = ip.getEquipoVisitante().equals(nombreEquipo);
            if (!esLocal && !esVisitante) continue;

            int gl = ip.getGolesLocal();
            int gv = ip.getGolesVisitante();

            // Total
            if (esLocal) {
                if (gl > gv) { estado.totalGanados++; estado.localGanados++; }
                else if (gl < gv) { estado.totalPerdidos++; estado.localPerdidos++; }
                else { estado.totalEmpatados++; estado.localEmpatados++; }
            } else if (esVisitante) {
                if (gv > gl) { estado.totalGanados++; estado.visitanteGanados++; }
                else if (gv < gl) { estado.totalPerdidos++; estado.visitantePerdidos++; }
                else { estado.totalEmpatados++; estado.visitanteEmpatados++; }
            }
        }
        return estado;
    }

    // Lógica de tabla de posiciones
    public static List<PosicionEquipo> calcularTablaPosiciones(
            List<Equipo> equipos,
            List<Partido> partidos,
            List<InformacionPartido> infoPartidos
    ) {
        Map<String, PosicionEquipo> tabla = new HashMap<>();
        for (Equipo eq : equipos) tabla.put(eq.getNombreEquipo(), new PosicionEquipo(eq.getNombreEquipo()));
        Map<String, InformacionPartido> infoPorId = new HashMap<>();
        for (InformacionPartido ip : infoPartidos) infoPorId.put(ip.getIdPartido(), ip);

        for (Partido partido : partidos) {
            InformacionPartido ip = infoPorId.get(partido.getIdPartido());
            if (ip == null) continue;
            String local = ip.getEquipoLocal(), visitante = ip.getEquipoVisitante();
            int golesLocal = ip.getGolesLocal(), golesVisitante = ip.getGolesVisitante();
            PosicionEquipo posLocal = tabla.get(local), posVisit = tabla.get(visitante);
            if (posLocal == null || posVisit == null) continue;
            posLocal.setPartidosJugados(posLocal.getPartidosJugados() + 1);
            posVisit.setPartidosJugados(posVisit.getPartidosJugados() + 1);
            posLocal.setGolesFavor(posLocal.getGolesFavor() + golesLocal);
            posLocal.setGolesContra(posLocal.getGolesContra() + golesVisitante);
            posVisit.setGolesFavor(posVisit.getGolesFavor() + golesVisitante);
            posVisit.setGolesContra(posVisit.getGolesContra() + golesLocal);
            if (golesLocal > golesVisitante) {
                posLocal.setGanados(posLocal.getGanados() + 1);
                posLocal.setPuntos(posLocal.getPuntos() + 3);
                posVisit.setPerdidos(posVisit.getPerdidos() + 1);
            } else if (golesLocal < golesVisitante) {
                posVisit.setGanados(posVisit.getGanados() + 1);
                posVisit.setPuntos(posVisit.getPuntos() + 3);
                posLocal.setPerdidos(posLocal.getPerdidos() + 1);
            } else {
                posLocal.setEmpatados(posLocal.getEmpatados() + 1);
                posVisit.setEmpatados(posVisit.getEmpatados() + 1);
                posLocal.setPuntos(posLocal.getPuntos() + 1);
                posVisit.setPuntos(posVisit.getPuntos() + 1);
            }
        }
        List<PosicionEquipo> tablaPosiciones = new ArrayList<>(tabla.values());
        tablaPosiciones.sort(java.util.Comparator.comparingInt(PosicionEquipo::getPuntos).reversed()
                .thenComparingInt(e -> (e.getGolesFavor() - e.getGolesContra()))
                .thenComparingInt(PosicionEquipo::getGolesFavor).reversed());
        return tablaPosiciones;
    }

    // --- Puedes pegar el código de HoverPressButton aquí, si lo usas ---
}