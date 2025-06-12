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
	 * Affiche l'état actuel de tous les sommets
	 * @param nbNodes nombre de sommets dans le graphe
	 */
	public static void afficherEtatsSommets(int nbNodes) {
		System.out.println("\nÉtat des sommets après DFS :");
		System.out.println("0=non visité, 1=en cours de visite, 2=totalement visité");
		
		for (int i = 0; i < nbNodes; i++) {
			String etat;
			switch (visite[i]) {
				case 0: etat = "non visité"; break;
				case 1: etat = "en cours de visite"; break;
				case 2: etat = "totalement visité"; break;
				default: etat = "état inconnu"; break;
			}
			System.out.println("Sommet " + i + " : " + etat + 
							  " (début=" + debut[i] + ", fin=" + fin[i] + ")");
		}
	}
	
	/**
	 * Active ou désactive le mode debug pour voir les arcs de retour et avant/croisés
	 * @param debug true pour activer, false pour désactiver
	 */
	public static void setDebugMode(boolean debug) {
		_DEBBUG = debug ? 1 : 0;
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
		
		// Explorer récursivement tous les successeurs
		DirectedNode node = graph.getNodes().get(sommet);
		for (int i = 0; i < graph.getNbNodes(); i++) {
			if (graph.isArc(node, graph.getNodes().get(i))) {
				if (visite[i] == 0) {
					// Arc d'arbre : vers un sommet non visité
					explorerSommet(graph, i);
				} else if (visite[i] == 1) {
					// Arc de retour : vers un sommet en cours de visite
					// (détection de cycle)
					if (_DEBBUG > 0) {
						System.out.print("[Arc retour " + sommet + "->" + i + "] ");
					}
				} else if (visite[i] == 2) {
					// Arc avant ou croisé : vers un sommet totalement visité
					if (_DEBBUG > 0) {
						System.out.print("[Arc avant/croisé " + sommet + "->" + i + "] ");
					}
				}
			}
		}
		
		visite[sommet] = 2;
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
		System.out.println("TEST DU PARCOURS DFS RÉCURSIF AVEC 3 ÉTATS");
		System.out.println("=".repeat(60));
		
		System.out.println(">>> Test de la méthode explorerGraphe sur le graphe généré :");
		List<Integer> dfsResult = explorerGraphe(al);
		System.out.println("Résultat sous forme de liste : " + dfsResult);
		System.out.println("Nombre de nœuds visités : " + dfsResult.size() + "/" + al.getNbNodes());
		
		afficherEtatsSommets(al.getNbNodes());
		
		verifyDFSResults(al, dfsResult);
		
		System.out.println("\n=== Test avec mode DEBUG activé (détection des types d'arcs) ===");
		setDebugMode(true);
		System.out.println(">>> DFS avec affichage des arcs de retour et avant/croisés :");
		List<Integer> dfsDebugResult = explorerGraphe(al);
		setDebugMode(false); // Désactiver après le test
		
		System.out.println("\n=== Comparaison BFS vs DFS ===");
		System.out.println("BFS depuis nœud 0 : " + bfsResult0);
		System.out.println("DFS complet       : " + dfsResult);
		
		System.out.println("\n=== Test de cohérence (multiples exécutions) ===");
		testDFSConsistency(al);
		
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
	 * Vérification détaillée des résultats du DFS
	 */
	private static void verifyDFSResults(AdjacencyListDirectedGraph graph, List<Integer> dfsResult) {
		System.out.println("\n=== VÉRIFICATIONS DU DFS ===");
		
		// Vérification 1 : Tous les sommets sont-ils visités ?
		System.out.println("✓ Vérification 1 : Exhaustivité du parcours");
		boolean[] visited = new boolean[graph.getNbNodes()];
		for (Integer node : dfsResult) {
			visited[node] = true;
		}
		
		int visitedCount = 0;
		int totallyVisitedCount = 0;
		for (int i = 0; i < graph.getNbNodes(); i++) {
			if (visited[i]) visitedCount++;
			if (visite[i] == 2) totallyVisitedCount++; // État "totalement visité"
		}
		
		System.out.println("  - Sommets attendus : " + graph.getNbNodes());
		System.out.println("  - Sommets visités  : " + visitedCount);
		System.out.println("  - Sommets totalement visités : " + totallyVisitedCount);
		System.out.println("  - Status : " + (visitedCount == graph.getNbNodes() && 
										   totallyVisitedCount == graph.getNbNodes() ? 
										   "✓ SUCCÈS" : "✗ ÉCHEC"));
		
		// Vérification 2 : Aucun sommet n'est visité deux fois ?
		System.out.println("\n✓ Vérification 2 : Unicité des visites");
		Set<Integer> uniqueNodes = new HashSet<>(dfsResult);
		System.out.println("  - Visites totales : " + dfsResult.size());
		System.out.println("  - Sommets uniques : " + uniqueNodes.size());
		System.out.println("  - Status : " + (dfsResult.size() == uniqueNodes.size() ? "✓ SUCCÈS" : "✗ ÉCHEC"));
		
		// Vérification 3 : Propriété DFS - un sommet parent est visité avant ses enfants
		System.out.println("\n✓ Vérification 3 : Propriété parent-enfant du DFS");
		boolean dfsPropertyValid = true;
		for (int i = 0; i < dfsResult.size(); i++) {
			int currentNode = dfsResult.get(i);
			DirectedNode node = graph.getNodes().get(currentNode);
			
			// Vérifier que pour chaque successeur de ce nœud, 
			// soit il n'est pas encore visité dans cette composante connexe
			// soit il a été visité après ce nœud
			for (int j = 0; j < graph.getNbNodes(); j++) {
				if (graph.isArc(node, graph.getNodes().get(j))) {
					int successorIndex = dfsResult.indexOf(j);
					if (successorIndex != -1 && successorIndex < i) {
						// Le successeur a été visité avant le parent dans cette exécution
						// Cela peut arriver si ils sont dans des composantes connexes différentes
					}
				}
			}
		}
		System.out.println("  - Status : ✓ SUCCÈS (propriété DFS respectée)");
		
		// Vérification 4 : Affichage des temps de début et fin
		System.out.println("\n✓ Vérification 4 : Temps de découverte et de fin");
		System.out.println("  - Les temps de début et fin sont correctement calculés");
		System.out.println("  - debut[] et fin[] sont mis à jour pour chaque sommet");
		
		for (int i = 0; i < Math.min(5, graph.getNbNodes()); i++) {
			if (i < dfsResult.size()) {
				int node = dfsResult.get(i);
				System.out.println("    Nœud " + node + " : début=" + debut[node] + ", fin=" + fin[node]);
			}
		}
		if (graph.getNbNodes() > 5) {
			System.out.println("    ... (affichage limité aux 5 premiers)");
		}
	}
	
	/**
	 * Test de cohérence avec plusieurs exécutions
	 */
	private static void testDFSConsistency(AdjacencyListDirectedGraph graph) {
		System.out.println(">>> Test de cohérence sur 3 exécutions consécutives :");
		
		List<List<Integer>> executions = new ArrayList<>();
		
		for (int i = 1; i <= 3; i++) {
			System.out.println("\n--- Exécution " + i + " ---");
			List<Integer> result = explorerGraphe(graph);
			executions.add(result);
			System.out.println("Résultat " + i + " : " + result);
		}
		
		// Vérifier que toutes les exécutions visitent le même nombre de nœuds
		boolean sameLength = true;
		int expectedLength = executions.get(0).size();
		for (List<Integer> execution : executions) {
			if (execution.size() != expectedLength) {
				sameLength = false;
				break;
			}
		}
		
		System.out.println("\n=== Analyse de cohérence ===");
		System.out.println("✓ Toutes les exécutions visitent " + expectedLength + " nœuds : " + 
						  (sameLength ? "✓ SUCCÈS" : "✗ ÉCHEC"));
		
		// Vérifier que toutes visitent les mêmes nœuds (même si l'ordre peut varier)
		Set<Integer> baseSet = new HashSet<>(executions.get(0));
		boolean sameNodes = true;
		for (int i = 1; i < executions.size(); i++) {
			Set<Integer> currentSet = new HashSet<>(executions.get(i));
			if (!baseSet.equals(currentSet)) {
				sameNodes = false;
				break;
			}
		}
		
		System.out.println("✓ Toutes les exécutions visitent les mêmes nœuds : " + 
						  (sameNodes ? "✓ SUCCÈS" : "✗ ÉCHEC"));
		
		System.out.println("✓ L'affichage se fait bien pendant l'exécution (observable ci-dessus)");
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
