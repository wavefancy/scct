package wavefancy.TwoGroupMutations;


import gnu.trove.list.array.TIntArrayList;

import akka.actor.UntypedActor;

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
 * @version 2.0
 * @since 2013 - 8 - 27
 * Discard a fraction of the most center part of SNPs, in order to reduce the the 
 * impaction of selection on the number of variants sites.
 *
 */
public class FComputeWorker extends UntypedActor{
	
	//95% confidence interval, 1.96;
	
//	static double ALPHA = 0.6744898; // 50% confidence interval.
	

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof WorkData) {
			WorkData workData = (WorkData) message;
			
			getSender().tell(compute(workData), getSelf());
//			System.err.println("DONE!!!!");
		}else {
			unhandled(message);
		}
	}
	
	
	public static String compute(WorkData workData) {
		
//		System.err.println("compute");
		
		int i = workData.run_index;
		int left = i - Parameters.FLANKING_SIZE;
		if (left < 0) {
			workData.dataSetResults.states[i] = "L_END";
			left=0;
		}
		
		int right = i + Parameters.FLANKING_SIZE;
		if (right >= workData.SNPS[0].length) {
			workData.dataSetResults.states[i] = "R_END";
			right = workData.SNPS[0].length - 1; 
		}
		
		
		TIntArrayList an_index = new TIntArrayList(workData.SNPS.length);
		TIntArrayList de_index = new TIntArrayList(workData.SNPS.length);
		
		for (int row = 0; row < workData.SNPS.length; row++) {
			if (workData.SNPS[row][i] == '0') {
				an_index.add(row);
			}else {
				de_index.add(row);
			}
		}
		
		workData.dataSetResults.d_num[i] = de_index.size();
//		System.err.println(workData.dataSetResults.d_num[i]);
		
		if (de_index.size() < Parameters.SKIP_DOWN || 
				de_index.size() > Parameters.SKIP_UP) {
				workData.dataSetResults.results[i][0] = -2000;
				workData.dataSetResults.states[i] = "MAF";
//				System.err.println("here");
			}else {
				int[] an_arr = an_index.toArray();
				int[] de_arr = de_index.toArray();
				
//				System.err.println("----------------------------: " + i);
				int[] left_m = mutationsTwoGroup(workData.SNPS, left, i-Parameters.DiscardWinHalfNum, i, an_arr, de_arr);
				int[] right_m = mutationsTwoGroup(workData.SNPS, i+Parameters.DiscardWinHalfNum, right, i, an_arr, de_arr);
				
//				System.err.println("----------------------------: " + i);
//				System.err.println(Arrays.toString(left_m));
//				System.err.println(Arrays.toString(right_m));
				
				
				int derived = left_m[0] + right_m[0];
				int ancestral = left_m[1] + right_m[1];
				
//				System.err.println(derived + "<---->" + ancestral);
				
				if (derived == 0) {
					derived = 1;
					workData.dataSetResults.states[i] = "DZERO";
				}
				
				if (ancestral ==0) {
					ancestral = 1;
					if (workData.dataSetResults.states[i].equalsIgnoreCase("DZERO")) {
						workData.dataSetResults.states[i] = "DAZERO";
					}else {
						workData.dataSetResults.states[i] = "AZERO";
					}
				}
				
				
//				workData.dataSetResults.results[i] = derived/(double)ancestral;
				
//				System.err.println(workData.dataSetResults.results);
				
//				if (ScaleAlpha.UPDATE) {
////					System.err.println("update: "+ de_arr.length + " " + workData.dataSetResults.results[i]);
//					ScaleAlpha.updateTempAlpha(de_arr.length, workData.dataSetResults.results[i]);
//				}
				
				workData.dataSetResults.results[i][0] = derived;
				workData.dataSetResults.results[i][1] = ancestral;
				
//				d_num[i] = derived;
//				a_num[i] = ancestral;
				
//				results[i] = Math.log(derived/FComputeMutationsFromRealDataMsDiscardCenter.ratio[de_arr.length-2]/ancestral);
			}
//		System.err.println("IN: "+Arrays.toString(workData.dataSetResults.results));
		return "DONE_SNP";	
	}
	
//	public static FDataSetResults compute(String[] seq) {
//
//		
//		//compute results.
//		for (int i = 0; i < snps[0].length; i++) {
//			int left = i - FComputeMutationsFromRealDataMsDiscardCenter.flankingSNP_NUM;
//			if (left < 0) {
//				states[i] = "L_END";
//				left=0;
//			}
//			
//			int right = i + FComputeMutationsFromRealDataMsDiscardCenter.flankingSNP_NUM;
//			if (right >= snps[0].length) {
//				states[i] = "R_END";
//				right = snps[0].length - 1; 
//			}
//			
//			//divided groups
////			LinkedList<Integer> an_index = new LinkedList<Integer>();
////			LinkedList<Integer> de_index = new LinkedList<Integer>();
//			TIntArrayList an_index = new TIntArrayList(snps.length);
//			TIntArrayList de_index = new TIntArrayList(snps.length);
//			
//			for (int row = 0; row < snps.length; row++) {
//				if (snps[row][i] == '0') {
//					an_index.add(row);
//				}else {
//					de_index.add(row);
//				}
//			}
//			
//			derived_fre[i] = de_index.size()/(double) snps.length;
//			
//			if (derived_fre[i] < FComputeMutationsFromRealDataMsDiscardCenter.SKIP_MAF || 
//				derived_fre[i] > 1- FComputeMutationsFromRealDataMsDiscardCenter.SKIP_MAF) {
//				results[i] = -2000;
//				states[i] = "MAF";
//			}else {
//				int[] an_arr = an_index.toArray();
//				int[] de_arr = de_index.toArray();
//				
//				
//				int[] left_m = mutationsTwoGruop(snps, left, i, i, an_arr, de_arr);
//				int[] right_m = mutationsTwoGruop(snps, i, right, i, an_arr, de_arr);
//
//				int derived = left_m[0] + right_m[0];
//				int ancestral = left_m[1] + right_m[1];
//				
////				System.err.println(derived + "<---->" + ancestral);
//				
//				if (derived == 0) {
//					derived = 1;
//					states[i] = "DZERO";
//				}
//				
//				if (ancestral ==0) {
//					ancestral = 1;
//					if (states[i].equalsIgnoreCase("DZERO")) {
//						states[i] = "DAZERO";
//					}else {
//						states[i] = "AZERO";
//					}
//				}
//				
//				
//				d_num[i] = derived;
//				a_num[i] = ancestral;
//				
//				results[i] = Math.log(derived/FComputeMutationsFromRealDataMsDiscardCenter.ratio[de_arr.length-2]/ancestral);
//			}
//		}
//		
//		FDataSetResults lr = new FDataSetResults(positions, states, results, derived_fre);
//		lr.a_num = a_num;
//		lr.d_num = d_num;
//		
//		return lr;
//	}
	
//	/**
//	 * Compute mutations for a group of haplotypes.
//	 * @param snps char[][]
//	 * @param from inclusive
//	 * @param to	inclusive
//	 * @param index_arr	haplotype index arrray
//	 * @return
//	 */
//	private static int mutations(char[][] snps, int from, int to, int[] index_arr) {
//		int nb_mutations = 0;
//		for (int i = from; i <= to; i++) {
//			char s = snps[index_arr[0]][i];
//			for (int row = 1; row < index_arr.length; row++) {
//				if (s != snps[index_arr[row]][i]) {
//					nb_mutations++;
//					break;
//				}
//			}
//		}
//		
//		return nb_mutations;
//	}
	
	/**
	 * Compute the number of mutations in ancestral and derived allele group.
	 * @param snps
	 * @param from
	 * @param to
	 * @param core
	 * @param an_index
	 * @param de_index
	 * @return
	 */
	private static int[] mutationsTwoGroup(char[][] snps, int from, int to, int core, int[] an_index, int[] de_index) {
		int[] results = new int[2]; // Segregating for, derived, ancestral segregating size.
		
		from = (from ==core ? from+1: from);//right
		to	 = (to == core ? to-1: to); //left
//		System.err.println("from:"+from + "-> to: "+to);
//		System.err.print("A:"+Arrays.toString(an_index));
//		System.err.print("----");
//		System.err.print("D:"+Arrays.toString(de_index));
//		System.err.print("----");
		
		for (int c = from; c <= to; c++) {
			int f_dm =0, f_am = 0;
			
			//count for a--1;
			for (int i : an_index) {
				if (snps[i][c] == '1') {// a--1;
					f_am++;
				}
			}
			//count for d--1;
			for (int i : de_index) {
				if (snps[i][c] == '1') { // d--1;
					f_dm++;
				}
			}
			
			
//			System.err.println("f_am:" + f_am);
//			System.err.println("f_dm:" + f_dm);
			if (f_am==0 && f_dm != 0) { //mutation in derived allele group.
				if (f_dm < de_index.length) { //Segregating site.
					results[0]++;
				}else { // mutation number equals the number of sequences in derived allele group. 
						// mutation must happened in ancestral group.
					results[1]++;
				}
			}else if (f_am !=0 && f_dm==0) { //Mutation in ancestral group.
					results[1]++;
			}else { // mutation in both ancestral and derived group. Check linkage.
				
				//Using odds ratio determine linkage. Yate correction.
				double f11 = f_dm + 0.5;
				double f10 = de_index.length - f_dm + 0.5;
				double f01 = f_am + 0.5;
				double f00 = an_index.length - f_am + 0.5;
				
				//95% confidence interval.
				double LOR = Math.log(f11) + Math.log(f00) - Math.log(f10) - Math.log(f01);
//				double ci = FComputeMutationsFromRealDataMsDiscardCenter.ALPHA * Math.sqrt( 1/f11+1/f10+1/f01+1/f00);
				
//				System.err.println("LOR: "+LOR);
//				System.err.println("CI: "+ci);
				
//				double L = LOR - ci;
//				double R = LOR + ci;
				
//				System.err.println(LOR);
				
				if (LOR > 0) { // mutation with great linkage with derived allele.
					if (f_dm+f_am >= de_index.length) { // two mutations happened at the same ancestral branch.
						
						results[1]++;
					}else {
						results[0]++;
					}
					
				}else if (LOR < 0) { //linkage with ancestral allele.
					results[1]++;
				}
				
//				else {// R > 0, L < 0;
//					
//					if (R > -L) { //more likely linkage with derived allele.
//						if (f_am+f_dm >= de_index.length) {
////							System.err.println("here!");
//							results[1]++;
//						}else {
//							results[0]++;
//						}
//					}else {
//						results[1]++;
//					}
//				}
				
				//else 95% confidence interval will coverage 0.
//				if (L*R <= 0) {
//					System.err.println("RareLinkage");
//				}
			}
		}	
		
//		System.err.println(Arrays.toString(results));
		
		return results;
	}
}
