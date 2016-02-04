package decisionTreeHomework;

import java.util.List;

public class Leaf<Type extends Event> {

	private Leaf<Type> output1 = null;
	private Leaf<Type> output2 = null;
	private double split;
	private int variable;
	
	private double nBackground;
	private double nSignal;
	
	public Leaf() {
		this(0, 0);
	}
	
	public Leaf(int variable, double split) {
		this.variable = variable;
		this.split = split;
	}

	public boolean isFinal() {
		return output1 == null || output2 == null;
	}
	
	public double getPurity() {
		return nSignal / (nSignal + nBackground);
	}
	
	public Leaf<Type> getLeftBranch() {
		return output1;
	}
	
	public Leaf<Type> getRightBranch() {
		return output2;
	}
	
	public Leaf<Type> runEvent(Type event) {
		if (isFinal()) {
			return null;
		}
		
		if (event.getVars()[variable] <= split) {
			return output1;
		} else {
			return output2;
		}
	}
	
	public void train(Data<Type> signal, Data<Type> background) {
		for(Event e: signal.getEvents()) {
			nSignal += e.getBoostWeight();
		}
		for(Event e: background.getEvents()) {
			nBackground += e.getBoostWeight();
		}
		
		boolean branch = chooseVariable(signal, background);
	
		if (branch) {
			output1 = new Leaf<Type>();
			output2 = new Leaf<Type>();
			
			Data<Type> signalLeft = new Data<>();
			Data<Type> signalRight = new Data<>();
			Data<Type> backgroundLeft = new Data<>();
			Data<Type> backgroundRight = new Data<>();
			
			for (Type event : signal.getEvents()) {
				if (runEvent(event) == output1) {
					signalLeft.addEvent(event);
				} else {
					signalRight.addEvent(event);
				}
			}
			
			for (Type event : background.getEvents()) {
				if (runEvent(event) == output1) {
					backgroundLeft.addEvent(event);
				} else {
					backgroundRight.addEvent(event);
				}
			}
			output1.train(signalLeft, backgroundLeft);
			output2.train(signalRight, backgroundRight);
		}		
	}
	
	private boolean chooseVariable(Data<Type> signal, Data<Type> background) {
		double bestSplit = 0;
		int bestVariable = 0;
		double bestCGini = 0;
		if(signal.getEvents().size() < 50 || background.getEvents().size() < 50) {
			return false;
		}
		for(int i = 0; i < 8; i++) {
			double min = signal.getEvents().get(0).getVars()[i];
			double max = signal.getEvents().get(0).getVars()[i];
			for(Event e: signal.getEvents()) {
				if(e.getVars()[i] < min) {
					min = e.getVars()[i];
				}
				else if(e.getVars()[i] > max) {
					max = e.getVars()[i];
				}
			}
			for(Event e: background.getEvents()) {
				if(e.getVars()[i] < min) {
					min = e.getVars()[i];
				}
				else if(e.getVars()[i] > max) {
					max = e.getVars()[i];
				}
			}
			double increment = (max - min)/100;
			
			for(double threshold = min + increment; threshold < max; threshold += increment) {
				if(getCGini(i, threshold, signal.getEvents(), background.getEvents()) > bestCGini) {
					bestSplit = threshold;
					bestVariable = i;
					bestCGini = getCGini(i, threshold, signal.getEvents(), background.getEvents());
				}
			}
		}
		split = bestSplit;
		variable = bestVariable;
		return true;
	}
	
	private double getCGini(int variable, double threshold, List<Type> signal, List<Type> background) {
		double signalLeft = 0;
		double signalRight = 0;
		double backgroundLeft = 0;
		double backgroundRight = 0;
		for(Type event: signal) {
			if(event.getVars()[variable] < threshold) {
				signalLeft += event.getBoostWeight();
			}
			else if(event.getVars()[variable] > threshold) {
				signalRight += event.getBoostWeight();
			}
		}
		for(Type event: background) {
			if(event.getVars()[variable] < threshold) {
				backgroundLeft += event.getBoostWeight();
			}
			else if(event.getVars()[variable] > threshold) {
				backgroundRight += event.getBoostWeight();
			}
		}
		double pLeft = ((double)signalLeft)/(signalLeft + backgroundLeft);
		double pRight = ((double)signalRight)/(signalRight + backgroundRight);
		double giniLeft = 0;
		double giniRight = 0;
		double gini = 0;
		for(Type event: signal) {
			gini += event.getBoostWeight()*getPurity()*(1 - getPurity());
			if(event.getVars()[variable] < threshold) {
				giniLeft += event.getBoostWeight()*pLeft*(1 - pLeft);
			}
			else if(event.getVars()[variable] > threshold) {
				giniRight += event.getBoostWeight()*pRight*(1 - pRight);
			}
		}
		for(Type event: background) {
			gini += event.getBoostWeight()*getPurity()*(1 - getPurity());
			if(event.getVars()[variable] < threshold) {
				giniLeft += event.getBoostWeight()*pLeft*(1 - pLeft);
			}
			else if(event.getVars()[variable] > threshold) {
				giniRight += event.getBoostWeight()*pRight*(1 - pRight);
			}
		}
		return gini - giniLeft - giniRight;
	}
}