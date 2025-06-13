package jframeessss;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

import clases.Equipo;
import services.EquipoService;

@SuppressWarnings("serial")
public class EditarEquipoFrame extends JDialog {
    private JTextField tfNombreEquipo, tfCampeonatosParticipados, tfCampeonatosGanados, tfMascota, tfProvincia;
    private HoverPressButton btnGuardar, btnCerrarRojo;
    private String nombreOriginal; // Para guardar el nombre original del equipo

    public EditarEquipoFrame(String nombreEquipo) {
        super((Frame) null, "Editar Equipo", true);
        this.nombreOriginal = nombreEquipo;

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); 

        setSize(480, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Obtener el equipo a editar
        EquipoService equipoService = new EquipoService();
        Equipo equipo = equipoService.getEquipoById(nombreEquipo);

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
        tfNombreEquipo = new JTextField(equipo.getNombreEquipo());
        tfNombreEquipo.setFont(textFont);
        tfNombreEquipo.setPreferredSize(new Dimension(200, 36));
        tfNombreEquipo.setEditable(false); // El nombre del equipo no se puede editar (clave primaria)
        bgPanel.add(tfNombreEquipo, gbc); 
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Campeonatos Participados:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfCampeonatosParticipados = new JTextField(Integer.toString(equipo.getCampeonatosParticipados()));
        tfCampeonatosParticipados.setFont(textFont);
        tfCampeonatosParticipados.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfCampeonatosParticipados, gbc); 
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Campeonatos Ganados:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfCampeonatosGanados = new JTextField(Integer.toString(equipo.getCampeonatosGanados()));
        tfCampeonatosGanados.setFont(textFont);
        tfCampeonatosGanados.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfCampeonatosGanados, gbc); 
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Mascota:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfMascota = new JTextField(equipo.getMascota());
        tfMascota.setFont(textFont);
        tfMascota.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfMascota, gbc); 
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        bgPanel.add(new JLabel("Provincia:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        tfProvincia = new JTextField(equipo.getProvincia());
        tfProvincia.setFont(textFont);
        tfProvincia.setPreferredSize(new Dimension(200, 36));
        bgPanel.add(tfProvincia, gbc); 
        row++;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnGuardar = new HoverPressButton("Guardar",
            new Color(0, 153, 76, 200),       
            new Color(0, 120, 60, 220),        
            new Color(0, 80, 40, 240)          
        );
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(130, 44));
        btnGuardar.setMaximumSize(new Dimension(130, 44));
        btnGuardar.setMinimumSize(new Dimension(130, 44));

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
        buttonPanel.add(btnGuardar);
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

        btnGuardar.addActionListener((ActionEvent e) -> {
            if (
                tfNombreEquipo.getText().trim().isEmpty() ||
                tfCampeonatosParticipados.getText().trim().isEmpty() ||
                tfCampeonatosGanados.getText().trim().isEmpty() ||
                tfMascota.getText().trim().isEmpty() ||
                tfProvincia.getText().trim().isEmpty()
            ) {
                JOptionPane.showMessageDialog(EditarEquipoFrame.this, 
                    "¡Debe completar todos los campos!", 
                    "Campos incompletos", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                Equipo equipoActualizado = new Equipo();
                equipoActualizado.setNombreEquipo(tfNombreEquipo.getText());
                equipoActualizado.setCampeonatosParticipados(Integer.parseInt(tfCampeonatosParticipados.getText()));
                equipoActualizado.setCampeonatosGanados(Integer.parseInt(tfCampeonatosGanados.getText()));
                equipoActualizado.setMascota(tfMascota.getText());
                equipoActualizado.setProvincia(tfProvincia.getText());
                
                // Actualizar el equipo usando el servicio
                EquipoService service = new EquipoService();
                boolean actualizado = service.updateEquipo(equipoActualizado, nombreOriginal);
                
                if (actualizado) {
                    JOptionPane.showMessageDialog(EditarEquipoFrame.this,
                        "Equipo actualizado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(EditarEquipoFrame.this,
                        "Error al actualizar el equipo",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(EditarEquipoFrame.this, 
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