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
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import services.PartidoService;
import services.ObtenerServices;
import clases.Partido;

@SuppressWarnings({ "serial", "unused" })
public class CrearPartidoFrame extends JDialog {
    private JTextField tfIdPartido, tfFecha, tfEquipoLocal, tfEquipoVisitante, tfGolesLocal, tfGolesVisitante, tfEstadio;
    private HoverPressButton btnCrear, btnCerrarRojo;

    public CrearPartidoFrame(ObtenerServices service,String temp) {
        super((Frame) null, "Crear Partido", true);

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); // Fondo transparente

        setSize(480, 500);
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
        bgPanel.add(new JLabel("ID Partido:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfIdPartido = new JTextField(); tfIdPartido.setFont(textFont); tfIdPartido.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfIdPartido, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Fecha (YYYY-MM-DD):") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfFecha = new JTextField(); tfFecha.setFont(textFont); tfFecha.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfFecha, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Equipo Local:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfEquipoLocal = new JTextField(); tfEquipoLocal.setFont(textFont); tfEquipoLocal.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfEquipoLocal, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Equipo Visitante:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfEquipoVisitante = new JTextField(); tfEquipoVisitante.setFont(textFont); tfEquipoVisitante.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfEquipoVisitante, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Goles Local:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfGolesLocal = new JTextField(); tfGolesLocal.setFont(textFont); tfGolesLocal.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfGolesLocal, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Goles Visitante:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfGolesVisitante = new JTextField(); tfGolesVisitante.setFont(textFont); tfGolesVisitante.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfGolesVisitante, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Estadio:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfEstadio = new JTextField(); tfEstadio.setFont(textFont); tfEstadio.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfEstadio, gbc); row++;

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
        btnCrear.addActionListener((ActionEvent e) -> {
            if (
                tfIdPartido.getText().trim().isEmpty() ||
                tfFecha.getText().trim().isEmpty() ||
                tfEquipoLocal.getText().trim().isEmpty() ||
                tfEquipoVisitante.getText().trim().isEmpty() ||
                tfGolesLocal.getText().trim().isEmpty() ||
                tfGolesVisitante.getText().trim().isEmpty() ||
                tfEstadio.getText().trim().isEmpty()
            ) {
                JOptionPane.showMessageDialog(CrearPartidoFrame.this, "¡Debe completar todos los campos!", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
            	int idTemporada =Integer.parseInt(temp);
                String idPartido = tfIdPartido.getText();
                String fecha = tfFecha.getText();
                String equipoLocal = tfEquipoLocal.getText();
                String equipoVisitante = tfEquipoVisitante.getText();
                int golesLocal = Integer.parseInt(tfGolesLocal.getText());
                int golesVisitante = Integer.parseInt(tfGolesVisitante.getText());
                String estadio = tfEstadio.getText();
                int asistenciasLocal=0;
                int asistenciaVisitante=0;
                Date fecha2= Date.valueOf(fecha);
                
                PartidoService partidoService = new PartidoService();
                partidoService.createPartido(idPartido, fecha2, tfGolesLocal.getText()+"-"+tfGolesVisitante.getText() ,idTemporada ,  estadio,
                         equipoLocal,  equipoVisitante, golesLocal, golesVisitante,asistenciasLocal,asistenciaVisitante);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CrearPartidoFrame.this, "Verifique que los campos numéricos tengan valores válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Botón personalizado igual al de CrearJugadorFrame
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