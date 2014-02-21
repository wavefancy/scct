package wavefancy.simulation.akka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

/**
 * 
 * Compute ratio from MS output directly, assume  no recombination in MS simulation.
 * 
 * @author wavefancy@gmail.com
 * @version 1.0
 * 
 * @version 2.0
 * @since 2013-11-13
 * 1. Change output format.
 * 2. Reduce memory using.
 */
public class ComputeRatioFromMS_Simu_AKKA {
	
//	final int NumberOfThreads;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			help();
		}
		
		new ComputeRatioFromMS_Simu_AKKA().runApp(Integer.parseInt(args[0]));
	}
	
	public static void help() {
		System.out.println("--------------------------------");
		System.out.println("    ComputeRatioFromMS.Simu.AKKA    version: V2.0     Author:wavefancy@gmail.com");
		System.out.println("--------------------------------");
		System.out.println("Usages: \nRead ms output directly from std.");
		System.out.println("Parameter1(int): Number of threads used for computing.");
		System.out.println("[-h|--h]: Output this help");
//		System.out.println("Specify multiplbe files as you like!");
		System.out.println("--------------------------------");

		System.exit(0);
	}
	
	public void runApp(final int NumberOfThreads) {
//		this.NumberOfThreads = NumberOfThreads;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String s = "";
		
		//Create an Akka System
		ActorSystem system = ActorSystem.create("MSComputer");
		
		ActorRef master = system.actorOf(new Props(new UntypedActorFactory() {
			private static final long serialVersionUID = 1L;

			@Override
			public UntypedActor create() throws Exception {
				return new WorkMaster(NumberOfThreads);
			}
		}), "master");
		

		try {
			boolean flag = false;
			int line = 0;
			
			while ((s=reader.readLine()) != null) {
				if (s.startsWith("//")) {
					//skip two lines for segregating sites and positions.
					reader.readLine();
					reader.readLine();
					
					char[][] seq = null;
					if (flag == false) { //check number of sequences at the first time.
						LinkedList<String> seqList = new LinkedList<String>();
						while ((s = reader.readLine()) != null && s.trim().length() > 0) { //read seq.
							seqList.add(s);
						}
						
						seq = new char[seqList.size()][seqList.getFirst().length()];
						int i = 0;
						for (String string : seqList) {
							seq[i] = string.toCharArray();
							i++;
						}
						
						flag = true;
						line = seqList.size();
						
						//initialize results cache.
						new ResultsV3(line);
						
					}else {
						seq = new char[line][];
						for (int i = 0; i < seq.length; i++) {
							seq[i] = reader.readLine().toCharArray();
						}
					}
					
					//add for version2.0, if lots of works are waiting, this thread do a computing.
					//this could reduce memory using.
					if (WorkMaster.waitingTaskNum() > 3*NumberOfThreads) {
						ComputeWorker.compute(seq);
					}else {
						master.tell(seq);
					}
				}
			}
			
			master.tell("end");
			
		} catch (IOException e) {
			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
		}
	}

}
