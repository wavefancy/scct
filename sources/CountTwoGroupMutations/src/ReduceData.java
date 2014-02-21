package wavefancy.TwoGroupMutations;


import java.util.Arrays;

import akka.actor.ActorRef;

/**
 * Compute results for a window.
 * In order to avoid 0 for numerator and denominator, 
 * 0 were set as 1.
 * 
 * Odds ratio was used to determine the original linkage between a novel mutation
 * and the allele of the core SNP.
 * 
 * @author wavefancy@gmail.com
 *
 */
public class ReduceData {
	
	//95% confidence interval, 1.96;
	
//	static double ALPHA = 0.6744898; // 50% confidence interval.
	
	public static FDataSetResults Reduce(String[] seq, ActorRef master) {
//		System.err.println(FComputeMutationsFromRealDataMsDiscardCenter.ALPHA);
		
		String[] positions = seq[0].split("\\s+");
		positions = Arrays.copyOfRange(positions, 1, positions.length); //positions.
		
		String[] states = new String[positions.length];
		Arrays.fill(states, "OK");//initialize.
//		double[] results= new double[positions.length];		
		int[][] results = new int[positions.length][2];
		int[] d_num = new int[positions.length];
//		int[] a_num = new int[positions.length];
		               
		
//		int[] m_derived = new int[positions.length];
//		int[] m_ancestral = new int[positions.length];
		
		char[][] snps = new char[seq.length-1][];
		for (int i = 1; i < seq.length; i++) {
			snps[i-1] = seq[i].toCharArray();
		}
		
		FDataSetResults fDataSetResults = new FDataSetResults(positions, states, results, d_num, snps.length);
		
		//compute results.
		for (int i = 0; i < snps[0].length; i++) {	
			WorkData w = new WorkData(i, fDataSetResults, snps);
			
			FWorkMaster.queueSize.increaseOne();
//			System.err.println(FWorkMaster.queueSize.getQueueSize());
			
			if (FWorkMaster.queueSize.getQueueSize() >= Parameters.MAX_WAITING_NUM) {
//				System.err.println("SELF:"+FWorkMaster.queueSize.getQueueSize());
				
				FComputeWorker.compute(w);
//				FWorkMaster.WAITING_NUM--;
				FWorkMaster.queueSize.decreaseOne();
				
			}else {
				master.tell(w);
			}
			
//			System.err.println("IN queue:" + FWorkMaster.queueSize.getQueueSize());
		}
		
//		while (FWorkMaster.queueSize.getQueueSize() != 0) {
//			System.err.println("waiting:" + FWorkMaster.queueSize.getQueueSize());
////			System.err.println("END"+Arrays.toString(fDataSetResults.results));
//			try {
//				Thread.sleep(Parameters.WAITING_TIME_PERIOD); // Holding waiting for ending.
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
//		System.err.println("END"+Arrays.toString(fDataSetResults.results));
		
//		System.err.println("FWorkMaster.WAITING_NUM: " + FWorkMaster.WAITING_NUM);
//		System.out.println(fDataSetResults.toString());
		return fDataSetResults;
	}
}
