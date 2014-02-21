package wavefancy.ratioExpectation;

import akka.actor.UntypedActor;

public class AkkWorker extends UntypedActor{

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof int[]) {
			int[] d_a = (int[]) message;
			CalculatorV5 c = new CalculatorV5();
			
			double[] results = new double[2];
			results[0] = d_a[0];
			results[1] = c.getExpectation(d_a[0], d_a[1]);
			
			getSender().tell(results, getSelf());
		}
	}

}
