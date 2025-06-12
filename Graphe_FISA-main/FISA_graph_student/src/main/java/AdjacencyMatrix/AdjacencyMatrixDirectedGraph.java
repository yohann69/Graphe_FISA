package AdjacencyMatrix;


import GraphAlgorithms.GraphTools;
import Nodes_Edges.AbstractNode;
import Nodes_Edges.DirectedNode;

import java.util.ArrayList;
import java.util.List;

import AdjacencyList.AdjacencyListDirectedGraph;

/**
 * This class represents the directed graphs structured by an adjacency matrix.
 * We consider only simple graph
 */
public class AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    protected int nbNodes;		// Number of vertices
    protected int nbArcs;		// Number of edges/arcs
    protected int[][] matrix;	// The adjacency matrix
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

    public AdjacencyMatrixDirectedGraph() {
        this.matrix = new int[0][0];
        this.nbNodes = 0;
        this.nbArcs = 0;
    }

      
	public AdjacencyMatrixDirectedGraph(int[][] mat) {
		this.nbNodes = mat.length;
		this.nbArcs = 0;
		this.matrix = new int[this.nbNodes][this.nbNodes];
		for(int i = 0; i<this.nbNodes; i++){
			for(int j = 0; j<this.nbNodes; j++){
				this.matrix[i][j] = mat[i][j];
				this.nbArcs += mat[i][j];
			}
		}
	}

	public AdjacencyMatrixDirectedGraph(AdjacencyListDirectedGraph g) {
		this.nbNodes = g.getNbNodes();
		this.nbArcs = g.getNbArcs();
		this.matrix = g.toAdjacencyMatrix();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------


    /**
     * Returns the matrix modeling the graph
     */
    public int[][] getMatrix() {
        return this.matrix;
    }

    /**
     * Returns the number of nodes in the graph (referred to as the order of the graph)
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
	
    /**
	 * @return the number of arcs in the graph
 	 */	
	public int getNbArcs() {
		return this.nbArcs;
	}

	
	/**
	 * @param u the vertex selected
	 * @return a list of vertices which are the successors of u
	 */
	public List<Integer> getSuccessors(int u) {
		List<Integer> succ = new ArrayList<Integer>();
		for(int v =0;v<this.matrix[u].length;v++){
			if(this.matrix[u][v]>0){
				succ.add(v);
			}
		}
		return succ;
	}

	/**
	 * @param v the vertex selected
	 * @return a list of vertices which are the predecessors of v
	 */
	public List<Integer> getPredecessors(int v) {
		List<Integer> pred = new ArrayList<Integer>();
		for(int u =0;u<this.matrix.length;u++){
			if(this.matrix[u][v]>0){
				pred.add(u);
			}
		}
		return pred;
	}
	
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	/**
	 * @return true if the arc (from,to) exists in the graph.
 	 */
	public boolean isArc(int from, int to) {
		if (from < 0 || from >= nbNodes || to < 0 || to >= nbNodes) {
			return false;
		}
		return matrix[from][to] > 0;
	}

	/**
	 * removes the arc (from,to) if there exists one between these nodes in the graph.
	 */
	public void removeArc(int from, int to) {
		if (from < 0 || from >= nbNodes || to < 0 || to >= nbNodes) {
			return;
		}
		
		if (matrix[from][to] > 0) {
			matrix[from][to] = matrix[from][to] - 1;
			nbArcs--;
		}
	}

	/**
	 * Adds the arc (from,to). 
	 */
	public void addArc(int from, int to) {
		if (from < 0 || from >= nbNodes || to < 0 || to >= nbNodes) {
			return;
		}
		
		matrix[from][to] = matrix[from][to] + 1;
		nbArcs++;
	}

	/**
	 * @return a new graph which is the inverse graph of this.matrix
 	 */
	public AdjacencyMatrixDirectedGraph computeInverse() {
		int[][] inverseMatrix = new int[this.nbNodes][this.nbNodes];
		
		for (int i = 0; i < this.nbNodes; i++) {
			for (int j = 0; j < this.nbNodes; j++) {
				inverseMatrix[j][i] = this.matrix[i][j];
			}
		}
		
		AdjacencyMatrixDirectedGraph amInv = new AdjacencyMatrixDirectedGraph(inverseMatrix);	
		return amInv;
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
		for (int[] ints : matrix) {
			for (int anInt : ints) {
				s.append(anInt).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] matrix2 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(matrix2);
		System.out.println(am);
		System.out.println("n = "+am.getNbNodes()+ "\nm = "+am.getNbArcs() +"\n");
		
		// Successors of vertex 1 :
		System.out.println("Sucesssors of vertex 1 : ");
		List<Integer> t = am.getSuccessors(1);
		for (Integer integer : t) {
			System.out.print(integer + ", ");
		}
		
		// Predecessors of vertex 2 :
		System.out.println("\n\nPredecessors of vertex 2 : ");
		List<Integer> t2 = am.getPredecessors(2);
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}
		
		// Tests additionnels pour vérifier les méthodes
		System.out.println("\n\n=== Tests des méthodes ===");
		
		// Test isArc
		System.out.println("isArc(0, 1) : " + am.isArc(0, 1));
		System.out.println("isArc(1, 0) : " + am.isArc(1, 0)); // Direction opposée
		
		// Test addArc et removeArc
		System.out.println("\nAjout d'un arc (7, 8)");
		am.addArc(7, 8);
		System.out.println("isArc(7, 8) après ajout : " + am.isArc(7, 8));
		System.out.println("isArc(8, 7) après ajout : " + am.isArc(8, 7)); // Direction opposée
		
		System.out.println("\nSuppression de l'arc (7, 8)");
		am.removeArc(7, 8);
		System.out.println("isArc(7, 8) après suppression : " + am.isArc(7, 8));
		
		// Test avec ajout d'arcs multiples
		System.out.println("\nAjout de trois arcs (5, 6)");
		for(int i = 0; i < 3; i++) {
			am.addArc(5, 6);
		}
		System.out.println("isArc(5, 6) après ajouts multiples : " + am.isArc(5, 6));
		System.out.println("Valeur dans la matrice [5][6] : " + am.getMatrix()[5][6]);
		
		System.out.println("\nSuppression d'un arc (5, 6)");
		am.removeArc(5, 6);
		System.out.println("Valeur dans la matrice [5][6] après suppression : " + am.getMatrix()[5][6]);
		
		// Test avec indices invalides
		System.out.println("\nTest avec indices invalides :");
		System.out.println("isArc(-1, 5) : " + am.isArc(-1, 5));
		System.out.println("isArc(0, 15) : " + am.isArc(0, 15));
		
		System.out.println("\nNombre final d'arcs : " + am.getNbArcs());
		
		System.out.println("\n=== Test de computeInverse() ===");
		System.out.println("\nGraphe original :");
		System.out.println("Successeurs de 1 : " + am.getSuccessors(1));
		System.out.println("Prédécesseurs de 1 : " + am.getPredecessors(1));
		
		AdjacencyMatrixDirectedGraph inverse = am.computeInverse();
		System.out.println("\nGraphe inverse :");
		System.out.println("Successeurs de 1 dans l'inverse : " + inverse.getSuccessors(1));
		System.out.println("Prédécesseurs de 1 dans l'inverse : " + inverse.getPredecessors(1));
		
		System.out.println("\n=== Vérification de l'inversion ===");
		
		am.addArc(3, 7);
		inverse = am.computeInverse();
		
		System.out.println("Arc (3,7) dans le graphe original : " + am.isArc(3, 7));
		System.out.println("Arc (7,3) dans le graphe inverse : " + inverse.isArc(7, 3));
		System.out.println("Arc (7,3) dans le graphe original : " + am.isArc(7, 3));
		System.out.println("Arc (3,7) dans le graphe inverse : " + inverse.isArc(3, 7));
		
		System.out.println("\nNombre d'arcs dans le graphe original : " + am.getNbArcs());
		System.out.println("Nombre d'arcs dans le graphe inverse : " + inverse.getNbArcs());
	}
}
