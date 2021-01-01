package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {

		protected String typeTag = "nlug";
		protected String desc = "";
	
	public Builder (){
		
	
		
	}	
	
	public abstract T  createTheInstance(JSONObject info);
	
	public T createInstance(JSONObject info) {
		
		T t = null;
		
		if(typeTag != null && typeTag.equals(info.getString("type"))) {
			t = createTheInstance(info.getJSONObject("data"));
			
		}
		
		return t;
	}
	
	
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		
		info.put("type", typeTag);
		info.put("data", createData());
		info.put("desc", desc);
		return info;
		

		
	}
	
	public  JSONObject createData() {
		JSONObject data = new JSONObject();
		
		data.put("type", "esto es el tipo");
		data.put("data", "esto es la informacion");
		
		
		return data;
		
		
	}
	
	public double[] jsonArryTodoubleArray(JSONArray info) {
		double[] res = new double[info.length()];
		for(int i = 0; i < info.length(); i++) {
			
			res[i] = info.getDouble(i);
			//System.out.println(res[i]);
		}
		
		
		return res;
		
	}
}
