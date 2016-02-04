package decisionTreeHomework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

abstract public class Event implements Serializable {

	private static final long serialVersionUID = -4320527264765339431L;
	
	private int nVariables;
	private List<String> names = new ArrayList<>();
	private double boostWeight;
	
	public Event(int nvar, List<String> names) {
		if (names.size() != nvar) {
			throw new RuntimeException("Invalid list of names entered!");
		}
		
		nVariables = nvar;
		this.names = names;
	}
	
	public double getBoostWeight() {
		return boostWeight;
	}

	public void setBoostWeight(double boostWeight) {
		this.boostWeight = boostWeight;
	}

	public String getName(int index) {
		return names.get(index);
	}
	
	public int getNVars() {
		return nVariables;
	}
	
	abstract public double[] getVars();
}
