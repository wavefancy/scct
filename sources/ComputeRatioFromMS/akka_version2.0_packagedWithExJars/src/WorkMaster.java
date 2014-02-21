package wavefancy.simulation.akka;

import java.text.DecimalFormat;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

public class WorkMaster extends UntypedActor{
	
	int NBofWorker = 0;
	final ActorRef workerRouter;
//	final ActorRef listener;
	volatile static int NBofWorkTask = 0;
	volatile static int NBofResults  = 0;
	boolean terminat = false;
	
//	LinkedList<ResultsV2> resultList = new LinkedList<ResultsV2>();
	
	public WorkMaster(int NumberOfWorkers) {
		this.NBofWorker = NumberOfWorkers;
//		this.listener = listener;
		
		this.workerRouter = this.getContext().actorOf(new Props(ComputeWorker.class).withRouter(
				new RoundRobinRouter(this.NBofWorker)), "workerRouter");
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof char[][]) {
			WorkMaster.NBofWorkTask ++;
			char[][] seq = (char[][]) message;
			workerRouter.tell(seq, getSelf());
			
//		}else if (message instanceof ResultsV2) {
//			resultList.addLast((ResultsV2) message);
//			NBofReults++;
////			System.err.println(NBofReults);
//			
//			if(terminat && (NBofReults==NBofWorkTask)){
//				this.OutputAndShutDownSystem();
//			};
			
		}else if (message instanceof String) {
			String msg = (String) message;
			if (msg.equalsIgnoreCase("end")) {
				terminat = true;
				
				if (NBofResults == NBofWorkTask) {
					this.OutputAndShutDownSystem();
				}
				
			}else if (msg.equalsIgnoreCase("Done_One")) {
				NBofResults++;
				if (terminat && NBofResults == NBofWorkTask) {
					this.OutputAndShutDownSystem();
				}
			}
		}
		
	}
	
	/**
	 * Number of workers at waiting.
	 * @return
	 */
	public synchronized static int waitingTaskNum() {
		return NBofWorkTask - NBofResults;
	}
	
	private void OutputAndShutDownSystem() {
//		System.out.println("Done: " + NBofReults);
		//get all the results, Compute and output.
		if (ResultsV3.mutations == null) {
			System.exit(0);
		}
		
		System.out.println("deFre\tRatio\tCount");
		DecimalFormat formater = new DecimalFormat("#.##########");
		for (int i = 2; i < ResultsV3.mutations.length-2 ; i++) {
			System.out.println(formater.format((i+1)*1.0/ResultsV3.mutations.length) + "\t"
					+ formater.format(ResultsV3.mutations[i][0]*1.0/ResultsV3.mutations[i][1]) + "\t"
					+ ResultsV3.mutations[i][2]
			);
		}
		
//		for (int i = 0 ; i< resultList.getFirst().getAncestralMutations().size(); i++) {
//			LinkedList<Integer> de = new LinkedList<Integer>();
//			LinkedList<Integer> an = new LinkedList<Integer>();
//			for (ResultsV2 r : resultList) {
//				de.addAll(r.getDerivedMutations().get(i));
//				an.addAll(r.getAncestralMutations().get(i));
//			}
//			
//			double[] de_arr = new double[de.size()];
//			double[] an_arr = new double[an.size()];
//			{
//				int j=0;
//				for (Integer in : de) {
//					de_arr[j++] = in;
//				}
//				
//				j=0;
//				for (Integer integer : an) {
//					an_arr[j++] = integer;
//				}
//			}
//			
////			System.err.println("total counted tree ["+i+"]: "+de.size());
//			
////			System.err.println(i+":"+Arrays.toString(arr));
//			
//			double m = new Mean().evaluate(de_arr)/new Mean().evaluate(an_arr);
////			double v = new Variance().evaluate(de_arr)/new Variance().evaluate(an_arr);
//			
//			System.out.print(i+2);
//			System.out.print("\t"+de_arr.length);
//			System.out.println("\t"+m);
////			System.out.println("\t"+v);
//			
//			
//		}
		
		//Stop this actor and all its supervised children
		getContext().stop(getSelf());
		//Stop system
		getContext().system().shutdown();
	}
}
