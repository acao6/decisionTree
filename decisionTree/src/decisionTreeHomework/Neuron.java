package decisionTreeHomework;

import java.util.Random;

public class Neuron {
	
	private double[] weights;
	private double bias;
	private final double learningConstant = 1e-5;
	
	public Neuron(int size) {
		Random random = new Random();
		weights = new double[size];
		for(int i = 0; i < size; i++) {
			weights[i] = 2*(random.nextDouble() - 0.5);
		}
		bias = 2*(random.nextDouble() - 0.5);
	}
	
	public double feedForward(double[] inputs) {
		double sum = 0;
		for(int i = 0; i < weights.length; i++) {
			sum += inputs[i]*weights[i];
		}
		sum += bias;
		return sum;
	}
	
	public int activate(double number) {
		if(number < 0) {
			return -1;
		}
		else return 1;
	}
	
	public void train(double[] inputs, double signal) {
		 double guess = feedForward(inputs);
		 double error = signal - guess;
		 System.out.println(error);
		 for (int i = 0; i < weights.length; i++) {
			 weights[i] += learningConstant*error*inputs[i];
		 }
		 bias += learningConstant*error;
	}
}
