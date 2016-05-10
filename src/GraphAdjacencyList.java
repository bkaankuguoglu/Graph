import java.util.ArrayList;
import java.util.Collections;

public class GraphAdjacencyList extends GraphADT {
	private int numOfVertices;
	private int numOfEdges;
	private ArrayList<Integer> vertices;
	private ArrayList<ArrayList<Integer>> adjacencyList;
	
	public GraphAdjacencyList(int v) {
		// TODO Auto-generated constructor stub
		adjacencyList = new ArrayList<ArrayList<Integer>>();
		vertices = new ArrayList<Integer>();
		this.numOfVertices = v;
		for (int i = 1; i <= v; i++) {
			adjacencyList.add(new ArrayList<Integer>());
			vertices.add(i);
		}
	}
	
	public int findIndex(int v){
		return vertices.indexOf(v);
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

	@Override
	public void addVertex() {
		// TODO Auto-generated method stub
		int v = vertices.get(numOfVertices-1) + 1;
		ArrayList<Integer> adjacentVertices = new ArrayList<Integer>();
		adjacencyList.add(adjacentVertices);
		vertices.add(v);
		numOfVertices++;
	}

	@Override
	public void addEdge(int v, int w) {
		// TODO Auto-generated method stub
		int indexOfV = findIndex(v);
		int indexOfW = findIndex(w);
		if((indexOfV<0)||(indexOfW<0))
			return;
		if(!adjacencyList.get(indexOfV).contains(w)){
			adjacencyList.get(indexOfV).add(w);
			adjacencyList.get(indexOfW).add(v);
			numOfEdges++;
		}else
			System.out.println("The edge is already in the graph!");

	}

	@Override
	public void removeVertex(int v) {
		// TODO Auto-generated method stub
		if(!vertices.contains(v)){
			System.out.println("The vertex " + v + " does not exist in the graph");
			return;
		}
		int index = findIndex(v);
		vertices.remove(index);
		adjacencyList.remove(index);
		numOfVertices--;
		for (int i = 0; i < numOfVertices; i++) {
			int posVertex = adjacencyList.get(i).indexOf(v);
			if(posVertex>-1)
			adjacencyList.get(i).remove(posVertex);
		}
	}

	@Override
	public void removeEdge(int v, int w) {
		// TODO Auto-generated method stub
		int indexOfV = findIndex(v);
		int indexOfW = findIndex(w);
		if((indexOfV<0)||(indexOfW<0))
			return;
		if(adjacencyList.get(v-1).contains(w)){			
			for (int i = 0; i < adjacencyList.get(indexOfV).size(); i++) {
				if(w==adjacencyList.get(indexOfV).get(i))
					adjacencyList.get(indexOfV).remove(i);
			}
			for (int i = 0; i < adjacencyList.get(indexOfW).size(); i++) {
				if(v==adjacencyList.get(indexOfW).get(i))
					adjacencyList.get(indexOfW).remove(i);
			}
			numOfEdges--;
		}else
			System.out.println("The edge does not exist in the list!");
		
	}

	@Override
	public ArrayList<Integer> getNeighbours(int v) {
		// TODO Auto-generated method stub
		int indexOfV = findIndex(v);
		//System.out.println(adjacencyList.get(v-1));
		return adjacencyList.get(indexOfV);
	}

	@Override
	public ArrayList<Integer> degreeSequence() {
		// TODO Auto-generated method stub
		ArrayList<Integer> listOfDegrees = new ArrayList<Integer>();
		for (int i = 0; i < numOfVertices; i++) {
			listOfDegrees.add(degree(i+1));
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
		// TODO Auto-generated method stub
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
		int[] verticesMatrix = new int[numOfVertices];
		int[][] adjacencyMatrix = new int[numOfVertices][numOfVertices];

		for (int i = 0; i < numOfVertices; i++) {
			verticesMatrix[i] = vertices.get(i);
		}
		
		for (int i = 0; i < numOfVertices; i++) {
			for (int j = 0; j < numOfVertices; j++) {
			if(getNeighbours(vertices.get(i)).contains(vertices.get(j)))
				adjacencyMatrix[i][j] = 1;
			}
		}
		
		String s = "------";
		for (int i = 0; i < getNumVertices(); i++) {
			s+= "--";
		}
		s += "-\n";
		s += "| X | ";
		for (int i = 0; i < verticesMatrix.length; i++) {
			s+= verticesMatrix[i] + " ";
		}
		s+="|\n";
		s+= "------";
		for (int i = 0; i < getNumVertices(); i++) {
			s+= "--";
		}
		s += "-\n";

		for (int i = 0; i < adjacencyMatrix.length; i++) {
			s+= "| "+ verticesMatrix[i] + " | ";
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
			s += vertices.get(i) + " -> " + adjacencyList.get(i);
			System.out.println(vertices.get(i) + " -> " + adjacencyList.get(i));
		}
		return s;
	}

}
