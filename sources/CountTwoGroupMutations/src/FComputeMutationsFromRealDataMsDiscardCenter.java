package wavefancy.TwoGroupMutations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

/**
 * Compute unstandardized ratio from real data.
 * 0 were set as the minimum value 1.
 * 
 * @author wavefancy@gmail.com
 *
 * @version 3.0
 * Output raw data, do not scaled.
 * Multiple thread works at SNP level.
 */
public class FComputeMutationsFromRealDataMsDiscardCenter {
	
//	static boolean out_alpha = false;
	static boolean out_results = true;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		LinkedList<String> aList = new LinkedList<String>();
		
//		for (int i = 0; i < args.length; i++) { //check parameters setting.
////			if (args[i].equalsIgnoreCase("-f")) {
////				ScaleAlpha.setAlphaByFile(new File(args[i+1]));
////				i=i+1;
////			}else if (args[i].equalsIgnoreCase("-a")) {
////				out_alpha = true;
////			}else 
//			if (args[i].equalsIgnoreCase("-x")) {
//				out_results = false;
//			}else {
//				aList.add(args[i]);
//			}
//		}
		
		
		if (args.length != 2) {
			help();
		}
		
//		args = aList.toArray(new String[0]);
		
		Parameters.setNUM_THREADS(Integer.parseInt(args[0]));
		Parameters.FLANKING_SIZE = Integer.parseInt(args[1]);
//		Parameters.setDiscardWinSize(Integer.parseInt(args[2]));
		Parameters.setDiscardWinSize(0);
		
		new FComputeMutationsFromRealDataMsDiscardCenter().runApp();
	}
	
	public static void help() {
		System.out.println("--------------------------------");
		System.out.println("    CountTwoGroupMutationsFromMsData    version: V1.0     Author:wavefancy@gmail.com");
		System.out.println("--------------------------------");
		System.out.println("Usages: \nRead ms format file directly from std., and output results to stdout.");
		System.out.println("Parameter1(int): Number of threads used for computing.");
		System.out.println("Parameter2(int): Number of SNPs for flanking size.");
//		System.out.println("Parameter3(int): Number of SNPs for discarding at the center(even int).");
//		System.out.println("Parameter[-f, followed by file name]: set the alpha scale file, otherwise this parameter will be estimated by data.");
//		System.out.println("Parameter[-a]: output alpha ratio to stderr.");
		System.out.println("Notes:\n1. Input file can contain multiple ms datasets. Output will be separated by title.");
		System.out.println("2. Multiple threads work at SNP level.");
		System.out.println("3. Frequency represents derived allele frequency in the output file.");
		System.out.println("4. Output File Title: #POS\tDE_FRE\tDerivedCount\tAncestralCount\tSTATES");
		System.out.println("--------------------------------");
		System.exit(0);
	}
	
	public void runApp() {
//		this.NumberOfThreads = NumberOfThreads;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String s = "";
		
		//Create an Akka System
		ActorSystem system = ActorSystem.create("MSComputer");
		
		ActorRef master = system.actorOf(new Props(new UntypedActorFactory() {
			private static final long serialVersionUID = 1897933432430L;

			@Override
			public UntypedActor create() throws Exception {
				return new FWorkMaster(Parameters.NUM_THREADS);
			}
		}), "master");
		

		try {
			//read ratio expectation.
//			BufferedReader expectationReader = new BufferedReader(new FileReader(expectRatioFile));
//			LinkedList<String> e_List = new LinkedList<String>();
//			expectationReader.readLine(); //skip title.
//			while ((s = expectationReader.readLine()) != null) {
//				e_List.addLast(s.trim());
//			}
//			
//			ratio = new double[e_List.size()]; //from 2 to n-1.
//			{
//				int i = 0;
//				for (String string : e_List) {
//					ratio[i++] = Double.parseDouble(string.split("\\s+")[1]);
//				}
//			}
//			expectationReader.close();
			
			
			boolean flag = false;
			int line = 0;
			
			
			LinkedList<FDataSetResults> resultList = new LinkedList<FDataSetResults>();
			
			while ((s=reader.readLine()) != null) {
				if (s.startsWith("//")) {
					//skip first line for segregating sites
					reader.readLine();
//					reader.readLine();
					
					String[] seq = null;
					if (flag == false) { //check number of sequences at the first time.
						LinkedList<String> seqList = new LinkedList<String>();
						while ((s = reader.readLine()) != null && s.length() > 0) { //read seq.
							seqList.add(s);
						}
						
						seq = new String[seqList.size()];
						int i = 0;
						for (String string : seqList) {
							seq[i] = string;
							i++;
						}
						
						flag = true;
						line = seqList.size();
						
						//set number of haplotypes
//						ScaleAlpha.setNum_SEQ(line-1);
						Parameters.setNum_SEQ(line-1);
						
					}else {
						seq = new String[line];
						for (int i = 0; i < seq.length; i++) {
							seq[i] = reader.readLine();
						}
					}
					
					resultList.add(ReduceData.Reduce(seq, master));
				}
			}
			
			
			while (!FWorkMaster.terminat) {
				try {
					Thread.sleep(Parameters.WAITING_TIME_PERIOD);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			master.tell("SHUT_DWON_SYSTEM");

			
//			ScaleAlpha.computeFinalAlpha();
//			if (out_alpha) {
//				System.err.println("Derived\tRatio");
//				for (int i = 2; i < ScaleAlpha.alpha.length; i++) {
//					System.err.println(i+"\t"+ScaleAlpha.alpha[i]);
//				}
//			}
			
			if (out_results) {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
				for (FDataSetResults fDataSetResults : resultList) {
					writer.write(fDataSetResults.toString());
				}
				writer.flush();
				writer.close();
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
