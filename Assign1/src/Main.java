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
 */
public class Main {
	
	static BufferedWriter out1;
	static BufferedWriter out2;
	static BufferedWriter out3;
	

	public static void main(String[] args) throws IOException {
		File f1 = new File("data/output/ratio.txt");
		File f2 = new File("data/output/triads.txt");
		File f3 = new File("data/output/difftriads.txt");
		
		try{
		FileWriter fstream1 = new FileWriter(f1);
		out1 = new BufferedWriter(fstream1);
		FileWriter fstream2 = new FileWriter(f2);
		out2 = new BufferedWriter(fstream2);
		FileWriter fstream3 = new FileWriter(f3);
		out3 = new BufferedWriter(fstream3);
		}
		catch (IOException e){
			System.out.println(e);
			
		}
		Graph graphToVisualize;
		graphToVisualize = new Graph();
		try{
			graphToVisualize =  GraphParser.loadGraph(new File("data/polbooks.gml"));	
		}
		catch(Exception ex ){
			System.out.println("Error Reading From File polbooks.gml");
			System.out.println(ex);
		}
		Visualize gd = new Visualize(graphToVisualize);
		Analyse an = new Analyse();
		an.evaluate(graphToVisualize);
		out1.write(an.getratio()+ "\n");
		out2.write(an.gettriads()+ "\n");
		out3.write(an.difftriads()+ "\n");
		
		try {
		Graph g = new Graph();
		for(int i = 0;i < 30; i++){
			g = GraphParser.loadGraph(new File("data/polbooks.gml"));
			g = an.makeRandomGraph(g);
			an.evaluate(g);
			out1.write(an.getratio() + "\n");
			out2.write(an.gettriads()+ "\n");
			out3.write(an.difftriads()+ "\n");
			
		}
		//Visualize gd2 = new Visualize(g);
		} catch (DataIOException e) {
			System.out.println("Error Reading From File polbooks.gml");
			System.out.println(e);
		}
	
		out1.close();
		out2.close();
		out3.close();
		
		
	}
	
	
}