package jframeessss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clases.Portero;
import services.PorteroService;
import services.ObtenerServices;

@SuppressWarnings("serial")
public class CrearPorteroFrame extends JDialog {
    private JTextField tfIdFutbolista, tfNombre, tfNumero, tfEdad, tfIdEquipo, tfPartidos, tfGoles, tfAsistencias, tfPromedioGoles, tfAtajadas, tfGolesEncajados;
    private HoverPressButton btnCrear, btnCerrarRojo;
    @SuppressWarnings("unused")
    private ObtenerServices service;

    public CrearPorteroFrame(ObtenerServices service) {
        super((Frame) null, "Crear Portero", true);

        this.service = service;

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); // Fondo transparente

        setSize(480, 640);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Coloca la ventana a la izquierda de la pantalla (centrada verticalmente)
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = 40;
        int y = (screen.height - getHeight()) / 2;
        setLocation(x, y);

        JPanel bgPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 36, 36);
                g2.dispose();
            }
        };
        bgPanel.setOpaque(false);
        bgPanel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 18);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        bgPanel.add(new JLabel("ID Futbolista:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfIdFutbolista = new JTextField(); tfIdFutbolista.setFont(textFont); tfIdFutbolista.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfIdFutbolista, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Nombre:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfNombre = new JTextField(); tfNombre.setFont(textFont); tfNombre.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfNombre, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Número:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfNumero = new JTextField(); tfNumero.setFont(textFont); tfNumero.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfNumero, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Edad:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfEdad = new JTextField(); tfEdad.setFont(textFont); tfEdad.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfEdad, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("ID Equipo:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfIdEquipo = new JTextField(); tfIdEquipo.setFont(textFont); tfIdEquipo.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfIdEquipo, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Partidos Jugados:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfPartidos = new JTextField(); tfPartidos.setFont(textFont); tfPartidos.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfPartidos, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Goles:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfGoles = new JTextField(); tfGoles.setFont(textFont); tfGoles.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfGoles, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Asistencias:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfAsistencias = new JTextField(); tfAsistencias.setFont(textFont); tfAsistencias.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfAsistencias, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Promedio Goles:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfPromedioGoles = new JTextField(); tfPromedioGoles.setFont(textFont); tfPromedioGoles.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfPromedioGoles, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Atajadas:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfAtajadas = new JTextField(); tfAtajadas.setFont(textFont); tfAtajadas.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfAtajadas, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Goles Encajados:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfGolesEncajados = new JTextField(); tfGolesEncajados.setFont(textFont); tfGolesEncajados.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfGolesEncajados, gbc); row++;

        // Panel de botones alineados
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCrear = new HoverPressButton("Crear",
            new Color(0, 153, 76, 200),        // Verde translúcido normal
            new Color(0, 120, 60, 220),        // Verde más oscuro hover
            new Color(0, 80, 40, 240)          // Verde aún más oscuro pressed
        );
        btnCrear.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setPreferredSize(new Dimension(130, 44));
        btnCrear.setMaximumSize(new Dimension(130, 44));
        btnCrear.setMinimumSize(new Dimension(130, 44));

        btnCerrarRojo = new HoverPressButton("Cerrar",
            new Color(255, 70, 70, 180),
            new Color(220, 20, 60, 200),
            new Color(180, 0, 0, 220)
        );
        btnCerrarRojo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnCerrarRojo.setForeground(Color.WHITE);
        btnCerrarRojo.setPreferredSize(new Dimension(130, 44));
        btnCerrarRojo.setMaximumSize(new Dimension(130, 44));
        btnCerrarRojo.setMinimumSize(new Dimension(130, 44));

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

        // Acción cerrar
        btnCerrarRojo.addActionListener(e -> dispose());

        // Acción crear (validar que todos los campos estén llenos)
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (
                    tfIdFutbolista.getText().trim().isEmpty() ||
                    tfNombre.getText().trim().isEmpty() ||
                    tfNumero.getText().trim().isEmpty() ||
                    tfEdad.getText().trim().isEmpty() ||
                    tfIdEquipo.getText().trim().isEmpty() ||
                    tfPartidos.getText().trim().isEmpty() ||
                    tfGoles.getText().trim().isEmpty() ||
                    tfAsistencias.getText().trim().isEmpty() ||
                    tfPromedioGoles.getText().trim().isEmpty() ||
                    tfAtajadas.getText().trim().isEmpty() ||
                    tfGolesEncajados.getText().trim().isEmpty()
                ) {
                    JOptionPane.showMessageDialog(CrearPorteroFrame.this, "¡Debe completar todos los campos!", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    Portero p = new Portero();
                    p.setIdFutbolista(tfIdFutbolista.getText());
                    p.setAtajadas(Integer.parseInt(tfAtajadas.getText()));
                    p.setGolesEncajados(Integer.parseInt(tfGolesEncajados.getText()));
                    String nombre = tfNombre.getText();
                    int numero = Integer.parseInt(tfNumero.getText());
                    int edad = Integer.parseInt(tfEdad.getText());
                    String idEquipo = tfIdEquipo.getText();
                    int partidosJugados = Integer.parseInt(tfPartidos.getText());
                    int goles = Integer.parseInt(tfGoles.getText());
                    int asistencias = Integer.parseInt(tfAsistencias.getText());
                    double promedioGoles = Double.parseDouble(tfPromedioGoles.getText());
                    PorteroService o = new PorteroService();
                    o.createPorteroCompleto(p, nombre, numero, edad, idEquipo, partidosJugados, goles, asistencias, promedioGoles);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CrearPorteroFrame.this, "Verifique que los campos numéricos tengan valores válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
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