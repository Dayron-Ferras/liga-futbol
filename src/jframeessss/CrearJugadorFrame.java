package jframeessss;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import services.ObtenerServices;

@SuppressWarnings("serial")
public class CrearJugadorFrame extends JDialog {
    private JComboBox<String> comboPosicion;
    private JPanel panelCampos;
    private JButton btnCerrar, btnCrear;

    public CrearJugadorFrame(ObtenerServices service) {
        super((Frame) null, "Crear Jugador", true);

        // Fondo personalizado (puedes cambiar el color aquí)
        Color fondo = new Color(35, 57, 93);
        getContentPane().setBackground(fondo);

        setUndecorated(true);
        setLayout(new BorderLayout());

        // ComboBox de posiciones
        comboPosicion = new JComboBox<>(new String[] {
            "Selecciona la posición...", "Portero", "Defensa", "Centrocampista", "Delantero"
        });
        comboPosicion.setBackground(Color.WHITE);
        comboPosicion.setFont(new Font("Arial", Font.PLAIN, 16));
        add(comboPosicion, BorderLayout.NORTH);

        // Panel contenedor de campos
        panelCampos = new JPanel();
        panelCampos.setBackground(fondo); // Fondo igual al de la ventana
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        add(panelCampos, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(fondo);
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnCrear = new JButton("Crear");
        btnCrear.setBackground(new Color(46, 204, 113));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFocusPainted(false);
        btnCrear.setFont(new Font("Arial", Font.BOLD, 14));

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(231, 76, 60));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));

        panelBotones.add(btnCrear);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        // Acción para cerrar
        btnCerrar.addActionListener(e -> dispose());

        // (Opcional) Acción para crear (completa según tu lógica)
        btnCrear.addActionListener(e -> {
        	String seleccion = (String) comboPosicion.getSelectedItem();
        	if( "Selecciona la posición...".equals(seleccion)) {
	
        	}
        	else if ("Portero".equals(seleccion)) {
              CrearPorteroFrame p = new CrearPorteroFrame(service);
              p.setVisible(true);
              
            } else if ("Defensa".equals(seleccion)) {
              CrearDefensaFrame d = new CrearDefensaFrame(service);
              d.setVisible(true);
            } else if ("Centrocampista".equals(seleccion)) {
               CrearCentrocampistaFrame c = new CrearCentrocampistaFrame(service);
               c.setVisible(true);
            } else if ("Delantero".equals(seleccion)) {
               CrearDelanteroFrame del = new CrearDelanteroFrame(service);
               del.setVisible(true);
            }
            
        });

      
        pack();
        setLocationRelativeTo(null);
    }

   
}