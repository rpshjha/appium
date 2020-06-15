package cucumber;

import java.util.Hashtable;
import java.util.Map;

import enums.Context;


public class ScenarioContext {

private Map<String,Object> scenarioContext;
	
	public ScenarioContext() {
		scenarioContext=new Hashtable<String, Object>();
	}
	
	public void setContext(Context key,Object value) {
		scenarioContext.put(key.toString(), value);
	}
	
	public Object getContext(Context key) {
		return scenarioContext.get(key.toString().trim());
	}
	
	public boolean isContains(Context key) {
		return scenarioContext.containsKey(key.toString());
	}
}
