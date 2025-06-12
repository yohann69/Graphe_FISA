package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import GraphAlgorithms.GraphTools;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;


public class AdjacencyListUndirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	protected List<UndirectedNode> nodes; // list of the nodes in the graph
	protected List<Edge> edges; // list of the edges in the graph
    protected int nbNodes; // number of nodes
    protected int nbEdges; // number of edges

    
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
    
	public AdjacencyListUndirectedGraph() {
		 this.nodes = new ArrayList<UndirectedNode>();
		 this.edges = new ArrayList<Edge>();
		 this.nbNodes = 0;
	     this.nbEdges = 0;
	}
	
		
	public AdjacencyListUndirectedGraph(List<UndirectedNode> nodes,List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
        this.nbNodes = nodes.size();
        this.nbEdges = edges.size();
        
    }

    public AdjacencyListUndirectedGraph(int[][] matrix) {
        this.nbNodes = matrix.length;
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new UndirectedNode(i));
        }
        for (UndirectedNode n1 : this.getNodes()) {
            for (int j = n1.getLabel(); j < matrix[n1.getLabel()].length; j++) {
            	UndirectedNode n2 = this.getNodes().get(j);
                if (matrix[n1.getLabel()][j] != 0) {
                    Edge e1 = new Edge(n1,n2);
                    n1.addEdge(e1);
                    this.edges.add(e1);
                	n2.addEdge(new Edge(n2,n1));
                    this.nbEdges++;
                }
            }
        }
    }

    public AdjacencyListUndirectedGraph(AdjacencyListUndirectedGraph g) {
        super();
        this.nbNodes = g.getNbNodes();
        this.nbEdges = g.getNbEdges();
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();
        
        
        for (UndirectedNode n : g.getNodes()) {
            this.nodes.add(new UndirectedNode(n.getLabel()));
        }
        
        for (Edge e : g.getEdges()) {
        	this.edges.add(e);
        	UndirectedNode new_n   = this.getNodes().get(e.getFirstNode().getLabel());
        	UndirectedNode other_n = this.getNodes().get(e.getSecondNode().getLabel());
        	new_n.addEdge(new Edge(e.getFirstNode(),e.getSecondNode(),e.getWeight()));
        	other_n.addEdge(new Edge(e.getSecondNode(),e.getFirstNode(),e.getWeight()));
        }        
    }
    

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    
    /**
     * Returns the list of nodes in the graph
     */
    public List<UndirectedNode> getNodes() {
        return this.nodes;
    }
    
    /**
     * Returns the list of edges in the graph
     */
    public List<Edge> getEdges() {
        return this.edges;
    }

    /**
     * Returns the number of nodes in the graph
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
    
    /**
     * @return the number of edges in the graph
     */ 
    public int getNbEdges() {
        return this.nbEdges;
    }

    /**
     * @return true if there is an edge between x and y
     */
    public boolean isEdge(UndirectedNode x, UndirectedNode y) {      	
        if (x == null || y == null) {
            return false;
        }
        
        UndirectedNode nodeX = getNodeOfList(x);
        
        for (Edge edge : nodeX.getIncidentEdges()) {
            if (edge.getSecondNode().equals(y)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Removes edge (x,y) if there exists one. And remove this edge and the inverse in the list of edges from the two extremities (nodes)
     */
    public void removeEdge(UndirectedNode x, UndirectedNode y) {
    	if(isEdge(x,y)){
    		UndirectedNode nodeX = getNodeOfList(x);
    		UndirectedNode nodeY = getNodeOfList(y);
    		
    		Edge edgeToRemove = null;
    		
    		for (Edge edge : nodeX.getIncidentEdges()) {
    			if (edge.getSecondNode().equals(nodeY)) {
    				edgeToRemove = edge;
    				break;
    			}
    		}
    		
    		if (edgeToRemove != null) {
    			nodeX.getIncidentEdges().remove(edgeToRemove);
    			
    			Edge edgeInverse = new Edge(nodeY, nodeX, edgeToRemove.getWeight());
    			nodeY.getIncidentEdges().remove(edgeInverse);
    			
    			this.edges.remove(edgeToRemove);
    			
    			this.nbEdges--;
    		}
    	}
    }

    /**
     * Adds edge (x,y) if it is not already present in the graph, requires that nodes x and y already exist. 
     * And adds this edge to the incident list of both extremities (nodes) and into the global list "edges" of the graph.
     * In non-valued graph, every edge has a cost equal to 0.
     */
    public void addEdge(UndirectedNode x, UndirectedNode y) {
    	if(!isEdge(x,y)){
    		UndirectedNode nodeX = getNodeOfList(x);
    		UndirectedNode nodeY = getNodeOfList(y);
    		
    		Edge newEdge = new Edge(nodeX, nodeY, 0); // Poids 0 par défaut pour graphe non valué
    		
    		nodeX.addEdge(newEdge);
    		
    		nodeY.addEdge(newEdge);
    		
    		this.edges.add(newEdge);
    		
    		this.nbEdges++;
    	}
    }

    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------
    
    

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public UndirectedNode getNodeOfList(UndirectedNode v) {
        return this.getNodes().get(v.getLabel());
    }
    
    /**
     * @return a matrix representation of the graph 
     */
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[nbNodes][nbNodes];
        
        for (UndirectedNode node : this.nodes) {
            int i = node.getLabel();
            
            for (Edge edge : node.getIncidentEdges()) {
                int j = edge.getSecondNode().getLabel();
                
                int weight = edge.getWeight();
                if (weight == 0) {
                    weight = 1; // Pour les graphes non valués, on met 1
                }
                
                matrix[i][j] = weight;
                matrix[j][i] = weight; // Symétrie pour graphe non orienté
            }
        }
        
        return matrix;
    }

    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("List of nodes and their neighbours :\n");
        for (UndirectedNode n : this.nodes) {
            s.append("Node ").append(n).append(" : ");
            s.append("\nList of incident edges : ");
            for (Edge e : n.getIncidentEdges()) {
                s.append(e).append("  ");
            }
            s.append("\n");            
        }
        s.append("\nList of edges :\n");
        for (Edge e : this.edges) {
        	s.append(e).append("  ");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        GraphTools.afficherMatrix(mat);
        AdjacencyListUndirectedGraph al = new AdjacencyListUndirectedGraph(mat);
        System.out.println(al);        
        System.out.println("(n_2,n_5) is it in the graph ? " +  al.isEdge(al.getNodes().get(2), al.getNodes().get(5)));
        
        System.out.println("\n=== Tests des méthodes ===");
        
        // Test isEdge
        UndirectedNode node0 = al.getNodes().get(0);
        UndirectedNode node1 = al.getNodes().get(1);
        UndirectedNode node2 = al.getNodes().get(2);
        
        System.out.println("isEdge(n_0, n_1) : " + al.isEdge(node0, node1));
        System.out.println("isEdge(n_1, n_2) : " + al.isEdge(node1, node2));
        
        // Test addEdge
        System.out.println("\nAjout d'une arête (n_0, n_9)");
        UndirectedNode node9 = al.getNodes().get(9);
        System.out.println("Avant ajout - isEdge(n_0, n_9) : " + al.isEdge(node0, node9));
        al.addEdge(node0, node9);
        System.out.println("Après ajout - isEdge(n_0, n_9) : " + al.isEdge(node0, node9));
        System.out.println("Symétrie - isEdge(n_9, n_0) : " + al.isEdge(node9, node0));
        System.out.println("Nombre d'arêtes après ajout : " + al.getNbEdges());
        
        // Test removeEdge
        System.out.println("\nSuppression de l'arête (n_0, n_9)");
        al.removeEdge(node0, node9);
        System.out.println("Après suppression - isEdge(n_0, n_9) : " + al.isEdge(node0, node9));
        System.out.println("Nombre d'arêtes après suppression : " + al.getNbEdges());
        
        System.out.println("\n=== Test de conversion en matrice d'adjacence ===");
        int[][] matrix = al.toAdjacencyMatrix();
        System.out.println("Matrice d'adjacence (extrait 5x5) :");
        for (int i = 0; i < Math.min(5, matrix.length); i++) {
            for (int j = 0; j < Math.min(5, matrix[i].length); j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        
        System.out.println("\nVérification cohérence matrice/liste :");
        for (int i = 0; i < Math.min(3, matrix.length); i++) {
            for (int j = i + 1; j < Math.min(3, matrix[i].length); j++) {
                boolean matrixEdge = matrix[i][j] > 0;
                boolean listEdge = al.isEdge(al.getNodes().get(i), al.getNodes().get(j));
                System.out.println("Arête (" + i + "," + j + ") - Matrice: " + matrixEdge + ", Liste: " + listEdge + 
                                 " " + (matrixEdge == listEdge ? "✓" : "✗"));
            }
        }
    }

}
