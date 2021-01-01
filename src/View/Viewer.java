package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver{
	
	private static final int _WIDTH = 1000;
	private static final int _HEIGHT = 1000;
	
	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;
	
	
	
	Viewer(Controller ctrl){
		initGUI();
		ctrl.addObserver(this);
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Viewer",
				TitledBorder.LEFT, TitledBorder.BOTTOM));
			
		
	}

	
	private void initGUI(){
		
		_bodies = new ArrayList<>();
		_scale = 1.0;
		_showHelp = true;
		
		addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				switch (e.getKeyChar()){
				case '-':
					_scale = _scale*1.1;
					break;
				case '+':
					_scale = Math.max(1000.0, _scale/1.1);
					break;
				case '=':
					autoScale();
					break;
				case 'h':
					if(_showHelp) {
						_showHelp = false;
					}
					else {
						_showHelp = true;
					}
					break;
				default:
				}
				repaint();
			}

		

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e){
				requestFocus();
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.setPreferredSize(new Dimension(400,600));
		
		
		
		
		
	}
	
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		_centerX = getWidth()/2;
		_centerY = getHeight()/2;
		
		
		gr.drawLine(_centerX, getHeight(),_centerX, 0); 
		gr.drawLine(0, _centerY, getWidth(), _centerY);
		
		if(_showHelp){
			gr.setColor(Color.red);
			gr.drawString("h: ayuda , + : zoom - in , - : zoom out", 7, 25);
			gr.drawString("Scaling Ratio " + Double.toString(_scale), 7, 40);
			

	
		}
		double posx;
		double posy;
		
		int rad = 5;
		
		gr.setColor(Color.blue);
		
		for(int i = 0; i < _bodies.size();i++){
			
			 posx =  _bodies.get(i).getPosition().coordinate(0);
			 posy =  _bodies.get(i).getPosition().coordinate(1);
			

			
			gr.fillOval(_centerX + (int) (posx/_scale) - rad, _centerY - (int) (posy/_scale) - rad ,rad*2 ,rad*2 );
			
				
			gr.drawString("b" + Integer.valueOf(i),_centerX + (int) (posx/_scale) - rad, _centerY - (int) (posy/_scale) - rad );

		}
		
	}
	
	
	
	void autoScale(){
	
		double max = 1.0;
		
		for(Body b : _bodies){
			Vector p = b.getPosition();
			for(int i = 0; i <p.dim(); i++){
				max = Math.max(max, Math.abs(b.getPosition().coordinate(i)));
			}
		}
		double size = Math.max(1.0, Math.min((double) getWidth(), (double) getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
			
		}

	
		
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt,	String gLawsDesc) {
		// TODO Auto-generated method stub
		
		_bodies = bodies;
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				autoScale();
				repaint();
			}
			
			
		});
	
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt,
			String gLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		this.autoScale();
		SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				repaint();
			}
			
			
		});
		
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		this.autoScale();
		SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				repaint();
			}
			
			
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
	
		this.autoScale();
		SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				repaint();
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