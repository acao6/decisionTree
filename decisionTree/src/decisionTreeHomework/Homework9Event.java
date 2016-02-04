package decisionTreeHomework;

import java.util.ArrayList;
import java.util.List;

public class Homework9Event extends Event {

	private static final long serialVersionUID = 2894618901475372580L;

	private static final int nVars = 8;
	
	private double mlnub;
	private double transverseMass;
	private double jet1ET;
	private double leptonpT;
	private double hT;
	private double mET;
	private double qEta;
	private double cosTheta;
	
	public Homework9Event(double mlnub, double transverseMass, double jet1ET, double leptonpT, double hT, double mET, double qEta,
			double cosTheta) {
		super(nVars, eventNames());
		this.mlnub = mlnub;
		this.transverseMass = transverseMass;
		this.jet1ET = jet1ET;
		this.leptonpT = leptonpT;
		this.hT = hT;
		this.mET = mET;
		this.qEta = qEta;
		this.cosTheta = cosTheta;
	}
	
	static private List<String> eventNames() {
		List<String> response = new ArrayList<>();
		response.add("Reconstructed top mass");
		response.add("W transverse mass");
		response.add("Leading jet transverse energy");
		response.add("Lepton transverse momentum");
		response.add("HT");
		response.add("Missing transverse energy");
		response.add("Q x eta of the second jet");
		response.add("cos of lepton and untagged jet");
		return response;
	}

	@Override
	public double[] getVars() {
		double[] response = new double[nVars];
		response[0] = mlnub;
		response[1] = transverseMass;
		response[2] = jet1ET;
		response[3] = leptonpT;
		response[4] = hT;
		response[5] = mET;
		response[6] = qEta;
		response[7] = cosTheta;
		return response;
	}

}
