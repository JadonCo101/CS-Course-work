import java.util.*;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Jadon Co
 * @version 1.0
 * @userid jco9
 * @GTID 903725118
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new java.lang.IllegalArgumentException("The input is null");
        }
        if (!(graph.getVertices()).contains(start)) {
            throw new java.lang.IllegalArgumentException("The start vertex doesn't exist in the Graph");
        }

        List<Vertex<T>> output = new ArrayList<>();
        Set<Vertex<T>> vs = new HashSet<>();
        Queue<Vertex<T>> Q = new LinkedList<>();


        output.add(start);
        Q.add(start);
        vs.add(start);

        while(!(Q.isEmpty())) {
            Vertex<T> v = (Q.remove());
            for (VertexDistance<T> w : graph.getAdjList().get(v)) {
                if (!(vs.contains(w.getVertex()))) {
                    output.add(w.getVertex());
                    vs.add(w.getVertex());
                    Q.add(w.getVertex());
                }
            }

        }

        return output;

    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new java.lang.IllegalArgumentException("The input is null");
        }
        if (!(graph.getVertices()).contains(start)) {
            throw new java.lang.IllegalArgumentException("The start vertex doesn't exist in the Graph");
        }

        List<Vertex<T>> output = new ArrayList<>();
        Set<Vertex<T>> vs = new HashSet<>();
        dfsHelp(start, graph, output, vs);
        return output;



    }

    private static <T> void dfsHelp(Vertex<T> v, Graph<T> graph, List<Vertex<T>> output, Set<Vertex<T>> vs) {
        if (vs.size() == (graph.getVertices()).size() || vs.contains(v)) {
            return;
        } else {
            output.add(v);
            vs.add(v);
            for (VertexDistance<T> w : graph.getAdjList().get(v)) {
                if (!(vs.contains(w.getVertex()))) {
                    dfsHelp(w.getVertex(), graph, output, vs);
                }
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new java.lang.IllegalArgumentException("The input is null");
        }
        if (!(graph.getVertices()).contains(start)) {
            throw new java.lang.IllegalArgumentException("The start vertex doesn't exist in the Graph");
        }


        Set<Vertex<T>> VS = new HashSet<>();
        Map<Vertex<T>, Integer> distMap = new HashMap<>();
        Queue<VertexDistance<T>> PQ = new PriorityQueue<>();

        for (Vertex<T> v : graph.getVertices()) {
            if (v.equals(start)) {
                distMap.put(v, 0);
            } else {
                distMap.put(v, Integer.MAX_VALUE);
            }
        }

        VertexDistance<T> sPath = new VertexDistance<>(start, 0);
        PQ.add(sPath);

        while (!PQ.isEmpty() && (VS.size() != (graph.getVertices()).size())) {
            VertexDistance<T> u = PQ.remove();
            VS.add(u.getVertex());
            for (VertexDistance<T> element : graph.getAdjList().get(u.getVertex())) {
                int currDist = 0;
                currDist = element.getDistance() + u.getDistance();
                if (VS.contains(element.getVertex()) == false && distMap.get(element.getVertex()) > currDist) {
                    VertexDistance<T> next = new VertexDistance<>(element.getVertex(), currDist);
                    distMap.put(next);
                    PQ.add(next);
                }
            }
        }

        return distMap;

    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new java.lang.IllegalArgumentException("The inputs for the method are invalid");
        }

        Set<Edge<T>> MST = new HashSet<>();
        DisjointSet<Vertex<T>> DS = new DisjointSet<>();
        Queue<Edge<T>> PQ = new PriorityQueue<>(graph.getEdges());

        while (PQ.isEmpty() == false && MST.size() < 2 * (graph.getEdges().size() - 1)) {
            Edge<T> edge = PQ.remove();
            Vertex<T> one = edge.getU();
            Vertex<T> two = edge.getV();
            if (!(DS.find(one).equals(DS.find(two)))) {
                DS.union(DS.find(one), DS.find(two));
                MST.add(edge);
                //System.out.println(MST);
                Edge<T> reverse = new Edge<>(edge.getV(), edge.getU(), edge.getWeight()); //readd reverse edge
                MST.add(reverse);
            }
        }
        if (MST.size() < (2 * (graph.getVertices().size() - 1))) {
            return null; //optimization from recitation
        }

        return MST;


    }
}
