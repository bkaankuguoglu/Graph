
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			GraphAdjacencyMatrix graph1 = new GraphAdjacencyMatrix(5);
			GraphAdjacencyList graph2 = new GraphAdjacencyList(5);
			
	/*
	 * Test cases for adjacency matrix implementation of graph.
	 * 		
	 */
			
			System.out.println("***************************************");
			System.out.println("* Part 1: Adjacency Matrix Test Cases *");
			System.out.println("***************************************\n");

			System.out.println("Construct a graph with 5 vertices.");			
			graph1.showAsMatrix();		
			graph1.addEdge(2, 1);
			graph1.addEdge(2, 5);
			graph1.addEdge(3, 1);
			graph1.addEdge(4, 3);
			graph1.addEdge(4, 1);
			graph1.addEdge(3, 5);

			
			System.out.println("The graph is filled with the edges: "
					+ "\n(2,1), (2,5), (3,1), (4,3), (4,1) and (3,5)");
			System.out.println("\nBefore removal of 2");
			graph1.showAsMatrix();
			
			System.out.println("Removal of the edges (4,5) and (2,1)");
			graph1.removeEdge(4, 5);
			graph1.removeEdge(2, 1);
			graph1.showAsMatrix();

			graph1.removeVertex(2);
			System.out.println("\nAfter removal of 2");
			graph1.showAsMatrix();
			
			System.out.println("\nNeighbours of the vertex 1:");
			System.out.println(graph1.getNeighbours(1)); //takes index
						
			System.out.println("\nDegree Sequence in descending order:");
			graph1.degreeSequence();
			
			System.out.println("\nVertices within the distance 2 to the vertex 1:");
			graph1.verticesWithinDistance(1);
			
			
			System.out.println("\nAdd Vertex:");
			graph1.addVertex();
			graph1.showAsMatrix();
			
			System.out.println("\nAdddition of the edges (1,6), (3,6), and (4,5):");
			graph1.addEdge(1, 6);
			graph1.addEdge(3, 6);
			graph1.addEdge(4, 5);		
			graph1.showAsMatrix();
			
			
			System.out.println("\nShow the adjacency matrix graph as adjacency list graph");
			graph1.showAsAdjacencyList();


	/*
	 * Test cases for adjacency list implementation of graph.
	 * 		
	 */
						
			
			System.out.println("\n*************************************");
			System.out.println("* Part 2: Adjacency List Test Cases *");
			System.out.println("*************************************\n");
			System.out.println("Construct a graph with 5 vertices.");			
			graph2.showAsAdjacencyList();

			graph2.addEdge(2, 1);
			graph2.addEdge(1, 3);
			graph2.addEdge(1, 5);
			graph2.addEdge(1, 4);
			
			System.out.println("\nAfter the adjacent list graph is filled with the edges"
					+ "\n (2,1), (1,3), (1,5) and (1,4)");
			graph2.showAsAdjacencyList();

			System.out.println("\nAfter removal of the edge (2,1)");
			graph2.removeEdge(2, 1);
			graph2.showAsAdjacencyList();

			System.out.println("\nNeighbours of 1:");
			graph2.getNeighbours(1);
			
			System.out.println("\nDegrees Sequence in descending order:");
			graph2.degreeSequence();
			
			System.out.println("\nVertices within the distance 2 to the vertex 4");
			graph2.verticesWithinDistance(4);
	
			System.out.println("\nRemoval of 4");
			graph2.removeVertex(4);
			graph2.showAsAdjacencyList();

			System.out.println("\nAdd vertex");
			graph2.addVertex();
			graph2.showAsAdjacencyList();
			
			System.out.println("\nAddition of the edges ((3,6), (1,6) and (5,3):");
			graph2.addEdge(3, 6);
			graph2.addEdge(1, 6);
			graph2.addEdge(5, 3);
			graph2.showAsAdjacencyList();
			
			System.out.println("\nShow the adjacency list graph as adjacency matrix graph");
			graph2.showAsMatrix();

			System.out.println("\n************************************");
			System.out.println("*             The End.             *");
			System.out.println("************************************\n");
			
	}

}
