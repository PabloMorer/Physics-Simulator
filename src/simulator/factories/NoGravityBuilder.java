package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;




public class NoGravityBuilder extends Builder<GravityLaws>{
	
	public NoGravityBuilder() {
		this.typeTag = "ng";
		this.desc = "No gravity";
	}
	public NoGravity createTheInstance(JSONObject info) {
		
		NoGravity res = new NoGravity ();

		return res;
	}
		
	
}
