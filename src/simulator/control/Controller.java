package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {

		private PhysicsSimulator ps;
		private Factory<Body> fbody;
		private Factory<GravityLaws> fg;
		
	public Controller(PhysicsSimulator ps, Factory<Body> fbody, Factory<GravityLaws> fg) {
		this.ps = ps;
		this.fbody = fbody;
		this.fg = fg;
		
	}
	
	public void loadBodies(InputStream in) {
		
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray ja = jsonInput.getJSONArray("bodies");
		JSONObject aux = new JSONObject();
		
		for (int i = 0; i < ja.length(); i++) {
	
			aux = (JSONObject) ja.get(i);
			
			Body b = fbody.createInstance(aux); 
			
			ps.addBody(b);
			
		}
		
		
	}
	
	public void run(int n, OutputStream out) throws IOException{//BATCH MODE
		String res = "";
		out.write("{\r\n".getBytes());
		out.write("\"states\":[\r\n".getBytes());
		
		for(int i = 0; i < n; i++){
			this.ps.advance();
			
			res = this.ps.toString();
			System.out.println(res);
			out.write(res.getBytes());
			out.write("}".getBytes());
			if(i < n-1){
			out.write(" ,".getBytes());
			}
			out.write("\r\n".getBytes());
		
		}
		out.write("]\r\n".getBytes());
		out.write("}\r\n".getBytes());
		
	}
	
	public void run(int n){ // GUI
			this.ps.advance();
	}
	
	
	public void reset() {
		ps.reset();
	}
	
	public void setDeltaTime(double dt) {
		ps.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		ps.addObserver(o);
	}
	public Factory<GravityLaws> getGravityLawsFactory(){
		return this.fg;
	}
	public void setGravityLaws(JSONObject info) {
	
		GravityLaws g = fg.createInstance(info);
		ps.setGravityLaws(g);
	}
	
}

////MAS DESPACIO CEREBRITO
