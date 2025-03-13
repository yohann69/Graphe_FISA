package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;


public class AdjacencyMatrixDirectedValuedGraph extends AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	// No class variable, we use the matrix variable but with costs values 

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedValuedGraph(int[][] matrixVal) {
		super(matrixVal);
	}

	
	// ------------------------------------------------
	// 					Methods
	// ------------------------------------------------	
	
	/**
     * removes the arc (from,to) if there exists one between these nodes in the graph.
     */
	@Override
	public void removeArc(int from, int to) {
		super.removeArc(from, to);
		// A completer
	}

	/**
     * adds the arc (from,to,cost). If there is already one initial cost, we replace it.
     */	
	public void addArc(int from, int to, int cost ) {
		// A completer
	}

	/**
	 * @return a new graph which is the inverse graph of this.matrix.
 	 */
	public AdjacencyMatrixDirectedValuedGraph computeInverse() {
		AdjacencyMatrixDirectedValuedGraph amInv = new AdjacencyMatrixDirectedValuedGraph(this.matrix);	
		// A completer
				
		return amInv;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("\n Matrix of Costs: \n");
		for (int[] lineCost : this.matrix) {
			for (int i : lineCost) {
				s.append(i).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
		AdjacencyMatrixDirectedValuedGraph am = new AdjacencyMatrixDirectedValuedGraph(matrixValued);
		System.out.println(am);
		// A completer
	}
}
