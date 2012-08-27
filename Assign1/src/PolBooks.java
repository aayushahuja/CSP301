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

public class PolBooks {
	
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
		
		Graphsdata gd = new Graphsdata(false);
		out1.write(gd.getratio()+ "\n");
		out2.write(gd.gettriads()+ "\n");
		out3.write(gd.difftriads()+ "\n");
		
		
		
		for(int i = 0;i < 30; i++){
			out1.write(new Graphsdata(true).getratio() + "\n");
			out2.write(new Graphsdata(true).gettriads()+ "\n");
			out3.write(new Graphsdata(true).difftriads()+ "\n");
			
		}
		out1.close();
		out2.close();
		out3.close();
		
		
	}
	
	
}