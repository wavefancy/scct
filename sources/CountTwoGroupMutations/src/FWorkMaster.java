package wavefancy.TwoGroupMutations;

import java.io.IOException;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

public class FWorkMaster extends UntypedActor{
	
	int NBofWorker = 0;
	final ActorRef workerRouter;
//	final ActorRef listener;
//	volatile static int NBofWorkTask = 0;
//  volatile static int NBofReults  = 0;
	
//	volatile static int WAITING_NUM = 0;
	static QueueSize queueSize = new QueueSize();
	
	static volatile boolean terminat = false;
	
//	BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
	
//	LinkedList<ResultsV2> resultList = new LinkedList<ResultsV2>();
	
	public FWorkMaster(int NumberOfWorkers) {
		this.NBofWorker = NumberOfWorkers;
//		this.listener = listener;
		
		this.workerRouter = this.getContext().actorOf(new Props(FComputeWorker.class).withRouter(
				new RoundRobinRouter(this.NBofWorker)), "workerRouter");
	}

	@Override
	public void onReceive(Object message) throws Exception {
//		System.err.println(message);
		
		if (message instanceof WorkData) {
			
			WorkData seq = (WorkData) message;
			workerRouter.tell(seq, getSelf());
			
//			System.err.println("QUEUE SIZE: " +(NBofWorkTask-NBofReults));
			
		}else if (message instanceof String) {
//			System.err.println("back");
			
			String msg = (String) message;
//			System.err.println("back:" +msg);
			if (msg.equalsIgnoreCase("DONE_SNP")) {
				
//				WAITING_NUM--;
				queueSize.decreaseOne();
//				System.err.println("DONE_SNP:" + queueSize.getQueueSize());
				
				if (queueSize.getQueueSize() == 0) {
					terminat = true;
				}
				
			}else if (msg.equalsIgnoreCase("SHUT_DWON_SYSTEM")) {
				
				shutDownWorkingSystem();
				
			} {
				unhandled(message);
			}
			
		}else {
			unhandled(message);
		}
		
	}

	
	/**
	 * Shutdown parallel system.
	 * @throws IOException 
	 */
	private void shutDownWorkingSystem() throws IOException {
		
		//Stop this actor and all its supervised children
		getContext().stop(getSelf());
		//Stop system
		getContext().system().shutdown();
	}
	
//	/**
//	 * Waiting queue size for computing.
//	 * @return
//	 */
//	public static int queueSize() {
//		return NBofWorkTask - NBofReults;
//	}
	
}
