package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory {
	protected List<Builder<T>> listaObjetos = null;
	public BuilderBasedFactory(List<Builder<T>> list) {
		listaObjetos = list;
	
	}

private static Builder[] avaliableBuilders = {
			
			new BasicBodyBuilder(),
			new MassLosingBodyBuilder(),
			new NewtonUniversalGravitationBuilder(),
			new FallingToCenterGravityBuilder(),
			new NoGravityBuilder(),
	};
	
	public Object createInstance(JSONObject info) throws IllegalArgumentException { 
	
		T aux = null;
		try{
			
		for(Builder builder : avaliableBuilders) {
			
			if(builder.typeTag.toString().equals(info.getString("type"))) {
			
				return  builder.createInstance(info);
			}
			
		}
		}
		
		catch (IllegalArgumentException e){
			throw new IllegalArgumentException("Incorrect info values");
		}
		
		
		//LANZAR EXCEPCION
		return null;
	}


	public List getInfo() {
	//List<JSONObject> listaAyudas = null; 
	List<JSONObject> listaAyudas = new ArrayList<JSONObject>();
	for(Builder builder : listaObjetos ) {
		
		listaAyudas.add(builder.getBuilderInfo());
	}
		
		return listaAyudas;
	}

}
