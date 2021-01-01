package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		this.typeTag = "basic";
	}
	
	public Body createTheInstance(JSONObject info) {
		
		
		String id = info.getString("id");
		
		double[] a_ = new double[info.getJSONArray("vel").length()];
		
		
		double[] v_ = this.jsonArryTodoubleArray(info.getJSONArray("vel"));
		//System.out.println(v_.toString());
		double[] p_ = this.jsonArryTodoubleArray(info.getJSONArray("pos"));
		
		
		double m = info.getDouble("mass");
		
		Vector a = new Vector( v_.length);
		Vector v = new Vector( v_);
		Vector p = new Vector( p_);
			
		
		Body res = new Body(id, a, v ,p ,m);
		
		//System.out.println("Vamos a ver la info del body que acabamos de meter: ");
		//System.out.println("ID: " + id);
		//System.out.println("pos: " + p.toString());
		//System.out.println("vel: " + v.toString());
		//System.out.println("mass: " + m);
		
		
		
		
		
		return res;
		
	}
	
	public JSONObject createData() {
		JSONObject data = new JSONObject();
		
		data.put("id", "esto es el id");
		data.put("pos", "esto es la posicion");
		data.put("vel", "esto es la velocidad");	
		data.put("mass", "esto es la masa");

		
		return data;
		
	}
}
