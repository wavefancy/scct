package wavefancy.TwoGroupMutations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ScaleAlphaDeleted {
	
	static double[] alpha;
	
	static boolean UPDATE = true;
	
	private static double[] temp_toal_alpha;  //total alpha
	private static int[] temp_derived_count; //total number of site with with i number of derived allele.
	
	
	/**
	 * Set total number of sequences.
	 * @param NUM_SEQ
	 */
	public static void setNum_SEQ(int NUM_SEQ) {
		if (UPDATE) { //if !update, alpha has been set by user specified file.
			alpha = new double[NUM_SEQ];
		}
		
		temp_derived_count = new int[NUM_SEQ];
		temp_toal_alpha = new double[NUM_SEQ];
	}
	
	/**
	 * @param derived_count
	 * @param alpha
	 */
	public synchronized static void updateTempAlpha(int derived_count, double alpha) {
		temp_toal_alpha[derived_count] += alpha;
		temp_derived_count[derived_count]++;
	}
	
	public static void computeFinalAlpha() {
		if (!UPDATE) {
			return;
		}
		
		for (int i = 0; i < temp_derived_count.length; i++) {
			if (temp_derived_count[i] != 0) {
				alpha[i] = temp_toal_alpha[i]/temp_derived_count[i];
			}
		}
	}
	
	public static void setAlphaByFile(File alpha_file) {
		UPDATE = false; //do not need real time updating alpha.
		
		try {
			BufferedReader expectationReader = new BufferedReader(new FileReader(alpha_file));
			LinkedList<String> e_List = new LinkedList<String>();
			
				expectationReader.readLine();
			 //skip title.
			String s = "";
			while ((s = expectationReader.readLine()) != null) {
				e_List.addLast(s.trim());
			}
			
			alpha = new double[e_List.size()+2]; //alpha recoding file is from 2 to n-1.
			{
				int i = 2;
				for (String string : e_List) {
					alpha[i++] = Double.parseDouble(string.split("\\s+")[1]);
				}
			}
			expectationReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
