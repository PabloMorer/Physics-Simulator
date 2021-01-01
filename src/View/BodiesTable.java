package View;

import java.awt.BorderLayout;
import java.awt.Color;



import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
	
	BodiesTable(Controller _ctrl) {
		
	setLayout(new BorderLayout());
	
	setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createLineBorder(Color.black, 2),
		"Bodies",
		TitledBorder.LEFT, TitledBorder.TOP));
	
	
	BodiesTableModel modelo = new BodiesTableModel(_ctrl);
	JTable tabla = new JTable(modelo); 
	
	JScrollPane scroll = new JScrollPane(tabla);


	this.add(scroll);
	
	
	// COMPLETAR
	}
}