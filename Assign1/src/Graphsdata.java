import java.awt.Color;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataShapeAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.io.DataIOException;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;


public class Graphsdata {
	double sameedges = 0.0;
	double crossedges = 0.0;
	int totalnodes = 0;
	boolean random ;
	int triads = 0;
	int difftriads = 0;
	Graph graph;
	
	//constructor
	public Graphsdata(boolean x){
	graph = new Graph();
	random = x;
	
	try{
		graph =  GraphParser.loadGraph(new File("data/polbooks.gml"));	
	}
	catch(Exception ex ){
		System.out.println("Error Reading From File polbooks.gml");
		System.out.println(ex);
	}
	//Calculate all the edge types and other triad details
	evaluate();
	if (random == false){
	Visualization vis = new Visualization();
	vis.addGraph("graph",graph);
	//vis.setInteractive("graph.edges",null,false);
	//LabelRenderer r = new LabelRenderer("label");
	//r.setRoundedCorner(8, 8);
	ShapeRenderer s = new ShapeRenderer(8);
	EdgeRenderer e = new EdgeRenderer(Constants.EDGE_TYPE_LINE);
	vis.setRendererFactory(new DefaultRendererFactory(s,e));
	
	ActionList color = new ActionList();
	//Different Colored nodes for different value fields
	int[] palette = new int[]{ColorLib.rgb(180, 0, 0),ColorLib.rgb(0, 180, 0),ColorLib.rgb(0, 0, 180)};
	//Different colored edges
	int[] palette1 = new int[]{ColorLib.gray(180),ColorLib.gray(5)};
	
	//Different Shapes for different value fields
	int[] shapes = new int[]{Constants.SHAPE_RECTANGLE,Constants.SHAPE_ELLIPSE,Constants.SHAPE_TRIANGLE_UP};
	DataColorAction fill = new DataColorAction("graph.nodes", "value", Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
	//ColorAction edges = new ColorAction("graph.edges",VisualItem.STROKECOLOR,ColorLib.gray(105));
	//ColorAction text = new ColorAction("graph.nodes",VisualItem.TEXTCOLOR,ColorLib.gray(5));
	DataShapeAction shape = new DataShapeAction("graph.nodes","value",shapes);
	DataColorAction edgecolor = new DataColorAction("graph.edges","type",Constants.NOMINAL,VisualItem.STROKECOLOR,palette1);
	color.add(fill);
	color.add(edgecolor);
	//color.add(text);
	color.add(shape);
	
	ActionList layout = new ActionList(Activity.INFINITY);
	//Force DIrected Layout
	layout.add(new ForceDirectedLayout("graph"));
	layout.add(new RepaintAction());
	
	vis.putAction("color",color);
	vis.putAction("layout",layout);
	
	Display d = new Display(vis);
	d.setBackground(Color.DARK_GRAY);
	d.setSize(720,480);
	d.addControlListener(new DragControl());
	d.addControlListener(new PanControl());
	d.addControlListener(new ZoomControl());
	//Adding custom control to display pop ups
	//d.addControlListener(new CustomControl());
	
	JFrame frame = new JFrame();
	frame.add(d);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setVisible(true);
	vis.run("color");
	vis.run("layout");
	
	}
	

	}
	public String difftriads(){
		return Integer.toString(difftriads/3);
	}
	public String getratio(){
		return Double.toString(crossedges/(crossedges+sameedges));
	}
	
	public String gettriads(){
		return Integer.toString(triads/3);
		
	}
	public void evaluate(){
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
	
	
}