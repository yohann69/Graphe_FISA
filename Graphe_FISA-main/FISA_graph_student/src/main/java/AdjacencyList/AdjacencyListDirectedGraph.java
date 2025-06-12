package AdjacencyList;

import java.util.ArrayList;
import java.util.List;



import GraphAlgorithms.GraphTools;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;



public class AdjacencyListDirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	private static int _DEBBUG =0;
	
	protected List<DirectedNode> nodes; // list of the nodes in the graph
	protected List<Arc> arcs; // list of the arcs in the graph
    protected int nbNodes; // number of nodes
    protected int nbArcs; // number of arcs
	
    

    
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
 

	public AdjacencyListDirectedGraph(){
		this.nodes = new ArrayList<DirectedNode>();
		this.arcs= new ArrayList<Arc>();
		this.nbNodes = 0;
	    this.nbArcs = 0;		
	}
	
	public AdjacencyListDirectedGraph(List<DirectedNode> nodes,List<Arc> arcs) {
		this.nodes = nodes;
		this.arcs= arcs;
        this.nbNodes = nodes.size();
        this.nbArcs = arcs.size();                
    }

    public AdjacencyListDirectedGraph(int[][] matrix) {
        this.nbNodes = matrix.length;
        this.nodes = new ArrayList<DirectedNode>();
        this.arcs= new ArrayList<Arc>();
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new DirectedNode(i));
        }
        
        for (DirectedNode n1 : this.getNodes()) {
            for (int j = 0; j < matrix[n1.getLabel()].length; j++) {
            	DirectedNode n2 = this.getNodes().get(j);
                if (matrix[n1.getLabel()][j] != 0) {
                	Arc a = new Arc(n1,n2);
                    n1.addArc(a);
                    this.arcs.add(a);                    
                    n2.addArc(a);
                    this.nbArcs++;
                }
            }
        }
    }

    public AdjacencyListDirectedGraph(AdjacencyListDirectedGraph g) {
        super();
        this.nodes = new ArrayList<>();
        this.arcs= new ArrayList<Arc>();
        this.nbNodes = g.getNbNodes();
        this.nbArcs = g.getNbArcs();
        
        for(DirectedNode n : g.getNodes()) {
            this.nodes.add(new DirectedNode(n.getLabel()));
        }
        
        for (Arc a1 : g.getArcs()) {
        	this.arcs.add(a1);
        	DirectedNode new_n   = this.getNodes().get(a1.getFirstNode().getLabel());
        	DirectedNode other_n = this.getNodes().get(a1.getSecondNode().getLabel());
        	Arc a2 = new Arc(a1.getFirstNode(),a1.getSecondNode(),a1.getWeight());
        	new_n.addArc(a2);
        	other_n.addArc(a2);
        }  

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    /**
     * Returns the list of nodes in the graph
     */
    public List<DirectedNode> getNodes() {
        return nodes;
    }
    
    /**
     * Returns the list of nodes in the graph
     */
    public List<Arc> getArcs() {
        return arcs;
    }

    /**
     * Returns the number of nodes in the graph
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
	 * @return true if arc (from,to) exists in the graph
 	 */
    public boolean isArc(DirectedNode from, DirectedNode to) {
        if (from == null || to == null) {
            return false;
        }
        
        DirectedNode nodeFrom = getNodeOfList(from);
        
        for (Arc arc : nodeFrom.getArcSucc()) {
            if (arc.getSecondNode().equals(to)) {
                return true;
            }
        }
        
        return false;
    }

    /**
	 * Removes the arc (from,to), if it exists. And remove this arc and the inverse in the list of arcs from the two extremities (nodes)
 	 */
    public void removeArc(DirectedNode from, DirectedNode to) {
    	if(isArc(from, to)){
    		DirectedNode nodeFrom = getNodeOfList(from);
    		DirectedNode nodeTo = getNodeOfList(to);
    		
    		Arc arcToRemove = null;
    		
    		for (Arc arc : nodeFrom.getArcSucc()) {
    			if (arc.getSecondNode().equals(nodeTo)) {
    				arcToRemove = arc;
    				break;
    			}
    		}
    		
    		if (arcToRemove != null) {
    			nodeFrom.getArcSucc().remove(arcToRemove);
    			
    			nodeTo.getArcPred().remove(arcToRemove);
    			
    			this.arcs.remove(arcToRemove);
    			
    			this.nbArcs--;
    		}
    	}
    }

    /**
	* Adds the arc (from,to) if it is not already present in the graph, requires the existing of nodes from and to. 
	* And add this arc to the incident list of both extremities (nodes) and into the global list "arcs" of the graph.   	 
  	* On non-valued graph, every arc has a weight equal to 0.
 	*/
    public void addArc(DirectedNode from, DirectedNode to) {
    	if(!isArc(from, to)){
    		DirectedNode nodeFrom = getNodeOfList(from);
    		DirectedNode nodeTo = getNodeOfList(to);
    		
    		Arc newArc = new Arc(nodeFrom, nodeTo, 0); // Poids 0 par défaut pour graphe non valué
    		
    		nodeFrom.addArc(newArc); // Ajoute à arcSucc de 'from'
    		nodeTo.addArc(newArc);   // Ajoute à arcPred de 'to'
    		
    		this.arcs.add(newArc);
    		
    		this.nbArcs++;
    	}
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

     /**
     * @return the corresponding nodes in the list this.nodes
     */
    public DirectedNode getNodeOfList(DirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[nbNodes][nbNodes];
        
        for (DirectedNode node : this.nodes) {
            int i = node.getLabel();
            
            for (Arc arc : node.getArcSucc()) {
                int j = arc.getSecondNode().getLabel();
                
                int weight = arc.getWeight();
                if (weight == 0) {
                    weight = 1; // Pour les graphes non valués, on met 1
                }
                
                matrix[i][j] = weight;
            }
        }
        
        return matrix;
    }

    /**
	 * @return a new graph implementing IDirectedGraph interface which is the inverse graph of this
 	 */
    public AdjacencyListDirectedGraph computeInverse() {
        AdjacencyListDirectedGraph inverse = new AdjacencyListDirectedGraph();
        
        inverse.nbNodes = this.nbNodes;
        for (DirectedNode node : this.nodes) {
            inverse.nodes.add(new DirectedNode(node.getLabel()));
        }
        
        for (Arc arc : this.arcs) {
            DirectedNode originalFrom = arc.getFirstNode();
            DirectedNode originalTo = arc.getSecondNode();
            
            DirectedNode newFrom = inverse.getNodes().get(originalTo.getLabel());
            DirectedNode newTo = inverse.getNodes().get(originalFrom.getLabel());
            
            Arc inverseArc = new Arc(newFrom, newTo, arc.getWeight());
            
            newFrom.addArc(inverseArc); // Ajoute à arcSucc
            newTo.addArc(inverseArc);   // Ajoute à arcPred
            
            inverse.arcs.add(inverseArc);
        }
        
        inverse.nbArcs = this.nbArcs;
        return inverse;
    }
    
    @Override
    public String toString(){
    	StringBuilder s = new StringBuilder();
        s.append("List of nodes and their successors/predecessors :\n");
        for (DirectedNode n : this.nodes) {
            s.append("\nNode ").append(n).append(" : ");
            s.append("\nList of out-going arcs: ");
            for (Arc a : n.getArcSucc()) {
                s.append(a).append("  ");
            }
            s.append("\nList of in-coming arcs: ");
            for (Arc a : n.getArcPred()) {
                s.append(a).append("  ");
            }
            s.append("\n");
        }
        s.append("\nList of arcs :\n");
        for (Arc a : this.arcs) {
        	s.append(a).append("  ");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.afficherMatrix(Matrix);
        AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
        System.out.println(al);
        System.out.println("(n_7,n_3) is it in the graph ? " +  al.isArc(al.getNodes().get(7), al.getNodes().get(3)));
        
        System.out.println("\n=== Tests des méthodes pour graphes orientés ===");
        
        DirectedNode node0 = al.getNodes().get(0);
        DirectedNode node1 = al.getNodes().get(1);
        DirectedNode node2 = al.getNodes().get(2);
        DirectedNode node3 = al.getNodes().get(3);
        
        System.out.println("isArc(n_0, n_1) : " + al.isArc(node0, node1));
        System.out.println("isArc(n_1, n_0) : " + al.isArc(node1, node0)); // Direction opposée
        System.out.println("isArc(n_1, n_2) : " + al.isArc(node1, node2));
        
        System.out.println("\nAjout d'un arc (n_0, n_9)");
        DirectedNode node9 = al.getNodes().get(9);
        System.out.println("Avant ajout - isArc(n_0, n_9) : " + al.isArc(node0, node9));
        al.addArc(node0, node9);
        System.out.println("Après ajout - isArc(n_0, n_9) : " + al.isArc(node0, node9));
        System.out.println("Direction opposée - isArc(n_9, n_0) : " + al.isArc(node9, node0));
        System.out.println("Nombre d'arcs après ajout : " + al.getNbArcs());
        
        System.out.println("\nSuppression de l'arc (n_0, n_9)");
        al.removeArc(node0, node9);
        System.out.println("Après suppression - isArc(n_0, n_9) : " + al.isArc(node0, node9));
        System.out.println("Nombre d'arcs après suppression : " + al.getNbArcs());
        
        System.out.println("\n=== Test de conversion en matrice d'adjacence ===");
        int[][] matrix = al.toAdjacencyMatrix();
        System.out.println("Matrice d'adjacence (extrait 5x5) :");
        for (int i = 0; i < Math.min(5, matrix.length); i++) {
            for (int j = 0; j < Math.min(5, matrix[i].length); j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        
        System.out.println("\n=== Test de computeInverse() ===");
        AdjacencyListDirectedGraph inverse = al.computeInverse();
        
        System.out.println("Graphe original - Successeurs de n_1 :");
        for (Arc arc : node1.getArcSucc()) {
            System.out.print(arc.getSecondNode() + " ");
        }
        
        System.out.println("\nGraphe inverse - Successeurs de n_1 :");
        DirectedNode inverseNode1 = inverse.getNodes().get(1);
        for (Arc arc : inverseNode1.getArcSucc()) {
            System.out.print(arc.getSecondNode() + " ");
        }
        
        System.out.println("\n\nNombre d'arcs original : " + al.getNbArcs());
        System.out.println("Nombre d'arcs inverse : " + inverse.getNbArcs());
        
        System.out.println("\nVérification cohérence matrice/liste :");
        for (int i = 0; i < Math.min(3, matrix.length); i++) {
            for (int j = 0; j < Math.min(3, matrix[i].length); j++) {
                boolean matrixArc = matrix[i][j] > 0;
                boolean listArc = al.isArc(al.getNodes().get(i), al.getNodes().get(j));
                System.out.println("Arc (" + i + "->" + j + ") - Matrice: " + matrixArc + ", Liste: " + listArc + 
                                 " " + (matrixArc == listArc ? "✓" : "✗"));
            }
        }
    }
}
