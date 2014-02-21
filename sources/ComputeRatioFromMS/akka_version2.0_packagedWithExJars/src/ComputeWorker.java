package wavefancy.simulation.akka;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import akka.actor.UntypedActor;

public class ComputeWorker extends UntypedActor{

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof char[][]) {
			char[][] seq = (char[][]) message;
			getSender().tell(compute(seq), getSelf());
		}
	}
	
	public static String compute(char[][] seq) {
//		double[] results = new double[this.seq.length]; //values only from  2 ... n-1.
//		Arrays.fill(results, -1);
//		ArrayList<LinkedList<Double>> results = new ArrayList<LinkedList<Double>>(this.seq.length-2);
//		for (int i = 0; i < this.seq.length-2; i++) {
//			results.add(new LinkedList<Double>());
//		}
		
//		ResultsV2 results = new ResultsV2(seq.length);
		
		//record if this ratio have been computed
		Set<String> patternSet = new HashSet<String>();
		
		
		int total = seq[0].length;
		
//		System.err.println("----------------");
		//partition at each site, and get compute the mutation for derived allele group.
		for (int col = 0; col < seq[0].length; col++) {
			LinkedList<Integer> rows = new LinkedList<Integer>();
			for (int i = 0; i < seq.length; i++) {
				if(seq[i][col] == '1')
					rows.add(i);
			}
			
			if (rows.size() >=2 && rows.size() <= seq.length-2 &&(!patternSet.contains(rows.toString()))) { //compute segregating site for derived allele group.
				//at each branch, only computed once, record meta info here.
				patternSet.add(rows.toString());
//				System.err.println(rows.toString());
				
				Integer[] de_array = rows.toArray(new Integer[0]);
				int seg = 0;
				for (int c = 0; c < seq[0].length; c++) {
					for (int r = 1; r < de_array.length; r++) {
						if (seq[de_array[r]][c] != seq[de_array[0]][c]) { //check if mutations happen at derived side.
							seg++;
							break;
						}
					}
				}
				
//				System.err.println(rows.size() + "_" + seg+" _ "+total);
				
//				results[rows.size()-2] = seg/(double)(total -seg);
//				results.get(rows.size()-2).addLast(seg/(double)(total -seg));
//				results.getDerivedMutations().get(rows.size()-2).add(seg);
//				results.getAncestralMutations().get(rows.size()-2).add(total-seg);
				ResultsV3.updateMutations(rows.size(), seg, total-seg);
			}
		}
		
//		return results;
		return "Done_One";
	}

}
