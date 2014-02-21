package wavefancy.simulation.akka;

/**
 * temporarily contain the results.
 * @author wavefancy@gmail.com
 *
 */
public class ResultsV3 {
	
	//array index for derived allele count, 
	//row: [mutations in derived group, mutations in ancestral group, occurrence count].
	static long[][] mutations = null; 
	
	public ResultsV3(int seqNum) {
		mutations = new long[seqNum][3];
	}
	
	synchronized static void updateMutations(int deCount, int deMutNum, int anNutNum){
		mutations[deCount][0] += deMutNum;
		mutations[deCount][1] += anNutNum;
		mutations[deCount][2] += 1;
	}
}
