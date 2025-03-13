package Nodes_Edges;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by gsimonin on 05/01/2020.
 */
public class UndirectedNode extends AbstractNode {

    //--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    //private Map<UndirectedNode, Integer> neighbours;
	//private List<UndirectedNode> neighbours;
	private List<Edge> incidentEdges; // List of edges incident with the node this.

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public UndirectedNode(int i) {
        super(i);
       // this.neighbours = new LinkedList<UndirectedNode>();
        this.incidentEdges = new LinkedList<Edge>();        
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    /**
     * @return the list of neighbors of the current node this
     */
   /* public List<UndirectedNode> getNeighbours() {
        return this.neighbours;
    }*/

    /**
     * @return the list of incident edges of the current node this
     */
    public List<Edge> getIncidentEdges() {
        return this.incidentEdges;
    }

    
    /**
     * @param neigh the new list of neighbors for node this
     */
    /*public void setNeighbours(List<UndirectedNode> neigh) {
		this.neighbours = neigh;
	}*/

    /**
     * @return the number of neighbors of node this
     */
	public int getNbNeigh() {
        return incidentEdges.size();
    }


	/**
	 * add a new neighbour if it does not exist already.
	 */
	/*public void addNeigh(UndirectedNode v) {
		if(!this.neighbours.contains(v)) {
			this.neighbours.add(v);
		}
	}*/

	/**
	 * add a new edge with its value cost. If the neighbour exists, the weight is changed.
	 */
	public void addEdge(Edge e1) {
		if(!this.getIncidentEdges().contains(e1)) {			
			//if(e1.getFirstNode().equals(this) && !this.neighbours.contains(e1.getSecondNode())) {
			if(e1.getFirstNode().equals(this)) {
				this.incidentEdges.add(e1);
				//this.addNeigh(e1.getSecondNode());				
			}
			//else if(e1.getSecondNode().equals(this) && !this.neighbours.contains(e1.getFirstNode())) {
			else if(e1.getSecondNode().equals(this) ) {
				this.incidentEdges.add(new Edge(e1.getSecondNode() ,e1.getFirstNode(),e1.getWeight()));
				//this.addNeigh(e1.getFirstNode());
			} 			
		}
		else {
			// Update of the existing weight
			int indexEdge;
			if(this.getIncidentEdges().contains(e1)){
				indexEdge = this.getIncidentEdges().indexOf(e1);
			}
			else {
				indexEdge = this.getIncidentEdges().indexOf(new Edge(e1.getSecondNode(),e1.getFirstNode()) );
			}
			this.getIncidentEdges().get(indexEdge).setWeight(e1.getWeight());
		}
	}

	
}
