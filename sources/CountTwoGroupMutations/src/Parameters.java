package wavefancy.TwoGroupMutations;

/**
 * Global control parameters.
 * 
 * @author icorner
 *
 */
public class Parameters {
	static int FLANKING_SIZE;
	static double SKIP_MAF = 0.05;
	static double SKIP_DOWN;
	static double SKIP_UP;
	static int NUM_SEQ;
	static int NUM_THREADS = 1; //number of threads want to run in parallel.
	static int MAX_WAITING_NUM;
	static int DiscardWinHalfNum;
	
	static int WAITING_TIME_PERIOD = 1000; //waiting AKKA actor results.ms
	
	public static void setNum_SEQ(int num_seq) {
		Parameters.NUM_SEQ = num_seq;
		SKIP_DOWN = SKIP_MAF * num_seq;
		SKIP_UP = (1-SKIP_MAF) * num_seq;
	}
	
	public static void setNUM_THREADS(int num_threads) {
		NUM_THREADS = num_threads;
		
		MAX_WAITING_NUM = NUM_THREADS * 3;
	}
	
	public static void setDiscardWinSize(int winSize) {
		DiscardWinHalfNum = winSize/2+1;
		if (DiscardWinHalfNum >= FLANKING_SIZE) {
			System.err.println("Caution: Discard more than flanking SNPs. Please check.");
			System.err.println("Flanking Size: "+ FLANKING_SIZE + " Discard half win size: "+DiscardWinHalfNum);
			System.err.println("System exited!");
			System.exit(-1);
		}
	}
	
}
