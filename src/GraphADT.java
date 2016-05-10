import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/* Since we're asked to implement two different implementations of Graph ADT,
 * I created an abstract GraphADT class that holds the common method for both
 * of these implementations. The subclasses GraphAdjacencyMatrix and GraphAdjacencyList
 * inherits these method and overrides them according to their structure.
 */

public abstract class GraphADT {
	
	public abstract int getNumVertices();
	public abstract int getNumEdges();
	public abstract void addVertex();
	public abstract void addEdge(int v, int w);
	public abstract void removeVertex(int index);
	public abstract void removeEdge(int v, int w);
	public abstract List<Integer> getNeighbours(int v);
	public abstract List<Integer> degreeSequence();
	public abstract List<Integer> verticesWithinDistance(int v);
	public abstract String showAsMatrix();
	public abstract String showAsAdjacencyList();

	
}
