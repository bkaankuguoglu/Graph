import java.util.ArrayList;
import java.util.Collections;

public class GraphAdjacencyMatrix extends GraphADT {
	private int numOfVertices;
	private int numOfEdges;
	private int[] vertices;
	private int[][] adjacencyMatrix;
	
	/* Constructor
	 */
	public GraphAdjacencyMatrix(int v) {
		this.numOfVertices = v;
		vertices = new int[v];
		for (int i = 0; i < v; i++) {
			vertices[i] = i+1;
		}
		adjacencyMatrix = new int[v][v];
	}
	
	@Override
	public int getNumVertices() {
		// TODO Auto-generated method stub
		return numOfVertices;
	}

	@Override
	public int getNumEdges() {
		// TODO Auto-generated method stub
		return numOfEdges;
	}

	/* Adds a vertex to the graph extending the size of the adjacency matrix, and
	 * filling corresponding row and column with 0's.
	 */
	@Override
	public void addVertex() {
		// TODO Auto-generated method stub
		int v = vertices[numOfVertices-1] + 1 ;
		int[][] newMatrix = new int[numOfVertices+1][numOfVertices+1];
		int[] newVertices = new int[numOfVertices+1];
		
		for (int i = 0; i < numOfVertices; i++){ 
			for (int j = 0; j < numOfVertices; j++){ 
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

	@Override
	public void addEdge(int v, int w) {
		int indexOfV = findIndex(v);
		int indexOfW = findIndex(w);
		// TODO Auto-generated method stub
		if((indexOfV<0)||(indexOfW<0))
			return;
		if(adjacencyMatrix[indexOfV][indexOfW] == 0){
			adjacencyMatrix[indexOfV][indexOfW] = 1;
			adjacencyMatrix[indexOfW][indexOfV] = 1;
			numOfEdges++;
		}else{
			System.out.println("The edge is already in the graph!");
		}
			
	}
	
	/* Removes a vertex from the graph decreasing the size of the adjacency matrix, and
	 * merging corresponding rows and columns after the removal of the vertex which has 
	 * recently been removed.
	 */
	
	@Override
	public void removeVertex(int v) {
		// TODO Auto-generated method stub
		boolean isHere = false;
		for (int i = 0; i < numOfVertices; i++) {
			if(vertices[i]==v){
				isHere = true;
			}
		}
		if(!isHere){
			System.out.println("The vertex " + v + " does not exist in the graph");
			return;
		}
		int index = findIndex(v);
		int[][] newMatrix = new int[numOfVertices-1][numOfVertices-1];
		int[] newVertices = new int[numOfVertices-1];
		numOfVertices--;
		for (int i = 0; i < index; i++) {
			newVertices[i]=vertices[i];
		}
		
		for (int i = index; i < numOfVertices; i++) {
			newVertices[i]=vertices[i+1];
		}
		
		for (int i = 0; i < index; i++){ 
			for (int j = 0; j < index; j++){ 
				newMatrix[i][j] = adjacencyMatrix[i][j];
			}		
		}
		
		for (int i = index; i < numOfVertices; i++){ 
			for (int j = 0; j < index; j++){ 
				newMatrix[i][j] = adjacencyMatrix[i+1][j];
			}		
		}
		
		for (int i = index; i < numOfVertices; i++){ 
			for (int j = index; j < numOfVertices; j++){ 
				newMatrix[i][j] = adjacencyMatrix[i+1][j+1];
			}		
		}
		
		for (int i = 0; i < index; i++){ 
			for (int j = index; j < numOfVertices; j++){ 
				newMatrix[i][j] = adjacencyMatrix[i][j+1];
			}		
		}
		vertices = newVertices;
		adjacencyMatrix = newMatrix;
		

	}

	@Override
	public void removeEdge(int v, int w) {
		// TODO Auto-generated method stub
		int indexOfV = findIndex(v);
		int indexOfW = findIndex(w);
		if((indexOfV<0)||(indexOfW<0))
			return;
		if(adjacencyMatrix[indexOfV][indexOfW] == 1){
			adjacencyMatrix[indexOfV][indexOfW] = 0;
			adjacencyMatrix[indexOfW][indexOfV] = 0;
			numOfEdges--;
			System.out.println("The edge (" +v+ "," +w+") is removed!");
			
		}else{
			System.out.println("The edge (" +v+ "," +w+") does not exist in the list!");
		}
	}
	
	public int findIndex(int v){
		int index = 0;
		for (int i = 0; i < vertices.length; i++) {
			if(vertices[i]==v)
				index = i;
		}		
		return index;
		
	}

	@Override
	public ArrayList<Integer> getNeighbours(int v) {
		// TODO Auto-generated method stub
		int index = findIndex(v);
		ArrayList<Integer> listOfNeighbors = new ArrayList<Integer>();
		for (int i = 0; i < numOfVertices; i++) {
			if(adjacencyMatrix[index][i]==1){
				listOfNeighbors.add(vertices[i]);
			}
			
		}
		//System.out.println("Neighbour vertexes of the Vertex " + v + " is/are ");
		return listOfNeighbors;
	}

	@Override
	public ArrayList<Integer> degreeSequence() {
		// TODO Auto-generated method stub
		ArrayList<Integer> listOfDegrees = new ArrayList<Integer>();
		for (int i = 0; i < numOfVertices; i++) {
			listOfDegrees.add(degree(vertices[i]));
		}
		Collections.sort(listOfDegrees,Collections.reverseOrder());
		System.out.println(listOfDegrees);
		return listOfDegrees;
	}
	
	public int degree(int v){
		int deg = getNeighbours(v).size();		
		return deg;
	}

	@Override
	public ArrayList<Integer> verticesWithinDistance(int v) {
		ArrayList<Integer> listOfNeighbours = getNeighbours(v);
		ArrayList<Integer> listOfVertices = new ArrayList<Integer>();
		// TODO Auto-generated method stub
		for (int i = 0; i < listOfNeighbours.size(); i++) {
			int neighbour = listOfNeighbours.get(i);
			ArrayList<Integer> distanceVertices = getNeighbours(neighbour);
			for (int j = 0; j < distanceVertices.size(); j++) {
				int distance = distanceVertices.get(j);
				if((!listOfVertices.contains(distance))&&(distance!=v))
					listOfVertices.add(distance);
			}		
		}
		listOfVertices.removeAll(listOfNeighbours);
		System.out.println(listOfVertices);
		return listOfVertices;
	}

	@Override
	public String showAsMatrix() {
		// TODO Auto-generated method stub
		String s = "------";
		for (int i = 0; i < getNumVertices(); i++) {
			s+= "--";
		}
		s += "-\n";
		s += "| X | ";
		for (int i = 0; i < vertices.length; i++) {
			s+= vertices[i] + " ";
		}
		s+="|\n";
		s+= "------";
		for (int i = 0; i < getNumVertices(); i++) {
			s+= "--";
		}
		s += "-\n";

		for (int i = 0; i < adjacencyMatrix.length; i++) {
			s+= "| "+ vertices[i] + " | ";
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				s += adjacencyMatrix[i][j] + " ";
			}
			s+="|\n";
		}
		s+= "------";
		for (int i = 0; i < getNumVertices(); i++) {
			s+= "--";
		}
		s += "-\n";
		System.out.println(s);
		return s;
	}

	@Override
	public String showAsAdjacencyList() {
		// TODO Auto-generated method stub
		String s = "";
		for (int i = 0; i < numOfVertices; i++) {
			s += vertices[i] + " -> " + getNeighbours(vertices[i]);
			System.out.println(vertices[i] + " -> " + getNeighbours(vertices[i]));
		}
		return s;
		}

}
