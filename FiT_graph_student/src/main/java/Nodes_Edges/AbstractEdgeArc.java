package Nodes_Edges;

public abstract class AbstractEdgeArc {

	//--------------------------------------------------
		// 				Class variables
		//--------------------------------------------------

		//private String label;
		protected AbstractNode node1;
		protected AbstractNode node2;
		private int weight; // the weight associated to the edge/arc (node1,node2). It equals 0 if the graph is not valued.
		
		//--------------------------------------------------
		// 				Constructors
		//--------------------------------------------------

		/*public AbstractEdgeArc(String s) {
			this.label = s;
			this.weight = 0; // By default, the graph is not valued. 
			

		}*/

		public AbstractEdgeArc(AbstractNode node1,AbstractNode node2) {
			//this.label = node1.toString()+","+node2.toString();
			this.node1 = node1;
			this.node2 = node2;
			this.weight = 0; // By default, the graph is not valued.
			
		}
		
		public AbstractEdgeArc(AbstractNode node1,AbstractNode node2, int weight) {
			this(node1,node2);
			this.weight = weight; // By default, the graph is not valued.
			
		}

		
		
		// ------------------------------------------
		// 				Accessors
		// ------------------------------------------

		/**
		 * @return a String which is a unique identifier of an edge
		 */
		/*public String getLabel(){
			return this.label;
		}*/
		
		/**
		 * Set the String which is a unique identifier of an edge
		 */
		/*public void setLabel(String l){
			this.label = l;
		}*/
		
		/**
		 * @return an integer which is the weight of the edge/arc (node1,node2).
		 */
		public int getWeight(){
			return this.weight;
		}
		
		/**
	     * @param weight the new weight of the edge/arc (node1,node2).
	     */
		public void setWeight(int weight){
			this.weight = weight;
		}
		
		/**
		 * check if two edges/arcs are equals => the label is the key
		 * @param e an object which is an abstract edge/arc
		 * @return true iff this and e are equal
		 */
		/*public boolean equals(Object e) {
			return e instanceof AbstractEdgeArc && ((AbstractEdgeArc) e).getLabel() == this.getLabel();
		}*/

		public String toString() {
			String s = "("+node1.toString()+","+node2.toString();
			if(this.getWeight()!=0) {s+=","+this.getWeight();}
			s+=")";
			return s;
		}
	
}
