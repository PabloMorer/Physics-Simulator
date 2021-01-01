package View;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar  extends JPanel implements SimulatorObserver{

		private JLabel _currTime; 
		private JLabel _currLaws;
		private JLabel _numOfBodies;
		
		StatusBar(Controller ctrl){
			initGUI();
			ctrl.addObserver(this);
			
			
		
			
	}
		
		private void initGUI(){
			_currTime = new JLabel("Time: ");
			_numOfBodies = new JLabel("Bodies ");	
			_currLaws = new JLabel("Laws: ");
			this.setLayout(new FlowLayout (FlowLayout.LEFT));
			this.setBorder( BorderFactory.createBevelBorder(1));
			
			
	}
		
	private void updateGui() {
		this.add(_currTime);
		this.add(_numOfBodies);
		this.add(_currLaws);
	
		this._currTime.setVisible(true);
		this._numOfBodies.setVisible(true);
		this._currLaws.setVisible(true);
	}
	
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {

		_currTime.setText("Time: " + Double.toString(time));
		_numOfBodies.setText("Bodies " + Integer.toString(bodies.size()));
		_currLaws.setText("Laws: " + gLawsDesc);
		
		updateGui();
		
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc){
		updateGui();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_numOfBodies.setText("Bodies " + Integer.toString(bodies.size()));
		
		updateGui();
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		_currTime.setText("Time: " + Double.toString(time));
		_numOfBodies.setText("Bodies " + Integer.toString(bodies.size()));
		
		updateGui();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		updateGui();
		
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		System.out.print("Status Bar: " + gLawsDesc);
		// TODO Auto-generated method stub
		_currLaws.setText("Laws: " + gLawsDesc);
		
		//updateGui();
	
		
	}

}
