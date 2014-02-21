package wavefancy.RatioCaculation.akka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * This class is used to standardize value according to the parameter in standardize file.
 * @author icorner
 *
 */
public class StandardizeFromFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			help();
		}
		StandardizeFromFile s = new StandardizeFromFile();
		
		s.runAPP(Integer.parseInt(args[0]), Integer.parseInt(args[1]), new File(args[2]));
	}
	
	public static void help() {
		System.out.println("--------------------------------");
		System.out.println("    StandardizerFromFile    version: 1.0     Author:wavefancy@gmail.com");
		System.out.println("--------------------------------");
		System.out.println("Usages: \nparameter1[int]: Column index for frequency.");
		System.out.println("parameter2[int]: Column index for value.");
		System.out.println("parameter3[file]: standardize file.");
//		System.out.println("Specify multiplbe files as you like!");
		System.out.println("[Column index start from 1.]");
		System.out.println("--------------------------------");

		System.exit(0);
	}
	
	/**
	 * grouped iHS file, no header.
	 * @param groupediHSFile
	 */
	public void runAPP(int fre_col, int val_col, File standerdizeFile) {
		fre_col--;
		val_col--;
		
		BufferedReader reader = null;
		LinkedList<double[]> sList = new LinkedList<double[]>();
		
		try {
			reader = new BufferedReader(new FileReader(standerdizeFile));
			
			String s = "";
			while ((s = reader.readLine()) != null) {
				String[] ss = s.split("\\s+");
				double[] tt = new double[3];
				boolean flag = false;
				for (int i = 0; i < tt.length; i++) {
					double t = 0;
					try {
						t = Double.parseDouble(ss[i]);
					} catch (NumberFormatException e) {
						flag = true;
						break;
					}
					tt[i] = t;
				}
				
				if (flag) {
					continue;
				}else {
					sList.add(tt);
				}
			}
			
			double[][] std = new double[sList.size()][];
			Iterator<double[]> iterator = sList.iterator();
			for (int i = 0; i < std.length; i++) {
				std[i] = iterator.next();
			}
			
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			DecimalFormat format = new DecimalFormat("#.##########");
			while ((s = reader.readLine()) != null) {
				String[] sArr = s.split("\\s+");
				double fre = 0;
				double value = 0;
				try {
					fre = Double.parseDouble(sArr[fre_col]);
					value = Double.parseDouble(sArr[val_col]);
				} catch (NumberFormatException e) {
					System.out.println(s+"\tSTD");
					continue;
				}
				
				for (int i = 0; i < std.length; i++) {
					if (fre <= std[i][0]) {
						value = (value - std[i][1])/std[i][2];
						System.out.print(s+"\t");
						System.out.println(format.format(value));
						break;
					}
				}
				
			}
			
			reader.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
