package jframeessss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import clases.Delantero;
import services.DelanteroService;
import services.ObtenerServices;

@SuppressWarnings("serial")
public class CrearDelanteroFrame extends JDialog {
    private JTextField tfIdFutbolista, tfNombre, tfNumero, tfEdad, tfIdEquipo, tfPartidos, tfGoles, tfAsistencias, tfPromedioGoles, tfDisparos;
    private HoverPressButton btnCrear, btnCerrarRojo;
    @SuppressWarnings("unused")
    private ObtenerServices service;

    public CrearDelanteroFrame(ObtenerServices service) {
        super((Frame) null, "Crear Delantero", true);

        this.service = service;

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); 

        setSize(480, 600);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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
        bgPanel.add(new JLabel("Disparos:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfDisparos = new JTextField(); tfDisparos.setFont(textFont); tfDisparos.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfDisparos, gbc); row++;

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

        btnCerrarRojo.addActionListener(e -> dispose());

        btnCrear.addActionListener((ActionEvent e) -> {
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
                tfDisparos.getText().trim().isEmpty()
            ) {
                JOptionPane.showMessageDialog(CrearDelanteroFrame.this, "¡Debe completar todos los campos!", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Delantero d = new Delantero();
                d.setIdFutbolista(tfIdFutbolista.getText());
                d.setTirosPuerta(Integer.parseInt(tfDisparos.getText()));
                String nombre = tfNombre.getText();
                int numero = Integer.parseInt(tfNumero.getText());
                int edad = Integer.parseInt(tfEdad.getText());
                String idEquipo = tfIdEquipo.getText();
                int partidosJugados = Integer.parseInt(tfPartidos.getText());
                int goles = Integer.parseInt(tfGoles.getText());
                int asistencias = Integer.parseInt(tfAsistencias.getText());
                double promedioGoles = Double.parseDouble(tfPromedioGoles.getText());
                DelanteroService o = new DelanteroService();
                o.createDelanteroCompleto(d, nombre, numero, edad, idEquipo, partidosJugados, goles, asistencias, promedioGoles);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CrearDelanteroFrame.this, "Verifique que los campos numéricos tengan valores válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
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