package jframeessss;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clases.Equipo;
import clases.EstadisticasPartido;
import clases.Futbolista;
import clases.InformacionPartido;
import clases.Jugador;
import services.EquipoService;
import services.EstadisticasPartidoService;
import services.InformacionPartidoService;
import services.JugadorService;
import services.ObtenerServices;

@SuppressWarnings("serial")
public class CrearEstadisticaPartido extends JDialog {
    private JTextField tfIdPartido, tfGoles, tfAsistencias;
    private JComboBox<String> cbEquipo, cbJugador;
    private HoverPressButton btnCrear, btnCerrarRojo;
    

    // Listas correctamente inicializadas
    private ArrayList<Equipo> equipos = new ArrayList<>();
    private ArrayList<Futbolista> futbolistas = new ArrayList<>();
    private ArrayList<Futbolista> futbolistas1 = new ArrayList<>();
    private ArrayList<Futbolista> futbolistas2 = new ArrayList<>();
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private ArrayList<Jugador> jugadores1 = new ArrayList<>();
    private ArrayList<Jugador> jugadores2 = new ArrayList<>();
    private static ArrayList<EstadisticasPartido> estadisticas = new ArrayList<EstadisticasPartido>();
private String IDP;
    @SuppressWarnings("deprecation")
    public CrearEstadisticaPartido(String IDPartido) {
        super((Frame) null, "Crear Estadística Partido", true);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        IDP=IDPartido;

        setSize(460, 520);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = 80;
        int y = (screen.height - getHeight()) / 2;
        setLocation(x, y);

        // Obtener equipos y jugadores
        ObtenerServices obtenerServices = new ObtenerServices();
        InformacionPartidoService ps =  new InformacionPartidoService();
        InformacionPartido i = ps.getInformacionPartidoById(IDPartido);
        EstadisticasPartidoService estad = new EstadisticasPartidoService();
        
        // Inicializar listas aquí para evitar NullPointerException
        equipos = new ArrayList<>();
        futbolistas = (ArrayList<Futbolista>) obtenerServices.obtenerFutbolistas();
        jugadores = (ArrayList<Jugador>) obtenerServices.obtenerJugadores();
        futbolistas1 = new ArrayList<>();
        futbolistas2 = new ArrayList<>();
        jugadores1 = new ArrayList<>();
        jugadores2 = new ArrayList<>();

        // Asegúrate de que el equipo existe antes de añadirlo
        Equipo equipoLocal = new EquipoService().getEquipoById(i.getEquipoLocal());
        Equipo equipoVisitante = new EquipoService().getEquipoById(i.getEquipoVisitante());
        if (equipoLocal != null) equipos.add(equipoLocal);
        if (equipoVisitante != null) equipos.add(equipoVisitante);

        // Asignar futbolistas y jugadores a cada equipo
        for(Futbolista f : futbolistas) {
            if(equipos.size() > 0 && equipos.get(0).getNombreEquipo().equals(f.getIdEquipo())) {
                futbolistas1.add(f);
                for(Jugador j : jugadores) {
                    if(j.getIdFutbolista().equals(f.getIdFutbolista()))
                        jugadores1.add(j);
                }
            } else if (equipos.size() > 1 && equipos.get(1).getNombreEquipo().equals(f.getIdEquipo())) {
                futbolistas2.add(f);
                for(Jugador j : jugadores) {
                    if(j.getIdFutbolista().equals(f.getIdFutbolista()))
                        jugadores2.add(j);
                }
            }
        }

        JPanel bgPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 210));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 36, 36);
                g2.dispose();
            }
        };
        bgPanel.setOpaque(false);
        bgPanel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(9, 9, 9, 9);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 18);

        // ID Partido
        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("ID Partido:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfIdPartido = new JTextField(); tfIdPartido.setFont(textFont); tfIdPartido.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfIdPartido, gbc); row++;
        tfIdPartido.setText(IDPartido);
        tfIdPartido.enable(false);

        // ComboBox Equipo
        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Equipo:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        cbEquipo = new JComboBox<>();
        for (Equipo eq : equipos) cbEquipo.addItem(eq.getNombreEquipo());
        cbEquipo.setFont(textFont);
        cbEquipo.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(cbEquipo, gbc); row++;

        // ComboBox Jugador
        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Jugador:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        cbJugador = new JComboBox<>();
        cbJugador.setFont(textFont);
        cbJugador.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(cbJugador, gbc); row++;

        // Inicializar jugadores para el primer equipo seleccionado
        actualizarJugadoresCombo();

        cbEquipo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarJugadoresCombo();
            }
        });

        // Goles
        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Goles:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfGoles = new JTextField(); tfGoles.setFont(textFont); tfGoles.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfGoles, gbc); row++;

        // Asistencias
        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Asistencias:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfAsistencias = new JTextField(); tfAsistencias.setFont(textFont); tfAsistencias.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfAsistencias, gbc); row++;

        // Botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCrear = new HoverPressButton("Crear",
            new Color(0, 153, 76, 200),
            new Color(0, 120, 60, 220),
            new Color(0, 80, 40, 240)
        );
        btnCrear.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setPreferredSize(new Dimension(120, 44));
        btnCrear.setMaximumSize(new Dimension(120, 44));
        btnCrear.setMinimumSize(new Dimension(120, 44));

        btnCerrarRojo = new HoverPressButton("Cerrar",
            new Color(255, 70, 70, 180),
            new Color(220, 20, 60, 200),
            new Color(180, 0, 0, 220)
        );
        btnCerrarRojo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnCerrarRojo.setForeground(Color.WHITE);
        btnCerrarRojo.setPreferredSize(new Dimension(120, 44));
        btnCerrarRojo.setMaximumSize(new Dimension(120, 44));
        btnCerrarRojo.setMinimumSize(new Dimension(120, 44));

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(btnCrear);
        buttonPanel.add(Box.createRigidArea(new Dimension(18, 0)));
        buttonPanel.add(btnCerrarRojo);
        buttonPanel.add(Box.createHorizontalGlue());

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bgPanel.add(buttonPanel, gbc);

        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(0,0,0,0));
        getContentPane().add(bgPanel);

        btnCerrarRojo.addActionListener(e -> dispose());
        btnCrear.addActionListener((ActionEvent e) -> {
            if (
                tfIdPartido.getText().trim().isEmpty() ||
                cbEquipo.getSelectedItem() == null ||
                cbJugador.getSelectedItem() == null ||
                tfGoles.getText().trim().isEmpty() ||
                tfAsistencias.getText().trim().isEmpty()
            ) {
                JOptionPane.showMessageDialog(CrearEstadisticaPartido.this, "¡Debe completar todos los campos!", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                JugadorService service = new JugadorService();
                String nombreEquipo = (String) cbEquipo.getSelectedItem();
                String nombre = (String) cbJugador.getSelectedItem();
                boolean stop = false;

                // Determina listas de jugadores y futbolistas según equipo
                ArrayList<Futbolista> listaFutbolistas;
                ArrayList<Jugador> listaJugadores;
                int golesMax, asistenciasMax;

                if (nombreEquipo.equals(equipos.get(0).getNombreEquipo())) {
                    listaFutbolistas = futbolistas1;
                    listaJugadores = jugadores1;
                    golesMax = i.getGolesLocal();
                    asistenciasMax = i.getAsistenciasLocal();
                } else {
                    listaFutbolistas = futbolistas2;
                    listaJugadores = jugadores2;
                    golesMax = i.getGolesVisitante();
                    asistenciasMax = i.getAsistenciasVisitante();
                    
                }

                // Busca el futbolista y jugador seleccionado
                for (int p = 0; p < listaFutbolistas.size() && !stop; p++) {
                    if (listaFutbolistas.get(p).getNombre().equals(nombre)) {
                        String idFutbolista = listaFutbolistas.get(p).getIdFutbolista();
                        String idEquipo = listaFutbolistas.get(p).getIdEquipo();
                        for (int k = 0; k < listaJugadores.size() && !stop; k++) {
                            if (listaJugadores.get(k).getIdFutbolista().equals(idFutbolista)) {
                                // Obtén estadísticas actuales
                                estadisticas = (ArrayList<EstadisticasPartido>) estad.obtenerInfoPartidoEquipo(IDPartido, idEquipo);
                                int goles = golesEnElpartido(estadisticas);
                                int asistencias = asistenciasEnElpartido(estadisticas);

                                int golesNuevos = Integer.parseInt(tfGoles.getText());
                                int asistenciasNuevas = Integer.parseInt(tfAsistencias.getText());

                                if (asistencias + asistenciasNuevas <= asistenciasMax) {
                                    if (goles + golesNuevos <= golesMax) {
                                        // Actualiza jugador
                                        service.actualizarGolesYAsistencias(
                                            listaJugadores.get(k).getIdFutbolista(),
                                            listaJugadores.get(k).getGoles() + golesNuevos,
                                            listaJugadores.get(k).getAsistencias() + asistenciasNuevas
                                        );
                                        // Crea la estadística solo una vez
                                        EstadisticasPartido estadistica = new EstadisticasPartido();
                                        estadistica.setIdPartido(IDPartido);
                                        estadistica.setIdFutbolista(idFutbolista);
                                        estadistica.setGoles(golesNuevos);
                                        estadistica.setAsistencias(asistenciasNuevas);
                                        estad.createEstadisticasPartido(estadistica);
                                        stop = true;
                                        JOptionPane.showMessageDialog(CrearEstadisticaPartido.this, "¡Estadística creada correctamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                        dispose();
                                    } else {
                                        JOptionPane.showMessageDialog(CrearEstadisticaPartido.this, "Error: Le está asignando demasiados goles. Solo puede asignarle: " + (golesMax - goles), "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(CrearEstadisticaPartido.this, "Error: Le está asignando demasiadas asistencias. Solo puede asignarle: " + (asistenciasMax - asistencias), "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CrearEstadisticaPartido.this, "Verifique que los campos numéricos tengan valores válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    public int golesEnElpartido(ArrayList<EstadisticasPartido>estadisticass) {
    	int goles = 0;
    	for(EstadisticasPartido es: estadisticass) {
    		goles+=es.getGoles();
    	}
    	
    	return goles;
    }
    
    public int asistenciasEnElpartido(ArrayList<EstadisticasPartido>estadisticass) {
    	int as = 0;
    	for(EstadisticasPartido es: estadisticass) {
    		
    		as+=es.getAsistencias	();
    	}
    	
    	return as;
    }
    
    private void actualizarJugadoresCombo() {
        cbJugador.removeAllItems();
        EstadisticasPartidoService estadis = new EstadisticasPartidoService();
        String nombreEquipo = (String) cbEquipo.getSelectedItem();
        if (nombreEquipo == null) return;
        // Solo puede haber dos equipos
        if(equipos.size() > 0 && nombreEquipo.equals(equipos.get(0).getNombreEquipo())) {
        	
        	
            for (Futbolista f : futbolistas1) {
//            	cbJugador.get
            	EstadisticasPartido estadistica3 =  estadis.getEstadisticasPartidoById(IDP, f.getIdFutbolista());
            	
            if(estadistica3==null)
            	if(!f.getTipo().equalsIgnoreCase("ENTRENADOR"))
                cbJugador.addItem(f.getNombre());
            }
        } else if(equipos.size() > 1 && nombreEquipo.equals(equipos.get(1).getNombreEquipo())) {
        	
            for (Futbolista f : futbolistas2) {
            	EstadisticasPartido estadistica5 =  estadis.getEstadisticasPartidoById(IDP, f.getIdFutbolista());
            	if(estadistica5==null)
            	if(!f.getTipo().equalsIgnoreCase("ENTRENADOR"))
                cbJugador.addItem(f.getNombre());
            }
        }
    }

   

    static class HoverPressButton extends JButton {
        private boolean pressed = false;
        private boolean hovered = false;
        private Color colorNormal;
        private Color colorHover;
        private Color colorPressed;

        public HoverPressButton(String text, Color normal, Color hover, Color pressedC) {
            super(text);
            setButtonColors(normal, hover, pressedC);
            setModel(new DefaultButtonModel());
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    pressed = true;
                    repaint();
                }
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    pressed = false;
                    repaint();
                }
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    hovered = true;
                    repaint();
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    hovered = false;
                    repaint();
                }
            });
        }
        public void setButtonColors(Color normal, Color hover, Color pressedC) {
            this.colorNormal = normal;
            this.colorHover  = hover;
            this.colorPressed= pressedC;
        }

     
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (pressed) {
                g2.setColor(colorPressed);
            } else if (hovered) {
                g2.setColor(colorHover);
            } else {
                g2.setColor(colorNormal);
            }
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
            super.paintComponent(g2);
            g2.dispose();
        }
    }
} 