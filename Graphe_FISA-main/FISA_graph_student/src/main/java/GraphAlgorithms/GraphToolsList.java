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
			
			System.out.print(currentNode + " ");
			
			DirectedNode node = graph.getNodes().get(currentNode);
			for (int i = 0; i < graph.getNbNodes(); i++) {
				if (graph.isArc(node, graph.getNodes().get(i)) && !visited.contains(i)) {
					// Ajouter le voisin non visité à la file et le marquer comme visité
					fifo.add(i);
					visited.add(i);
				}
			}
		}
		
		System.out.println(); // Nouvelle ligne après l'affichage
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
	
	/**
	 * Exploration complète du graphe en utilisant le parcours en profondeur (DFS) récursif
	 * Lance l'exploration depuis les sommets non visités
	 * @param graph le graphe à explorer
	 * @return la liste des nœuds visités dans l'ordre DFS
	 */
	public static List<Integer> explorerGraphe(AdjacencyListDirectedGraph graph) {
		int nbNodes = graph.getNbNodes();
		visite = new int[nbNodes];
		debut = new int[nbNodes];
		fin = new int[nbNodes];
		order_CC = new ArrayList<>();
		cpt = 0;
		
		for (int i = 0; i < nbNodes; i++) {
			visite[i] = 0;
		}
		
		System.out.println("=== Parcours DFS récursif ===");
		System.out.print("Ordre de visite DFS : ");
		
		for (int i = 0; i < nbNodes; i++) {
			if (visite[i] == 0) {
				explorerSommet(graph, i);
			}
		}
		
		System.out.println();
		return order_CC;
	}
	
	/**
	 * Exploration récursive en profondeur d'un sommet et de ses voisins
	 * @param graph le graphe à explorer
	 * @param sommet le sommet à explorer
	 */
	private static void explorerSommet(AdjacencyListDirectedGraph graph, int sommet) {
		visite[sommet] = 1;
		debut[sommet] = cpt++;
		order_CC.add(sommet);
		
		System.out.print(sommet + " ");
		
		DirectedNode node = graph.getNodes().get(sommet);
		for (int i = 0; i < graph.getNbNodes(); i++) {
			if (graph.isArc(node, graph.getNodes().get(i)) && visite[i] == 0) {
				explorerSommet(graph, i);
			}
		}
		
		fin[sommet] = cpt++;
	}


	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		System.out.println(al);

		System.out.println("\n" + "=".repeat(60));
		System.out.println("TEST DU PARCOURS BFS");
		System.out.println("=".repeat(60));
		
		System.out.println("\n>>> Test BFS à partir du nœud 0 :");
		List<Integer> bfsResult0 = BFS(al, 0);
		System.out.println("Résultat sous forme de liste : " + bfsResult0);
		System.out.println("Nombre de nœuds visités : " + bfsResult0.size() + "/" + al.getNbNodes());
		
		System.out.println("\n>>> Test BFS à partir du nœud 1 :");
		List<Integer> bfsResult1 = BFS(al, 1);
		
		System.out.println("\n>>> Test BFS à partir du nœud 7 :");
		List<Integer> bfsResult7 = BFS(al, 7);
		
		System.out.println("\n" + "=".repeat(60));
		System.out.println("TEST DU PARCOURS DFS RÉCURSIF");
		System.out.println("=".repeat(60));
		
		List<Integer> dfsResult = explorerGraphe(al);
		System.out.println("Résultat sous forme de liste : " + dfsResult);
		System.out.println("Nombre de nœuds visités : " + dfsResult.size() + "/" + al.getNbNodes());
		
		System.out.println("\n=== Comparaison BFS vs DFS ===");
		System.out.println("BFS depuis nœud 0 : " + bfsResult0);
		System.out.println("DFS complet       : " + dfsResult);
		
		System.out.println("\n" + "=".repeat(60));
		System.out.println("VÉRIFICATION DES PROPRIÉTÉS BFS");
		System.out.println("=".repeat(60));
		
		verifyBFSOrder(al, bfsResult0, 0);
		
		// Test avec un graphe simple pour démonstration
		System.out.println("\n" + "=".repeat(60));
		System.out.println("TEST AVEC UN GRAPHE SIMPLE (démonstration)");
		System.out.println("=".repeat(60));
		testSimpleGraph();
	}
	
	/**
	 */
	private static void testSimpleGraph() {
		// Créer un graphe simple : 0->1, 0->2, 1->3, 2->3
		int[][] simpleMatrix = {
			{0, 1, 1, 0},  // 0 -> 1, 2
			{0, 0, 0, 1},  // 1 -> 3
			{0, 0, 0, 1},  // 2 -> 3
			{0, 0, 0, 0}   // 3 (aucun successeur)
		};
		
		System.out.println("Graphe simple (matrice 4x4) :");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(simpleMatrix[i][j] + " ");
			}
		}
		System.out.println("\nReprésentation du graphe :");
		System.out.println("0 -> 1, 2");
		System.out.println("1 -> 3");
		System.out.println("2 -> 3");
		System.out.println("3 -> (aucun)");
		
		AdjacencyListDirectedGraph simpleGraph = new AdjacencyListDirectedGraph(simpleMatrix);
		
		System.out.println("\n>>> BFS sur le graphe simple à partir du nœud 0 :");
		List<Integer> simpleBFS = BFS(simpleGraph, 0);
		
		System.out.println("\n>>> DFS sur le graphe simple :");
		List<Integer> simpleDFS = explorerGraphe(simpleGraph);
		
		System.out.println("\nComparaison BFS vs DFS sur graphe simple :");
		System.out.println("BFS : " + simpleBFS + " (largeur d'abord - niveau par niveau)");
		System.out.println("DFS : " + simpleDFS + " (profondeur d'abord - va au bout d'un chemin)");
		
		System.out.println("\nAnalyse de l'ordre BFS :");
		System.out.println("- Niveau 0 : nœud 0 (départ)");
		System.out.println("- Niveau 1 : nœuds 1, 2 (voisins directs de 0)");
		System.out.println("- Niveau 2 : nœud 3 (voisin de 1 et 2)");
		
		System.out.println("\nAnalyse de l'ordre DFS :");
		System.out.println("- Commence par 0, va vers 1, puis vers 3 (profondeur max)");
		System.out.println("- Remonte et explore 2 (autre voisin de 0)");
		System.out.println("Ordre attendu DFS : 0 1 3 2 (profondeur d'abord)");
	}
	
	/**
	 * Méthode pour vérifier que l'ordre BFS respecte bien la propriété de parcours par niveaux
	 */
	private static void verifyBFSOrder(AdjacencyListDirectedGraph graph, List<Integer> bfsOrder, int startNode) {
		System.out.println("Vérification de l'ordre BFS à partir du nœud " + startNode + " :");
		System.out.println("Ordre obtenu : " + bfsOrder);
		
		// Afficher les successeurs directs pour comprendre l'ordre
		System.out.println("\nAnalyse du graphe :");
		for (Integer node : bfsOrder) {
			System.out.print("Nœud " + node + " -> successeurs : ");
			DirectedNode currentNode = graph.getNodes().get(node);
			for (int i = 0; i < graph.getNbNodes(); i++) {
				if (graph.isArc(currentNode, graph.getNodes().get(i))) {
					System.out.print(i + " ");
				}
			}
			System.out.println();
		}
		
		System.out.println("\n✓ Vérifiez que l'ordre respecte le principe BFS :");
		System.out.println("  - Tous les voisins directs du nœud de départ sont visités avant leurs voisins");
		System.out.println("  - Le parcours se fait niveau par niveau");
	}
}
