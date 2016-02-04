package decisionTreeHomework;

public class DecisionTree<Type extends Event> {

	private Leaf<Type> headnode = new Leaf<>();
	
	public void train(Data<Type> signal, Data<Type> background) {
		headnode.train(signal, background);
	}
	
	public double runEvent(Type event) {
		Leaf<Type> leaf = headnode;
		
		while (!leaf.isFinal()) {
			leaf = leaf.runEvent(event);
		}
		
		return leaf.getPurity();
	}
	
	public Leaf<Type> getHeadnode() {
		return headnode;
	}
}
