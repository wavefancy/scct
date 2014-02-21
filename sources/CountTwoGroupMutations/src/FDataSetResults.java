package wavefancy.TwoGroupMutations;

import java.text.DecimalFormat;


/**
 * Record the results for a data set.
 * 
 * @author wavefancy@gmail.com
 *
 */
public class FDataSetResults {
	
	String[] positions;
	String[] states;
	int[][] results; //derived mutation count, ancestral mutation count.
	double total_seq_Num; //total number of sequence.
	int[] d_num; //derived count
	
	DecimalFormat formater = new DecimalFormat("#.##########");
	
	/**
	 * 
	 * @param positions
	 * @param states
	 * @param results
	 * @param NUM_SEQ total number of sequences.
	 */
	public FDataSetResults(String[] positions, String[] states,int[][] results,int[] d_num, int NUM_SEQ) {
		this.positions = positions;
		this.states = states;
		this.results = results;
		
		this.total_seq_Num = NUM_SEQ;
		this.d_num = d_num;
	}
	
	@Override
	public String toString() {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("#POS\tDE_FRE\tDerivedCount\tAncestralCount\tSTATES\n");
		for (int i = 0; i < positions.length; i++) {
			sBuilder.append(positions[i]);
			
			sBuilder.append("\t" + formater.format(d_num[i]/total_seq_Num));
			
			if (results[i][0] < 0) {
				sBuilder.append("\tNA");
			}else {
//				sBuilder.append("\t"+ formater.format(Math.log(results[i]/ScaleAlphaDeleted.alpha[d_num[i]])));
				sBuilder.append("\t"+Integer.toString(results[i][0])+"\t"+Integer.toString(results[i][1]));
			}
			
			sBuilder.append("\t" + states[i]);
			
			sBuilder.append("\n");
		}
	
		return sBuilder.toString();
	}
}
