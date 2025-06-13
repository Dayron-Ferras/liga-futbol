package jframeessss;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

import clases.Equipo;
import clases.EstadioPartido;
import clases.EstadisticasPartido;
import clases.Futbolista;
import clases.InformacionPartido;
import clases.Partido;
import services.EstadioPartidoService;
import services.FutbolistaService;
import services.ObtenerServices;
import services.PartidoService;

public class PartidosDialogs extends JDialog {

	private static final long serialVersionUID = 1L;
	private static HoverPressButton btnAtras;
	private static HoverPressButton btnMostrarPartidos;
	private static HoverPressButton btnMostrarPorEquipo;
	private static HoverPressButton btnPartidoEntreEquipos;
	private static HoverPressButton btnPartidosPorFecha;

	private JComboBox<String> comboEquipos;
	private JPanel panelComboEquipos = null;
	private JPanel panelComboDosEquipos = null;
	private JPanel panelComboFecha = null;
	private JPanel panelComboEstadio = null;
	private String temp = null;
	private static JPanel panelBotones;
	private JPanel panelTabla = null;
	private static HoverPressButton btnCerrarTabla = null;
	public PartidosDialogs(java.awt.Frame parent,String temp) {
		super(parent, true);
		setUndecorated(true);
		this.temp=temp;
		Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int width = screenBounds.width;
		int height = screenBounds.height;

		setBounds(screenBounds);

		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/jframeessss/recursos/5024318950237909646.jpg"));
		Image originalImage = originalIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel labelFondo = new JLabel(scaledIcon);
		labelFondo.setBounds(0, 0, width, height);
		setContentPane(labelFondo);
		labelFondo.setLayout(null);

		panelBotones = new JPanel(new GridLayout(4, 1, 0, 18));
		panelBotones.setOpaque(false);
		int panelBotonesWidth = 340;
		int panelBotonesHeight = 350;
		int panelBotonesX = (width - panelBotonesWidth) / 2;
		int panelBotonesY = (height - panelBotonesHeight) / 2;
		panelBotones.setBounds(panelBotonesX, panelBotonesY, panelBotonesWidth, panelBotonesHeight);

		btnMostrarPartidos = createHoverPressButton("Partidos");
		btnMostrarPorEquipo = createHoverPressButton("Partidos por Equipo");
		btnPartidoEntreEquipos = createHoverPressButton("Partido entre dos Equipos");
		btnPartidosPorFecha = createHoverPressButton("Partidos por Fecha");

		panelBotones.add(btnMostrarPartidos);
		panelBotones.add(btnMostrarPorEquipo);
		panelBotones.add(btnPartidoEntreEquipos);
		panelBotones.add(btnPartidosPorFecha);

		btnMostrarPartidos.addActionListener(evt -> {
			setAllButtonsVisible(false);
			mostrarTablaPartidos(labelFondo, width, height);
			btnAtras.setVisible(false);
		});
		btnMostrarPorEquipo.addActionListener(evt -> {
			mostrarComboEquipos(labelFondo, width, height);
			setAllButtonsVisible(false);
			btnAtras.setVisible(true);
		});
		btnPartidoEntreEquipos.addActionListener(evt -> {
			mostrarComboDosEquipos(labelFondo, width, height);
			setAllButtonsVisible(false);
			btnAtras.setVisible(true);
		});
		btnPartidosPorFecha.addActionListener(evt -> {
			mostrarComboFecha(labelFondo, width, height);
			setAllButtonsVisible(false);
			btnAtras.setVisible(true);
		});

		labelFondo.add(panelBotones);

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
				panelTabla = null;
				panelBotones.setVisible(true);
				setAllButtonsVisible(true); 
				btnAtras.setVisible(true);
				getContentPane().repaint();
				getContentPane().revalidate();
			} else if(panelComboEquipos != null) {
				labelFondo.remove(panelComboEquipos);
				panelComboEquipos = null;
				panelBotones.setVisible(true);
				setAllButtonsVisible(true);
				btnAtras.setVisible(true);
				labelFondo.repaint();
				labelFondo.revalidate();
			} else if(panelComboDosEquipos != null) {
				labelFondo.remove(panelComboDosEquipos);
				panelComboDosEquipos = null;
				panelBotones.setVisible(true);
				setAllButtonsVisible(true);
				btnAtras.setVisible(true);
				labelFondo.repaint();
				labelFondo.revalidate();
			} else if(panelComboEstadio != null) {
				labelFondo.remove(panelComboEstadio);
				panelComboEstadio = null;
				mostrarComboFecha(labelFondo, getWidth(), getHeight());
			} else if(panelComboFecha != null) {
				labelFondo.remove(panelComboFecha);
				panelComboFecha = null;
				panelBotones.setVisible(true);
				setAllButtonsVisible(true);
				btnAtras.setVisible(true);
				labelFondo.repaint();
				labelFondo.revalidate();
			} else {
				dispose();
			}
		});
		labelFondo.add(btnAtras);
	}

	@SuppressWarnings("serial")
	private void mostrarComboFecha(JLabel labelFondo, int width, int height) {
		
		if (panelComboFecha != null) {
			labelFondo.remove(panelComboFecha);
			panelComboFecha = null;
		}
		if(panelComboEstadio != null) {
			labelFondo.remove(panelComboEstadio);
			panelComboEstadio = null;
		}
		panelBotones.setVisible(false);

		int panelWidth = 400;
		int panelHeight = 120;
		int panelX = (width - panelWidth) / 2;
		int panelY = (height - panelHeight) / 2;

		panelComboFecha = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,255,210));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelComboFecha.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelComboFecha.setOpaque(false);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblFecha.setBounds(30, 25, 90, 40);
		panelComboFecha.add(lblFecha);

		JComboBox<String> comboFechas = new JComboBox<>(obtenerFechasUnicas());
		comboFechas.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		comboFechas.setBounds(120, 25, 200, 40);
		panelComboFecha.add(comboFechas);

		HoverPressButton btnSiguiente = createHoverPressButton("Siguiente",
				new Color(76, 175, 80, 220),
				new Color(56, 142, 60, 230),
				new Color(27, 94, 32, 255)
				);
		btnSiguiente.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnSiguiente.setBounds((panelWidth-140)/2, 75, 140, 35);
		btnSiguiente.addActionListener(e -> {
			String fecha = (String) comboFechas.getSelectedItem();
			mostrarComboEstadio(labelFondo, width, height, fecha);
			labelFondo.remove(panelComboFecha);
			panelComboFecha = null;
			btnAtras.setVisible(true);

		});
		panelComboFecha.add(btnSiguiente);

		labelFondo.add(panelComboFecha);
		labelFondo.setComponentZOrder(panelComboFecha, 0);
		labelFondo.repaint();
		labelFondo.revalidate();
	}

	@SuppressWarnings("serial")
	private void mostrarComboEstadio(JLabel labelFondo, int width, int height, String fecha) {
	
		if(panelComboEstadio != null) {
			labelFondo.remove(panelComboEstadio);
			panelComboEstadio = null;
		}
		int panelWidth = 500;
		int panelHeight = 120;
		int panelX = (width - panelWidth) / 2;
		int panelY = (height - panelHeight) / 2;

		panelComboEstadio = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,255,210));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelComboEstadio.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelComboEstadio.setOpaque(false);

		JLabel lblEstadio = new JLabel("Estadio:");
		lblEstadio.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblEstadio.setBounds(30, 25, 110, 40);
		panelComboEstadio.add(lblEstadio);

		JComboBox<String> comboEstadios = new JComboBox<>(obtenerEstadiosPorFecha(fecha));
		comboEstadios.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		comboEstadios.setBounds(140, 25, 220, 40);
		panelComboEstadio.add(comboEstadios);

		HoverPressButton btnAceptar = createHoverPressButton("Aceptar",
				new Color(76, 175, 80, 220),
				new Color(56, 142, 60, 230),
				new Color(27, 94, 32, 255)
				);
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnAceptar.setBounds(panelWidth - 160, 75, 120, 35);
		btnAceptar.addActionListener(e -> {
			String estadio = (String) comboEstadios.getSelectedItem();
			mostrarTablaPartidosPorFecha( labelFondo, getWidth(), getHeight(), fecha, estadio);
			labelFondo.remove(panelComboEstadio);
			btnAtras.setVisible(false);
			panelComboEstadio = null;
			setAllButtonsVisible(false);
		});
		panelComboEstadio.add(btnAceptar);

		labelFondo.add(panelComboEstadio);
		labelFondo.setComponentZOrder(panelComboEstadio, 0);
		labelFondo.repaint();
		labelFondo.revalidate();
	}

	private String[] obtenerFechasUnicas() {
		ObtenerServices o = new ObtenerServices();
		ArrayList<Partido> partidos = (ArrayList<Partido>) o.obtenerPartidos2(temp) ;
		ArrayList<String> fechas = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Partido p : partidos) {
			String fecha = sdf.format(p.getFecha());
			if (!fechas.contains(fecha)) {
				fechas.add(fecha);
			}
		}
		return fechas.toArray(new String[0]);
	}

	private String[] obtenerEstadiosPorFecha(String fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PartidoService partidoService = new PartidoService();
		EstadioPartidoService estadioService = new EstadioPartidoService();

		ArrayList<Partido> partidos = (ArrayList<Partido>) partidoService.getAllPartidos();
		ArrayList<EstadioPartido> estadioPartidos = (ArrayList<EstadioPartido>) estadioService.getAllEstadioPartidos();

		ArrayList<String> ids = new ArrayList<>();
		for (Partido p : partidos) {
			String fechaPartido = sdf.format(p.getFecha());
			if (fechaPartido.equals(fecha)) {
				ids.add(p.getIdPartido());
			}
		}
		ArrayList<String> estadios = new ArrayList<>();
		estadios.add("Todos");
		for (EstadioPartido ep : estadioPartidos) {
			if (ids.contains(ep.getIdPartido())) {
				if (!estadios.contains(ep.getNombreEstadio())) {
					estadios.add(ep.getNombreEstadio());
				}
			}
		}
		return estadios.toArray(new String[0]);
	}

	@SuppressWarnings("serial")
	private void mostrarTablaPartidosPorFecha(JLabel labelFondo, int width, int height, String fecha, String estadio) {
		panelBotones.setVisible(false);
		if(panelTabla != null) {
			labelFondo.remove(panelTabla);
			panelTabla = null;
		}


		EstadioPartidoService estadioService = new EstadioPartidoService();
		ObtenerServices service = new ObtenerServices();
		ArrayList<Partido> p = (ArrayList<Partido>) service.obtenerPartidos2(temp);
		ArrayList<InformacionPartido> y = (ArrayList<InformacionPartido>) service.Informacion(p);
		ArrayList<EstadioPartido> estadioPartidos = (ArrayList<EstadioPartido>) estadioService.getAllEstadioPartidos();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> partidoAEstadio = new HashMap<>();
		for (EstadioPartido ep : estadioPartidos) {
			partidoAEstadio.put(ep.getIdPartido(), ep.getNombreEstadio());
		}
		Map<String, InformacionPartido> partidoAInfo = new HashMap<>();
		for (InformacionPartido ip : y) {
			partidoAInfo.put(ip.getIdPartido(), ip);
		}

		ArrayList<Object[]> datosList = new ArrayList<>();
		for (Partido partido : p) {
			String fechaPartido = sdf.format(partido.getFecha());
			if (!fechaPartido.equals(fecha)) continue;
			String estadioPartido = partidoAEstadio.get(partido.getIdPartido());
			if (estadioPartido == null) continue;
			if (!estadio.equals("Todos") && !estadioPartido.equals(estadio)) continue;
			InformacionPartido ip = partidoAInfo.get(partido.getIdPartido());
			if (ip == null) continue;
			Object[] fila = new Object[5];
			fila[0] = ip.getEquipoLocal();
			fila[1] = ip.getGolesLocal() + " - " + ip.getGolesVisitante();
			fila[2] = ip.getEquipoVisitante();
			fila[3] = sdf.format(partido.getFecha());
			fila[4] = estadioPartido;
			datosList.add(fila);
		}

		String[] columnas = { "Equipo Local", "Resultado", "Equipo Visitante", "Fecha", "Estadio" };
		if (datosList.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay partidos para ese filtro.");
			setAllButtonsVisible(true);
			return;
		}
		Object[][] datos = datosList.toArray(new Object[0][columnas.length]);
		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable tabla = new JTable(model);
		tabla.setOpaque(false);
		tabla.setBackground(new Color(255, 255, 255, 170));
		tabla.setForeground(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setGridColor(new Color(0, 0, 0, 100));
		tabla.setFillsViewportHeight(true);
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tabla.setRowHeight(28);

		int panelWidth = 960;
		int panelHeight = 380;
		int scrollWidth = 900;
		int scrollHeight = 260;
		int panelX = (width - panelWidth) / 2;
		int panelY = (height - panelHeight) / 2;

		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds((panelWidth - scrollWidth) / 2, 45, scrollWidth, scrollHeight);

		panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,255,140));
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
			btnAtras.setVisible(true);
			panelBotones.setVisible(true);
			panelTabla = null;
			getContentPane().repaint();
			getContentPane().revalidate();
		});

		// Botón para ver estadísticas de jugadores del partido seleccionado
		HoverPressButton btnVerEstadisticas = createHoverPressButton("Ver Estadísticas Jugadores",
		    new Color(70, 130, 180, 180),
		    new Color(30, 144, 255, 200),
		    new Color(0, 60, 120, 220)
		);
		btnVerEstadisticas.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnVerEstadisticas.setBounds(270, panelHeight-50, 250, 30);
		btnVerEstadisticas.addActionListener(evt -> {
		    int selectedRow = tabla.getSelectedRow();
		    if (selectedRow == -1) {
		        JOptionPane.showMessageDialog(tabla, "Seleccione un partido primero.");
		        return;
		    }
		    // Busca el idPartido correspondiente
		    String idPartido = p.get(selectedRow).getIdPartido();
		    mostrarEstadisticasJugadoresPorPartido(labelFondo, width, height, idPartido);
		});
		panelTabla.add(btnVerEstadisticas);
		HoverPressButton btnExportarPartidos = createHoverPressButton("Exportar PDF",
				new Color(70, 130, 180, 180),
				new Color(30, 144, 255, 200),
				new Color(0, 60, 120, 220)
				);
		btnExportarPartidos.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnExportarPartidos.setBounds((panelWidth - 200) / 2, 320, 200, 44);
		btnExportarPartidos.setToolTipText("Exportar partidos entre equipos a PDF");
		btnExportarPartidos.addActionListener(evt -> {
			Document document = new Document();
			try {
				String filename = "Partidos_" + fecha.replaceAll("\\s+", "_") +  estadio.replaceAll("\\s+","_") + "_ pdf";
				PdfWriter.getInstance(document, new FileOutputStream(filename));
				document.open();

				Paragraph title = new Paragraph("Partidos por fecha : ",
						FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
				title.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(title);
				document.add(new Paragraph(" "));

				PdfPTable pdfTable = new PdfPTable(tabla.getColumnCount());
				pdfTable.setWidthPercentage(100);

				// Encabezados
				for (int c = 0; c < tabla.getColumnCount(); c++) {
					PdfPCell cell = new PdfPCell(new Phrase(tabla.getColumnName(c)));
					cell.setBackgroundColor(new com.itextpdf.text.BaseColor(200, 200, 255));
					pdfTable.addCell(cell);
				}
				// Filas
				for (int row = 0; row < tabla.getRowCount(); row++) {
					for (int col = 0; col < tabla.getColumnCount(); col++) {
						Object value = tabla.getValueAt(row, col);
						pdfTable.addCell(value != null ? value.toString() : "");
					}
				}
				document.add(pdfTable);
				document.close();
				JOptionPane.showMessageDialog(tabla, "Partidos exportados a PDF exitosamente\nArchivo: " + filename, "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);

			} catch (DocumentException | IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(tabla, "Error al exportar PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				if (document.isOpen()) document.close();
			}
		});

		panelTabla.add(scrollPane);
		panelTabla.add(btnExportarPartidos);
		panelTabla.add(btnCerrarTabla);
		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		labelFondo.repaint();
		labelFondo.revalidate();
	}

	// --- RESTO DE MÉTODOS IGUALES QUE ANTES ---

	private static void setAllButtonsVisible(boolean visible) {
		panelBotones.setVisible(visible);
		btnMostrarPartidos.setVisible(visible);
		btnMostrarPorEquipo.setVisible(visible);
		btnPartidoEntreEquipos.setVisible(visible);
		btnPartidosPorFecha.setVisible(visible);
	}

	private String[] Nombreequipos() {
		ObtenerServices o = new ObtenerServices();
		ArrayList<Equipo>equipo = (ArrayList<Equipo>) o.obtenerEquipos();
		String [] equiposs = new String[equipo.size()];
		for (int i=0;i<equipo.size();i++) {
			equiposs[i]=equipo.get(i).getNombreEquipo();
		}
		return equiposs;
	}

	@SuppressWarnings("serial")
	private void mostrarComboEquipos(JLabel labelFondo, int width, int height) {

		if(panelComboEquipos != null) {
			labelFondo.remove(panelComboEquipos);
			panelComboEquipos = null;
		}
		int panelWidth = 500;
		int panelHeight = 120;
		int panelX = (width - panelWidth) / 2;
		int panelY = (height - panelHeight) / 2;

		panelComboEquipos = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,255,210));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelComboEquipos.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelComboEquipos.setOpaque(false);

		comboEquipos = new JComboBox<>(Nombreequipos());
		comboEquipos.setFont(new Font("Segoe UI", Font.PLAIN, 28));
		comboEquipos.setBounds(30, 35, 320, 50);
		panelComboEquipos.add(comboEquipos);

		HoverPressButton btnAceptar = createHoverPressButton("Aceptar",
				new Color(76, 175, 80, 220),
				new Color(56, 142, 60, 230),
				new Color(27, 94, 32, 255)
				);
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnAceptar.setBounds(panelWidth - 140, 32, 125, 56);
		btnAceptar.addActionListener(e -> {
			mostrarTablaPartidosPorEquipo(labelFondo, width, height, (String) comboEquipos.getSelectedItem());
			setAllButtonsVisible(false);
			btnAtras.setVisible(false);
		});

		panelComboEquipos.add(btnAceptar);
		labelFondo.add(panelComboEquipos);
		labelFondo.setComponentZOrder(panelComboEquipos, 0);
		labelFondo.repaint();
		labelFondo.revalidate();
	}

	@SuppressWarnings("serial")
	private void mostrarComboDosEquipos(JLabel labelFondo, int width, int height) {

		if(panelComboDosEquipos != null) {
			labelFondo.remove(panelComboDosEquipos);
			panelComboDosEquipos = null;
		}
		int panelWidth = 700;
		int panelHeight = 180;
		int panelX = (width - panelWidth) / 2;
		int panelY = (height - panelHeight) / 2;

		panelComboDosEquipos = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,255,210));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panelComboDosEquipos.setBounds(panelX, panelY, panelWidth, panelHeight);
		panelComboDosEquipos.setOpaque(false);

		String[] equipos = Nombreequipos();

		JComboBox<String> comboEquipo1 = new JComboBox<>(equipos);
		comboEquipo1.setFont(new Font("Segoe UI", Font.PLAIN, 28));
		comboEquipo1.setBounds(30, 40, 250, 50);
		panelComboDosEquipos.add(comboEquipo1);

		JComboBox<String> comboEquipo2 = new JComboBox<>(equipos);
		comboEquipo2.setFont(new Font("Segoe UI", Font.PLAIN, 28));
		comboEquipo2.setBounds(panelWidth-280, 40, 250, 50);
		panelComboDosEquipos.add(comboEquipo2);

		HoverPressButton btnAceptar = createHoverPressButton("Aceptar",
				new Color(76, 175, 80, 220),
				new Color(56, 142, 60, 230),
				new Color(27, 94, 32, 255)
				);
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnAceptar.setBounds((panelWidth-125)/2, 110, 125, 56);
		btnAceptar.addActionListener(e -> {
			String equipo1 = (String) comboEquipo1.getSelectedItem();
			String equipo2 = (String) comboEquipo2.getSelectedItem();
			mostrarTablaPartidosEntreEquipos(labelFondo, width, height, equipo1, equipo2);
			setAllButtonsVisible(false);
			btnAtras.setVisible(false);
		});
		panelComboDosEquipos.add(btnAceptar);

		labelFondo.add(panelComboDosEquipos);
		labelFondo.setComponentZOrder(panelComboDosEquipos, 0);
		labelFondo.repaint();
		labelFondo.revalidate();
	}

	@SuppressWarnings("serial")
	private void mostrarTablaPartidos(JLabel labelFondo, int width, int height) {
		panelBotones.setVisible(false);

		if(panelTabla != null) {
			labelFondo.remove(panelTabla);
			panelTabla = null;
		}

		ObtenerServices service = new ObtenerServices();
		ArrayList<Partido> p = (ArrayList<Partido>) service.obtenerPartidos2(temp);
		ArrayList<InformacionPartido> y = (ArrayList<InformacionPartido>) service.Informacion(p);
		String[] columnas = { "Equipo Local","Resultado", "Equipo Visitante", "Fecha"};
		Object[][] datos = new Object[p.size()][columnas.length];
		for( int i=0 ; i<p.size();i++) {
			datos[i][0]= y.get(i).getEquipoLocal();
			datos[i][1]="        "+y.get(i).getGolesLocal()+"         -         "+y.get(i).getGolesVisitante();
			datos[i][2]= y.get(i).getEquipoVisitante();
			datos[i][3] = p.get(i).getFecha();
		}

		DefaultTableModel model = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable tabla = new JTable(model);
		tabla.setOpaque(false);
		tabla.setBackground(new Color(255, 255, 255, 170));
		tabla.setForeground(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setGridColor(new Color(0, 0, 0, 100));
		tabla.setFillsViewportHeight(true);
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tabla.setRowHeight(28);

		int panelWidth = 860;
		int panelHeight = 350;
		int scrollWidth = 800;
		int scrollHeight = 250;
		int panelX = (width - panelWidth) / 2;
		int panelY = (height - panelHeight) / 2;

		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds((panelWidth - scrollWidth) / 2, 45, scrollWidth, scrollHeight);

		panelTabla = new JPanel(null) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,255,140));
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
			panelTabla = null;
			panelBotones.setVisible(true);
			setAllButtonsVisible(true); 
			btnAtras.setVisible(true);
			getContentPane().repaint();
			getContentPane().revalidate();
		});
		// Botón para ver estadísticas de jugadores del partido seleccionado
		HoverPressButton btnVerEstadisticas = createHoverPressButton("Ver Estadísticas Jugadores",
		    new Color(70, 130, 180, 180),
		    new Color(30, 144, 255, 200),
		    new Color(0, 60, 120, 220)
		);
		btnVerEstadisticas.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnVerEstadisticas.setBounds(270, panelHeight-50, 250, 30);
		btnVerEstadisticas.addActionListener(evt -> {
		    int selectedRow = tabla.getSelectedRow();
		    if (selectedRow == -1) {
		        JOptionPane.showMessageDialog(tabla, "Seleccione un partido primero.");
		        return;
		    }
		    // Busca el idPartido correspondiente
		    String idPartido = p.get(selectedRow).getIdPartido();
		    mostrarEstadisticasJugadoresPorPartido(labelFondo, width, height, idPartido);
		});
		panelTabla.add(btnVerEstadisticas);
		HoverPressButton btnCrearPartido = createHoverPressButton("Crear Partido");
		btnCrearPartido.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCrearPartido.setBounds(40, panelHeight-50, 220, 30);
		btnCrearPartido.addActionListener(evt -> {
			ObtenerServices serviceNew = new ObtenerServices();
			CrearPartidoFrame frame = new CrearPartidoFrame(serviceNew, temp);
			frame.setVisible(true);
			mostrarTablaPartidos(labelFondo, width, height);
		});



		panelTabla.add(scrollPane);
		panelTabla.add(btnCrearPartido);
		panelTabla.add(btnCerrarTabla);

		labelFondo.add(panelTabla);
		labelFondo.setComponentZOrder(panelTabla, 0);
		setAllButtonsVisible(false);
		labelFondo.repaint();
		labelFondo.revalidate();
	}

	@SuppressWarnings({ "serial" })
	private void mostrarTablaPartidosPorEquipo(JLabel labelFondo, int width, int height,String nombre) {
		panelBotones.setVisible(false);
		if(panelTabla != null) {
			labelFondo.remove(panelTabla);
			panelTabla = null;
		}

		ObtenerServices service = new ObtenerServices();
		ArrayList<Partido> p = (ArrayList<Partido>) service.obtenerPartidos2(temp);
		ArrayList<InformacionPartido> y = (ArrayList<InformacionPartido>) service.Informacion(p);
		String[] columnas = { "Equipo Local","Resultado", "Equipo Visitante", "Fecha"};
		int k = 0;
		int l=0;
		while(l<p.size()) {
			if(y.get(l).getEquipoLocal().equals(nombre)||y.get(l).getEquipoVisitante().equals(nombre)) {
				k++;
				l++;
			}
			else {
				l++;
			}
		}
		if(k>0) {
			Object[][] datos = new Object[k][columnas.length];
			int i = 0;
			int j= 0;

			while(i<p.size()) {
				if(y.get(i).getEquipoLocal().equals(nombre)||y.get(i).getEquipoVisitante().equals(nombre)) {
					datos[j][0]= y.get(i).getEquipoLocal();
					datos[j][1]="        "+y.get(i).getGolesLocal()+"         -         "+y.get(i).getGolesVisitante();
					datos[j][2]= y.get(i).getEquipoVisitante();
					datos[j][3] = p.get(i).getFecha();
					i++;
					j++;
				}
				else {
					i++;
				}
			}

			DefaultTableModel model = new DefaultTableModel(datos, columnas) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			JTable tabla = new JTable(model);
			tabla.setOpaque(false);
			tabla.setBackground(new Color(255, 255, 255, 170));
			tabla.setForeground(Color.BLACK);
			tabla.setShowGrid(true);
			tabla.setGridColor(new Color(0, 0, 0, 100));
			tabla.setFillsViewportHeight(true);
			tabla.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			tabla.setRowHeight(28);

			int panelWidth = 860;
			int panelHeight = 350;
			int scrollWidth = 800;
			int scrollHeight = 250;
			int panelX = (width - panelWidth) / 2;
			int panelY = (height - panelHeight) / 2;

			JScrollPane scrollPane = new JScrollPane(tabla);
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.setBounds((panelWidth - scrollWidth) / 2, 45, scrollWidth, scrollHeight);

			panelTabla = new JPanel(null) {
				@Override
				protected void paintComponent(java.awt.Graphics g) {
					super.paintComponent(g);
					g.setColor(new Color(255,255,255,140));
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
				labelFondo.remove(panelComboEquipos);
				panelComboEquipos = null;
				panelBotones.setVisible(true);
				setAllButtonsVisible(true);
				btnAtras.setVisible(true);
				labelFondo.repaint();
				labelFondo.revalidate();
				labelFondo.remove(panelTabla);
				panelTabla = null;
				getContentPane().repaint();
				getContentPane().revalidate();

			});

			// Botón para ver estadísticas de jugadores del partido seleccionado
			HoverPressButton btnVerEstadisticas = createHoverPressButton("Ver Estadísticas Jugadores",
			    new Color(70, 130, 180, 180),
			    new Color(30, 144, 255, 200),
			    new Color(0, 60, 120, 220)
			);
			btnVerEstadisticas.setFont(new Font("Segoe UI", Font.BOLD, 18));
			btnVerEstadisticas.setBounds(270, panelHeight-50, 250, 30);
			btnVerEstadisticas.addActionListener(evt -> {
			    int selectedRow = tabla.getSelectedRow();
			    if (selectedRow == -1) {
			        JOptionPane.showMessageDialog(tabla, "Seleccione un partido primero.");
			        return;
			    }
			    // Busca el idPartido correspondiente
			    String idPartido = p.get(selectedRow).getIdPartido();
			    mostrarEstadisticasJugadoresPorPartido(labelFondo, width, height, idPartido);
			});
			panelTabla.add(btnVerEstadisticas);
			panelTabla.add(scrollPane);
			panelTabla.add(btnCerrarTabla);
			labelFondo.add(panelTabla);
			labelFondo.setComponentZOrder(panelTabla, 0);
			labelFondo.repaint();
			labelFondo.revalidate();

		}
		else {
			JOptionPane.showMessageDialog(null, "No tiene Partidos");
		}
	}

	@SuppressWarnings({ "serial" })
	private void mostrarTablaPartidosEntreEquipos(JLabel labelFondo, int width, int height, String equipo1, String equipo2) {
		panelBotones.setVisible(false);
		if (panelTabla != null) {
			labelFondo.remove(panelTabla);
			panelTabla = null;
		}
		if (equipo1.equals(equipo2)) {
			JOptionPane.showMessageDialog(null, "Selecciona equipos diferentes");
		} else {
			ObtenerServices service = new ObtenerServices();
			ArrayList<Partido> p = (ArrayList<Partido>) service.obtenerPartidos2(temp);
			ArrayList<InformacionPartido> y = (ArrayList<InformacionPartido>) service.Informacion(p);
			String[] columnas = { "Equipo Local","Resultado", "Equipo Visitante", "Fecha"};
			int k = 0;
			int l = 0;
			while (l < p.size()) {
				boolean match = 
						(y.get(l).getEquipoLocal().equals(equipo1) && y.get(l).getEquipoVisitante().equals(equipo2)) ||
						(y.get(l).getEquipoLocal().equals(equipo2) && y.get(l).getEquipoVisitante().equals(equipo1));
				if (match) {
					k++;
				}
				l++;
			}
			if (k > 0) {
				Object[][] datos = new Object[k][columnas.length];
				int i = 0;
				int j = 0;
				while (i < p.size()) {
					boolean match =
							(y.get(i).getEquipoLocal().equals(equipo1) && y.get(i).getEquipoVisitante().equals(equipo2)) ||
							(y.get(i).getEquipoLocal().equals(equipo2) && y.get(i).getEquipoVisitante().equals(equipo1));
					if (match) {
						datos[j][0] = y.get(i).getEquipoLocal();
						datos[j][1] = "        " + y.get(i).getGolesLocal() + "         -         " + y.get(i).getGolesVisitante();
						datos[j][2] = y.get(i).getEquipoVisitante();
						datos[j][3] = p.get(i).getFecha();
						j++;
					}
					i++;
				}

				DefaultTableModel model = new DefaultTableModel(datos, columnas) {
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				JTable tabla = new JTable(model);
				tabla.setOpaque(false);
				tabla.setBackground(new Color(255, 255, 255, 170));
				tabla.setForeground(Color.BLACK);
				tabla.setShowGrid(true);
				tabla.setGridColor(new Color(0, 0, 0, 100));
				tabla.setFillsViewportHeight(true);
				tabla.setFont(new Font("Segoe UI", Font.PLAIN, 18));
				tabla.setRowHeight(28);

				int panelWidth = 860;
				int panelHeight = 430; // Aumentado para el botón
				int scrollWidth = 800;
				int scrollHeight = 250;
				int panelX = (width - panelWidth) / 2;
				int panelY = (height - panelHeight) / 2;

				JScrollPane scrollPane = new JScrollPane(tabla);
				scrollPane.setOpaque(false);
				scrollPane.getViewport().setOpaque(false);
				scrollPane.setBounds((panelWidth - scrollWidth) / 2, 45, scrollWidth, scrollHeight);

				// CREA EL PANEL PRIMERO
				panelTabla = new JPanel(null) {
					@Override
					protected void paintComponent(java.awt.Graphics g) {
						super.paintComponent(g);
						g.setColor(new Color(255,255,255,140));
						g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
					}
				};
				panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
				panelTabla.setOpaque(false);

				// Botón para ver estadísticas de jugadores del partido seleccionado
				HoverPressButton btnVerEstadisticas = createHoverPressButton("Ver Estadísticas Jugadores",
				    new Color(70, 130, 180, 180),
				    new Color(30, 144, 255, 200),
				    new Color(0, 60, 120, 220)
				);
				btnVerEstadisticas.setFont(new Font("Segoe UI", Font.BOLD, 18));
				btnVerEstadisticas.setBounds(270, panelHeight-50, 250, 30);
				btnVerEstadisticas.addActionListener(evt -> {
				    int selectedRow = tabla.getSelectedRow();
				    if (selectedRow == -1) {
				        JOptionPane.showMessageDialog(tabla, "Seleccione un partido primero.");
				        return;
				    }
				    // Busca el idPartido correspondiente
				    String idPartido = p.get(selectedRow).getIdPartido();
				    mostrarEstadisticasJugadoresPorPartido(labelFondo, width, height, idPartido);
				});
				panelTabla.add(btnVerEstadisticas);

				// Botón Cerrar
				btnCerrarTabla = createHoverPressButton("Cerrar",
						new Color(255, 70, 70, 180),
						new Color(220, 20, 60, 200),
						new Color(180, 0, 0, 220)
						);
				btnCerrarTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));
				btnCerrarTabla.setBounds(panelWidth-160, panelHeight-50, 120, 30);
				btnCerrarTabla.addActionListener(evt -> {
					labelFondo.remove(panelComboDosEquipos);
					panelComboDosEquipos = null;
					panelBotones.setVisible(true);
					setAllButtonsVisible(true);
					btnAtras.setVisible(true);
					labelFondo.repaint();
					labelFondo.revalidate();
					labelFondo.remove(panelTabla);
					panelTabla = null;
					getContentPane().repaint();
					getContentPane().revalidate();
				});


				// LUEGO CREA Y AGREGA EL BOTÓN
				HoverPressButton btnExportarPartidos = createHoverPressButton("Exportar PDF",
						new Color(70, 130, 180, 180),
						new Color(30, 144, 255, 200),
						new Color(0, 60, 120, 220)
						);
				btnExportarPartidos.setFont(new Font("Segoe UI", Font.BOLD, 20));
				btnExportarPartidos.setBounds((panelWidth - 200) / 2, 320, 200, 44);
				btnExportarPartidos.setToolTipText("Exportar partidos entre equipos a PDF");
				btnExportarPartidos.addActionListener(evt -> {
					Document document = new Document();
					try {
						String filename = "Partidos_" + equipo1.replaceAll("\\s+", "_") + "_vs_" + equipo2.replaceAll("\\s+", "_") + ".pdf";
						PdfWriter.getInstance(document, new FileOutputStream(filename));
						document.open();

						Paragraph title = new Paragraph("Partidos entre " + equipo1 + " y " + equipo2,
								FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
						title.setAlignment(Paragraph.ALIGN_CENTER);
						document.add(title);
						document.add(new Paragraph(" "));

						PdfPTable pdfTable = new PdfPTable(tabla.getColumnCount());
						pdfTable.setWidthPercentage(100);

						// Encabezados
						for (int c = 0; c < tabla.getColumnCount(); c++) {
							PdfPCell cell = new PdfPCell(new Phrase(tabla.getColumnName(c)));
							cell.setBackgroundColor(new com.itextpdf.text.BaseColor(200, 200, 255));
							pdfTable.addCell(cell);
						}
						// Filas
						for (int row = 0; row < tabla.getRowCount(); row++) {
							for (int col = 0; col < tabla.getColumnCount(); col++) {
								Object value = tabla.getValueAt(row, col);
								pdfTable.addCell(value != null ? value.toString() : "");
							}
						}
						document.add(pdfTable);
						document.close();
						JOptionPane.showMessageDialog(tabla, "Partidos exportados a PDF exitosamente\nArchivo: " + filename, "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);

					} catch (DocumentException | IOException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(tabla, "Error al exportar PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} finally {
						if (document.isOpen()) document.close();
					}
				});

				panelTabla.add(scrollPane);
				panelTabla.add(btnExportarPartidos);
				panelTabla.add(btnCerrarTabla);
				labelFondo.add(panelTabla);
				labelFondo.setComponentZOrder(panelTabla, 0);
				labelFondo.repaint();
				labelFondo.revalidate();
			} else {
				JOptionPane.showMessageDialog(null, "No hay partidos entre los equipos seleccionados.");
			}
		}
	}
	
	// ... (resto de la clase igual)

	@SuppressWarnings({ "serial" })
	private void mostrarEstadisticasJugadoresPorPartido(JLabel labelFondo, int width, int height, String idPartido) {
	    if(panelTabla != null) {
	        labelFondo.remove(panelTabla);
	        panelTabla = null;
	    }

	    // Llama a tu servicio para traer la lista de estadísticas de jugadores para el partido
	    ObtenerServices service = new ObtenerServices();
	    ArrayList<EstadisticasPartido> estadisticas = (ArrayList<EstadisticasPartido>) service.obtenerEstadisticasPartido();
	    ArrayList<EstadisticasPartido> estadisticasqueson = new ArrayList<EstadisticasPartido>();
	    for(EstadisticasPartido e : estadisticas) {
	    	if(e.getIdPartido().equals(idPartido)) {
	    		estadisticasqueson.add(e);
	    	}
	    		
	    }
	    
	    String[] columnas = {"Nombre", "Equipo", "Goles", "Asistencias"};
	    Object[][] datos = new Object[estadisticasqueson.size()][columnas.length];
	    for (int i = 0; i < estadisticasqueson.size(); i++) {
	        EstadisticasPartido est = estadisticasqueson.get(i);
	        FutbolistaService f = new FutbolistaService();
	        Futbolista f1 = f.getFutbolistaById(est.getIdFutbolista());
	        datos[i][0] = f1.getNombre();
	        datos[i][1] = f1.getIdEquipo();
	        datos[i][2] = est.getGoles();
	        datos[i][3] = est.getAsistencias();
	        
	    }
	    

	    DefaultTableModel model = new DefaultTableModel(datos, columnas) {
	        public boolean isCellEditable(int row, int column) { return false; }
	    };
	    JTable tablaEstadisticas = new JTable(model);
	    tablaEstadisticas.setOpaque(false);
	    tablaEstadisticas.setBackground(new Color(255, 255, 255, 170));
	    tablaEstadisticas.setForeground(Color.BLACK);
	    tablaEstadisticas.setShowGrid(true);
	    tablaEstadisticas.setGridColor(new Color(0, 0, 0, 100));
	    tablaEstadisticas.setFillsViewportHeight(true);
	    tablaEstadisticas.setFont(new Font("Segoe UI", Font.PLAIN, 18));
	    tablaEstadisticas.setRowHeight(28);

	    int panelWidth = 1000;
	    int panelHeight = 480;
	    int scrollWidth = 950;
	    int scrollHeight = 280;
	    int panelX = (width - panelWidth) / 2;
	    int panelY = (height - panelHeight) / 2;

	    JScrollPane scrollPane = new JScrollPane(tablaEstadisticas);
	    scrollPane.setOpaque(false);
	    scrollPane.getViewport().setOpaque(false);
	    scrollPane.setBounds((panelWidth - scrollWidth) / 2, 45, scrollWidth, scrollHeight);

	    panelTabla = new JPanel(null) {
	        private static final long serialVersionUID = 1L;
	        @Override
	        protected void paintComponent(java.awt.Graphics g) {
	            super.paintComponent(g);
	            g.setColor(new Color(255,255,255,140));
	            g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
	        }
	    };
	    panelTabla.setBounds(panelX, panelY, panelWidth, panelHeight);
	    panelTabla.setOpaque(false);

	    // Botón Cerrar
	    HoverPressButton btnCerrarEstadisticas = createHoverPressButton("Cerrar",
	        new Color(255, 70, 70, 180),
	        new Color(220, 20, 60, 200),
	        new Color(180, 0, 0, 220)
	    );
	    btnCerrarEstadisticas.setFont(new Font("Segoe UI", Font.BOLD, 18));
	    btnCerrarEstadisticas.setBounds(panelWidth-160, panelHeight-50, 120, 30);
	    btnCerrarEstadisticas.addActionListener(evt -> {
	        labelFondo.remove(panelTabla);
	        panelTabla = null;
	        setAllButtonsVisible(true);
	        labelFondo.repaint();
	        labelFondo.revalidate();
	        btnAtras.setVisible(true);
	        panelBotones.setVisible(true);
	        getContentPane().repaint();
	        getContentPane().revalidate();
	    });

	    // Botón Crear
	    HoverPressButton btnCrear = createHoverPressButton("Crear",
	        new Color(70, 180, 70, 180),
	        new Color(30, 144, 60, 200),
	        new Color(0, 128, 0, 220)
	    );
	    btnCrear.setFont(new Font("Segoe UI", Font.BOLD, 18));
	    btnCrear.setBounds(40, panelHeight-50, 120, 30);
	    btnCrear.setToolTipText("Crear estadística para un jugador");
	    btnCrear.addActionListener(evt -> {
	       CrearEstadisticaPartido c = new CrearEstadisticaPartido(idPartido);
	       c.setVisible(true);
	       
      
	    });

	    // Botón Actualizar
	    HoverPressButton btnActualizar = createHoverPressButton("Actualizar",
	        new Color(180, 180, 70, 180),
	        new Color(240, 230, 60, 200),
	        new Color(180, 180, 0, 220)
	    );
	    btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 18));
	    btnActualizar.setBounds(180, panelHeight-50, 120, 30);
	    btnActualizar.setToolTipText("Actualizar estadística seleccionada");
	    btnActualizar.addActionListener(evt -> {
	        int selectedRow = tablaEstadisticas.getSelectedRow();
	        if (selectedRow == -1) {
	            JOptionPane.showMessageDialog(tablaEstadisticas, "Seleccione una estadística para actualizar.");
	            return;
	        }
	        // Lógica para actualizar estadística (puedes mostrar un diálogo para editar datos)
	        JOptionPane.showMessageDialog(tablaEstadisticas, "Funcionalidad de actualizar estadística (implementar)");
	    });

	    // Botón Eliminar
	    HoverPressButton btnEliminar = createHoverPressButton("Eliminar",
	        new Color(220, 70, 70, 180),
	        new Color(255, 0, 0, 200),
	        new Color(120, 0, 0, 220)
	    );
	    btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 18));
	    btnEliminar.setBounds(320, panelHeight-50, 120, 30);
	    btnEliminar.setToolTipText("Eliminar estadística seleccionada");
	    btnEliminar.addActionListener(evt -> {
	        int selectedRow = tablaEstadisticas.getSelectedRow();
	        if (selectedRow == -1) {
	            JOptionPane.showMessageDialog(tablaEstadisticas, "Seleccione una estadística para eliminar.");
	            return;
	        }
	        // Lógica para eliminar estadística (confirmar y eliminar)
	        JOptionPane.showMessageDialog(tablaEstadisticas, "Funcionalidad de eliminar estadística (implementar)");
	    });
	    
	    

	    panelTabla.add(scrollPane);
	    panelTabla.add(btnCrear);
	    panelTabla.add(btnActualizar);
	    panelTabla.add(btnEliminar);
	    panelTabla.add(btnCerrarEstadisticas);
	   
	    labelFondo.add(panelTabla);
	    labelFondo.setComponentZOrder(panelTabla, 0);
	    labelFondo.repaint();
	    labelFondo.revalidate();
	   
	  if(estadisticasqueson.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay Estadisticas", "No hay Estadisticas", JOptionPane.WARNING_MESSAGE);

	  }
	}

	// ... (resto de la clase igual)
	
	// Este método es solo ejemplo, debes adaptarlo a tu estructura y tus servicios
	@SuppressWarnings("serial")
	static class HoverPressButton extends javax.swing.JButton {
		private boolean pressed = false;
		private boolean hovered = false;
		private Color colorNormal = new Color(255, 255, 255, 180);
		private Color colorHover = new Color(200, 200, 200, 200);
		private Color colorPressed = new Color(180, 180, 180, 220);

		public HoverPressButton(String text) {
			super(text);
			setModel(new javax.swing.DefaultButtonModel());
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
		protected void paintComponent(java.awt.Graphics g) {
			java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
			g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
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
}