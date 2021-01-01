package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws {
	public static final double G = 6.67E-11;
	public NewtonUniversalGravitation() {
		
	}
	
	
	public void apply(List<Body> bodies) {
		
		
		for(int i = 0; i < bodies.size() ; i++) {
			Vector Ftotal = new Vector(bodies.get(i).getPosition().dim());
			for(int j = 0 ; j < bodies.size() ; j++) {
				if((bodies.get(i).getMass() > 0 && bodies.get(j).getMass() > 0) && i!=j) {		
					Ftotal = Ftotal.plus(calcularFuerza(bodies.get(i), bodies.get(j)));
				}
			}//calculamos la fuerza alrededor del cuerpo i
			bodies.get(i).setAcceleration(Ftotal.scale(1/bodies.get(i).getMass()));
		}
	}
		
	
	
	private Vector calcularFuerza(Body i, Body j) {//i, j
		
		Vector f = new Vector(i.getPosition().dim());
		double fij;
		double mass = i.getMass() * j.getMass();
		double distance = j.getPosition().distanceTo(i.getPosition());
		distance = Math.abs(distance);//en el caso que sea negativo
		double aux = distance * distance;//distance^2
		
		
		fij = G * mass /aux;
		
		Vector d = new Vector(j.getPosition().minus(i.getPosition())); 
		d = d.direction();
		f = d.scale(fij);
		
		
		return f;
	}

	public String toString() {
		return("Newton Universal Gravity Law");
	}
}
