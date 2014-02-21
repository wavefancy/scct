package wavefancy.RatioCaculation.akka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import jstats.Mean;
import jstats.Variance;

/**
 * This class is used to compute the standardize file for iHS.
 * @author icorner
 *
 */
public class StandardizeFileGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			help();
		}
		StandardizeFileGenerator s = new StandardizeFileGenerator();
		
		s.runAPP(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Double.parseDouble(args[2]));
	}
	
	public static void help() {
		System.out.println("--------------------------------");
		System.out.println("    StandardizeFileGenerator    version: 1.0     Author:wavefancy@gmail.com");
		System.out.println("--------------------------------");
		System.out.println("Usages: \nparameter1[int]: Column index for frequency.");
		System.out.println("parameter2[int]: Column index for value.");
		System.out.println("parameter2[double]: frequency interval.");
//		System.out.println("Specify multiplbe files as you like!");
		System.out.println("[Column index start from 1.]");
		System.out.println("--------------------------------");

		System.exit(0);
	}
	
	/**
	 * grouped iHS file, no header.
	 * @param groupediHSFile
	 */
	public void runAPP(int fre_col, int val_col, double interval) {
		fre_col--;
		val_col--;
		
		double step = interval;
		double[] end = new double[(int)(1/step)]; //interval end.
		for (int i = 0; i < end.length; i++) {
			end[i] = step * (i+1);
		}
		
		ArrayList<LinkedList<Double>> iHSlist = new ArrayList<LinkedList<Double>>(end.length);
		for (int i = 0; i < end.length; i++) {
			iHSlist.add(new LinkedList<Double>());
		}
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			String s = "";
			String[] sArr = null;
//			LinkedList<String[]> content = new LinkedList<String[]>();
//			LinkedList<Integer> category = new LinkedList<Integer>(); //store frequency category of this SNP.
			
			double freq = 0;
			double value = 0;
			while ((s = reader.readLine()) != null) {
				sArr = s.trim().split("\\s+");
//				content.addLast(sArr);
				
				//group iHS.
				try {
					freq = Double.parseDouble(sArr[fre_col]);
					value = Double.parseDouble(sArr[val_col]);
				} catch (NumberFormatException  e) {
					continue;
				}

				
				for (int i = 0; i < end.length; i++) {
					if (freq <= end[i]) {
						iHSlist.get(i).add(value);
						break;
					}
				}
			}
			
			DecimalFormat decimalFormat = new DecimalFormat("#.##########"); //10
			
//			double[] mean = new double[end.length];
//			double[] sd = new double[end.length];
			
			
			System.out.println("Fre_end\tMean\tSD.");
			//compute mean and sd. for each category.
			for (int i = 0; i < iHSlist.size(); i++) {

				double[] array = new double[iHSlist.get(i).size()];
				Iterator<Double> iterator = iHSlist.get(i).iterator();
				for (int j = 0; j < array.length; j++) {
						array[j] = iterator.next();
				}
				
//				System.err.println(iHSlist.get(i));
				
				if (array.length <=0) {
					//no data for this category.
				}else {
					System.out.print(decimalFormat.format(end[i]));
					System.out.print("\t");
					System.out.print(decimalFormat.format(Mean.arithmeticMean(array)));
					System.out.print("\t");
					System.out.println(decimalFormat.format(Variance.sampleStandardDeviation(array)));
				}
				
//				mean[i] = Mean.arithmeticMean(array);
//				sd[i] = Variance.sampleStandardDeviation(array);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
