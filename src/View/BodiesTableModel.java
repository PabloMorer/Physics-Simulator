package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel  extends AbstractTableModel implements SimulatorObserver{
	
	
	//Añade nombres de las columnas
	private List<Body> _bodies;
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	
	String columnas[] = new String[] {"Id", "Mass", "Position", "Velocity", "Acceleration"};
	
	
	@Override
	public int getColumnCount() {
		return columnas.length;
		
	}

	@Override
	public int getRowCount() {
		return _bodies.size();
	}
	@Override 
	public String getColumnName(int column) {
		
		return columnas[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body b = _bodies.get(rowIndex);
		String c = "";
		switch (columnIndex) {
		case 0: c=b.getId(); break;
		case 1: c=String.valueOf(b.getMass()); break;
		case 2: c=b.getPosition().toString(); break;
		case 3: c=b.getVelocity().toString(); break;
		case 4: c=b.getAcceleration().toString(); break;
		default: c="ERROR INDICE COLUMNA";
		}
		return c;
	}
	
	

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				fireTableStructureChanged(); //revisar
			}
			
			
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
	
	
		_bodies = bodies;
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				fireTableStructureChanged(); //revisar
			}
			
			
		});
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
	
		_bodies = bodies;
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				fireTableStructureChanged(); //revisar
			}
			
			
		});
		
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub

		
		_bodies = bodies;
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				fireTableStructureChanged(); //revisar
			}
			
			
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}