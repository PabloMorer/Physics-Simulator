package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body {
	protected double lossFactor;
	protected double lossFrequency;
	protected double c;
	public MassLossingBody(String id, Vector a, Vector v, Vector p, double m, double lossFactor, double lossFrequency) {
		
		super(id,a,v,p,m);
		this.lossFactor= lossFactor;
		this.lossFrequency = lossFrequency;
		this.c = 0.0;
		
	}
	
	
	public void move( double t) {
		//PATATA
		super.move(t);
		c += this.lossFrequency;
		if(c >= this.lossFrequency) {
			this.m = this.m*( 1- this.lossFactor);
			this.c = 0.0;
		}
		
	}
	
}
