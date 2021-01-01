package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class FallingToCenterGravity implements GravityLaws {
	public static final double g = 9.81;
	
	public FallingToCenterGravity () {
		
	}
	public void apply(List<Body> bodies) {
		
		for(int i = 0; i < bodies.size(); i++) {
			
			Vector aux = bodies.get(i).getPosition().direction();
			//System.out.println(i+ " " + bodies.get(i).getPosition().toString() + " -> " + aux.toString());
			
			bodies.get(i).setAcceleration(aux.scale(-g));
			//System.out.println("SE QUEDA -> " + bodies.get(i).getAcceleration().toString());
			
		}
	}
	
	public String toString() {
		return("Falling to center gravity");
	}

	

}
