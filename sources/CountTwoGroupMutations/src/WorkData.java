package wavefancy.TwoGroupMutations;

public class WorkData {
	FDataSetResults dataSetResults;
	char[][] SNPS;
	
	int run_index = 0;
	
	public WorkData(int run_index, FDataSetResults dataSetResults, char[][] SNPS) {
		this.run_index = run_index;
		this.dataSetResults = dataSetResults;
		this.SNPS = SNPS;
	}
}
