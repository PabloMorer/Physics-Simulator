package simulator.factories;

import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;


public class FallingToCenterGravityBuilder extends Builder<GravityLaws> {

	public FallingToCenterGravityBuilder() {
		this.typeTag = "ftcg";
		this.desc = "Falling to center gravity";
	}
	public FallingToCenterGravity createTheInstance(JSONObject info) {
		
		FallingToCenterGravity res = new FallingToCenterGravity ();

		return res;
		
	}
}
