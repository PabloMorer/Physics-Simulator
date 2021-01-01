package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.MassLossingBody;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws> {

		public NewtonUniversalGravitationBuilder() {
			this.typeTag = "nlug";
			this.desc = "Newton's law of universal gravitation";
		}
		
		public NewtonUniversalGravitation createTheInstance(JSONObject info) {
			
			//this.typeTag = info.getString("type");
			NewtonUniversalGravitation res = new NewtonUniversalGravitation ();
			
			return res;
			
		}
}
