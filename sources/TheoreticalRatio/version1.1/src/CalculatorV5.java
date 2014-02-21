package wavefancy.ratioExpectation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * This class is used to calculate the ration for expectation 
 * of derived allele group / ancestral allele group.
 * 
 * @author wangminxian
 *
 */
public class CalculatorV5 {
	BigDecimal[][] BT = null; // branch length expectation.
	int dn;	// number of derived chromosome.
	int an; // number of ancestral chromosome.
	int BIG_NUMBER_PRECISION = 1000;
	RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
//	int THETA = 200;
	
	/**
	 * 
	 * @param dn, number of derived chromosome.
	 * @param an, number of ancestral chromosome.
	 */
	public CalculatorV5(int dn, int an) {
		this.setIndividual(dn, an);
	}
	
	public CalculatorV5() {
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		CalculatorV5 calculator = new CalculatorV5(4, 1);
//		System.err.println(calculator.getExpectation());
		
//		System.err.println(calculator.conditionOnM_new(1));
//		System.err.println(calculator.conditionOnM_new(2));
//		System.err.println(calculator.conditionOnM_new(3));
//		calculator.totalDerivedBranchLength();
//		calculator.totalBranchLength();
//		System.err.println(calculator.getExpectation());
//		
		int N = 30;
		for (int d = 2; d <= N-1; d++) {
			System.err.println(d+"\t"+calculator.getExpectation(d, N-d));
		}
		
//		System.err.println(new PoissonDistribution(1).probability(0));
//		System.err.println(new PoissonDistribution(2).probability(0));
//		System.err.println(calculator.getExpectation(3, 1));
	}
	
	/**
	 * get the expectation for E[dn]/E[an].
	 * @param dn
	 * @param an
	 * @return
	 */
	public double getExpectation(int dn, int an) {
		this.setIndividual(dn, an);
		return getExpectation();
	}
	
	/**
	 * get the expectation for E[dn]/E[an]
	 * @return
	 */
	private double getExpectation() {
		
		// tree length for iteration.
//		BigDecimal[][] tolD = new BigDecimal[dn+1][an+1]; //derived
//		BigDecimal[][] tolA = new BigDecimal[dn+1][an+1]; //ancestral.
		BigDecimal tl_D = new BigDecimal("0");
		BigDecimal tl_A = new BigDecimal("0");
		
		/**
		 * Transition matrix, condition on (1,a)
		 * 
		 * R(d,a->d-1,a) = (d+1)/(d+a)
		 * R(d,a->d,a-1) = (a-1)/(d+a)
		 */

		/**
		 * Length iteration. (d>1, d<dn, a < an)
		 * T(d,a) = (d+2)/(d+a+1)(T(d+1,a) + (d+1)* t(d+1,a))
		 * 			+ a/(d+a+1)(T(d,a+1) + d*t(d,a+1));
		 * 
		 * 1. if d = dn; matrix boundary.
		 * T(d, a) = (T(d,a+1)+d*t(d,a+1)) * a/(d+a+1)
		 * 2. if a = an; matrix boundary.
		 * T(d,a) = (T(d+1,a) + (d+1) * t(d+1,a)) * (d+2)/(d+a+1)
		 * 3. if d = 1; P((1,a)|(1,a+1)) = 0;
		 * T(1,a) = (T(2,a) + 2*t(2,a))*3/(a+2)
		 */
		
//		//computation boundary.
//		tolD[dn][an] = new BigDecimal("0");
//		tolA[dn][an] = new BigDecimal("0");
		
		//test probability
		BigDecimal[][] p = new BigDecimal[dn+1][an+1];
		for (int i = 0; i < p.length; i++) {
			Arrays.fill(p[i], new BigDecimal("0"));
		}
		p[dn][an] = new BigDecimal("1");
		
		/**
		 * Compute probability matrix
		 * p[d][a] =  (d+2)/(d+a+1)*p[d+1][a] + (a)/(d+a+1)*p[d][a+1]
		 * 
		 * p[dn][an] = 1;
		 * p[d][a] --> p[d-1][a]; (d+1)/(d+a)
		 * p[d][a] --> p[d][a-1]; (a-1)/(d+a)
		 */
		
//		//for boundary a = an
//		for (int d = dn-1; d >= 1; d--) {
//			p[d][an] = p[d+1][an].multiply(new BigDecimal(d+2).divide(new BigDecimal(d+1+an), BIG_NUMBER_PRECISION, ROUNDING_MODE));
//		}
//		//for boundary d = dn
//		for (int a = an-1; a >= 1; a--) {
//			p[dn][a] = p[dn][a+1].multiply(new BigDecimal(a).divide(new BigDecimal(dn+a+1), BIG_NUMBER_PRECISION, ROUNDING_MODE));
//		}
		
		for (int d = dn; d >= 2; d--) { //for the end condition is (1,a). (1,a+1) will not go to (1,a)
			for (int a = an; a >= 1; a--) {
//				BigDecimal l = normalBranchLen(d+a);
				BigDecimal l = BT[d][a];
				//[d][a] --> [d-1][a]
				BigDecimal tL = p[d][a].multiply(new BigDecimal(d+1).divide(new BigDecimal(d+a), BIG_NUMBER_PRECISION, ROUNDING_MODE));
				p[d-1][a] = p[d-1][a].add(tL);
				
				BigDecimal tR = p[d][a].multiply(new BigDecimal(a-1).divide(new BigDecimal(d+a), BIG_NUMBER_PRECISION, ROUNDING_MODE));
				p[d][a-1] = p[d][a-1].add(tR);
				
				BigDecimal temp = tL.add(tR);
				tl_D = tl_D.add(new BigDecimal(d).multiply(l).multiply(temp));
				tl_A = tl_A.add(new BigDecimal(a).multiply(l).multiply(temp));
			}
		}
		
		//Compute the total branch length when reach (1,a)
		for (int a = 1; a <= an; a++) {
			tl_A = tl_A.add(normalTotalTreeLen(a+1).multiply(p[1][a]));
		}
		
//		System.err.println("tl_D: " + tl_D);
//		System.err.println("tl_A: " + tl_A);

		return tl_D.divide(tl_A,BIG_NUMBER_PRECISION,ROUNDING_MODE).doubleValue();
	}
	
//	/**
//	 * compute branch length for normal coalescent.
//	 * @param 2/(i*(i-1))
//	 * @return
//	 */
//	private BigDecimal normalBranchLen(int i){
//		return new BigDecimal("2").divide(new BigDecimal(i), BIG_NUMBER_PRECISION, ROUNDING_MODE)
//								.divide(new BigDecimal(i-1), BIG_NUMBER_PRECISION, ROUNDING_MODE);
//	}
	
	/**
	 * Set the number of individuals for derived and ancestral group.
	 * @param dn
	 * @param an
	 */
	private void setIndividual(int dn, int an) {
		this.dn = dn;
		this.an = an;
		
		this.BT = new BigDecimal[dn+1][an+1];
		//calculate the expectation of branch length.
		for (int i = 1; i < BT.length; i++) {
			for (int j = 1; j < BT[i].length; j++) {
				if (i==1 && j==1) {
					BT[i][j] = new BigDecimal("1"); //won't be used.
				}else {
//					BT[i][j] = 2.0/(i*(i-1) + j*(j-1));
//					BT[i][j] = 2.0/((i+j)*(i+j-1));
					BT[i][j] = new BigDecimal("2").divide(new BigDecimal(i+j), BIG_NUMBER_PRECISION, ROUNDING_MODE)
									.divide(new BigDecimal(i+j-1),BIG_NUMBER_PRECISION,ROUNDING_MODE);
				}
			}
		}
	}
	
	/**
	 * Compute total tree length for normal coalescent model.
	 * @param n,  total leaf number.
	 * @return total tree length.
	 */
	private BigDecimal normalTotalTreeLen(int n){
		BigDecimal x = new BigDecimal("0");
		for(int i=2;i<=n;i++){
//			x += (2.0/(i-1));
			x = x.add(new BigDecimal("2").divide(new BigDecimal(i-1),BIG_NUMBER_PRECISION,ROUNDING_MODE));
		}
		return x;
	}
	
	
	
	
	
//	/**
//	 * Compute the (1,a) distribution condition on M (a mutation on the MRCA of derived allele group).
//	 * @param a
//	 * @return
//	 */
//	private double conditionOnM(int a){
//		/**
//		 * a*{[n-a-2]choose[dn-2]}/{[n-1]choose[dn]}, n = an + dn.
//		 */
//		
////		System.err.println(combinatorialNumber((dn+an)-a-2, dn-2));
////		System.err.println(combinatorialNumber(dn+an-1, dn));
////		System.err.println(combinatorialNumber((dn+an)-a-2, dn-2).divide(combinatorialNumber(dn+an-1, dn),BIG_NUMBER_PRECISION,RoundingMode.HALF_UP));
//		
//		return combinatorialNumber((dn+an)-a-2, dn-2).divide(combinatorialNumber(dn+an-1, dn),BIG_NUMBER_PRECISION,RoundingMode.HALF_UP).multiply(new BigDecimal(a)).doubleValue();
//	}
	
	/**
	 * Compute the (1,a) distribution.
	 * @param a
	 * @return
	 */
//	private double conditionOnM_new(int a){
////		System.err.println("m: " + combinatorialNumber(an-1, a-1));
////		System.err.println("n: " + combinatorialNumber(an+dn-1, a));
////		System.err.println("r:" + combinatorialNumber(an-1, a-1).divide(combinatorialNumber(an+dn-1, a), BIG_NUMBER_PRECISION, RoundingMode.HALF_UP).doubleValue());
////		return combinatorialNumber(an-1, a-1).divide(combinatorialNumber(an+dn-1, a), BIG_NUMBER_PRECISION, RoundingMode.HALF_UP).doubleValue();
//		
//		return combinatorialNumber(an+dn-a-2, dn-2).multiply(combinatorialNumber(a+1, 2)).divide(combinatorialNumber(an+dn, dn+1),BIG_NUMBER_PRECISION,RoundingMode.HALF_UP).doubleValue();
//		
//	}
	
	/**
	 * Compute the combinatorial number , from n choose m.
	 * @param n
	 * @param m
	 * @return
	 */
//	private BigDecimal combinatorialNumber(int n, int m){
//		if (n<m) {
//			return new BigDecimal(0);
//		}else if (m==0 || m==n) {
//			return new BigDecimal(1);
//		}
//		
//		if (n-m < m) {
////			System.err.println("in");
//			m = n-m;
//		}
//		
//		BigDecimal k = new BigDecimal(1);
//		for (int i = n,j=1 ; j <= m; i--,j++) {
//			k =  k.multiply(new BigDecimal(i)).divide(new BigDecimal(j));
//		}
//		
//		return k;
//	}

}
