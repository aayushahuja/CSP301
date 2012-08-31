import java.io.*;
import java.util.*;
import prefuse.data.*;
import javax.swing.JFrame;

import prefuse.controls.*;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataShapeAction;
import prefuse.action.layout.*;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.data.Graph;

import prefuse.data.io.DataIOException;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.collections.IntIterator;
import prefuse.visual.EdgeItem;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/**
 * @author PAKV
 * This class mainly coordinated the flow of the execution . It calls various other classes with relevant parameters
 */


public class Main {
	
	// Buffered Writers for writing analysis data
	static BufferedWriter out1;
	static BufferedWriter out2;
	static BufferedWriter out3;
	static BufferedWriter out4;
	

	public static void main(String[] args) throws IOException {
		
		//args should contain the filename ie polbooks.gml or polblogs.gml
		
		File f1 = new File("data/output/ratio.txt");
		File f2 = new File("data/output/triads.txt");
		File f3 = new File("data/output/difftriads.txt");
		File f4 = new File("data/output/clustering_coeff.txt");
		
		try{
		FileWriter fstream1 = new FileWriter(f1);
		out1 = new BufferedWriter(fstream1);
		FileWriter fstream2 = new FileWriter(f2);
		out2 = new BufferedWriter(fstream2);
		FileWriter fstream3 = new FileWriter(f3);
		out3 = new BufferedWriter(fstream3);
		FileWriter fstream4 = new FileWriter(f4);
		out4 = new BufferedWriter(fstream4);
		}
		catch (IOException e){
			System.out.println(e);
			
		}
		Graph graphToVisualize;
		graphToVisualize = new Graph();
		try{
			// The filename is passed to the GraphParser class which will parse and return a Graph structure of the data
			graphToVisualize =  GraphParser.loadGraph(args[0]);	
		}
		catch(Exception ex ){
			System.err.println("Error Reading From File " + args[0]);
			ex.printStackTrace();
			System.exit(1);
		}
		// Analyse class is used for the analysis part , ie calculating triadsRatio , edgeratio etc , and 
		//generating random graphs 
		Analyse an = new Analyse();
		//evaluate funuction of the Analyse class takes as input a Graph and computed various analysis ratios which 
		// are written to files 
		an.evaluate(graphToVisualize);
		int node_count1 = graphToVisualize.getNodeCount();
		double denom1 = (node_count1*(node_count1  - 1))/2.0;
		String triads1 = an.gettriads();
		double cluster_ratio1 = (Integer.parseInt(triads1))/denom1;
		out1.write(an.getratio()+ "\n");
		out2.write(triads1 + "\n");
		out3.write(an.difftriads() + "\n");
		out4.write(cluster_ratio1 + "\n");
		
		
		
		try {
			//Following block is for calculating edge ratio , triads ratio etc on 30 random graphs
		Graph g = new Graph();
		for(int i = 0;i < 30; i++){
			g = GraphParser.loadGraph(args[0]);
			an.crossedges = 0.0;
			an.difftriads = 0;
			an.sameedges = 0;
			an.triads = 0;
			//makerandomGraph takes the given graph in the file and creates a new random graph by changing the edges
			g = an.makeRandomGraph(g);
			an.evaluate(g);
			int node_count2 = g.getNodeCount();
			double denom = (node_count2*(node_count2  - 1))/2.0;
			String triads = an.gettriads();
			double cluster_ratio = (Integer.parseInt(triads))/denom;
			out1.write(an.getratio() + "\n");
			out2.write(triads + "\n");
			out3.write(an.difftriads() + "\n");
			out4.write(cluster_ratio + "\n");
			
		}
		//Visualize class takes a Graph as input and creates a frame showing the visualizatio of the graph 
		//gd2 is for displaying a random graph to compare with the given graph
		Visualize gd2 = new Visualize(g);
		} catch (DataIOException e) {
			System.out.println("Error Reading From File "+args[0]);
			System.out.println(e);
		}
	
		out1.close();
		out2.close();
		out3.close();
		out4.close();
		
		//visualize the given graph
		Visualize gd = new Visualize(graphToVisualize);

	}
	
	
}