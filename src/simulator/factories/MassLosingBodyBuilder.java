package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.MassLossingBody;


public class MassLosingBodyBuilder extends Builder<Body> {
	
	public MassLosingBodyBuilder() {
		this.typeTag = "mlb";
		
	}
	
	public Body createTheInstance(JSONObject info) {
	
		String id = info.getString("id");
		
		double[] a_ = new double[info.getJSONArray("vel").length()];
		
		
		double[] v_ = this.jsonArryTodoubleArray(info.getJSONArray("vel"));
		
		double[] p_ = this.jsonArryTodoubleArray(info.getJSONArray("pos"));
		
		double m = info.getDouble("mass");
		
		double freq= info.getDouble("freq");
		
		double factor = info.getDouble("factor");
		
		Vector a = new Vector( v_.length);
		Vector v = new Vector( v_);
		Vector p = new Vector( p_);
		
		MassLossingBody res = new MassLossingBody(id, a, v ,p ,m, factor, freq);
		
		return res;
		
	}
	
	public JSONObject createData() {
		JSONObject data = new JSONObject();
		
		data.put("id", "esto es el id");
		data.put("pos", "esto es la posicion");
		data.put("vel", "esto es la velocidad");	
		data.put("mass", "esto es la masa");
		data.put("freq", "esto es la frecuencia");
		data.put("factor", "esto es el factor");
		
		return data;
		
	}
}
