package jframeessss;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.DefaultButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import clases.Centrocampista;
import clases.Defensa;
import clases.Delantero;
import clases.Futbolista;
import clases.Jugador;
import clases.Portero;
import services.CentrocampistaService;
import services.DefensaService;
import services.DelanteroService;
import services.JugadorService;
import services.ObtenerServices;
import services.PorteroService;

public class VentanaPrincipal extends JDialog {

	private static final long serialVersionUID = 1L;
	private static JPanel panelTabla = null;
	private static JScrollPane scrollPaneJugadores = null;
	private static HoverPressButton btnCerrarTabla = null;
	private static HoverPressButton btnJugador = null;
	private static HoverPressButton btnMostrarDelantero = null;
	private static HoverPressButton btnCrearDefensa = null;
	private static HoverPressButton btnCrearCentrocampista = null;
	private static HoverPressButton btnCrearPortero = null;
	private static HoverPressButton btnFlechaIzq;
	private Principal atras;
	private static JPanel panelBotones;

	public VentanaPrincipal(Principal principal) {
		super(principal, true); // Llama al constructor de JDialog y ponlo modal
		setUndecorated(true);
		atras = principal;

		Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setBounds(screenBounds);

		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/jframeessss/recursos/5024318950237909646.jpg"));
		Image originalImage = originalIcon.getImage();
		int width = screenBounds.width;
		int height = screenBounds.height;
		Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel labelFondo = new JLabel(scaledIcon);
		labelFondo.setBounds(0, 0, width, height);
		setContentPane(labelFondo);
		labelFondo.setLayout(null);


		int panelBotonesWidth = 320;
		int panelBotonesHeight = 400;
		int panelBotonesX = (width - panelBotonesWidth) / 2;
		int panelBotonesY = (height - panelBotonesHeight) / 2;
		panelBotones = new JPanel(new GridLayout(6, 1, 0, 18));
		panelBotones.setOpaque(false);
		panelBotones.setBounds(panelBotonesX, panelBotonesY, panelBotonesWidth, panelBotonesHeight);




		btnJugador = createHoverPressButton("Jugadores");
		btnMostrarDelantero = createHoverPressButton("Delantero");
		btnCrearDefensa = createHoverPressButton("Defensa");
		btnCrearCentrocampista = createHoverPressButton("Centrocampista");
		btnCrearPortero = createHoverPressButton("Portero");

		panelBotones.add(btnJugador);
		panelBotones.add(btnMostrarDelantero);
		panelBotones.add(btnCrearDefensa);
		panelBotones.add(btnCrearCentrocampista);
		panelBotones.add(btnCrearPortero);

		labelFondo.add(panelBotones);

		
	
		
		
		btnJugador.addActionListener(evt -> {
			setAllButtonsVisible(false);
			mostrarTablaJugadores(labelFondo);
			btnFlechaIzq.setVisible(false);

		});

		

		btnMostrarDelantero.addActionListener(evt -> {
			setAllButtonsVisible(false);
			mostrarTablaDelantero(labelFondo);
			btnFlechaIzq.setVisible(false);

		});

		btnCrearDefensa.addActionListener(evt -> {
			setAllButtonsVisible(false);
			mostrarTablaDefensa(labelFondo);
			btnFlechaIzq.setVisible(false);
		});

		btnCrearCentrocampista.addActionListener(evt -> {
			setAllButtonsVisible(false);
			mostrarTablaCentrocampista(labelFondo);
			btnFlechaIzq.setVisible(false);

		});

		btnCrearPortero.addActionListener(evt -> {
			setAllButtonsVisible(false);
			mostrarTablaPortero(labelFondo);
			btnFlechaIzq.setVisible(false);

		});

		btnFlechaIzq = createHoverPressButton("Atras",
				new Color(70, 130, 180, 180),   // color normal (puedes ajustar)
				new Color(30, 144, 255, 200),   // color hover (puedes ajustar)
				new Color(0, 60, 120, 220)      // color pressed (puedes ajustar)
				);
		btnFlechaIzq.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnFlechaIzq.setBounds(15, 15, 100,60 );
		btnFlechaIzq.setToolTipText("Volver");
		// Aquí defines la acción que quieres que haga la flecha
		btnFlechaIzq.addActionListener(evt -> {
			atras.setVisible(true);
			dispose();
		});
		labelFondo.add(btnFlechaIzq);
	}


	@SuppressWarnings("serial")
	private void mostrarTablaPortero(JLabel labelFondo) {
		if (panelTabla != null) {
			labelFondo.remove(panelTabla);
			panelTabla = null;
		}

		ObtenerServices service = new ObtenerServices();
		ArrayList<Portero> porteros = (ArrayList<Portero>) service.obtenerPorteros();
		ArrayList<Futbolista> futbolistas = (ArrayList<Futbolista>) service.obtenerFutbolistas();
		ArrayList<Jugador> jugadores = (ArrayList<Jugador>) service.obtenerJugadores();

		String[] columnas = {"Nombre", "Numero", "Edad", "Equipo", "ID", "Partidos", "Goles", "Asistencias", "Posición", "Atajadas", "Goles Encajados"};
		ArrayList<Object[]> datosList = new ArrayList<>();

		for (Portero portero : porteros) {
			Futbolista fMatch = null;
			Jugador jMatch = null;
			for (Futbolista f : futbolistas) {
				if (f.getIdFutbolista().equals(portero.getIdFutbolista()) && f.getTipo().equals("JUGADOR")) {
					fMatch = f;
					break;
				}
			}
			for (Jugador j : jugadores) {
				if (j.getIdFutbolista().equals(portero.getIdFutbolista())) {
					jMatch = j;
					break;
				}
			}
			if (fMatch != null && jMatch != null) {
				Object[] fila = new Object[columnas.length];
				fila[0] = fMatch.getNombre();
				fila[1] = fMatch.getNumero();
				fila[2] = fMatch.getEdad();
				fila[3] = fMatch.getIdEquipo();
				fila[4] = jMatch.getIdFutbolista();
				fila[5] = jMatch.getPartidosJugados();
				fila[6] = jMatch.getGoles();
				fila[7] = jMatch.getAsistencias();
				fila[8] = jMatch.getPosicion();
				fila[9] = portero.getAtajadas();          // ajusta el método a tu clase Portero
				fila[10] = portero.getGolesEncajados();  // ajusta el método a tu clase Portero
				datosList.add(fila);
			}
		}

		Object[][] datos = datosList.toArray(new Object[0][columnas.length]);
		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tabla = new JTable(model);
		tabla.setOpaque(false);
		tabla.setBackground(new Color(255, 255, 255, 100));
		tabla.setForeground(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setGridColor(new Color(0, 0, 0, 100));
		tabla.setFillsViewportHeight(true);
		tabla.setEnabled(true);
		tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		int panelWidth = 1260;
		int panelHeight = 600;
		int scrollWidth = 1200;
		int scrollHeight = 450;

		Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int panelX = (screenBounds.width - panelWidth) / 2;
		int panelY = (screenBounds.height - panelHeight) / 2;

		scrollPaneJugadores = new JScrollPane(tabla);
		scrollPaneJugadores.setOpaque(false);
		scrollPaneJugadores.getViewport().setOpaque(false);
		scrollPaneJugadores.setBounds((panelWidth-scrollWidth)/2, 50, scrollWidth, scrollHeight);

		panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255, 255, 255, 120));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelTabla.setOpaque(false);

		// Botón Cerrar
		btnCerrarTabla = createHoverPressButton("Cerrar",
				new Color(255, 70, 70, 180),
				new Color(220, 20, 60, 200),
				new Color(180, 0, 0, 220)
				);
		btnCerrarTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCerrarTabla.setBounds(panelWidth-160, panelHeight-50, 120, 30);
		btnCerrarTabla.addActionListener(evt -> {
			labelFondo.remove(panelTabla);
			setAllButtonsVisible(true);
			labelFondo.repaint();
			labelFondo.revalidate();
			btnFlechaIzq.setVisible(true);

		});

		// Botón Crear Nuevo Portero
		HoverPressButton btnCrearPortero = createHoverPressButton("Crear Portero");
		btnCrearPortero.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCrearPortero.setBounds(40, panelHeight-50, 220, 30);
		btnCrearPortero.addActionListener(evt -> {
			ObtenerServices serviceNew = new ObtenerServices();
			CrearPorteroFrame frame = new CrearPorteroFrame(serviceNew);
			frame.setVisible(true);
			mostrarTablaPortero(labelFondo);
		});

		// Botón Eliminar Portero Seleccionado
		HoverPressButton btnEliminarPortero = createHoverPressButton("Eliminar Portero");
		btnEliminarPortero.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnEliminarPortero.setBounds(280, panelHeight-50, 280, 30);
		btnEliminarPortero.addActionListener(evt -> {
			int selectedRow = tabla.getSelectedRow();
			if (selectedRow >= 0) {
				String idFutbolista = tabla.getValueAt(selectedRow, 4).toString();
				PorteroService p = new PorteroService();
				p.eliminarPortero(idFutbolista);
				model.removeRow(selectedRow);
			}
		});

		// Botón Actualizar Portero
		HoverPressButton btnActualizarPortero = createHoverPressButton("Actualizar Portero");
		btnActualizarPortero.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnActualizarPortero.setBounds(580, panelHeight-50, 220, 30);
		btnActualizarPortero.addActionListener(evt -> {
			int selectedRow = tabla.getSelectedRow();
			if (selectedRow >= 0) {
				String idFutbolista = tabla.getValueAt(selectedRow, 4).toString();
				EditarPorteroFrame e1 = new EditarPorteroFrame(idFutbolista);
				e1.setVisible(true);
				mostrarTablaPortero(labelFondo);
			}
		});

		panelTabla.add(scrollPaneJugadores);
		panelTabla.add(btnCerrarTabla);
		panelTabla.add(btnCrearPortero);
		panelTabla.add(btnEliminarPortero);
		panelTabla.add(btnActualizarPortero);

		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		setAllButtonsVisible(false);
		labelFondo.repaint();
		labelFondo.revalidate();

	}

	@SuppressWarnings("serial")
	private void mostrarTablaCentrocampista(JLabel labelFondo) {
		if (panelTabla != null) {
			labelFondo.remove(panelTabla);
			panelTabla = null;
		}

		ObtenerServices service = new ObtenerServices();
		ArrayList<Centrocampista> centrocampistas = (ArrayList<Centrocampista>) service.obtenerCentrocampistas();
		ArrayList<Futbolista> futbolistas = (ArrayList<Futbolista>) service.obtenerFutbolistas();
		ArrayList<Jugador> jugadores = (ArrayList<Jugador>) service.obtenerJugadores();

		// Añade o modifica las columnas según tu modelo de Centrocampista
		String[] columnas = {"Nombre", "Numero", "Edad", "Equipo", "ID", "Partidos", "Goles", "Asistencias", "Posición", "Pases", "Recuperaciones"};
		ArrayList<Object[]> datosList = new ArrayList<>();

		for (Centrocampista centrocampista : centrocampistas) {
			Futbolista fMatch = null;
			Jugador jMatch = null;
			for (Futbolista f : futbolistas) {
				if (f.getIdFutbolista().equals(centrocampista.getIdFutbolista()) && f.getTipo().equals("JUGADOR")) {
					fMatch = f;
					break;
				}
			}
			for (Jugador j : jugadores) {
				if (j.getIdFutbolista().equals(centrocampista.getIdFutbolista())) {
					jMatch = j;
					break;
				}
			}
			if (fMatch != null && jMatch != null) {
				Object[] fila = new Object[columnas.length];
				fila[0] = fMatch.getNombre();
				fila[1] = fMatch.getNumero();
				fila[2] = fMatch.getEdad();
				fila[3] = fMatch.getIdEquipo();
				fila[4] = jMatch.getIdFutbolista();
				fila[5] = jMatch.getPartidosJugados();
				fila[6] = jMatch.getGoles();
				fila[7] = jMatch.getAsistencias();
				fila[8] = jMatch.getPosicion();
				fila[9] = centrocampista.getPasesCompletos();          // Reemplaza por tu método real
				fila[10] = centrocampista.getIntercepciones();// Reemplaza por tu método real
				datosList.add(fila);
			}
		}

		Object[][] datos = datosList.toArray(new Object[0][columnas.length]);
		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tabla = new JTable(model);
		tabla.setOpaque(false);
		tabla.setBackground(new Color(255, 255, 255, 100));
		tabla.setForeground(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setGridColor(new Color(0, 0, 0, 100));
		tabla.setFillsViewportHeight(true);
		tabla.setEnabled(true);
		tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		int panelWidth = 1260;
		int panelHeight = 600;
		int scrollWidth = 1200;
		int scrollHeight = 450;

		Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int panelX = (screenBounds.width - panelWidth) / 2;
		int panelY = (screenBounds.height - panelHeight) / 2;

		scrollPaneJugadores = new JScrollPane(tabla);
		scrollPaneJugadores.setOpaque(false);
		scrollPaneJugadores.getViewport().setOpaque(false);
		scrollPaneJugadores.setBounds((panelWidth-scrollWidth)/2, 50, scrollWidth, scrollHeight);

		panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255, 255, 255, 120));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelTabla.setOpaque(false);

		// Botón Cerrar
		btnCerrarTabla = createHoverPressButton("Cerrar",
				new Color(255, 70, 70, 180),
				new Color(220, 20, 60, 200),
				new Color(180, 0, 0, 220)
				);
		btnCerrarTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCerrarTabla.setBounds(panelWidth-160, panelHeight-50, 120, 30);
		btnCerrarTabla.addActionListener(evt -> {
			labelFondo.remove(panelTabla);
			setAllButtonsVisible(true);
			labelFondo.repaint();
			labelFondo.revalidate();
			btnFlechaIzq.setVisible(true);
		});

		// Botón Crear Nuevo Centrocampista
		HoverPressButton btnCrearCentrocampista = createHoverPressButton("Crear Centrocampista");
		btnCrearCentrocampista.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCrearCentrocampista.setBounds(40, panelHeight-50, 220, 30);
		btnCrearCentrocampista.addActionListener(evt -> {
			ObtenerServices serviceNew = new ObtenerServices();
			CrearCentrocampistaFrame frame = new CrearCentrocampistaFrame(serviceNew);
			frame.setVisible(true);
			mostrarTablaCentrocampista(labelFondo);
		});

		// Botón Eliminar Centrocampista Seleccionado
		HoverPressButton btnEliminarCentrocampista = createHoverPressButton("Eliminar Centrocampista");
		btnEliminarCentrocampista.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnEliminarCentrocampista.setBounds(280, panelHeight-50, 280, 30);
		btnEliminarCentrocampista.addActionListener(evt -> {
			int selectedRow = tabla.getSelectedRow();
			if (selectedRow >= 0) {
				String idFutbolista = tabla.getValueAt(selectedRow, 4).toString();
				CentrocampistaService c = new CentrocampistaService();
				c.eliminarCentrocampista(idFutbolista);
				model.removeRow(selectedRow);
			}
		});

		// Botón Actualizar Centrocampista
		HoverPressButton btnActualizarCentrocampista = createHoverPressButton("Actualizar Centrocampista");
		btnActualizarCentrocampista.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnActualizarCentrocampista.setBounds(580, panelHeight-50, 220, 30);
		btnActualizarCentrocampista.addActionListener(evt -> {
			int selectedRow = tabla.getSelectedRow();
			if (selectedRow >= 0) {
				String idFutbolista = tabla.getValueAt(selectedRow, 4).toString();
				EditarCentrocampistaFrame e1 = new EditarCentrocampistaFrame(idFutbolista);
				e1.setVisible(true);
				mostrarTablaCentrocampista(labelFondo);
			}
		});

		panelTabla.add(scrollPaneJugadores);
		panelTabla.add(btnCerrarTabla);
		panelTabla.add(btnCrearCentrocampista);
		panelTabla.add(btnEliminarCentrocampista);
		panelTabla.add(btnActualizarCentrocampista);

		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		setAllButtonsVisible(false);
		labelFondo.repaint();
		labelFondo.revalidate();
	}

	@SuppressWarnings("serial")
	private void mostrarTablaDefensa(JLabel labelFondo) {
		if (panelTabla != null) {
			labelFondo.remove(panelTabla);
			panelTabla = null;
		}

		ObtenerServices service = new ObtenerServices();
		ArrayList<Defensa> defensas = (ArrayList<Defensa>) service.obtenerDefensas();
		ArrayList<Futbolista> futbolistas = (ArrayList<Futbolista>) service.obtenerFutbolistas();
		ArrayList<Jugador> jugadores = (ArrayList<Jugador>) service.obtenerJugadores();

		String[] columnas = {"Nombre", "Numero", "Edad", "Equipo", "ID", "Partidos", "Goles", "Asistencias", "Posición", "Entradas", "Bloqueos"};
		ArrayList<Object[]> datosList = new ArrayList<>();

		for (Defensa defensa : defensas) {
			Futbolista fMatch = null;
			Jugador jMatch = null;
			for (Futbolista f : futbolistas) {
				if (f.getIdFutbolista().equals(defensa.getIdFutbolista()) && f.getTipo().equals("JUGADOR")) {
					fMatch = f;
					break;
				}
			}
			for (Jugador j : jugadores) {
				if (j.getIdFutbolista().equals(defensa.getIdFutbolista())) {
					jMatch = j;
					break;
				}
			}
			if (fMatch != null && jMatch != null) {
				Object[] fila = new Object[columnas.length];
				fila[0] = fMatch.getNombre();
				fila[1] = fMatch.getNumero();
				fila[2] = fMatch.getEdad();
				fila[3] = fMatch.getIdEquipo();
				fila[4] = jMatch.getIdFutbolista();
				fila[5] = jMatch.getPartidosJugados();
				fila[6] = jMatch.getGoles();
				fila[7] = jMatch.getAsistencias();
				fila[8] = jMatch.getPosicion();
				fila[9] = defensa.getEntradas();       // Suponiendo que existe este método
				fila[10] = defensa.getBloqueos();// Suponiendo que existe este método
				datosList.add(fila);
			}
		}

		Object[][] datos = datosList.toArray(new Object[0][columnas.length]);
		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tabla = new JTable(model);
		tabla.setOpaque(false);
		tabla.setBackground(new Color(255, 255, 255, 100));
		tabla.setForeground(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setGridColor(new Color(0, 0, 0, 100));
		tabla.setFillsViewportHeight(true);
		tabla.setEnabled(true);
		tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		int panelWidth = 1260;
		int panelHeight = 600;
		int scrollWidth = 1200;
		int scrollHeight = 450;

		Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int panelX = (screenBounds.width - panelWidth) / 2;
		int panelY = (screenBounds.height - panelHeight) / 2;

		scrollPaneJugadores = new JScrollPane(tabla);
		scrollPaneJugadores.setOpaque(false);
		scrollPaneJugadores.getViewport().setOpaque(false);
		scrollPaneJugadores.setBounds((panelWidth-scrollWidth)/2, 50, scrollWidth, scrollHeight);

		panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255, 255, 255, 120));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelTabla.setOpaque(false);

		// Botón Cerrar
		btnCerrarTabla = createHoverPressButton("Cerrar",
				new Color(255, 70, 70, 180),
				new Color(220, 20, 60, 200),
				new Color(180, 0, 0, 220)
				);
		btnCerrarTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCerrarTabla.setBounds(panelWidth-160, panelHeight-50, 120, 30);
		btnCerrarTabla.addActionListener(evt -> {
			labelFondo.remove(panelTabla);
			setAllButtonsVisible(true);
			labelFondo.repaint();
			labelFondo.revalidate();
			btnFlechaIzq.setVisible(true);
		});

		// Botón Crear Nuevo Defensa
		HoverPressButton btnCrearDefensa = createHoverPressButton("Crear Defensa");
		btnCrearDefensa.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCrearDefensa.setBounds(40, panelHeight-50, 220, 30);
		btnCrearDefensa.addActionListener(evt -> {
			ObtenerServices serviceNew = new ObtenerServices();
			CrearDefensaFrame frame = new CrearDefensaFrame(serviceNew);
			frame.setVisible(true);
			mostrarTablaDefensa(labelFondo);
		});

		// Botón Eliminar Defensa Seleccionado
		HoverPressButton btnEliminarDefensa = createHoverPressButton("Eliminar Defensa");
		btnEliminarDefensa.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnEliminarDefensa.setBounds(280, panelHeight-50, 280, 30);
		btnEliminarDefensa.addActionListener(evt -> {
			int selectedRow = tabla.getSelectedRow();
			if (selectedRow >= 0) {
				String idFutbolista = tabla.getValueAt(selectedRow, 4).toString();
				DefensaService d = new DefensaService();
				d.eliminarDefensa(idFutbolista);
				model.removeRow(selectedRow);
			}
		});

		// Botón Actualizar Defensa
		HoverPressButton btnActualizarDefensa = createHoverPressButton("Actualizar Defensa");
		btnActualizarDefensa.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnActualizarDefensa.setBounds(580, panelHeight-50, 220, 30);
		btnActualizarDefensa.addActionListener(evt -> {
			int selectedRow = tabla.getSelectedRow();
			if (selectedRow >= 0) {
				String idFutbolista = tabla.getValueAt(selectedRow, 4).toString();
				EditarDefensaFrame e1 = new EditarDefensaFrame(idFutbolista);
				e1.setVisible(true);
				mostrarTablaDefensa(labelFondo);
			}
		});

		panelTabla.add(scrollPaneJugadores);
		panelTabla.add(btnCerrarTabla);
		panelTabla.add(btnCrearDefensa);
		panelTabla.add(btnEliminarDefensa);
		panelTabla.add(btnActualizarDefensa);

		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		setAllButtonsVisible(false);
		labelFondo.repaint();
		labelFondo.revalidate();

	}

	// Botón personalizado con colores configurables SOLO para el de cerrar
	private static HoverPressButton createHoverPressButton(String text, Color normal, Color hover, Color pressed) {
		HoverPressButton btn = new HoverPressButton(text);
		btn.setButtonColors(normal, hover, pressed);
		btn.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setOpaque(false);
		btn.setPreferredSize(new Dimension(48, 48));
		btn.setMaximumSize(new Dimension(48, 48));
		btn.setHorizontalAlignment(SwingConstants.CENTER);
		return btn;
	}

	private static HoverPressButton createHoverPressButton(String text) {
		HoverPressButton btn = new HoverPressButton(text);
		btn.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btn.setForeground(Color.BLACK);
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setOpaque(false);
		btn.setPreferredSize(new Dimension(280, 48));
		btn.setMaximumSize(new Dimension(280, 48));
		btn.setHorizontalAlignment(SwingConstants.CENTER);
		return btn;
	}

	private static void setAllButtonsVisible(boolean visible) {
		panelBotones.setVisible(visible);
		btnJugador.setVisible(visible);
		btnMostrarDelantero.setVisible(visible);
		btnCrearDefensa.setVisible(visible);
		btnCrearCentrocampista.setVisible(visible);
		btnCrearPortero.setVisible(visible);
	}

	@SuppressWarnings({ "serial" })
	private void mostrarTablaDelantero(JLabel labelFondo) {
		if (panelTabla != null) {
			labelFondo.remove(panelTabla);
			panelTabla = null;
		}

		ObtenerServices service = new ObtenerServices();
		ArrayList<Delantero> delanteros = (ArrayList<Delantero>) service.obtenerDelanteros();
		ArrayList<Futbolista> futbolistas = (ArrayList<Futbolista>) service.obtenerFutbolistas();
		ArrayList<Jugador> jugadores = (ArrayList<Jugador>) service.obtenerJugadores();

		String[] columnas = {"Nombre", "Numero", "Edad", "Equipo", "ID", "Partidos", "Goles", "Asistencias", "Posición", "Promedio Goles","Disparos"};
		ArrayList<Object[]> datosList = new ArrayList<>();

		for (Delantero delantero : delanteros) {
			Futbolista fMatch = null;
			Jugador jMatch = null;
			for (Futbolista f : futbolistas) {
				if (f.getIdFutbolista().equals(delantero.getIdFutbolista()) && f.getTipo().equals("JUGADOR")) {
					fMatch = f;
					break;
				}
			}
			for (Jugador j : jugadores) {
				if (j.getIdFutbolista().equals(delantero.getIdFutbolista())) {
					jMatch = j;
					break;
				}
			}
			if (fMatch != null && jMatch != null) {
				Object[] fila = new Object[columnas.length];
				fila[0] = fMatch.getNombre();
				fila[1] = fMatch.getNumero();
				fila[2] = fMatch.getEdad();
				fila[3] = fMatch.getIdEquipo();
				fila[4] = jMatch.getIdFutbolista();
				fila[5] = jMatch.getPartidosJugados();
				fila[6] = jMatch.getGoles();
				fila[7] = jMatch.getAsistencias();
				fila[8] = jMatch.getPosicion();
				fila[9] = jMatch.getPromedioGoles();
				fila[10]= delantero.getTirosPuerta();
				datosList.add(fila);
			}
		}

		Object[][] datos = datosList.toArray(new Object[0][columnas.length]);
		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable tabla = new JTable(model);
		tabla.setOpaque(false);
		tabla.setBackground(new Color(255, 255, 255, 100));
		tabla.setForeground(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setGridColor(new Color(0, 0, 0, 100));
		tabla.setFillsViewportHeight(true);
		tabla.setEnabled(true);
		tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		int panelWidth = 1260;
		int panelHeight = 600;
		int scrollWidth = 1200;
		int scrollHeight = 450;

		Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int panelX = (screenBounds.width - panelWidth) / 2;
		int panelY = (screenBounds.height - panelHeight) / 2;

		scrollPaneJugadores = new JScrollPane(tabla);
		scrollPaneJugadores.setOpaque(false);
		scrollPaneJugadores.getViewport().setOpaque(false);
		scrollPaneJugadores.setBounds((panelWidth-scrollWidth)/2, 50, scrollWidth, scrollHeight);

		panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255, 255, 255, 120));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelTabla.setOpaque(false);

		// Botón Cerrar
		btnCerrarTabla = createHoverPressButton("Cerrar",
				new Color(255, 70, 70, 180),
				new Color(220, 20, 60, 200),
				new Color(180, 0, 0, 220)
				);
		btnCerrarTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCerrarTabla.setBounds(panelWidth-160, panelHeight-50, 120, 30);
		btnCerrarTabla.addActionListener(evt -> {
			labelFondo.remove(panelTabla);
			setAllButtonsVisible(true);
			labelFondo.repaint();
			labelFondo.revalidate();
			btnFlechaIzq.setVisible(true);
		});

		// Botón Crear Nuevo Delantero
		HoverPressButton btnCrearDelantero = createHoverPressButton("Crear Delantero");
		btnCrearDelantero.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCrearDelantero.setBounds(40, panelHeight-50, 220, 30);
		btnCrearDelantero.addActionListener(evt -> {
			ObtenerServices serviceNew = new ObtenerServices();
			CrearDelanteroFrame frame = new CrearDelanteroFrame(serviceNew);
			frame.setVisible(true);
			mostrarTablaDelantero(labelFondo);
		});

		// Botón Eliminar Delantero Seleccionado
		HoverPressButton btnEliminarDelantero = createHoverPressButton("Eliminar Delantero");
		btnEliminarDelantero.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnEliminarDelantero.setBounds(280, panelHeight-50, 280, 30);
		btnEliminarDelantero.addActionListener(evt -> {
			int selectedRow = tabla.getSelectedRow();
			if (selectedRow >= 0) {
				String idFutbolista = tabla.getValueAt(selectedRow, 4).toString();
				DelanteroService d = new DelanteroService();
				d.eliminarDelantero(idFutbolista);

				model.removeRow(selectedRow);
			}
		});

		// Botón Actualizar Delantero
		HoverPressButton btnActualizarDelantero = createHoverPressButton("Actualizar Delantero");
		btnActualizarDelantero.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnActualizarDelantero.setBounds(580, panelHeight-50, 220, 30);
		btnActualizarDelantero.addActionListener(evt -> {
			int selectedRow = tabla.getSelectedRow();
			if (selectedRow >= 0) {
				String idFutbolista = tabla.getValueAt(selectedRow, 4).toString();
				EditarDelanteroFrame e1 = new EditarDelanteroFrame(idFutbolista);
				e1.setVisible(true);
				mostrarTablaDelantero(labelFondo);
			}});

		panelTabla.add(scrollPaneJugadores);
		panelTabla.add(btnCerrarTabla);
		panelTabla.add(btnCrearDelantero);
		panelTabla.add(btnEliminarDelantero);
		panelTabla.add(btnActualizarDelantero);

		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		setAllButtonsVisible(false);
		labelFondo.repaint();
		labelFondo.revalidate();
	}

	@SuppressWarnings("serial")

	private void mostrarTablaJugadores(JLabel labelFondo) {
		if (panelTabla != null) {
			labelFondo.remove(panelTabla);
			panelTabla = null;
		}

		ObtenerServices service = new ObtenerServices();
		ArrayList<Jugador> jugadores = (ArrayList<Jugador>) service.obtenerJugadores();
		ArrayList<Futbolista> futbolistas = (ArrayList<Futbolista>) service.obtenerFutbolistas();
		String[] columnas = {"Nombre","Numero","Edad","Equipo","ID", "Partidos", "Goles", "Asistencias", "Posición", "Promedio Goles"};

		ArrayList<Object[]> datosList = new ArrayList<>();
		for (Jugador j : jugadores) {
			// Buscar el futbolista correspondiente a este jugador
			Futbolista fMatch = null;
			for (Futbolista f : futbolistas) {
				if (f.getIdFutbolista().equals(j.getIdFutbolista()) && f.getTipo().equalsIgnoreCase("JUGADOR")) {
					fMatch = f;
					break;
				}
			}
			if (fMatch != null) {
				Object[] fila = new Object[columnas.length];
				fila[0] = fMatch.getNombre();
				fila[1] = fMatch.getNumero();
				fila[2] = fMatch.getEdad();
				fila[3] = fMatch.getIdEquipo();
				fila[4] = j.getIdFutbolista();
				fila[5] = j.getPartidosJugados();
				fila[6] = j.getGoles();
				fila[7] = j.getAsistencias();
				fila[8] = j.getPosicion();
				fila[9] = j.getPromedioGoles();
				datosList.add(fila);
			}
		}

		Object[][] datos = datosList.toArray(new Object[0][columnas.length]);

		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable tabla = new JTable(model);
		tabla.setOpaque(false);
		tabla.setBackground(new Color(255, 255, 255, 100));
		tabla.setForeground(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setGridColor(new Color(0, 0, 0, 100));
		tabla.setFillsViewportHeight(true);
		tabla.setEnabled(true);
		tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		int panelWidth = 1260;
		int panelHeight = 600;
		int scrollWidth = 1200;
		int scrollHeight = 450;
		Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int panelX = (screenBounds.width - panelWidth) / 2;
		int panelY = (screenBounds.height - panelHeight) / 2;

		scrollPaneJugadores = new JScrollPane(tabla);
		scrollPaneJugadores.setOpaque(false);
		scrollPaneJugadores.getViewport().setOpaque(false);
		scrollPaneJugadores.setBounds((panelWidth-scrollWidth)/2, 50, scrollWidth, scrollHeight);

		panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255, 255, 255, 120));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelTabla.setOpaque(false);

		// Botón Cerrar
		btnCerrarTabla = createHoverPressButton("Cerrar",
				new Color(255, 70, 70, 180),
				new Color(220, 20, 60, 200),
				new Color(180, 0, 0, 220)
				);
		btnCerrarTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCerrarTabla.setBounds(panelWidth-160, panelHeight-50, 120, 30);
		btnCerrarTabla.addActionListener(evt -> {
			labelFondo.remove(panelTabla);
			setAllButtonsVisible(true);
			labelFondo.repaint();
			labelFondo.revalidate();
			btnFlechaIzq.setVisible(true);
		});

		// Botón Crear Nuevo Jugador
		HoverPressButton btnCrearJugador = createHoverPressButton("Crear Jugador");
		btnCrearJugador.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCrearJugador.setBounds(40, panelHeight-50, 220, 30);
		btnCrearJugador.addActionListener(evt -> {
			ObtenerServices serviceNew = new ObtenerServices();
			CrearJugadorFrame frame = new CrearJugadorFrame(serviceNew);
			frame.setVisible(true);
			mostrarTablaJugadores(labelFondo);
		});

		// Botón Eliminar Jugador Seleccionado
		HoverPressButton btnEliminarJugador = createHoverPressButton("Eliminar Jugador");
		btnEliminarJugador.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnEliminarJugador.setBounds(280, panelHeight-50, 280, 30);
		btnEliminarJugador.addActionListener(evt -> {
			int selectedRow = tabla.getSelectedRow();
			if (selectedRow >= 0) {
				String idFutbolista = tabla.getValueAt(selectedRow, 4).toString();
				JugadorService p = new JugadorService();
				p.eliminarJugador(idFutbolista);
				model.removeRow(selectedRow);
			}
		});

		// Botón Actualizar Jugador
		HoverPressButton btnActualizarJugador = createHoverPressButton("Actualizar Jugador");
		btnActualizarJugador.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnActualizarJugador.setBounds(580, panelHeight-50, 220, 30);
		btnActualizarJugador.addActionListener(evt -> {
			int selectedRow = tabla.getSelectedRow();
			if (selectedRow >= 0) {
				String idFutbolista = tabla.getValueAt(selectedRow, 4).toString();
				if ("Portero".equalsIgnoreCase((String) tabla.getValueAt(selectedRow, 8))) {
					EditarPorteroFrame p = new EditarPorteroFrame(idFutbolista);
					p.setVisible(true);

				} else if ("Defensa".equalsIgnoreCase((String) tabla.getValueAt(selectedRow, 8))) {
					EditarDefensaFrame d = new EditarDefensaFrame(idFutbolista);
					d.setVisible(true);
				} else if ("Centrocampista".equalsIgnoreCase((String) tabla.getValueAt(selectedRow, 8))) {
					EditarCentrocampistaFrame c  = new EditarCentrocampistaFrame(idFutbolista);
					c.setVisible(true);
				} else if ("Delantero".equalsIgnoreCase((String) tabla.getValueAt(selectedRow, 8))) {
					EditarDelanteroFrame del = new EditarDelanteroFrame(idFutbolista);
					del.setVisible(true);
				}            
				mostrarTablaJugadores(labelFondo);
			}
			else {
				System.out.println("ffff");
			}
		});

		panelTabla.add(scrollPaneJugadores);
		panelTabla.add(btnCerrarTabla);
		panelTabla.add(btnCrearJugador);
		panelTabla.add(btnEliminarJugador);
		panelTabla.add(btnActualizarJugador);

		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		setAllButtonsVisible(false);
		labelFondo.repaint();
		labelFondo.revalidate();
	}
	
	
	
	

	@SuppressWarnings("serial")
	static class HoverPressButton extends JButton {
		private boolean pressed = false;
		private boolean hovered = false;
		private Color colorNormal = new Color(255, 255, 255, 180);
		private Color colorHover  = new Color(200, 200, 200, 200);
		private Color colorPressed= new Color(180, 180, 180, 220);

		public HoverPressButton(String text) {
			super(text);
			setModel(new DefaultButtonModel());
			addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent evt) {
					pressed = true;
					repaint();
				}
				@Override
				public void mouseReleased(java.awt.event.MouseEvent evt) {
					pressed = false;
					repaint();
				}
				@Override
				public void mouseEntered(java.awt.event.MouseEvent evt) {
					hovered = true;
					repaint();
				}
				@Override
				public void mouseExited(java.awt.event.MouseEvent evt) {
					hovered = false;
					repaint();
				}
			});
		}
		public void setButtonColors(Color normal, Color hover, Color pressed) {
			this.colorNormal = normal;
			this.colorHover  = hover;
			this.colorPressed= pressed;
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
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), 26, 26);
			super.paintComponent(g2);
			g2.dispose();
		}

	}
}