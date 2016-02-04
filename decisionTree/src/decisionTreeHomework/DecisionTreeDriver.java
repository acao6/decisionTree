package decisionTreeHomework;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class DecisionTreeDriver {
	
	public static void main(String[] args) throws FileNotFoundException {
		//level1();
		//level2();
		//level3();
		//challenge();
	}
	
	public static void level1() throws FileNotFoundException {
		PrintStream out = new PrintStream(new FileOutputStream("C:\\Users\\student\\Documents\\Computational Science\\decisionTreeLevel1.xls"));
		IOHelper<Data<Homework9Event>> myReader = new IOHelper<>();
		Data<Homework9Event> backgroundData = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\background.dat");
		Data<Homework9Event> signalData = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\signal.dat");
		Data<Homework9Event> data = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\decisionTreeData.dat");
		for(Event e: backgroundData.getEvents()) {
			e.setBoostWeight(1);
		}
		for(Event e: signalData.getEvents()) {
			e.setBoostWeight(1);
		}
		for(Event e: data.getEvents()) {
			e.setBoostWeight(1);
		}
		Leaf<Homework9Event> leaf = new Leaf<>();
		leaf.train(signalData, backgroundData);
		for(int i = 0; i < data.getEvents().size(); i++) {
			if(leaf.runEvent(data.getEvents().get(i)) == leaf.getLeftBranch()) {
				out.println(i + "\t" + Math.round(leaf.getLeftBranch().getPurity()));
			}
			else out.println(i + "\t" + Math.round(leaf.getRightBranch().getPurity()));
		}
		out.close();
	}
	
	public static void level2() throws FileNotFoundException {
		PrintStream out = new PrintStream(new FileOutputStream("C:\\Users\\student\\Documents\\Computational Science\\decisionTreeLevel2.xls"));
		IOHelper<Data<Homework9Event>> myReader = new IOHelper<>();
		Data<Homework9Event> backgroundData = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\background.dat");
		Data<Homework9Event> signalData = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\signal.dat");
		Data<Homework9Event> data = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\decisionTreeData.dat");
		for(Event e: backgroundData.getEvents()) {
			e.setBoostWeight(1);
		}
		for(Event e: signalData.getEvents()) {
			e.setBoostWeight(1);
		}
		for(Event e: data.getEvents()) {
			e.setBoostWeight(1);
		}
		DecisionTree<Homework9Event> tree = new DecisionTree<>();
		tree.train(signalData, backgroundData);
		for(int i = 0; i < data.getEvents().size(); i++) {
			out.println(i + "\t" + tree.runEvent(data.getEvents().get(i)));
		}
		out.close();
	}
	public static void level3() throws FileNotFoundException {
		PrintStream out = new PrintStream(new FileOutputStream("C:\\Users\\student\\Documents\\Computational Science\\decisionTreeLevel3.xls"));
		IOHelper<Data<Homework9Event>> myReader = new IOHelper<>();
		Data<Homework9Event> backgroundData = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\background.dat");
		Data<Homework9Event> signalData = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\signal.dat");
		Data<Homework9Event> data = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\decisionTreeData.dat");
		for(Event e: backgroundData.getEvents()) {
			e.setBoostWeight(1);
		}
		for(Event e: signalData.getEvents()) {
			e.setBoostWeight(1);
		}
		for(Event e: data.getEvents()) {
			e.setBoostWeight(1);
		}
		
		ArrayList<BoostedDecisionTree<Homework9Event>> forest = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			BoostedDecisionTree<Homework9Event> tree = new BoostedDecisionTree<>();
			tree.train(signalData, backgroundData);
			tree.changeBoostWeight(signalData, backgroundData);
			forest.add(tree);
		}
		double max = 0;
		for(int i = 0; i < data.getEvents().size(); i++) {
			double eventClassification = 0;
			for(BoostedDecisionTree<Homework9Event> t: forest) {
				eventClassification += t.getBoostWeight()*t.runEvent(data.getEvents().get(i));
			}
			if(eventClassification > max) {
				max = eventClassification;
			}
		}
		for(int i = 0; i < data.getEvents().size(); i++) {
			double eventClassification = 0;
			for(BoostedDecisionTree<Homework9Event> t: forest) {
				eventClassification += t.getBoostWeight()*t.runEvent(data.getEvents().get(i))/max;
			}
			out.println(i + "\t" + eventClassification);
		}
		out.close();
	}
	
	public static void challenge() throws FileNotFoundException {
		PrintStream out = new PrintStream(new FileOutputStream("C:\\Users\\student\\Documents\\Computational Science\\decisionTreeChallenge.xls"));
		IOHelper<Data<Homework9Event>> myReader = new IOHelper<>();
		Data<Homework9Event> backgroundData = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\background.dat");
		Data<Homework9Event> signalData = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\signal.dat");
		Data<Homework9Event> data = myReader.read("C:\\Users\\student\\workspace\\Computational Science\\decisionTreeHomework\\decisionTreeData.dat");
		Neuron neuron = new Neuron(8);
		for(Event e: signalData.getEvents()) {
			neuron.train(e.getVars(), 1);
		}
		for(Event e: backgroundData.getEvents()) {
			neuron.train(e.getVars(), -1);
		}
		for(Event e: signalData.getEvents()) {
			neuron.train(e.getVars(), 1);
		}
		/*for(Event e: backgroundData.getEvents()) {
			neuron.train(e.getVars(), -1);
		}*/
		for(int i = 0; i < data.getEvents().size(); i++) {
			double[] input = data.getEvents().get(i).getVars();
			out.println(i + "\t" + neuron.feedForward(input) + "\t" + neuron.activate(neuron.feedForward(input)));
		}
		out.close();
	}
}
