package decisionTreeHomework;

public class BoostedDecisionTree<Type extends Event> extends DecisionTree<Type> {
	
	private double boostWeight;
	private final double beta = 0.5;
	
	public void changeBoostWeight(Data<Type> signal, Data<Type> background) {
		double errorWeight = 0;
		double totalWeight = 0;
		for(Type e: signal.getEvents()) {
			totalWeight += e.getBoostWeight();
			if(Math.round(runEvent(e)) != 1) {
				errorWeight += e.getBoostWeight();
			}
		}
		for(Type e: background.getEvents()) {
			totalWeight += e.getBoostWeight();
			if(Math.round(runEvent(e)) != 0) {
				errorWeight += e.getBoostWeight();
			}
		}
		double r = errorWeight/totalWeight;
		double newWeight = beta*Math.log((1 - r)/r);
		boostWeight = newWeight;
		for(Type e: signal.getEvents()) {
			if(Math.round(runEvent(e)) != 1) {
				e.setBoostWeight(e.getBoostWeight()*Math.pow(Math.E, newWeight));
			}
		}
		for(Type e: background.getEvents()) {
			if(Math.round(runEvent(e)) != 0) {
				e.setBoostWeight(e.getBoostWeight()*Math.pow(Math.E, newWeight));
			}
		}
		double newTotalWeight = 0;
		for(Type e: signal.getEvents()) {
			newTotalWeight += e.getBoostWeight();
		}
		for(Type e: background.getEvents()) {
			newTotalWeight += e.getBoostWeight();
		}
		for(Type e: signal.getEvents()) {
			e.setBoostWeight(e.getBoostWeight()/newTotalWeight);
		}
		for(Type e: background.getEvents()) {
			e.setBoostWeight(e.getBoostWeight()/newTotalWeight);
		}
	}
	
	public double getBoostWeight() {
		return boostWeight;
	}
}
