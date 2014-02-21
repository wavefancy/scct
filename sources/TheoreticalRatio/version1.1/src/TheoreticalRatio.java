package wavefancy.ratioExpectation;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;

/**
 * This class is used to compute the theoretical ratio for NumberOfDerivedMutations/NumberOfAncestralMutatoins.
 * @author wavefancy@gmail.com
 *
 * @version 1.1
 * @since 2013-11-10
 * 1. Alter the output format. output derived allele frequency instead of derived allele count.
 * 
 */
public class TheoreticalRatio {
	
	static int totalHaplotype = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			help();
		}
		
		new TheoreticalRatio().runApp(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
//		new TheoreticalRatio().runApp(2, 5);
	}
	
	public static void help() {
		System.out.println("--------------------------------");
		System.out.println("    TheoreticalRatio    version: 1.1     Author:wavefancy@gmail.com");
		System.out.println("--------------------------------");
		System.out.println("Usages: \nparameter1: Number of threads used for computing.");
		System.out.println("parameter2: Number of Haplotypes");
//		System.out.println("Specify multiplbe files as you like!");
		System.out.println("--------------------------------");

		System.exit(0);
	}

	
	public void runApp(final int NumberOfTheads, final int NumberOfHaplotype) {
		ActorSystem system = ActorSystem.create("TheoreticalRatioComputer");
		
		ActorRef master = system.actorOf(new Props(new UntypedActorFactory() {

			private static final long serialVersionUID = 139734738473894347L;

			@Override
			public Actor create() throws Exception {
				return new AkkaWorkMaster(NumberOfHaplotype-2, NumberOfTheads);
			}
		}),"master");
		
		TheoreticalRatio.totalHaplotype = NumberOfHaplotype;
		for (int i = 2; i < NumberOfHaplotype; i++) {
			int[] d_a = new int[2];
			d_a[0] = i;
			d_a[1] = NumberOfHaplotype - i;
			master.tell(d_a);
		}
	}
	
}
