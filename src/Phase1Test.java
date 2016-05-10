
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class Phase1Test {
	String border = "*******************************************\n";
	String passed = "* Passed!                                 *\n";
	String failed = "* Failed!                                 *\n";
	String test;
	
	AssertionError ae;
	Exception e;
	
	int seed, prob;
	int V, lastV, w, lastw;
	LinkedList<Edge> g, g2, lastg, lastg2;
	List<Integer> dlist, lastdlist;
	String str, laststr;
	
	public Phase1Test () {
	}

	// generates a random set of edges of the form (v, w), v0 <= v < v1, w0 <= w < w1 
	// where each edge is independently included in the set with prob/128 probability
	public LinkedList<Edge> randomEdges(int seed, int v0, int v1, int w0, int w1, int prob) {
		LinkedList<Edge> edges = new LinkedList<Edge>();
		Random rand = new Random(seed);
		
		if (w0 < v0) {
			int temp = w0;
			w0 = v0;
			v0 = temp;
			temp = w1;
			w1 = v1;
			v1 = temp;
		}
		
		for (int v = v0; v < v1; v++) {
			for (int w = (w0 > v + 1 ? w0 : (v + 1)); w < w1; w++) {
				int pick = rand.nextInt() & ((1 << 7) - 1);
				if (prob > pick) {
					edges.add(new Edge(v, w));
				}
			}
		}
		
		return edges;
	}
	
	private String graphToString(int V, LinkedList<Edge> edges) {
		LinkedList<Edge> sorted = new LinkedList<Edge>(edges);
		sorted.sort(Edge.DEF);
		StringBuilder s = new StringBuilder();
		for (Edge e : sorted)
			if (e.valid(V)) s.append(e);
		return "V = " + V + ", edges = " + ((s.length() == 0) ? "-" : s.toString());
	}
	
	private String dlistToString(List<Integer> dlist) {
		if (dlist == null) return "null";
		StringBuilder s = new StringBuilder();
		for (int d : dlist) {
			s.append(d + "-");
		}
		return s.toString();
	}
	
	private void assertSameGraph(int V, LinkedList<Edge> edges, Graph gr) {
		assertNotEquals(null, gr);
		assertEquals(V, gr.getNumVertices());
		int numEdges = 0;
		for (Edge e : edges)
			if (e.valid(V)) numEdges++; // count the number of edges
		int numEdgesGr = 0;
		for (int v = 0; v < V; v++) {
			for (int w : gr.getNeighbors(v)) {
				assertTrue(w < V);
				Edge e = new Edge(v, w);
				assertTrue(e.valid(V));
				assertTrue(edges.contains(e));
				numEdgesGr++;
			}
		}
		assertEquals(numEdges * 2, numEdgesGr);
		assertEquals(numEdges, gr.getNumEdges());
	}

	private void testConstruct() {
		for (V = 1; V <= 20; V++) {
			for (seed = 0; seed < 8; seed++) {
				for (prob = 21 - V; prob <= 128; prob*=2) {
					g = this.randomEdges(seed, 0, V, 0, V, prob);
					Graph gr = new Graph(V);
					for (Edge e : g) {
						gr.addEdge(e.v, e.w);
					}
					assertSameGraph(V, g, gr);
					// save last successful test
					lastV = V;
					lastg = g;
				}
			}
		}
	}
	
	private void testAddVertex() {
		for (int W = 1; W <= 20; W++) {
			for (seed = 0; seed < 8; seed++) {
				for (prob = 21 - W; prob <= 128; prob*=2) {
					g = this.randomEdges(seed, 0, W, 0, W, prob);
					Graph gr = new Graph(1);
					for (V = 2; V <= W; V++) { // add vertices (and edges) until V > W
						gr.addVertex();
						for (Edge e : g) {
							if (e.valid(V) && e.w == V - 1)	gr.addEdge(e.v, e.w);
						}
						assertSameGraph(V, g, gr);
						// save last successful test
						lastV = V;
						lastg = g;
					}
				}
			}
		}
	}
	
	private void testRemoveEdge() {
		for (V = 1; V <= 20; V++) {
			for (seed = 0; seed < 8; seed++) {
				for (prob = 21 - V; prob <= 128; prob*=2) {
					g = this.randomEdges(seed, 0, V, 0, V, prob);
					g2 = this.randomEdges(seed, 0, V, 0, V, prob + 21 - V);
					Graph gr = new Graph(V);
					for (Edge e : g2) {
						gr.addEdge(e.v, e.w);
					}
					boolean removed = false;
					for (Edge e : g2) { // remove g2 / g to obtain g
						if (g.contains(e)) continue;
						gr.removeEdge(e.v, e.w);
						removed = true;
					}
					if (!removed) continue;
					assertSameGraph(V, g, gr);
					// save last successful test
					lastV = V;
					lastg = g;
					lastg2 = g2;
				}
			}
		}
	}
	
	private void testDegreeSequence() {
		for (V = 1; V <= 20; V++) {
			for (seed = 0; seed < 8; seed++) {
				for (prob = 21 - V; prob <= 128; prob*=2) {
					g = this.randomEdges(seed, 0, V, 0, V, prob);
					int d[] = new int[V];
					Graph gr = new Graph(V);
					for (Edge e : g) { // compute the degree array
						gr.addEdge(e.v, e.w);
						d[e.v]++;
						d[e.w]++;
					}
					dlist = gr.degreeSequence();
					assertNotEquals(null, dlist);
					assertEquals(V, dlist.size());
					int lastdg = V;
					for (int dg : dlist) { // cross out (set to -1) each listed degree
						assertTrue(dg <= lastdg);
						lastdg = dg;
						boolean found = false;
						for (int i = 0; !found && i < V; i++) {
							if (d[i] == dg) {
								d[i] = -1;
								found = true;
							}
						}
						if (!found) fail();
					}
					// save last successful test
					lastV = V;
					lastg = g;
					lastdlist = dlist;
				}
			}
		}
	}
	
	private void testDistance2() {
		for (V = 1; V <= 20; V++) {
			for (seed = 0; seed < 8; seed++) {
				for (prob = 21 - V; prob <= 128; prob*=2) {
					g = this.randomEdges(seed, 0, V, 0, V, prob);
					Graph gr = new Graph(V);
					for (Edge e : g) {
						gr.addEdge(e.v, e.w);
					}
					for (w = 0; w < V; w++) { // mark vertices within distance 1
						boolean d[] = new boolean[V];
						d[w] = true;
						for (Edge e : g) {
							if (e.v == w) d[e.w] = true;
							if (e.w == w) d[e.v] = true;
						}
						dlist = gr.verticesWithinDistance2(w);
						assertNotEquals(null, dlist);
						boolean nd[] = new boolean[V];
						for (int d2 : dlist) { // check if all returned are within distance 2
							nd[d2] = true;
							boolean found = false;
							for (int u = 0; !found && u < V; u++) {
								if (!d[u]) continue;
								found = (u == d2 || g.contains(new Edge(u, d2)));
							}
							if (!found) fail();
						}
						for (int d2 = 0; d2 < V; d2++) { // check if others are within distance 2
							if (nd[d2]) continue;
							boolean found = false;
							for (int u = 0; !found && u < V; u++) {
								if (!d[u]) continue;
								found = (u == d2 || g.contains(new Edge(u, d2)));
							}
							if (found) fail();
						}
						// save last successful test
						lastV = V;
						lastg = g;
						lastw = w;
						lastdlist = dlist;						
					}
				}
			}
		}
	}
	
	private void testAdjMatrixString() {
		for (V = 1; V <= 20; V++) {
			for (seed = 0; seed < 8; seed++) {
				for (prob = 21 - V; prob <= 128; prob*=2) {
					g = this.randomEdges(seed, 0, V, 0, V, prob);
					Graph gr = new Graph(V);
					for (Edge e : g) {
						gr.addEdge(e.v, e.w);
					}
					assertSameGraph(V, g, gr);
					str = gr.showAsMatrix();
					assertNotEquals(null, str);
					String[] lines = str.split("\n");
					assertEquals(V, lines.length);
					for (int v = 0; v < V; v++) {
						String[] adj = lines[v].split("-");
						assertEquals(V, adj.length);
						for (int w = 0; w < V; w++) {
							assertEquals(v != w && g.contains(new Edge(v, w)), adj[w].equals("1"));
						}
					}
					// save last successful test
					lastV = V;
					lastg = g;
					laststr = str;
				}
			}
		}
	}
	
	private void testAdjListString() {
		for (V = 1; V <= 20; V++) {
			for (seed = 0; seed < 8; seed++) {
				for (prob = 21 - V; prob <= 128; prob*=2) {
					g = this.randomEdges(seed, 0, V, 0, V, prob);
					Graph gr = new Graph(V);
					for (Edge e : g) {
						gr.addEdge(e.v, e.w);
					}
					assertSameGraph(V, g, gr);
					str = gr.showAsAdjacencyList();
					assertNotEquals(null, str);
					String[] lines = str.split("\n");
					assertEquals(V, lines.length);
					int numEdges = 0;
					for (int v = 0; v < V; v++) {
						String[] adj = lines[v].split("-");
						for (int i = 0; i < adj.length; i++) {
							int w = Integer.parseInt(adj[i]);
							assertTrue(w < V);
							Edge e = new Edge(v, w);
							assertTrue(e.valid(V));
							assertTrue(g.contains(e));
							numEdges++;
						}
					}
					assertEquals(g.size() * 2, numEdges);
					// save last successful test
					lastV = V;
					lastg = g;
					laststr = str;
				}
			}
		}
	}
	
	private void testMethod(int method_id) throws Exception {
		try {
			System.out.print(border + test + border);
			switch (method_id) {
			case 0: testConstruct(); break;
			case 1: testAddVertex(); break;
			case 2: testRemoveEdge(); break;
			case 3: testDegreeSequence(); break;
			case 4: testDistance2(); break;
			case 5: testAdjMatrixString(); break;
			case 6: testAdjListString(); break;
			}
		} catch(AssertionError aerr) {
			ae = aerr;
		} catch(Exception err) {
			e = err;
		}
		
		if (ae != null || e != null) {
			System.out.print("\n" + border + test + failed + border);
			System.out.println("failing case seed = " + seed + " prob = " + prob + "/128");
			System.out.println("the corresponding graph is:");
			System.out.println(graphToString(V, g));
			if (method_id == 2) {
				System.out.println("the corresponding graph before removal of edges is:");
				System.out.println(graphToString(V, g2));
			} else if (method_id == 3) {
				System.out.println("the corresponding degree sequence returned is:");
				System.out.println(dlistToString(dlist));
			} else if (method_id == 4) {
				System.out.println("the corresponding distance 2 list(" + w + ") returned is:");
				System.out.println(dlistToString(dlist));
			} else if (method_id == 5 || method_id == 6) {
				System.out.println("string returned is:");
				System.out.println(str);
			}
			if (lastg != null) {
				System.out.println("the last successful graph is:");
				System.out.println(graphToString(lastV, lastg));
				if (method_id == 2) {
					System.out.println("the last successful graph before removal of edges is:");
					System.out.println(graphToString(lastV, lastg2));
				} else if (method_id == 3) {
					System.out.println("the last successful degree sequence returned is:");
					System.out.println(dlistToString(lastdlist));
				} else if (method_id == 4) {
					System.out.println("the last successful distance 2 list(" +lastw +") returned is:");
					System.out.println(dlistToString(lastdlist));
				} else if (method_id == 5 || method_id == 6) {
					System.out.println("the last successful string returned is:");
					System.out.println(laststr);
				}
			}
			if (ae != null) throw ae;
			if (e != null) throw e;
		} else {
			System.out.print(border + test + passed + border);
		}
	}
	
	@Test
	public void testGraphAdjListString() throws Exception {
		test = "* Testing showing as adj. list string     *\n";
		testMethod(6);
	}
	
	@Test
	public void testGraphMatrixString() throws Exception {
		test = "* Testing showing as adj. matrix string   *\n";
		testMethod(5);
	}
	
	@Test
	public void testGraphVerticesDistance2() throws Exception {
		test = "* Testing vertices within distance 2      *\n";
		testMethod(4);
	}
	
	@Test
	public void testGraphDegreeSequence() throws Exception {
		test = "* Testing degree sequence                 *\n";
		testMethod(3);
	}
	
	@Test
	public void testGraphRemoveEdge() throws Exception {
		test = "* Testing removeEdge                      *\n";
		testMethod(2);
	}
	
	@Test
	public void testGraphAddVertex() throws Exception {
		test = "* Testing addVertex                       *\n";
		testMethod(1);
	}
	
	@Test
	public void testGraphConstruction() throws Exception {
		test = "* Testing graph construction              *\n";
		testMethod(0);
	}
}