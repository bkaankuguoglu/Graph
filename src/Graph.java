
import java.util.*;

public class Graph {
	// TODO : complete the graph data structure
	private static final Comparator<Integer> ASC = Comparator.naturalOrder(); // ascending
																				// comparator
	private static final Comparator<Integer> DESC = ASC.reversed(); // descending
																	// comparator
	private int numOfVertices;
	private int numOfEdges;
	private int[] vertices;
	private int[][] adjacencyMatrix;

	// create an empty graph with V vertices
	public Graph(int v) {
		// TODO : complete this method
		this.numOfVertices = v;
		vertices = new int[v];
		for (int i = 0; i < v; i++) {
			vertices[i] = i;
		}
		adjacencyMatrix = new int[v][v];
	}

	public int degree(int v) {
		int deg = ((LinkedList<Integer>) getNeighbors(v)).size();
		return deg;
	}

	public int findIndex(int v) {
		int index = 0;
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i] == v)
				index = i;
		}
		return index;

	}

	// return the number of vertices
	public int getNumVertices() {
		// TODO : complete this method
		return numOfVertices;
	}

	// return the number of edges
	public int getNumEdges() {
		// TODO : complete this method
		return numOfEdges;
	}

	// add a new vertex to the graph
	public void addVertex() {
		// TODO : complete this method
		int v = vertices[numOfVertices - 1] + 1;
		int[][] newMatrix = new int[numOfVertices + 1][numOfVertices + 1];
		int[] newVertices = new int[numOfVertices + 1];

		for (int i = 0; i < numOfVertices; i++) {
			for (int j = 0; j < numOfVertices; j++) {
				newMatrix[i][j] = adjacencyMatrix[i][j];
			}
		}

		for (int i = 0; i < numOfVertices; i++) {
			newVertices[i] = vertices[i];

		}
		newVertices[numOfVertices] = v;

		numOfVertices++;
		vertices = newVertices;
		adjacencyMatrix = newMatrix;
	}

	// add a new edge between vertices v and w
	public void addEdge(int v, int w) {
		// TODO : complete this method
		int indexOfV = findIndex(v);
		int indexOfW = findIndex(w);
		// TODO Auto-generated method stub
		if ((indexOfV < 0) || (indexOfW < 0))
			return;
		if (adjacencyMatrix[indexOfV][indexOfW] == 0) {
			adjacencyMatrix[indexOfV][indexOfW] = 1;
			adjacencyMatrix[indexOfW][indexOfV] = 1;
			numOfEdges++;
		} else {
			System.out.println("The edge is already in the graph!");
		}
	}

	// remove the edge between vertices v and w
	public void removeEdge(int v, int w) {
		// TODO : complete this method
		int indexOfV = findIndex(v);
		int indexOfW = findIndex(w);
		if ((indexOfV < 0) || (indexOfW < 0))
			return;
		if (adjacencyMatrix[indexOfV][indexOfW] == 1) {
			adjacencyMatrix[indexOfV][indexOfW] = 0;
			adjacencyMatrix[indexOfW][indexOfV] = 0;
			numOfEdges--;

		} else {
			System.out.println("The edge (" + v + "," + w + ") does not exist in the list!");
		}

	}

	// return the list of vertices which are adjacent to vertex v
	public Iterable<Integer> getNeighbors(int v) {
		// TODO : complete this method
		int index = findIndex(v);
		LinkedList<Integer> listOfNeighbors = new LinkedList<Integer>();
		for (int i = 0; i < numOfVertices; i++) {
			if (adjacencyMatrix[index][i] == 1) {
				listOfNeighbors.add(vertices[i]);
			}

		}
		return listOfNeighbors;
	}

	// return the list of the degrees of the vertices in this graph in sorted
	// order (from largest to smallest)
	public List<Integer> degreeSequence() {
		// TODO : complete this method
		LinkedList<Integer> listOfDegrees = new LinkedList<Integer>();
		for (int i = 0; i < numOfVertices; i++) {
			listOfDegrees.add(degree(vertices[i]));
		}
		Collections.sort(listOfDegrees, Collections.reverseOrder());
		return listOfDegrees;
	}

	// return the list of the vertices which are at distance 2 away from the
	// vertex v
	public List<Integer> verticesWithinDistance2(int v) {
		// TODO : complete this method
		LinkedList<Integer> listOfNeighbours = (LinkedList<Integer>) getNeighbors(v);
		LinkedList<Integer> listOfVertices = new LinkedList<Integer>();
		listOfVertices.add(v);
		LinkedList<Integer> distanceVertices;

		for (int i = 0; i < listOfNeighbours.size(); i++) {
			int neighbour = listOfNeighbours.get(i);
			distanceVertices = (LinkedList<Integer>) getNeighbors(neighbour);
			for (int j = 0; j < distanceVertices.size(); j++) {
				int distance = distanceVertices.get(j);
				if ((!listOfVertices.contains(distance)) 
						&&(!listOfNeighbours.contains(distance)) 
						&&(distance != v))
					listOfVertices.add(distance);
			}

		}	
		listOfVertices.addAll(listOfNeighbours);
		return listOfVertices;
	}

	// return the string representation of the graph in adjacency matrix format
	public String showAsMatrix() {
		// TODO : complete this method
		String s = "";

		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				s += adjacencyMatrix[i][j] + "-";
			}
			s += "\n";
		}
		
		return s;
	}

	// return the string representation of the graph in adjacency list format
	public String showAsAdjacencyList() {
		// TODO : complete this method
		LinkedList<Integer> neighbors;
		String s = "";
		for (int i = 0; i < vertices.length; i++) {
			neighbors = (LinkedList<Integer>) getNeighbors(vertices[i]);
			for (int j = 0; j < neighbors.size(); j++) {
				s += neighbors.get(j) + "-";
			}
			s += "-\n";
		}
		return s;
	}
}