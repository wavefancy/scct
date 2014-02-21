package wavefancy.ratioExpectation;

import java.text.DecimalFormat;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

public class AkkaWorkMaster extends UntypedActor{
	
	final int NBofTaks; //total number of tasks
	final ActorRef workRouter;
	final int NBofWorker; //number of workers
	
	double[] results;
	int NBofDoneTasks = 0;
	
	
	
	public AkkaWorkMaster(int NumberOfTasks, int NumberOfWorker) {
		this.NBofTaks = NumberOfTasks;
		this.NBofWorker = NumberOfWorker;
		results = new double[NumberOfTasks];
		
		workRouter = this.getContext().actorOf(new Props(AkkWorker.class).withRouter(
				new RoundRobinRouter(NBofWorker)), "workerRouter");
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof int[]) {
			int[] d_a = (int[]) message;
			workRouter.tell(d_a, getSelf());
		}else if (message instanceof double[]) {
			double[] m = (double[]) message;
			results[(int)m[0] - 2] = m[1];
			
			NBofDoneTasks++;
			if (NBofDoneTasks == NBofTaks) { //Done all works, output results.
				DecimalFormat formater = new DecimalFormat("#.##########");
				System.out.println("derived\tratio");
				
				for (int i = 0; i < results.length; i++) {
					//version 1.1, output as allele frequency.
					System.out.print((formater.format((i+2)*1.0/TheoreticalRatio.totalHaplotype)+"\t"));
					System.out.println(results[i]);
				}
				
				getContext().stop(getSelf());
				getContext().system().shutdown();
			}
		}
		
	}
}
