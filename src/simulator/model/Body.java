package simulator.model;

import simulator.misc.Vector;

public class Body {
	protected String id;	//Identificador
	protected Vector v; 	//velocidad
	protected Vector a;	//aceleracion
	protected Vector p;	//posicion
	protected double m;	//masa
	
	public Body(String id, Vector a, Vector  v , Vector p, double m) {
		this.id = id;
		this.v = v;
		this.a = a;
		this.p = p;
		this.m = m;
	}
	
	
	//Gets
	public String getId() {
		return this.id;
	}

	public Vector getVelocity() {
		return this.v;
	}
	
	public Vector getAcceleration() {
		return this.a;
	}

	public Vector getPosition() {
		return this.p;
	}
	
	public double getMass() {
		return this.m;
	}

	//Sets
	public void setVelocity(Vector v) {
		this.v = v;
	}

	public void setAcceleration(Vector a) {
		this.a = a;
	}

	public void setPosition(Vector p) {
		this.p = p;
	}

	
	
	
	public void move(double t) {
		
		//NUEVA POSICION = p + v*t + 0.5*a*t*t
		
		Vector vxt = v.scale(t);
		Vector auxPos = a.scale(t*t*0.5);
		Vector finalPos = vxt.plus(auxPos);
		finalPos = p.plus(finalPos);
		
		this.p = finalPos;
		
		//Nueva velocidad = v + a*t PATATA
		Vector aux2 = a.scale(t);
		this.v = v.plus(aux2);
		
		
	}
	
	
	public String toString() {
		String dev; 
		//{  "id": "b1", "mass": 1.5E30, "pos": [0.0, 4.5E10], "vel": [10000.0, 0.0], "acc": [0.0, 0.0] }
		dev = " {" +  " \"id\": \"" + this.id + "\", \"mass\": "  + this.m + ", \"pos\": " + this.p +  ", \"vel\": " + this.v + ", \"acc\": " + this.a + " }" ;
		
		return dev;
	}
	



}
