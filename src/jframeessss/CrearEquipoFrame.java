package jframeessss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import clases.Equipo;
import services.EquipoService;

@SuppressWarnings("serial")
public class CrearEquipoFrame extends JDialog {
    private JTextField tfNombreEquipo, tfCampeonatosParticipados, tfCampeonatosGanados, tfMascota, tfProvincia;
    private HoverPressButton btnCrear, btnCerrarRojo;

    public CrearEquipoFrame() {
        super((Frame) null, "Crear Equipo", true);

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); 

        setSize(480, 500);
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
        bgPanel.add(new JLabel("Nombre Equipo:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfNombreEquipo = new JTextField(); 
        tfNombreEquipo.setFont(textFont); 
        tfNombreEquipo.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfNombreEquipo, gbc); 
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Campeonatos Participados:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfCampeonatosParticipados = new JTextField(); 
        tfCampeonatosParticipados.setFont(textFont); 
        tfCampeonatosParticipados.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfCampeonatosParticipados, gbc); 
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Campeonatos Ganados:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfCampeonatosGanados = new JTextField(); 
        tfCampeonatosGanados.setFont(textFont); 
        tfCampeonatosGanados.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfCampeonatosGanados, gbc); 
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Mascota:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfMascota = new JTextField(); 
        tfMascota.setFont(textFont); 
        tfMascota.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfMascota, gbc); 
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Provincia:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfProvincia = new JTextField(); 
        tfProvincia.setFont(textFont); 
        tfProvincia.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfProvincia, gbc); 
        row++;

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
                tfNombreEquipo.getText().trim().isEmpty() ||
                tfCampeonatosParticipados.getText().trim().isEmpty() ||
                tfCampeonatosGanados.getText().trim().isEmpty() ||
                tfMascota.getText().trim().isEmpty() ||
                tfProvincia.getText().trim().isEmpty()
            ) {
                JOptionPane.showMessageDialog(CrearEquipoFrame.this, 
                    "¡Debe completar todos los campos!", 
                    "Campos incompletos", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                
            	
            	Equipo equipo = new Equipo();
                equipo.setNombreEquipo(tfNombreEquipo.getText());
                equipo.setCampeonatosParticipados(Integer.parseInt(tfCampeonatosParticipados.getText()));
                equipo.setCampeonatosGanados(Integer.parseInt(tfCampeonatosGanados.getText()));
                equipo.setMascota(tfMascota.getText());
                equipo.setProvincia(tfProvincia.getText());
                
                EquipoService service = new EquipoService();
                boolean creado = service.createEquipo(equipo);
                
                if (creado) {
                    JOptionPane.showMessageDialog(CrearEquipoFrame.this,
                        "Equipo creado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CrearEquipoFrame.this,
                        "Error al crear el equipo",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CrearEquipoFrame.this, 
                    "Verifique que los campos numéricos tengan valores válidos.", 
                    "Error de formato", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Clase HoverPressButton (igual que en el ejemplo original)
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