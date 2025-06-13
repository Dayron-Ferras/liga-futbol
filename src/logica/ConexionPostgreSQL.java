package logica;

	import java.awt.EventQueue;

import jframeessss.Principal;

	public class ConexionPostgreSQL {
	    public static void main(String[] args) {
	    	EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Principal frame = new Principal();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	    }
	}
