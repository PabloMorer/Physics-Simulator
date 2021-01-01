
package simulator.model;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class PhysicsSimulator  {
	private double dt;
	private double time;
	protected GravityLaws ley;
	protected List<Body> listaCuerpos = null;
	protected List<SimulatorObserver> listaObserv = null;
	
	

	public PhysicsSimulator(double t, GravityLaws gl) {
		this.dt = t;
		this.ley = gl;
		this.time = 0.0;
		this.listaCuerpos = new ArrayList<Body>();
		this.listaObserv = new ArrayList<>();
	}
	
	public void setDeltaTime(double dt) {
		this.dt = dt;
		
		for(SimulatorObserver o : this.listaObserv) {
			o.onDeltaTimeChanged(this.dt);
		}
	}
	
	public List<Body> getLista(){
		return this.listaCuerpos;
	}
	public void advance() throws IllegalArgumentException {
	
		try{
		this.ley.apply(listaCuerpos);
		}
		catch(IllegalArgumentException e){
			throw new IllegalArgumentException("Invalid gravity law: NULL ");
		}
		
		for(int i = 0; i < listaCuerpos.size();i++) {
			
		this.listaCuerpos.get(i).move(this.dt);
		}
		this.time+= this.dt;
		
		
		for(SimulatorObserver o : this.listaObserv) {
			o.onAdvance(this.listaCuerpos, this.time);
		}
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		int i = 0;
		boolean encontrado = false;
		if (this.listaCuerpos.size() > 0){
			while( i < listaCuerpos.size() && !encontrado) {
				try{
					if(listaCuerpos.get(i).getId().equals(b.getId())) {
						encontrado = true;
					}
				}
				catch(IllegalArgumentException e){
					throw new IllegalArgumentException("Error at adding the same body twice: " + b.getId());
				}
				
				i++;
			}
		}

		if(!encontrado) {
			this.listaCuerpos.add(b);
			
			for(SimulatorObserver o : this.listaObserv) {
				o.onBodyAdded(this.listaCuerpos, b);
			}
			
		}
		
	}
	
	public void addObserver(SimulatorObserver o) {
		int i = 0;
		boolean encontrado = false;
		if (this.listaObserv.size() > 0){
			while( i < listaObserv.size() && !encontrado) {
				try{
					if(listaObserv.get(i).equals(o)) {//no estoy seguro
						encontrado = true;
					}
				}
				catch(IllegalArgumentException e){
					throw new IllegalArgumentException("Error at adding the observer");
				}
				
				i++;
			}
		}

		if(!encontrado) {
			this.listaObserv.add(o);
			
			for(SimulatorObserver a : this.listaObserv) {
				a.onRegister(this.listaCuerpos, this.time, this.dt, this.ley.toString());
			}
			
		}
	}
	
	public void reset() {
		this.dt = 0.0;
		listaCuerpos.clear();
		
		for(SimulatorObserver o : this.listaObserv) {
			o.onReset(this.listaCuerpos, this.time ,this.dt,this.ley.toString());
		}
		
	}
	
	public void setGravityLaws(GravityLaws gravityLaws) throws IllegalArgumentException{
		try {
			this.ley = gravityLaws;
			System.out.print("Problemas" + gravityLaws.toString());
			for(SimulatorObserver o : this.listaObserv) {
				o.onGravityLawChanged(this.ley.toString());
			}
		}
		catch(IllegalArgumentException e){
			throw new IllegalArgumentException("Invalid Gravity law value");
		}
		
		
	}
	
	public String toString() {
		
		String res = "";
		
		res += "{ \"time\": " + this.time +  ", \"bodies\": [";
		for(int i = 0; i < listaCuerpos.size();i++) {
			if(i == 0) {
				res+= listaCuerpos.get(i).toString();
			}
			else {
				res+= ", "+listaCuerpos.get(i).toString();
			}
		}
		
		res += "]";
		
		return res;
	}


	
}
