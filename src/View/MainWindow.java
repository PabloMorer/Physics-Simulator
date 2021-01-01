package View;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	// Añade atributos para todos los componentes (clases)
	Controller _ctrl;
	private ControlPanel ControlPanel;
	private BodiesTable BodiesTable;
	private Viewer Viewer;
	private StatusBar StatusBar;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	private void initGUI() {
		
		JPanel mainPanel = new JPanel(new  BorderLayout());
		setContentPane(mainPanel);
		
		this.ControlPanel = new ControlPanel(this._ctrl);
		this.BodiesTable = new BodiesTable(this._ctrl);
		this.Viewer = new Viewer(this._ctrl);
		this.StatusBar = new StatusBar(this._ctrl);
		
		
		mainPanel.add(this.ControlPanel, BorderLayout.PAGE_START);
		mainPanel.add(this.StatusBar, BorderLayout.PAGE_END);
		
		JPanel aux  = new JPanel(); 
		aux.setLayout((LayoutManager) new BoxLayout(aux, BoxLayout.Y_AXIS));
		aux.add(this.BodiesTable);
		aux.add(this.Viewer);
		mainPanel.add(aux, BorderLayout.CENTER);
		
		this.ControlPanel.setVisible(true);
		this.BodiesTable.setVisible(true);
		this.Viewer.setVisible(true);
		this.StatusBar.setVisible(true);
		aux.setVisible(true);
		
	
		this.setSize(600, 400);
		this.setVisible(true);


		mainPanel.setVisible(true);
		
		
		// Completa el método para construir la GUI
		
	}
	// Añade private/protected methods
} 