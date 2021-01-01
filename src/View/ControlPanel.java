package View;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.SimulatorObserver;
 

import java.util.*;

public class ControlPanel extends JPanel implements SimulatorObserver {
// ...
private Controller _ctrl;
//volatile boolean _stopped;
private JButton fichero;
private JButton GL ;
private JButton inicio ;
private JButton parar ;
private JButton salir;	
private JSpinner delay;
private JSpinner spinner_pasos;
private JTextField deltaTime;
private Icon iconoFich;
private Icon iconoGL;
private Icon iconoInicio;
private Icon iconoParar;
private Icon iconoSalir;
private JOptionPane pre_salir;
private JFileChooser fc;
volatile Thread _thread;




ControlPanel(Controller ctrl) {
	 _ctrl = ctrl;
	initGUI();
	_ctrl.addObserver(this);
	}



private void initGUI() {
	// TODO build the tool bar by adding buttons, etc.
	JToolBar menu = new JToolBar("Menu");
	
	//crear Spinner
	
	
	delay = new JSpinner(new SpinnerNumberModel(0,0,1000,1));
	spinner_pasos = new JSpinner( new SpinnerNumberModel(10000,2500,50000,50));
	deltaTime = new JTextField();
	deltaTime.setText("10000");
	
	//crear hilo
	

		//crear Botones
	 fichero = new JButton();
	 GL = new JButton();
	 inicio = new JButton();
	 parar = new JButton();
	 salir = new JButton();		
	
	
	//Añadir iconos a los botonoes
	iconoFich = new ImageIcon("resources/icons/open.png");
	fichero.setIcon(iconoFich);
	fichero.setPreferredSize(new Dimension(40,40));
	iconoGL = new ImageIcon("resources/icons/physics.png");
	GL.setIcon(iconoGL);
	iconoInicio = new ImageIcon("resources/icons/run.png");
	inicio.setIcon(iconoInicio);
	iconoParar = new ImageIcon("resources/icons/stop.png");
	parar.setIcon(iconoParar);
	iconoSalir = new ImageIcon("resources/icons/exit.png");
	salir.setIcon(iconoSalir);
	
	
	//Añadir texto a los botonoes
	fichero.setToolTipText("Pide Fichero");
	GL.setToolTipText("Pide ley de gravedad");
	inicio.setToolTipText("Inicio");
	parar.setToolTipText("Detiene la ejecucion");
	salir.setToolTipText("Cierra la aplicacion");
	delay.setToolTipText("Retardo entre pasos de simulación consecutivos");
	
	
	//Añadir funcionalidad a los botones
	fichero.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			fc = new JFileChooser(System.getProperty("user.dir") + "/resources/examples");
			int ret = fc.showOpenDialog(getParent());
			
			if (ret == JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(getParent(), "Se ha seleccionado abrir el archivo: " + fc.getSelectedFile());
			} else {
				JOptionPane.showMessageDialog(getParent(), "Se ha pulsado cancelar o ha ocurrido un error.");
			}
			
		    fc.getSelectedFile();
		   
			_ctrl.reset();
			InputStream input;
			try { //A REVISAR
				input = new FileInputStream(fc.getSelectedFile());
				_ctrl.loadBodies(input); 
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	});
		
	GL.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			List<JSONObject>  list =  _ctrl.getGravityLawsFactory().getInfo();
			
			String[] listaString = new String[list.size()];
			
			for(int i = 0; i < list.size(); i++) {
				
				System.out.print(list.get(i).getString("desc")  + " " + list.get(i).getString("type") + "\n");
				
				listaString[i] = (list.get(i).getString("desc")  + " " +  list.get(i).getString("type"));
			}
			
			
			JOptionPane dialog = new JOptionPane("Leyes");
			String res =  (String) JOptionPane.showInputDialog(
					getParent(),
					"Select gravity law to be used",
					"Gravity Law Selector", JOptionPane.DEFAULT_OPTION,  null,
					listaString, "ng");
			
			
			
			int i = 0; 
			while( res != null && !res.equals(listaString[i])) {
				i++;
			}
			
			
			_ctrl.setGravityLaws(list.get(i));
					
			
		}
	});
	
	
	inicio.addActionListener(new ActionListener() {
		
		
		public void actionPerformed(ActionEvent e) {
			
			fichero.setEnabled(false);
            GL.setEnabled(false);
            inicio.setEnabled(false);
            salir.setEnabled(false);

            String res_dt = deltaTime.getText();
            double res = Double.parseDouble(res_dt);

            _ctrl.setDeltaTime(res);

            int pasos = (int)spinner_pasos.getValue();
            int d =  	 (int)delay.getValue();
            long dl = (long) d;
            _thread = new Thread(){
				@Override
				public void run() {
				// TODO Auto-generated method stub
					try {
						 
						run_sim(pasos,dl);
								
						_thread = null;
						
						fichero.setEnabled(true);
					    GL.setEnabled(true);
					    inicio.setEnabled(true);
					    salir.setEnabled(true);
					            
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
			};
			
			_thread.start();
			
		}
	});
	
	parar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if( _thread != null ) {
				System.out.print("parar");
				
				_thread.interrupt();
				
			}
			
			
			fichero.setEnabled(true);
			GL.setEnabled(true);
			inicio.setEnabled(true);
			salir.setEnabled(true);
			
		}
	});
	
	salir.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			JOptionPane pre_salir = new JOptionPane("¿Salir?");
			
			String[] listaString = {
					"Yes",
					"No"
			};
			
			int res =  JOptionPane.showOptionDialog(
					getParent(),
					"Are you sure you want to quit?",
					"Exit", JOptionPane.DEFAULT_OPTION, 0, null,
					listaString, "0");
			
			if(res == 0) {
				System.exit(0);
			}
			
			
		}
	});
	
	
	//Añadir los botones al panel
	
	
	
	
	menu.add(fichero);

	menu.add(GL);

	menu.add(inicio);

	menu.add(parar);

	menu.add(salir);
	
	menu.add(delay);
	
	menu.add(spinner_pasos);
	
	menu.add(deltaTime);
	
	
	
	
	
	this.add(menu);
		
		
	}


	// other private/protected methods
	// ...
	private void run_sim(int n, long del) throws InterruptedException {
		
		while ( n>0 && !Thread.interrupted()) {
			//System.out.print(i + "\n");
			
			try{
				// 1. execute the simulator one step, i.e., call method
				// _ctrl.run(1) and handle exceptions if any
				_ctrl.run(1);
				
			} catch(Exception e) {
				//e.printStackTrace();
				return;
			}
			
			try{
				// 2. sleep the current thread for ’delay’ milliseconds
				Thread.sleep(del);
			
			}catch(InterruptedException e){
				//e.printStackTrace();
				return;
			}
			n--;
		}
		
		
		
	}
	// SimulatorObserver methods
	// ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		String nuevoDt =  String.valueOf(dt);
		this.deltaTime.setText(nuevoDt);
		
		
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		String nuevoDt =  String.valueOf(dt);
		this.deltaTime.setText(nuevoDt);
	}



	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		String nuevoDt =  String.valueOf(dt);
		this.deltaTime.setText(nuevoDt);
		
		
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
		
	}
}