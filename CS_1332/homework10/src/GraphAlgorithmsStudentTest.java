import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/*
 * In the event that there is an error found in these tests or an
 * untested edge case is found, this test file may be updated. Check
 * back on the same gist later for updates.
 */

public class GraphAlgorithmsStudentTest {

    private static final int TIMEOUT = 200;

    private Graph<String> undirectedGraph;
    private Graph<String> directedGraph;
    private Graph<String> disconnectedGraph;

    @Before
    public void setUp() {
        createUndirectedGraph();
        createDirectedGraph();
        createDisconnectedGraph();
    }

    private void createUndirectedGraph() {
        Set<Vertex<String>> vertices = new HashSet<>();
        for (int i = 65; i <= 77; i++) {
            vertices.add(new Vertex<>("" + (char) i));
        }

        Set<Edge<String>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 1));
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("C"), 1));
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("D"), 2));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("A"), 1));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("E"), 9));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("A"), 1));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("J"), 2));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("M"), 3));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("A"), 2));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("D"), 1));
        edges.add(new Edge<>(new Vertex<>("E"), new Vertex<>("B"), 9));
        edges.add(new Edge<>(new Vertex<>("E"), new Vertex<>("F"), 3));
        edges.add(new Edge<>(new Vertex<>("E"), new Vertex<>("H"), 10));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("E"), 3));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("G"), 1));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("G"), 1));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("J"), 2));
        edges.add(new Edge<>(new Vertex<>("G"), new Vertex<>("F"), 1));
        edges.add(new Edge<>(new Vertex<>("G"), new Vertex<>("F"), 1));
        edges.add(new Edge<>(new Vertex<>("H"), new Vertex<>("E"), 10));
        edges.add(new Edge<>(new Vertex<>("H"), new Vertex<>("I"), 1));
        edges.add(new Edge<>(new Vertex<>("I"), new Vertex<>("H"), 1));
        edges.add(new Edge<>(new Vertex<>("I"), new Vertex<>("J"), 7));
        edges.add(new Edge<>(new Vertex<>("J"), new Vertex<>("C"), 2));
        edges.add(new Edge<>(new Vertex<>("J"), new Vertex<>("F"), 2));
        edges.add(new Edge<>(new Vertex<>("J"), new Vertex<>("I"), 7));
        edges.add(new Edge<>(new Vertex<>("J"), new Vertex<>("K"), 2));
        edges.add(new Edge<>(new Vertex<>("J"), new Vertex<>("L"), 3));
        edges.add(new Edge<>(new Vertex<>("K"), new Vertex<>("J"), 2));
        edges.add(new Edge<>(new Vertex<>("L"), new Vertex<>("J"), 3));
        edges.add(new Edge<>(new Vertex<>("L"), new Vertex<>("M"), 7));
        edges.add(new Edge<>(new Vertex<>("M"), new Vertex<>("C"), 3));
        edges.add(new Edge<>(new Vertex<>("M"), new Vertex<>("L"), 7));

        undirectedGraph = new Graph<>(vertices, edges);
    }

    private void createDirectedGraph() {
        Set<Vertex<String>> vertices = new HashSet<>();
        for (int i = 65; i <= 71; i++) {
            vertices.add(new Vertex<>("" + (char) i));
        }

        Set<Edge<String>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 2));
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("D"), 7));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("A"), 2));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("B"), 1));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("C"), 2));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("D"), 2));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("E"), 4));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("E"), 4));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("F"), 1));
        edges.add(new Edge<>(new Vertex<>("E"), new Vertex<>("A"), 5));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("G"), 2));
        edges.add(new Edge<>(new Vertex<>("G"), new Vertex<>("F"), 2));

        directedGraph = new Graph<>(vertices, edges);
    }

    private void createDisconnectedGraph() {
        Set<Vertex<String>> vertices = new HashSet<>();
        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<>("" + (char) i));
        }

        Set<Edge<String>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 1));
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("C"), 1));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("A"), 1));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("C"), 1));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("D"), 1));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("A"), 1));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("B"), 1));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("B"), 1));
        edges.add(new Edge<>(new Vertex<>("E"), new Vertex<>("F"), 1));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("E"), 1));

        disconnectedGraph = new Graph<>(vertices, edges);
    }

    private Graph<String> getRandomGraph(Random rand, boolean disconnected) {
        Set<Vertex<String>> vertices = new HashSet<>();
        for (int i = 0; i < rand.nextInt(20); i++) {
            vertices.add(new Vertex<String>("" + i));
        }

        Set<Edge<String>> edges = new HashSet<>();
        for (int i = 0; i <  vertices.size() * 2; i++) {
            int initSize = edges.size();
            while (!disconnected && edges.size() == initSize) {
                List<Vertex<String>> v = vertices.stream().collect(Collectors.toList());
                int ind1 = rand.nextInt(v.size());
                int ind2 = rand.nextInt(v.size());
                int weight = rand.nextInt(10);
                edges.add(new Edge<>(v.get(ind1), v.get(ind2), weight));
                edges.add(new Edge<>(v.get(ind2), v.get(ind1), weight));
            }
        }

        return new Graph<String>(vertices, edges);
    }

    private static <T> Set<Edge<T>> prims(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("arguements cannot be null");
        }

        Set<Edge<T>> mst = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Vertex<T> currVertex;
        try {
            currVertex = graph.getVertices().iterator().next();
        } catch (NoSuchElementException e) {
            return mst;
        }

        for (VertexDistance<T> dist : graph.getAdjList().get(currVertex)) {
            pq.add(new Edge<>(currVertex, dist.getVertex(), dist.getDistance()));
        }
        visited.add(currVertex);

        Edge<T> currEdge;
        List<VertexDistance<T>> adjList;

        while (!pq.isEmpty() && visited.size() != graph.getAdjList().size()) {
            currEdge = pq.remove();

            if (!visited.contains(currEdge.getV())) {
                mst.add(currEdge);
                mst.add(new Edge<>(currEdge.getV(), currEdge.getU(), currEdge.getWeight()));
                visited.add(currEdge.getV());
            }

            adjList = graph.getAdjList().get(currEdge.getV());
            for (int i = 0; i < adjList.size(); i++) {
                if (!visited.contains(adjList.get(i).getVertex())) {
                    pq.add(new Edge<T>(currEdge.getV(), adjList.get(i).getVertex(), adjList.get(i).getDistance()));
                }
            }
        }

        if (mst.size() < (graph.getVertices().size() - 1) * 2) {
            return null;
        }

        return mst;
    }

    @Test(timeout = TIMEOUT)
    public void testBfsUndirected() {
        List<Vertex<String>> bfs =
                GraphAlgorithms.bfs(new Vertex<>("A"), undirectedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("D"));
        desired.add(new Vertex<>("E"));
        desired.add(new Vertex<>("J"));
        desired.add(new Vertex<>("M"));
        desired.add(new Vertex<>("F"));
        desired.add(new Vertex<>("H"));
        desired.add(new Vertex<>("I"));
        desired.add(new Vertex<>("K"));
        desired.add(new Vertex<>("L"));
        desired.add(new Vertex<>("G"));

        assertEquals(desired, bfs);
    }

    @Test(timeout = TIMEOUT)
    public void testBfsDirected() {
        List<Vertex<String>> bfs =
                GraphAlgorithms.bfs(new Vertex<>("A"), directedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("D"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("E"));
        desired.add(new Vertex<>("F"));
        desired.add(new Vertex<>("G"));

        assertEquals(desired, bfs);
    }

    @Test(timeout = TIMEOUT)
    public void testBfsDisconnected() {
        List<Vertex<String>> bfs =
                GraphAlgorithms.bfs(new Vertex<>("A"), disconnectedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("D"));

        assertEquals(desired, bfs);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBfsNullStart() {
        GraphAlgorithms.bfs(null, undirectedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBfsNullGraph() {
        GraphAlgorithms.bfs(new Vertex<>("A"), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBfsStartNotPresent() {
        GraphAlgorithms.bfs(new Vertex<>("Z"), undirectedGraph);
    }

    @Test(timeout = TIMEOUT)
    public void testDfsUndirected() {
        List<Vertex<String>> dfs =
                GraphAlgorithms.dfs(new Vertex<>("A"), undirectedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("E"));
        desired.add(new Vertex<>("F"));
        desired.add(new Vertex<>("G"));
        desired.add(new Vertex<>("J"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("M"));
        desired.add(new Vertex<>("L"));
        desired.add(new Vertex<>("I"));
        desired.add(new Vertex<>("H"));
        desired.add(new Vertex<>("K"));
        desired.add(new Vertex<>("D"));

        assertEquals(desired, dfs);
    }

    @Test(timeout = TIMEOUT)
    public void testDfsDirected() {
        List<Vertex<String>> dfs =
                GraphAlgorithms.dfs(new Vertex<>("A"), directedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("D"));
        desired.add(new Vertex<>("E"));
        desired.add(new Vertex<>("F"));
        desired.add(new Vertex<>("G"));

        assertEquals(desired, dfs);
    }

    @Test(timeout = TIMEOUT)
    public void testDfsDisconnected() {
        List<Vertex<String>> dfs =
                GraphAlgorithms.dfs(new Vertex<>("A"), disconnectedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("D"));

        assertEquals(desired, dfs);
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDfsNullStart() {
        GraphAlgorithms.dfs(null, undirectedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDfsNullGraph() {
        GraphAlgorithms.dfs(new Vertex<>("A"), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDfsStartNotPresent() {
        GraphAlgorithms.dfs(new Vertex<>("Z"), undirectedGraph);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasUndirected() {
        Map<Vertex<String>, Integer> dijkstras =
                GraphAlgorithms.dijkstras(new Vertex<>("A"),
                        undirectedGraph);

        Map<Vertex<String>, Integer> desired = new HashMap<>();
        desired.put(new Vertex<>("A"), 0);
        desired.put(new Vertex<>("B"), 1);
        desired.put(new Vertex<>("C"), 1);
        desired.put(new Vertex<>("D"), 2);
        desired.put(new Vertex<>("E"), 8);
        desired.put(new Vertex<>("F"), 5);
        desired.put(new Vertex<>("G"), 6);
        desired.put(new Vertex<>("H"), 11);
        desired.put(new Vertex<>("I"), 10);
        desired.put(new Vertex<>("J"), 3);
        desired.put(new Vertex<>("K"), 5);
        desired.put(new Vertex<>("L"), 6);
        desired.put(new Vertex<>("M"), 4);

        assertEquals(desired, dijkstras);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasDirected() {
        Map<Vertex<String>, Integer> dijkstras =
                GraphAlgorithms.dijkstras(new Vertex<>("A"),
                        directedGraph);

        Map<Vertex<String>, Integer> desired = new HashMap<>();
        desired.put(new Vertex<>("A"), 0);
        desired.put(new Vertex<>("B"), 2);
        desired.put(new Vertex<>("C"), 4);
        desired.put(new Vertex<>("D"), 6);
        desired.put(new Vertex<>("E"), 10);
        desired.put(new Vertex<>("F"), 7);
        desired.put(new Vertex<>("G"), 9);

        assertEquals(desired, dijkstras);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasDisconnected() {
        Map<Vertex<String>, Integer> dijkstras =
                GraphAlgorithms.dijkstras(new Vertex<>("A"),
                        disconnectedGraph);

        Map<Vertex<String>, Integer> desired = new HashMap<>();
        desired.put(new Vertex<>("A"), 0);
        desired.put(new Vertex<>("B"), 1);
        desired.put(new Vertex<>("C"), 1);
        desired.put(new Vertex<>("D"), 2);
        desired.put(new Vertex<>("E"), Integer.MAX_VALUE);
        desired.put(new Vertex<>("F"), Integer.MAX_VALUE);

        assertEquals(desired, dijkstras);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasNullStart() {
        GraphAlgorithms.dijkstras(null, undirectedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasNullGraph() {
        GraphAlgorithms.dijkstras(new Vertex<>("A"), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasStartNotPresent() {
        GraphAlgorithms.dijkstras(new Vertex<>("Z"), undirectedGraph);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsUndirected() {
        Set<Edge<String>> kruskals = GraphAlgorithms.kruskals(undirectedGraph);

        Set<Edge<String>> desired = new HashSet<>();
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 1));
        desired.add(new Edge<>(new Vertex<>("B"), new Vertex<>("A"), 1));
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("C"), 1));
        desired.add(new Edge<>(new Vertex<>("C"), new Vertex<>("A"), 1));
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("D"), 2));
        desired.add(new Edge<>(new Vertex<>("D"), new Vertex<>("A"), 2));
        desired.add(new Edge<>(new Vertex<>("C"), new Vertex<>("J"), 2));
        desired.add(new Edge<>(new Vertex<>("J"), new Vertex<>("C"), 2));
        desired.add(new Edge<>(new Vertex<>("C"), new Vertex<>("M"), 3));
        desired.add(new Edge<>(new Vertex<>("M"), new Vertex<>("C"), 3));
        desired.add(new Edge<>(new Vertex<>("J"), new Vertex<>("K"), 2));
        desired.add(new Edge<>(new Vertex<>("K"), new Vertex<>("J"), 2));
        desired.add(new Edge<>(new Vertex<>("J"), new Vertex<>("L"), 3));
        desired.add(new Edge<>(new Vertex<>("L"), new Vertex<>("J"), 3));
        desired.add(new Edge<>(new Vertex<>("J"), new Vertex<>("F"), 2));
        desired.add(new Edge<>(new Vertex<>("F"), new Vertex<>("J"), 2));
        desired.add(new Edge<>(new Vertex<>("F"), new Vertex<>("G"), 1));
        desired.add(new Edge<>(new Vertex<>("G"), new Vertex<>("F"), 1));
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 1));
        desired.add(new Edge<>(new Vertex<>("F"), new Vertex<>("E"), 3));
        desired.add(new Edge<>(new Vertex<>("E"), new Vertex<>("F"), 3));
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 1));
        desired.add(new Edge<>(new Vertex<>("J"), new Vertex<>("I"), 7));
        desired.add(new Edge<>(new Vertex<>("I"), new Vertex<>("J"), 7));
        desired.add(new Edge<>(new Vertex<>("H"), new Vertex<>("I"), 1));
        desired.add(new Edge<>(new Vertex<>("I"), new Vertex<>("H"), 1));

        assertEquals(desired, kruskals);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsDisconnected() {
        assertNull(GraphAlgorithms.kruskals(disconnectedGraph));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKruskalsNullGraph() {
        GraphAlgorithms.kruskals(null);
    }

    @Test(timeout = 30000)
    public void testKruskalsPrims() {

        Random rand = new Random();

        for (int i = 0; i < 1000; i++) {

            boolean disconnected = rand.nextDouble() < 0.01;

            Graph<String> graph = getRandomGraph(rand, disconnected);
            Set<Edge<String>> mstActualKruskal = GraphAlgorithms.kruskals(graph);
            Set<Edge<String>> mstActualPrims = prims(graph);

            int sumKruskal = 0;
            int sumPrims = 0;

            System.out.println("Graph: " + disconnected);
            System.out.println(graph.getEdges());
            System.out.println();

            System.out.println("Prims: ");
            System.out.println(mstActualPrims == null ? "null" : mstActualPrims);
            System.out.println("Kruskals: ");
            System.out.println(mstActualKruskal == null ? "null" : mstActualKruskal);

            if (mstActualPrims == null) {
                assertNull(mstActualKruskal);
                System.out.println();
                continue;
            }

            for (Edge<String> edge : mstActualKruskal) {
                sumKruskal += edge.getWeight();
            }

            for (Edge<String> edge : mstActualPrims) {
                sumPrims += edge.getWeight();
            }

            assertEquals(sumKruskal, sumPrims);

            System.out.println();
        }
    }


}