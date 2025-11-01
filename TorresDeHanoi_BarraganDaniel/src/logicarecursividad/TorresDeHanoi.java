package logicarecursividad;

import interfaz.TorresDeHanoiPanel;

public class TorresDeHanoi {
	private TorresDeHanoiPanel panel;
	
	public TorresDeHanoi(TorresDeHanoiPanel panel) {
		this.panel=panel;
	}
	
	public void resolver(int numDiscos, int origen, int auxiliar, int destino, int delay) {
		if(numDiscos>0) {
			//PASO 1: 	MOVER TODOS LOS DISCOS MENOS EL ULTIMO DESDE EL ORGEN HATA EL AUAXILIAR	
			resolver( numDiscos - 1, origen, destino, auxiliar,delay);
			
			//PASO 2: MOVER EL DICO MAS GRANDE DIRECTAMENTE A DESTINO
			panel.moverDisco(origen,destino);
			try {
				Thread.sleep(delay);
			}catch (InterruptedException e ) {
				Thread.currentThread().interrupt();
				
			}
			
			//PASO 3 : MOVER LO DICOS EN AUXILIAR  A DESTINO 
			resolver(numDiscos -1, auxiliar, origen, destino, delay);
		}
	}

}

