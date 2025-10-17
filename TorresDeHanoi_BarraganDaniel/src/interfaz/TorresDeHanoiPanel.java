package interfaz;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.List;

public class TorresDeHanoiPanel extends JPanel {
		private Stack<Integer>[] torres;
		private List<Color> discoColores;
		private int movimientos;
		
		public TorresDeHanoiPanel(int numDiscos) {
			torres = new Stack[3];
			discoColores = new ArrayList<>();
			movimientos = 0;
			
			for(int i = 0; i < 3; i++) {
				torres[i] = new Stack<>();
			}
			
			for(int i = numDiscos; i > 0; i--) {
				torres[0].push(i);
				discoColores.add(new Color((int)(Math.random()* 0x1000000)));
			}
			
			setPreferredSize(new Dimension(600, 300));
			setBackground(Color.WHITE);
		}
		
		public void moverDisco(int origen, int destino) {
		    int disco = torres[origen].pop();
		    torres[destino].push(disco);
		    movimientos++;
		    repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g);

		    int torreWidth = getWidth() / 4;
		    int torreHeight = getHeight() / 2;
		    int discoHeight = 20;

		    for (int i = 0; i < 3; i++) {
		        int torreX = (i + 1) * torreWidth - torreWidth / 2;
		        int torreY = getHeight() - torreHeight;
		        g.setColor(Color.BLACK);
		        g.fillRect(torreX, torreY, 10, torreHeight);

		        int discoY = getHeight() - discoHeight;
		        for (int disco : torres[i]) {
		            int discoWidth = disco * 20;
		            int discoX =  torreX - discoWidth / 2 + 5;
		            g.setColor(discoColores.get(disco - 1));
		            g.fillRect(discoX, discoY, discoWidth, discoHeight);
		            discoY -= discoHeight;
		        }
		    }

		    g.setColor(Color.BLACK);
		    g.drawString("Movimientos: " + movimientos, 10, 20);
		}
		
		public void reiniciar(int numDiscos) {
			movimientos = 0;
			for(int i = 0; i < 3; i++) {
				torres[i].clear();
			}
			discoColores.clear();
			
			for (int i = numDiscos; i > 0; i--) {
				torres[0].push(i);
				discoColores.add(new Color ((int)(Math.random()*0x1000000)));
			}
			repaint();
		}
}

