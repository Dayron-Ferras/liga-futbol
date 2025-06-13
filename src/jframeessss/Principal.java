package jframeessss;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import services.*;
import clases.*;

public class Principal extends JFrame {
	private static final long serialVersionUID = 1L;
	private static HoverPressButton btnAtras;
	private static HoverPressButton btnJugadores = null;
	private static HoverPressButton btnPartidos = null;
	private static HoverPressButton btnTablaPosiciones = null;
	private static HoverPressButton btnEntrenadores = null;
	private static HoverPressButton btnEstadios = null;
	private static HoverPressButton btnAllStars = null; // <--- NUEVO
	private static HoverPressButton btnXClose = null;
	
	
	private static JPanel panelTabla = null;
	private static HoverPressButton btnEquipos = null;
	private static JPanel panelBotones;
	private JPanel panelComboTemporada;
	@SuppressWarnings("unused")
	private Object panelComboEquipos;
	@SuppressWarnings("unused")
	private Component panelComboDosEquipos;

	public Principal() {
		setUndecorated(true);
		Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setBounds(screenBounds);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		// ComboBox de temporada en esquina superior izquierda
		panelComboTemporada = new JPanel(null);
		panelComboTemporada.setOpaque(false);
		panelComboTemporada.setBounds(15, 15, 160, 45);
		ObtenerServices o = new ObtenerServices();
		ArrayList<Temporada> t = (ArrayList<Temporada>) o.obtenerTemporadas();
		String[] temporadas = new String[t.size()];
		int j = 0;
		for (int i = t.size() - 1; i >= 0; i--) {
			temporadas[j] = String.valueOf(t.get(i).getIdTemporada());
			j++;
		}
		JComboBox<String> comboTemporada = new JComboBox<>(temporadas);
		comboTemporada.setFont(new Font("Segoe UI", Font.BOLD, 16));
		comboTemporada.setBounds(0, 0, 160, 38);
		comboTemporada.setOpaque(true);
		comboTemporada.setBackground(Color.BLACK);
		comboTemporada.setForeground(Color.WHITE);
		comboTemporada.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel label = new JLabel(value != null ? value.toString() : "");
			label.setFont(comboTemporada.getFont());
			label.setOpaque(true);
			label.setForeground(Color.WHITE);
			label.setBackground(isSelected ? new Color(40, 40, 40) : Color.BLACK);
			return label;
		});
		panelComboTemporada.add(comboTemporada);
		labelFondo.add(panelComboTemporada);

		btnXClose = createHoverPressButton("X",
				new Color(255, 70, 70, 180),
				new Color(220, 20, 60, 200),
				new Color(180, 0, 0, 220)
				);
		btnXClose.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnXClose.setBounds(width - 63, 15, 48, 48);
		btnXClose.setToolTipText("Cerrar ventana");
		btnXClose.addActionListener(evt -> System.exit(0));
		labelFondo.add(btnXClose);

		int panelBotonesWidth = 320;
		int panelBotonesHeight = 370; // Ajustado para 6 botones
		int panelBotonesX = (width - panelBotonesWidth) / 2;
		int panelBotonesY = (height - panelBotonesHeight) / 2;

		panelBotones = new JPanel(new GridLayout(6, 1, 0, 18)); // <--- 6 filas
		panelBotones.setOpaque(false);
		panelBotones.setBounds(panelBotonesX, panelBotonesY, panelBotonesWidth, panelBotonesHeight);

		
		
		btnEquipos = createHoverPressButton("Equipos");
		btnJugadores = createHoverPressButton("Jugadores");
		btnPartidos = createHoverPressButton("Partidos");
		btnTablaPosiciones = createHoverPressButton("Tabla de Posiciones");
		btnEntrenadores = createHoverPressButton("Entrenadores");
		btnEstadios = createHoverPressButton("Estadios");
		btnAllStars = createHoverPressButton("All Stars");

		
		
		
		
		
		
		btnJugadores.setButtonColors(
				new Color(70, 130, 180, 180),
				new Color(100, 149, 237, 220),
				new Color(65, 105, 225, 220)
				);
		btnPartidos.setButtonColors(
				new Color(60, 179, 113, 180),
				new Color(50, 205, 50, 220),
				new Color(34, 139, 34, 220)
				);
		btnTablaPosiciones.setButtonColors(
				new Color(255, 140, 0, 180),
				new Color(255, 165, 0, 220),
				new Color(255, 69, 0, 220)
				);
		btnEntrenadores.setButtonColors(
				new Color(170, 70, 200, 180),
				new Color(200, 120, 237, 220),
				new Color(120, 65, 180, 220)
				);
		btnEstadios.setButtonColors(
				new Color(70, 70, 180, 180),
				new Color(120, 120, 237, 220),
				new Color(65, 65, 225, 220)
				);
		btnAllStars.setButtonColors(
				new Color(255, 215, 0, 200),      // Oro
				new Color(255, 223, 80, 220),     // Oro claro
				new Color(218, 165, 32, 220)      // Oro oscuro
				);

		
		
	   panelBotones.add(btnEquipos);
		
		
		
		panelBotones.add(btnJugadores);
		panelBotones.add(btnPartidos);
		panelBotones.add(btnTablaPosiciones);
		panelBotones.add(btnEntrenadores);
		panelBotones.add(btnEstadios);
		panelBotones.add(btnAllStars);

		labelFondo.add(panelBotones);

		
		btnEquipos.addActionListener(evt -> {
			setAllButtonsVisible(false);
			mostrarTablaEquipos(labelFondo);
			panelComboTemporada.setVisible(false);

		});

		btnJugadores.addActionListener(evt -> {
			VentanaPrincipal v = new VentanaPrincipal(this);
			v.setVisible(true);

		});

		btnPartidos.addActionListener(evt -> {
			PartidosDialogs p = new PartidosDialogs(this, (String) comboTemporada.getSelectedItem());
			p.setVisible(true);

		});

		btnTablaPosiciones.addActionListener(evt -> {
			int idTemporada = Integer.parseInt((String) comboTemporada.getSelectedItem());
			mostrarTablaPosiciones(labelFondo, panelBotonesWidth, panelBotonesHeight, idTemporada);
			setAllButtonsVisible(false);
			panelComboTemporada.setVisible(false);
		});

		btnEntrenadores.addActionListener(evt -> {
			mostrarTablaEntrenadores((JLabel) getContentPane(), getWidth(), getHeight());
			setAllButtonsVisible(false);
			panelComboTemporada.setVisible(false);
		});

		btnEstadios.addActionListener(evt -> {
			mostrarTablaEstadios((JLabel) getContentPane(), getWidth(), getHeight());
			setAllButtonsVisible(false);
			panelComboTemporada.setVisible(false);
		});

		btnAllStars.addActionListener(evt -> {
			int idTemporada = Integer.parseInt((String) comboTemporada.getSelectedItem());
			mostrarEquipoAllStars((JLabel) getContentPane(), getWidth(), getHeight(), idTemporada);
			setAllButtonsVisible(false);
			panelComboTemporada.setVisible(false);
		});
	}




	private void mostrarEquipoAllStars(JLabel labelFondo, int width, int height, int idTemporada) {
		if (labelFondo.getClientProperty("panelTablaAllStars") != null) {
			JPanel oldPanel = (JPanel) labelFondo.getClientProperty("panelTablaAllStars");
			labelFondo.remove(oldPanel);
			labelFondo.putClientProperty("panelTablaAllStars", null);
		}
		ObtenerServices service = new ObtenerServices();
		ArrayList<Jugador> jugadores = service.obtenerIdsEquipoTodosEstrellas();
		String[] columnas = {"Posición", "Jugador", "Equipo", "Estadísticas destacadas"};
		Object[][] datos = new Object[11][columnas.length];
		Futbolista fut = new Futbolista();

		int fila = 0;
		for(Jugador j : jugadores)
			if ("Portero".equalsIgnoreCase(j.getPosicion())) {
				PorteroService por = new PorteroService();
				FutbolistaService f = new FutbolistaService();
				Portero p = por.getPorteroById(j.getIdFutbolista());
				fut= f.getFutbolistaById(j.getIdFutbolista());
				datos[fila][0] = "Portero";
				datos[fila][1] = fut.getNombre();
				datos[fila][2] = fut.getIdEquipo();
				datos[fila][3] = "Atajadas: " + p.getAtajadas() + ", Goles Encajados: " + p.getGolesEncajados();
				 fila++;
			} else if ("Defensa".equalsIgnoreCase(j.getPosicion())) {
				FutbolistaService f = new FutbolistaService();
				DefensaService def = new DefensaService();
				Defensa d = def.getDefensaById(j.getIdFutbolista());
				fut= f.getFutbolistaById(j.getIdFutbolista());
				        	  datos[fila][0] = "Defensa";
				              datos[fila][1] = fut.getNombre();
				              datos[fila][2] = fut.getIdEquipo();
				              datos[fila][3] = "Entradas: " + d.getEntradas() + ", Bloqueos: " + d.getBloqueos();
				              fila++;


			} else if ("Centrocampista".equalsIgnoreCase(j.getPosicion())) {
				FutbolistaService f = new FutbolistaService();
				CentrocampistaService cen = new CentrocampistaService();
				Centrocampista c = cen.getCentrocampistaById(j.getIdFutbolista());
				fut= f.getFutbolistaById(j.getIdFutbolista());
				        	  datos[fila][0] = "Centrocampista";
				              datos[fila][1] = fut.getNombre();
				              datos[fila][2] = fut.getIdEquipo();
				              datos[fila][3] = "Intercepciones: " + c.getIntercepciones() + ", Asistencias: " + j.getAsistencias();
				              fila++;
			} else if ("Delantero".equalsIgnoreCase(j.getPosicion())) {
				DelanteroService del = new DelanteroService();
				Delantero d = del.getDelanteroById(j.getIdFutbolista());
				FutbolistaService f = new FutbolistaService();
				fut= f.getFutbolistaById(j.getIdFutbolista());
				        	  datos[fila][0] = "Delantero";
				              datos[fila][1] = fut.getNombre();
				              datos[fila][2] = fut.getIdEquipo();
				              datos[fila][3] = "Goles: " + j.getGoles() + ", Tiros Puerta: " + d.getTirosPuerta();
				              fila++;
			}








		@SuppressWarnings({ "serial", "unused" })
		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		@SuppressWarnings("serial")
		DefaultTableModel model1 = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		JTable tablaJ = new JTable(model1);
		tablaJ.setOpaque(false);
		tablaJ.setBackground(new Color(255, 255, 255, 170));
		tablaJ.setForeground(Color.BLACK);
		tablaJ.setShowGrid(true);
		tablaJ.setGridColor(new Color(0, 0, 0, 100));
		tablaJ.setFillsViewportHeight(true);
		tablaJ.setFont(new Font("Segoe UI", Font.BOLD, 18));
		tablaJ.setRowHeight(32);

		int panelWidth = 1300;
		int panelHeight = 520;
		int scrollWidth = 1300;
		int scrollHeight = 500;
		int panelX = (labelFondo.getWidth() - panelWidth) / 2;
		int panelY = (labelFondo.getHeight() - panelHeight) / 2;

		JScrollPane scrollPane = new JScrollPane(tablaJ);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds((panelWidth - scrollWidth) / 2, 65, scrollWidth, scrollHeight);

		@SuppressWarnings("serial")
		JPanel panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,255,200));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelTabla.setOpaque(false);

		JLabel titulo = new JLabel("Equipo All Stars de la Liga", SwingConstants.CENTER);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		titulo.setBounds(0, 10, panelWidth, 45);
		panelTabla.add(titulo);
		panelTabla.add(scrollPane);

		HoverPressButton btnExportarAllStars = createHoverPressButton("Exportar PDF",
				new Color(70, 130, 180, 180),
				new Color(30, 144, 255, 200),
				new Color(0, 60, 120, 220)
				);
		btnExportarAllStars.setFont(new Font("Segoe UI", Font.BOLD, 20));
		// Ponlo centrado y debajo de la tabla
		int botonWidth = 180;
		int botonHeight = 44;
		int botonX = 900;
		int botonY = 450;// debajo del scroll
		btnExportarAllStars.setBounds(botonX, botonY, botonWidth, botonHeight);
		btnExportarAllStars.setToolTipText("Exportar equipo All Stars a PDF");
		btnExportarAllStars.addActionListener(evt -> {
			Document document = new Document();
			try {
				String filename = "AllStars.pdf";
				PdfWriter.getInstance(document, new java.io.FileOutputStream(filename));
				document.open();

				Paragraph title = new Paragraph("Equipo All Stars de la Liga",
						FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
				title.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(title);
				document.add(new Paragraph(" "));

				PdfPTable pdfTable = new PdfPTable(tablaJ.getColumnCount());
				pdfTable.setWidthPercentage(100);
				for (int i = 0; i < tablaJ.getColumnCount(); i++) {
					PdfPCell cell = new PdfPCell(new Phrase(tablaJ.getColumnName(i)));
					cell.setBackgroundColor(new com.itextpdf.text.BaseColor(200, 200, 255));
					pdfTable.addCell(cell);
				}
				for (int row = 0; row < tablaJ.getRowCount(); row++) {
					for (int col = 0; col < tablaJ.getColumnCount(); col++) {
						Object value = tablaJ.getValueAt(row, col);
						pdfTable.addCell(value != null ? value.toString() : "");
					}
				}
				document.add(pdfTable);
				document.close();
				JOptionPane.showMessageDialog(tablaJ, "Equipo All Stars exportado a PDF exitosamente\nArchivo: " + filename, "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
			} catch (com.itextpdf.text.DocumentException | java.io.IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(tablaJ, "Error al exportar PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				if (document.isOpen()) document.close();
			}
		});
		panelTabla.add(btnExportarAllStars);
		// Asegura que el botón esté al frente
		panelTabla.setComponentZOrder(btnExportarAllStars, 0);

		HoverPressButton btnAtrasAllStars = createHoverPressButton("Atrás",
				new Color(70, 130, 180, 180),
				new Color(30, 144, 255, 200),
				new Color(0, 60, 120, 220)
				);
		btnAtrasAllStars.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnAtrasAllStars.setBounds(37, 25, 100, 60);
		btnAtrasAllStars.setToolTipText("Volver");
		btnAtrasAllStars.addActionListener(evt -> {
			labelFondo.remove(panelTabla);
			labelFondo.remove(btnAtrasAllStars);
			panelBotones.setVisible(true);
			setAllButtonsVisible(true);
			labelFondo.repaint();
			labelFondo.revalidate();
			panelComboTemporada.setVisible(true);

		});

		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		labelFondo.add(btnAtrasAllStars);
		labelFondo.setComponentZOrder(btnAtrasAllStars, 0);
		labelFondo.putClientProperty("panelTablaAllStars", panelTabla);
		labelFondo.repaint();
		labelFondo.revalidate();
	}
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
		btn.setForeground(Color.WHITE);
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
		btnEquipos.setVisible(visible);
		panelBotones.setVisible(visible);
		btnJugadores.setVisible(visible);
		btnPartidos.setVisible(visible);
		btnTablaPosiciones.setVisible(visible);
		btnEntrenadores.setVisible(visible);
		btnEstadios.setVisible(visible);
		btnXClose.setVisible(visible);
	}

	private void mostrarTablaPosiciones(JLabel labelFondo, int width, int height, int idTemporada) {
		// Elimina panel anterior si existe
		if (labelFondo.getClientProperty("panelTablaPos") != null) {
			JPanel oldPanel = (JPanel) labelFondo.getClientProperty("panelTablaPos");
			labelFondo.remove(oldPanel);
			labelFondo.putClientProperty("panelTablaPos", null);
		}

		// Obtener datos de servicios
		ObtenerServices service = new ObtenerServices();
		java.util.List<clases.TablaPosiciones> posiciones;
		try {
			posiciones = service.obtenerTablaPosiciones(idTemporada);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al obtener la tabla de posiciones.");
			ex.printStackTrace();
			return;
		}

		String[] columnas = {"Posicion", "   Equipo   ", "Pts", "PJ", "G", "E", "P", "GF", "GC", "DG"};
		Object[][] datos = new Object[posiciones.size()][columnas.length];
		for (int i = 0; i < posiciones.size(); i++) {
			clases.TablaPosiciones e = posiciones.get(i);
			datos[i][0] = i + 1;
			datos[i][1] = e.getEquipo();
			datos[i][2] = e.getPuntos();
			datos[i][3] = e.getPartidosJugados();
			datos[i][4] = e.getGanados();
			datos[i][5] = e.getEmpatados();
			datos[i][6] = e.getPerdidos();
			datos[i][7] = e.getGolesFavor();
			datos[i][8] = e.getGolesContra();
			datos[i][9] = e.getDiferenciaGoles();
		}

		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		JTable tablaJ = new JTable(model);
		tablaJ.setOpaque(false);
		tablaJ.setBackground(new Color(255, 255, 255, 170));
		tablaJ.setForeground(Color.BLACK);
		tablaJ.setShowGrid(true);
		tablaJ.setGridColor(new Color(0, 0, 0, 100));
		tablaJ.setFillsViewportHeight(true);
		tablaJ.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tablaJ.setRowHeight(28);


		HoverPressButton btnexportar = createHoverPressButton("Exportar PDF",
				new Color(70, 130, 180, 180),   
				new Color(30, 144, 255, 200),   
				new Color(0, 60, 120, 220)      
				);
		btnexportar.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnexportar.setBounds(700, 550, 180, 44);
		btnexportar.setToolTipText("EXPORTAR");
		btnexportar.addActionListener(evt -> {
			Document document = new Document();
			try {
				String filename = "Tabla_Posiciones.pdf";
				PdfWriter.getInstance(document, new FileOutputStream(filename));
				document.open();

				// Título
				Paragraph title = new Paragraph("Tabla de Posiciones", 
						FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
				title.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(title);
				document.add(new Paragraph(" ")); // Espacio

				// Crear la tabla PDF con el mismo número de columnas
				PdfPTable pdfTable = new PdfPTable(tablaJ.getColumnCount());
				pdfTable.setWidthPercentage(100);

				// Añadir encabezados
				for (int i = 0; i < tablaJ.getColumnCount(); i++) {
					PdfPCell cell = new PdfPCell(new Phrase(tablaJ.getColumnName(i)));
					cell.setBackgroundColor(new com.itextpdf.text.BaseColor(200, 200, 255));
					pdfTable.addCell(cell);
				}

				// Añadir filas
				for (int row = 0; row < tablaJ.getRowCount(); row++) {
					for (int col = 0; col < tablaJ.getColumnCount(); col++) {
						Object value = tablaJ.getValueAt(row, col);
						pdfTable.addCell(value != null ? value.toString() : "");
					}
				}

				document.add(pdfTable);
				document.close();

				JOptionPane.showMessageDialog(tablaJ, "Tabla exportada a PDF exitosamente\nArchivo: " + filename, "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
			} catch (DocumentException | IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(tablaJ, "Error al exportar PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				if (document.isOpen()) document.close();
			}
		});


		HoverPressButton btnEstadoEquipo = createHoverPressButton("Estado equipo",
				new Color(70, 180, 70, 180),
				new Color(30, 144, 60, 200),
				new Color(0, 128, 0, 220)
				);
		btnEstadoEquipo.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnEstadoEquipo.setBounds(100, 550, 180, 44);
		btnEstadoEquipo.setToolTipText("Ver estado detallado del equipo seleccionado");
		btnEstadoEquipo.addActionListener(evt -> {
			int row = tablaJ.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(tablaJ, "Seleccione un equipo de la tabla.");
				return;
			}


			String equipoSel = (String) tablaJ.getValueAt(row, 1);

			ObtenerServices serv = new ObtenerServices();
			java.util.List<Partido> partidos = serv.obtenerPartidos2(String.valueOf(idTemporada));
			java.util.List<InformacionPartido> infoPartidos = serv.Informacion(new ArrayList<>(partidos));
			int ganadosTotal = 0, empatadosTotal = 0, perdidosTotal = 0;
			int ganadosLocal = 0, empatadosLocal = 0, perdidosLocal = 0;
			int ganadosVisit = 0, empatadosVisit = 0, perdidosVisit = 0;
			HashMap<String, InformacionPartido> infoPorId = new HashMap<>();
			for (InformacionPartido ip : infoPartidos) infoPorId.put(ip.getIdPartido(), ip);

			for (Partido p : partidos) {
				InformacionPartido ip = infoPorId.get(p.getIdPartido());
				if (ip == null) continue;
				boolean esLocal = ip.getEquipoLocal().equals(equipoSel);
				boolean esVisit = ip.getEquipoVisitante().equals(equipoSel);
				int gl = ip.getGolesLocal(), gv = ip.getGolesVisitante();
				if (esLocal) {
					if (gl > gv) { ganadosTotal++; ganadosLocal++; }
					else if (gl < gv) { perdidosTotal++; perdidosLocal++; }
					else { empatadosTotal++; empatadosLocal++; }
				} else if (esVisit) {
					if (gv > gl) { ganadosTotal++; ganadosVisit++; }
					else if (gv < gl) { perdidosTotal++; perdidosVisit++; }
					else { empatadosTotal++; empatadosVisit++; }
				}
			}
			String msg = "Equipo: " + equipoSel +
					"\n\nTotal:\n  Ganados: " + ganadosTotal +
					"\n  Empatados: " + empatadosTotal +
					"\n  Perdidos: " + perdidosTotal +
					"\n\nComo Local:\n  Ganados: " + ganadosLocal +
					"\n  Empatados: " + empatadosLocal +
					"\n  Perdidos: " + perdidosLocal +
					"\n\nComo Visitante:\n  Ganados: " + ganadosVisit +
					"\n  Empatados: " + empatadosVisit +
					"\n  Perdidos: " + perdidosVisit;

			

			Object[] options = {"Cerrar", "Exportar a PDF"};
			int opcion = JOptionPane.showOptionDialog(
			    tablaJ,
			    msg,
			    "Estado de " + equipoSel,
			    JOptionPane.DEFAULT_OPTION,
			    JOptionPane.INFORMATION_MESSAGE,
			    null,
			    options,
			    options[0]
			);

			if (opcion == 1) { // Exportar a PDF
			    // Aquí va tu código para exportar el mensaje a PDF
			    try {
			        String filename = "Estado_" + equipoSel.replaceAll("\\s+", "_") + ".pdf";
			        Document document = new Document();
			        PdfWriter.getInstance(document, new FileOutputStream(filename));
			        document.open();
			        Paragraph title = new Paragraph("Estado del Equipo " + equipoSel, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
			        title.setAlignment(Paragraph.ALIGN_CENTER);
			        document.add(title);
			        document.add(new Paragraph(" "));
			        document.add(new Paragraph(msg, FontFactory.getFont(FontFactory.HELVETICA, 14)));
			        document.close();
			        JOptionPane.showMessageDialog(tablaJ, "Estado exportado a PDF exitosamente\nArchivo: " + filename, "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
			    } catch (DocumentException | IOException ex) {
			        ex.printStackTrace();
			        JOptionPane.showMessageDialog(tablaJ, "Error al exportar PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			    }
			}
		});


		int panelWidth = 1100;
		int panelHeight = 600;
		int scrollWidth = 1000;
		int scrollHeight = 500;
		int panelX = (labelFondo.getWidth() - panelWidth) / 2;
		int panelY = (labelFondo.getHeight() - panelHeight) / 2;

		JScrollPane scrollPane = new JScrollPane(tablaJ);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds((panelWidth - scrollWidth) / 2, 45, scrollWidth, scrollHeight);

		@SuppressWarnings("serial")
		JPanel panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,255,140));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelTabla.setOpaque(false);

		panelTabla.add(scrollPane);
		panelTabla.add(btnEstadoEquipo);
		panelTabla.add(btnexportar);
		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		labelFondo.putClientProperty("panelTablaPos", panelTabla);

		// Botón Atrás
		btnAtras = createHoverPressButton("Atras",
				new Color(70, 130, 180, 180),
				new Color(30, 144, 255, 200),
				new Color(0, 60, 120, 220)
				);
		btnAtras.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnAtras.setBounds(15, 15, 100, 60);
		btnAtras.setToolTipText("Volver");
		btnAtras.addActionListener(evt -> {
			if(panelTabla != null) {
				labelFondo.remove(panelTabla);
				panelBotones.setVisible(true);
				btnAtras.setVisible(true);
				labelFondo.repaint();
				labelFondo.revalidate();
				btnAtras.setVisible(false);
				setAllButtonsVisible(true);
				panelComboTemporada.setVisible(true);
			}
		});
		labelFondo.add(btnAtras);
		labelFondo.repaint();
		labelFondo.revalidate();
	}

	private void mostrarTablaEntrenadores(JLabel labelFondo, int width, int height) {
		if (labelFondo.getClientProperty("panelTablaEntrenadores") != null) {
			JPanel oldPanel = (JPanel) labelFondo.getClientProperty("panelTablaEntrenadores");
			labelFondo.remove(oldPanel);
			labelFondo.putClientProperty("panelTablaEntrenadores", null);
		}
		services.EntrenadorService entrenadorService = new services.EntrenadorService();
		services.FutbolistaService futbolistaService = new services.FutbolistaService();
		services.ObtenerServices obtenerServices = new services.ObtenerServices();
		ArrayList<clases.Entrenador> entrenadores = (ArrayList<clases.Entrenador>) entrenadorService.getAllEntrenadores();
		ArrayList<clases.Futbolista> futbolistas = (ArrayList<clases.Futbolista>) futbolistaService.getAllFutbolistas();
		ArrayList<clases.Equipo> equipos = (ArrayList<clases.Equipo>) obtenerServices.obtenerEquipos();
		java.util.Map<String, Integer> equipoCampeonatos = new java.util.HashMap<>();
		java.util.Map<String, String> equipoNombre = new java.util.HashMap<>();
		for (clases.Equipo eq : equipos) {
			equipoCampeonatos.put(eq.getNombreEquipo(), eq.getCampeonatosGanados());
			equipoNombre.put(eq.getNombreEquipo(), eq.getNombreEquipo());
		}
		class EntrenadorFila {
			String nombre;
			int numero;
			int aniosExp;
			String nombreEquipo;
			int campeonatosGanados;
		}
		ArrayList<EntrenadorFila> filas = new ArrayList<>();
		for (clases.Entrenador ent : entrenadores) {
			for (clases.Futbolista fut : futbolistas) {
				if (fut.getIdFutbolista().equals(ent.getIdFutbolista()) && fut.getTipo().equals("ENTRENADOR")) {
					EntrenadorFila fila = new EntrenadorFila();
					fila.nombre = fut.getNombre();
					fila.numero = fut.getNumero();
					fila.aniosExp = ent.getAniosExp();
					fila.nombreEquipo = equipoNombre.getOrDefault(fut.getIdEquipo(), fut.getIdEquipo());
					fila.campeonatosGanados = equipoCampeonatos.getOrDefault(fut.getIdEquipo(), 0);
					filas.add(fila);
					break;
				}
			}
		}
		filas.sort((a, b) -> Integer.compare(b.campeonatosGanados, a.campeonatosGanados));
		String[] columnas = {"Nombre", "Número", "Años de Experiencia", "Equipo", "Campeonatos Ganados"};
		Object[][] datos = new Object[filas.size()][columnas.length];
		for (int i = 0; i < filas.size(); i++) {
			datos[i][0] = filas.get(i).nombre;
			datos[i][1] = filas.get(i).numero;
			datos[i][2] = filas.get(i).aniosExp;
			datos[i][3] = filas.get(i).nombreEquipo;
			datos[i][4] = filas.get(i).campeonatosGanados;
		}
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		JTable tablaJ = new JTable(model);
		tablaJ.setOpaque(false);
		tablaJ.setBackground(new Color(255, 255, 255, 170));
		tablaJ.setForeground(Color.BLACK);
		tablaJ.setShowGrid(true);
		tablaJ.setGridColor(new Color(0, 0, 0, 100));
		tablaJ.setFillsViewportHeight(true);
		tablaJ.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tablaJ.setRowHeight(28);

		int panelWidth = 900;
		int panelHeight = 400;
		int scrollWidth = 800;
		int scrollHeight = 280;
		int panelX = (width - panelWidth) / 2;
		int panelY = (height - panelHeight) / 2;


		JScrollPane scrollPane = new JScrollPane(tablaJ);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds((panelWidth - scrollWidth) / 2, 45, scrollWidth, scrollHeight);



		@SuppressWarnings("serial")
		JPanel panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,255,140));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelTabla.setOpaque(false);
		panelTabla.add(scrollPane);

		HoverPressButton btnExportarEntrenadores = createHoverPressButton("Exportar PDF",
				new Color(70, 130, 180, 180),
				new Color(30, 144, 255, 200),
				new Color(0, 60, 120, 220)
				);
		btnExportarEntrenadores.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnExportarEntrenadores.setBounds(650, 340, 180, 44); // Ajusta posición si es necesario
		btnExportarEntrenadores.setToolTipText("Exportar tabla de entrenadores a PDF");
		btnExportarEntrenadores.addActionListener(evt -> {
			Document document = new Document();
			try {
				String filename = "Entrenadores.pdf";
				PdfWriter.getInstance(document, new FileOutputStream(filename));
				document.open();

				Paragraph title = new Paragraph("Entrenadores de la Liga",
						FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
				title.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(title);
				document.add(new Paragraph(" "));

				PdfPTable pdfTable = new PdfPTable(tablaJ.getColumnCount());
				pdfTable.setWidthPercentage(100);
				for (int i = 0; i < tablaJ.getColumnCount(); i++) {
					PdfPCell cell = new PdfPCell(new Phrase(tablaJ.getColumnName(i)));
					cell.setBackgroundColor(new com.itextpdf.text.BaseColor(200, 200, 255));
					pdfTable.addCell(cell);
				}
				for (int row = 0; row < tablaJ.getRowCount(); row++) {
					for (int col = 0; col < tablaJ.getColumnCount(); col++) {
						Object value = tablaJ.getValueAt(row, col);
						pdfTable.addCell(value != null ? value.toString() : "");
					}
				}
				document.add(pdfTable);
				document.close();
				JOptionPane.showMessageDialog(tablaJ, "Tabla de entrenadores exportada a PDF exitosamente\nArchivo: " + filename, "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
			} catch (DocumentException | IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(tablaJ, "Error al exportar PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				if (document.isOpen()) document.close();
			}
		});
		panelTabla.add(btnExportarEntrenadores);


		HoverPressButton btnAtrasEntrenadores = createHoverPressButton("Atrás",
				new Color(70, 130, 180, 180),
				new Color(30, 144, 255, 200),
				new Color(0, 60, 120, 220)
				);
		btnAtrasEntrenadores.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnAtrasEntrenadores.setBounds(15, 15, 100, 60);
		btnAtrasEntrenadores.setToolTipText("Volver");
		btnAtrasEntrenadores.addActionListener(evt -> {
			labelFondo.remove(panelTabla);
			labelFondo.remove(btnAtrasEntrenadores);
			panelBotones.setVisible(true);
			setAllButtonsVisible(true);
			labelFondo.repaint();
			labelFondo.revalidate();
			panelComboTemporada.setVisible(true);
		});
		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		labelFondo.add(btnAtrasEntrenadores);
		labelFondo.setComponentZOrder(btnAtrasEntrenadores, 0);
		labelFondo.putClientProperty("panelTablaEntrenadores", panelTabla);
		labelFondo.repaint();
		labelFondo.revalidate();


	}

	private void mostrarTablaEstadios(JLabel labelFondo, int width, int height) {
		if (labelFondo.getClientProperty("panelTablaEstadios") != null) {
			JPanel oldPanel = (JPanel) labelFondo.getClientProperty("panelTablaEstadios");
			labelFondo.remove(oldPanel);
			labelFondo.putClientProperty("panelTablaEstadios", null);
		}
		ObtenerServices service = new ObtenerServices();
		ArrayList<Estadio> estadios = (ArrayList<Estadio>) service.obtenerEstadios();
		class EstadioRow {
			String nombre;
			int capacidadMax;
			int audienciaTotal;
			double porcentaje;
			EstadioRow(String nombre, int capacidadMax, int audienciaTotal) {
				this.nombre = nombre;
				this.capacidadMax = capacidadMax;
				this.audienciaTotal = audienciaTotal;
				if (capacidadMax > 0) {
					this.porcentaje = (audienciaTotal * 100.0) / capacidadMax;
				} else {
					this.porcentaje = 0.0;
				}
			}
		}
		ArrayList<EstadioRow> filas = new ArrayList<>();
		for (Estadio e : estadios) {
			filas.add(new EstadioRow(e.getNombre(), e.getCapacidadMax(), e.getAudienciaTotal()));
		}
		filas.sort((a, b) -> Double.compare(b.porcentaje, a.porcentaje));
		String[] columnas = {"Nombre", "Capacidad Máx", "Audiencia Total", "% Audiencia"};
		Object[][] datos = new Object[filas.size()][columnas.length];
		for (int i = 0; i < filas.size(); i++) {
			datos[i][0] = filas.get(i).nombre;
			datos[i][1] = filas.get(i).capacidadMax;
			datos[i][2] = filas.get(i).audienciaTotal;
			datos[i][3] = String.format("%.2f%%", filas.get(i).porcentaje);
		}
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		JTable tablaJ = new JTable(model);
		tablaJ.setOpaque(false);
		tablaJ.setBackground(new Color(255, 255, 255, 170));
		tablaJ.setForeground(Color.BLACK);
		tablaJ.setShowGrid(true);
		tablaJ.setGridColor(new Color(0, 0, 0, 100));
		tablaJ.setFillsViewportHeight(true);
		tablaJ.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tablaJ.setRowHeight(28);

		int panelWidth = 900;
		int panelHeight = 400;
		int scrollWidth = 800;
		int scrollHeight = 280;
		int panelX = (width - panelWidth) / 2;
		int panelY = (height - panelHeight) / 2;

		JScrollPane scrollPane = new JScrollPane(tablaJ);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds((panelWidth - scrollWidth) / 2, 45, scrollWidth, scrollHeight);

		@SuppressWarnings("serial")
		JPanel panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,255,140));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelTabla.setOpaque(false);
		panelTabla.add(scrollPane);

		HoverPressButton btnExportarEstadios = createHoverPressButton("Exportar PDF",
				new Color(70, 130, 180, 180),
				new Color(30, 144, 255, 200),
				new Color(0, 60, 120, 220)
				);
		btnExportarEstadios.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnExportarEstadios.setBounds(650, 340, 180, 44); // Ajusta posición vertical si lo deseas
		btnExportarEstadios.setToolTipText("Exportar tabla de estadios a PDF");
		btnExportarEstadios.addActionListener(evt -> {
			Document document = new Document();
			try {
				String filename = "Estadios.pdf";
				PdfWriter.getInstance(document, new FileOutputStream(filename));
				document.open();

				Paragraph title = new Paragraph("Estadios de la Liga",
						FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
				title.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(title);
				document.add(new Paragraph(" "));

				PdfPTable pdfTable = new PdfPTable(tablaJ.getColumnCount());
				pdfTable.setWidthPercentage(100);
				for (int i = 0; i < tablaJ.getColumnCount(); i++) {
					PdfPCell cell = new PdfPCell(new Phrase(tablaJ.getColumnName(i)));
					cell.setBackgroundColor(new com.itextpdf.text.BaseColor(200, 200, 255));
					pdfTable.addCell(cell);
				}
				for (int row = 0; row < tablaJ.getRowCount(); row++) {
					for (int col = 0; col < tablaJ.getColumnCount(); col++) {
						Object value = tablaJ.getValueAt(row, col);
						pdfTable.addCell(value != null ? value.toString() : "");
					}
				}
				document.add(pdfTable);
				document.close();
				JOptionPane.showMessageDialog(tablaJ, "Tabla de estadios exportada a PDF exitosamente\nArchivo: " + filename, "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
			} catch (DocumentException | IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(tablaJ, "Error al exportar PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				if (document.isOpen()) document.close();
			}
		});
		panelTabla.add(btnExportarEstadios);

		HoverPressButton btnAtrasEstadios = createHoverPressButton("Atrás",
				new Color(70, 130, 180, 180),
				new Color(30, 144, 255, 200),
				new Color(0, 60, 120, 220)
				);
		btnAtrasEstadios.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnAtrasEstadios.setBounds(15, 15, 100, 60);
		btnAtrasEstadios.setToolTipText("Volver");
		btnAtrasEstadios.addActionListener(evt -> {
			labelFondo.remove(panelTabla);
			labelFondo.remove(btnAtrasEstadios);
			panelBotones.setVisible(true);
			setAllButtonsVisible(true);
			labelFondo.repaint();
			labelFondo.revalidate();
			panelComboTemporada.setVisible(true);


		});

		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		labelFondo.add(btnAtrasEstadios);
		labelFondo.setComponentZOrder(btnAtrasEstadios, 0);
		labelFondo.putClientProperty("panelTablaEstadios", panelTabla);
		labelFondo.repaint();
		labelFondo.revalidate();
	}

	
	@SuppressWarnings("serial")
	private void mostrarTablaEquipos(JLabel labelFondo) {
	    if (panelTabla != null) {
	        labelFondo.remove(panelTabla);
	        panelTabla = null;
	    }

	    ObtenerServices service = new ObtenerServices();
	    ArrayList<Equipo> equipos = (ArrayList<Equipo>) service.obtenerEquipos();
	    String[] columnas = {"Nombre", "Campeonatos Participados", "Campeonatos Ganados", "Mascota", "Provincia"};
	    Object[][] datos = new Object[equipos.size()][columnas.length];

	    for (int i = 0; i < equipos.size(); i++) {
	        Equipo eq = equipos.get(i);
	        datos[i][0] = eq.getNombreEquipo();
	        datos[i][1] = eq.getCampeonatosParticipados();
	        datos[i][2] = eq.getCampeonatosGanados();
	        datos[i][3] = eq.getMascota();
	        datos[i][4] = eq.getProvincia();
	    }

	    DefaultTableModel model = new DefaultTableModel(datos, columnas) {
	        @Override
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
	    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    int panelWidth = 900, panelHeight = 400;
	    int scrollWidth = 850, scrollHeight = 280;
	    Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	    int panelX = (screenBounds.width - panelWidth) / 2;
	    int panelY = (screenBounds.height - panelHeight) / 2;

	    JScrollPane scrollPaneEquipos = new JScrollPane(tabla);
	    scrollPaneEquipos.setOpaque(false);
	    scrollPaneEquipos.getViewport().setOpaque(false);
	    scrollPaneEquipos.setBounds((panelWidth-scrollWidth)/2, 40, scrollWidth, scrollHeight);

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

	    // Botón Cerrar (mantenemos el original)
	    HoverPressButton btnCerrarTablaEquipos = createHoverPressButton("Cerrar",
	            new Color(255, 70, 70, 180),
	            new Color(220, 20, 60, 200),
	            new Color(180, 0, 0, 220)
	            );
	    btnCerrarTablaEquipos.setFont(new Font("Segoe UI", Font.BOLD, 18));
	    btnCerrarTablaEquipos.setBounds(panelWidth-160, panelHeight-50, 120, 30);
	    btnCerrarTablaEquipos.addActionListener(evt -> {
	        labelFondo.remove(panelTabla);
	        setAllButtonsVisible(true);
	        labelFondo.repaint();
	        labelFondo.revalidate();
	        panelComboTemporada.setVisible(true);
	    });

	    // Botón Crear Equipo (modificado)
	    HoverPressButton btnCrearEquipo = createHoverPressButton("Crear",
	            new Color(200, 230, 200, 180),  // Verde claro
	            new Color(180, 210, 180, 200),
	            new Color(160, 190, 160, 220)
	            );
	    btnCrearEquipo.setFont(new Font("Segoe UI", Font.BOLD, 16));
	    btnCrearEquipo.setForeground(Color.BLACK);  // Texto negro
	    btnCrearEquipo.setBounds(40, panelHeight-50, 150, 28);  // Más pequeño
	    btnCrearEquipo.addActionListener(evt -> {
	        CrearEquipoFrame frame = new CrearEquipoFrame();
	        frame.setVisible(true);
	        mostrarTablaEquipos(labelFondo);
	    });

	    // Botón Actualizar Equipo (modificado)
	    HoverPressButton btnActualizarEquipo = createHoverPressButton("Editar",
	            new Color(200, 200, 230, 180),  // Azul claro
	            new Color(180, 180, 210, 200),
	            new Color(160, 160, 190, 220)
	            );
	    btnActualizarEquipo.setFont(new Font("Segoe UI", Font.BOLD, 16));
	    btnActualizarEquipo.setForeground(Color.BLACK);  // Texto negro
	    btnActualizarEquipo.setBounds(200, panelHeight-50, 150, 28);  // Más pequeño
	    btnActualizarEquipo.addActionListener(evt -> {
	        int selectedRow = tabla.getSelectedRow();
	        if (selectedRow >= 0) {
	            String idEquipo = (String) model.getValueAt(selectedRow, 0);
	            EditarEquipoFrame e1 = new EditarEquipoFrame(idEquipo);
	            e1.setVisible(true);
	            mostrarTablaEquipos(labelFondo);
	        } else {
	            JOptionPane.showMessageDialog(panelTabla, 
	                "Por favor seleccione un equipo para editar", 
	                "Advertencia", 
	                JOptionPane.WARNING_MESSAGE);
	        }
	    });

	    // Botón Eliminar Equipo (modificado)
	    HoverPressButton btnEliminarEquipo = createHoverPressButton("Eliminar",
	            new Color(230, 200, 200, 180),  // Rojo claro
	            new Color(210, 180, 180, 200),
	            new Color(190, 160, 160, 220)
	            );
	    btnEliminarEquipo.setFont(new Font("Segoe UI", Font.BOLD, 16));
	    btnEliminarEquipo.setForeground(Color.BLACK);  // Texto negro
	    btnEliminarEquipo.setBounds(360, panelHeight-50, 150, 28);  // Más pequeño
	    btnEliminarEquipo.addActionListener(evt -> {
	        int selectedRow = tabla.getSelectedRow();
	        if (selectedRow >= 0) {
	            int confirm = JOptionPane.showConfirmDialog(
	                panelTabla, 
	                "¿Está seguro que desea eliminar este equipo?", 
	                "Confirmar eliminación", 
	                JOptionPane.YES_NO_OPTION
	            );
	            
	            if (confirm == JOptionPane.YES_OPTION) {
	                String nombreEquipo = (String) model.getValueAt(selectedRow, 0);
	                EquipoService d = new EquipoService();
	                d.deleteEquipo(nombreEquipo);
	                model.removeRow(selectedRow);
	                labelFondo.repaint();
	                labelFondo.revalidate();
	            }
	        } else {
	            JOptionPane.showMessageDialog(
	                panelTabla, 
	                "Por favor seleccione un equipo para eliminar", 
	                "Advertencia", 
	                JOptionPane.WARNING_MESSAGE
	            );
	        }
	    });

	    panelTabla.add(scrollPaneEquipos);
	    panelTabla.add(btnCerrarTablaEquipos);
	    panelTabla.add(btnCrearEquipo);
	    panelTabla.add(btnEliminarEquipo);
	    panelTabla.add(btnActualizarEquipo);
	    
	    labelFondo.add(panelTabla);
	    labelFondo.setComponentZOrder(panelTabla, 0);
	    setAllButtonsVisible(false);
	    labelFondo.repaint();
	    labelFondo.revalidate();
	}
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> new Principal().setVisible(true));
	}
}

@SuppressWarnings("serial")
class HoverPressButton extends JButton {
	private boolean pressed = false;
	private boolean hovered = false;
	private Color colorNormal = new Color(255, 255, 255, 180);
	private Color colorHover = new Color(200, 200, 200, 200);
	private Color colorPressed = new Color(180, 180, 180, 220);

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
		this.colorHover = hover;
		this.colorPressed = pressed;
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