/*
 * @Authors Jacob Binder, Zachary Carpenter
 * 
 * The model used for creating the actor graph.
 * Will go through the file and fill up hashmaps giving each movie and actor a unique id.
 * Then creates a graph and makes the connections between each movie and actor.
 * Has a method for finding the number of hops between two actors.
 */

import edu.princeton.cs.algs4.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActorGraphModel {
	private String fileName = "/movies-mpaa.txt";
	private HashMap<String, Integer> actors = new HashMap<String, Integer>();
	private HashMap<String, Integer> movies = new HashMap<String, Integer>();
	private List<String> names = new ArrayList<String>();
	private Graph graph;
	
	
	public ActorGraphModel() {
		int numOfVertices = FillHashmaps();	
		graph = new Graph(numOfVertices);
		CreateEdges();
		
	}
	
	/*
	 * Fills the two hashmaps with the data from the file and returns the number of vertices required.
	 */
	private int FillHashmaps() {
		InputStream res = ActorGraphDemo.class.getResourceAsStream(fileName);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(res));
	    String line = null;
	    int id = 0;
	    
	    try {
	    	while ((line = reader.readLine()) != null) {
	    		//Split the line by '/'
	    		String[] words = line.split("[/]");
	    		//Put the movie title in movies map and the names list
	    		movies.put(words[0], id);
	    		names.add(words[0]);
	    		for(int i = 1; i<words.length; i++) {
	    			//Put the actors in actors map and names list
	    			if(!actors.containsKey(words[i])) {
	    				id++;
	    				actors.put(words[i], id);
	    				names.add(words[i]);
	    			}
	    		}
	    		id++;
	    	}
	    	reader.close();
	    }catch(Exception ex) {}
	    return id;
	}
	
	/*
	 *  Goes through the file and adds the connections between each vertex.
	 */
	private void CreateEdges() {
		InputStream res = ActorGraphDemo.class.getResourceAsStream(fileName);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(res));
	    String line = null;
	    
	    try {
	    	while ((line = reader.readLine()) != null) {
	    		//Split the line by '/'
	    		String[] words = line.split("[/]");
	    		for(int i = 1; i<words.length; i++) {
	    			//Put the actors in actors map
	    			if(!actors.containsKey(words[i])) {
	    				graph.addEdge(movies.get(words[0]), actors.get(words[i]));
	    			}else {
	    				graph.addEdge(movies.get(words[0]), actors.get(words[i]));
	    			}
	    		}
	    	}
	    	reader.close();
	    }catch(Exception ex) {}
	}
	
	/*
	 * Takes two actors and checks the number of hops between them.
	 * The number of hops include going from actors to movies to actors.
	 * The number of hops between only actors can be found by dividing the number in half.
	 * If one of the actors does not exist in the hashmaps, throw an exception and exit the program.
	 */
	public int distanceToActor(String source, String dest) {
		int sourceID = 0;
		int destID = 0;
		try {
			sourceID = actors.get(source);
			destID = actors.get(dest);
		}catch(Exception ex ){
			System.err.println("Actors not in file.");
			System.exit(0);
		}
		
		BreadthFirstPaths bfp = new BreadthFirstPaths(graph, sourceID);
		int numHops = bfp.distTo(destID);
		return numHops;
	}
	
	public String[] trace(String source, String dest) {
		int sourceID = 0;
		int destID = 0;
		try {
			sourceID = actors.get(source);
			destID = actors.get(dest);
		}catch(Exception ex ){
			System.err.println("Actors not in file.");
			System.exit(0);
		}
		
		BreadthFirstPaths bfp = new BreadthFirstPaths(graph, sourceID);
		Iterable<Integer> pathIter = bfp.pathTo(destID);
		List<Integer> pathList = new ArrayList<Integer>();
		
		for(Integer id : pathIter) {
			pathList.add(id);
		}
		String[] pathStr = new String[pathList.size()];
		for(int i = 0; i<pathList.size(); i++) {
			pathStr[i] = names.get(pathList.get(i));
		}
		return pathStr;
	}
}
