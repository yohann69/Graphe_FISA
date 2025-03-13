package Nodes_Edges;

public class Arc extends AbstractEdgeArc {

	/*public Arc(String s) {
		super(s); 
	}*/
	
	public Arc(DirectedNode node1, DirectedNode node2) {
		super(node1, node2);
	}
	
	public Arc(DirectedNode node1, DirectedNode node2, int weight) {
		super(node1, node2,weight);
	}
	
		
	/**
	 * @return the origin node of the arc
	 */
	public DirectedNode getFirstNode(){
		return (DirectedNode) super.node1;
	}
	
	/**
	 * @return the destination node  of the arc
	 */
	public DirectedNode getSecondNode(){
		return (DirectedNode) super.node2;
	}
	
	/**
	 * check if two arcs are equals => the nodes are the same
	 * @param e an object which is an arc
	 * @return true iff this and e are equal
	 */
	@Override
	public boolean equals(Object e) {
		return e instanceof AbstractEdgeArc &&
				( ((Arc) e).getFirstNode() == this.getFirstNode() && ((Arc) e).getSecondNode() == this.getSecondNode() );
	}

}
