import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;


public class Analyse {
	double sameedges = 0.0;
	double crossedges = 0.0;
	//int totalnodes =0;
	boolean random ;
	int triads = 0;
	int difftriads = 0;
	public String difftriads(){
		return Integer.toString(difftriads/3);
	}
	public String getratio(){
		return Double.toString(crossedges/(crossedges+sameedges));
	}
	
	public String gettriads(){
		return Integer.toString(triads/3);
		
	}
	public void evaluate(Graph graph){
		Iterator<Edge> i0 = graph.edges();
		while(i0.hasNext()){
			Edge e = i0.next();
			Node m1 = graph.getSourceNode(e);
			Node m2 = graph.getTargetNode(e);
			if (m1.get("value").equals(m2.get("value"))){
				e.set("type", "same");
				sameedges++;
			}
			else{
				e.set("type","cross");
				crossedges ++;
			}
			//Triads
			Iterator<Node> i1 = graph.neighbors(m1);
			Iterator<Node> i2 = graph.neighbors(m2);
			ArrayList<Node> a1 = new ArrayList<Node>();
			ArrayList<Node> a2 = new ArrayList<Node>();
			while(i1.hasNext()){
				a1.add(i1.next());
			}
			while(i2.hasNext()){
				a2.add(i2.next());
			}
			for(Node n : a1){
				if(a2.contains(n)){
					triads++;
					if(!n.get("value").equals(m1.get("value")) && !m2.get("value").equals(m1.get("value")) && !n.get("value").equals(m2.get("value"))){
						difftriads++;
					}
				}
			}
			
			
		}

	}
	
	public Graph makeRandomGraph(Graph g) {
		Graph result = g;
		Iterator<Edge> it = result.edges();
		int source;
		int target;
		Edge e1;
		while(it.hasNext()) {
			Edge e = it.next();
			source = (int)(Math.random()*g.getNodeCount());
			target = (int)(Math.random()*g.getNodeCount());
			result.removeEdge(e);
			
			//System.out.println(a);
			//System.out.println(b);
			
			e1 = result.getEdge(result.addEdge(source,target));
			if (result.getNode(source).get("value").equals(result.getNode(target).get("value"))){
				e1.set("type", "same");
			}
			else{
				e1.set("type","cross");
			}
			
		}
		
		return result;
	}
	
}
