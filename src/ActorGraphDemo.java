/*
 * @Authors Jacob Binder, Zachary Carpenter
 * 
 * Can find the number of hops between two actors.
 */
public class ActorGraphDemo {

	public static void main(String[] args) {
		if(args.length == 0) {
			test();
		}
		else if(args.length == 1) {
			if(args[0].equals("help")) {
				System.out.println("Prints out the number of hops between two actors. Give two actor names as arguments. Use \"-t\" to print the trace."
					+ "	\nExample: java -jar ActorGraph.jar \"Bacon, Kevin\" \"Stallone, Sylvester\" -t ");
			}
		}
		else if(args.length == 2) {
			ActorGraphModel agm = new ActorGraphModel();
			int numHops = agm.distanceToActor(args[0], args[1]);
			System.out.println(args[1] + " is " + numHops + " movie and actor hops away from " + args[0]);
			System.out.println();
		}
		else if(args.length == 3) {
			ActorGraphModel agm = new ActorGraphModel();
			int numHops = agm.distanceToActor(args[0], args[1]);
			System.out.println(args[1] + " is " + numHops + " movie and actor hops away from " + args[0]);
			
			if(args[2].equals("-t")) {
				String[] path = agm.trace(args[0], args[1]);
				for(String s : path) {
					System.out.print(s + " --> ");
				}
				System.out.println();
			}
		}
		else {
			System.err.println("Error: Must give two actors.");
		}
	}
	
	private static void test(){
		ActorGraphModel agm = new ActorGraphModel();
		String[] testExamples = new String[] {"Bacon, Kevin", "Conley, Jack", "Stallone, Sylvester", "Polanski, Roman", "Allen, Tim", "Chaplin, Charles"};
		int numHops1 = agm.distanceToActor(testExamples[0], testExamples[1]);
		int numHops2 = agm.distanceToActor(testExamples[2], testExamples[3]);
		int numHops3 = agm.distanceToActor(testExamples[4], testExamples[5]);
		System.out.println(testExamples[1] + " is " + numHops1 + " movie and actor hops away from " + testExamples[0]);
		System.out.println(testExamples[3] + " is " + numHops2 + " movie and actor hops away from " + testExamples[2]);
		System.out.println(testExamples[5] + " is " + numHops3 + " movie and actor hops away from " + testExamples[4]);
	}
}
