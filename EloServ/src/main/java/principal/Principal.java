package principal;

import controlador.Controlador;

public class Principal {


	public static void main(String[] args) {
		try {
			vista.Principal ventanaPrincipal = new vista.Principal();
			ventanaPrincipal.setVisible(true);
			new Controlador(ventanaPrincipal);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}