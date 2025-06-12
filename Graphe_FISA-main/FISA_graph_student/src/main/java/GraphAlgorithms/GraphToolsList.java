package GraphAlgorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import AdjacencyList.AdjacencyListDirectedGraph;
import AdjacencyList.AdjacencyListDirectedValuedGraph;
import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import Collection.Triple;
import Nodes_Edges.DirectedNode;
import Nodes_Edges.UndirectedNode;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------

	/**
	 * Parcours en largeur (BFS) itératif d'un graphe orienté
	 * @param graph le graphe à parcourir
	 * @param startNode le nœud de départ (par défaut 0)
	 * @return la liste des nœuds visités dans l'ordre de parcours BFS
	 */
	public static List<Integer> BFS(AdjacencyListDirectedGraph graph, int startNode) {
		List<Integer> visitedOrder = new ArrayList<>();
		Set<Integer> visited = new HashSet<>();
		Queue<Integer> fifo = new LinkedList<>();
		
		if (startNode < 0 || startNode >= graph.getNbNodes()) {
			System.out.println("Nœud de départ invalide");
			return visitedOrder;
		}
		
		fifo.add(startNode);
		visited.add(startNode);
		
		System.out.println("=== Parcours BFS à partir du nœud " + startNode + " ===");
		
		while (!fifo.isEmpty()) {
			Integer currentNode = fifo.poll();
			visitedOrder.add(currentNode);
			
			System.out.println("Visite du nœud : " + currentNode);
			
			DirectedNode node = graph.getNodes().get(currentNode);
			for (int i = 0; i < graph.getNbNodes(); i++) {
				if (graph.isArc(node, graph.getNodes().get(i)) && !visited.contains(i)) {
					// Ajouter le voisin non visité à la file et le marquer comme visité
					fifo.add(i);
					visited.add(i);
					System.out.println("  Ajout du nœud " + i + " à la file");
				}
			}
		}
		
		System.out.println("Ordre de visite BFS : " + visitedOrder);
		return visitedOrder;
	}
	
	/**
	 * Parcours en largeur (BFS) itératif d'un graphe orienté à partir du nœud 0
	 * @param graph le graphe à parcourir
	 * @return la liste des nœuds visités dans l'ordre de parcours BFS
	 */
	public static List<Integer> BFS(AdjacencyListDirectedGraph graph) {
		return BFS(graph, 0);
	}


	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		System.out.println(al);

		System.out.println("\n" + "=".repeat(50));
		System.out.println("TEST DU PARCOURS BFS");
		System.out.println("=".repeat(50));
		
		List<Integer> bfsResult = BFS(al, 0);
		System.out.println("\nRésultat final du BFS : " + bfsResult);
		System.out.println("Nombre de nœuds visités : " + bfsResult.size() + "/" + al.getNbNodes());
	}
}
