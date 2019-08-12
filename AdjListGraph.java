import java.util.Hashtable;
import java.util.Iterator;

/**
 * 
 * Part 1 of Assignment 3 
 * A generic data structure to store a graph using Adjacency List Structure.
 * 
 * @author Rayyan Albaz
 * 
 * Student ID# 214540017
 *
 */


public class AdjListGraph < V, E > implements Graph < V, E > {

    /**
     * Implements the Vertex Class 
     * @author RayyanAlbaz
     *
     * @param <V>
     */
    @SuppressWarnings("hiding")
    private class InnerVertex < V > implements Vertex < V > {

        private V element;
        private Position < Vertex < V >> position;
        private LinkedPositionalList < Edge < E >> incoming,
        outgoing;

        /**
         * Constructs a new InnerVertex instance storing the given element.
         *
         * @param element
         * 
         */
        public InnerVertex(V element) {
            this.element = element;
            outgoing = new LinkedPositionalList < Edge < E >> ();
            if (directed) {
                incoming = new LinkedPositionalList < Edge < E >> ();
            } else {
                incoming = outgoing; // if undirected, alias outgoing map
            }
        }
        /**
         * Returns the elements stored in the vertex
         * 
         * @return V 
         */
        @Override
        public V getElement() {
            return this.element;
        }
        /**
         * Sets the position of the vertex to the given position 
         * @param position
         */
        public void setPosition(Position < Vertex < V >> position) {
            this.position = position;
        }
        /**
         * Returns the Position of the vertex
         * @return Position
         */
        public Position < Vertex < V >> getPosition() {
            return this.position;
        }

        /**
         * Returns reference to the underlying list of outgoing edges
         *
         * @return LinkedPositionalList Out going edges 
         */
        public LinkedPositionalList < Edge < E >> getOutgoing() {
            return this.outgoing;
        }

        /**
         * Returns reference to the underlying list of incoming edges
         *
         * @return LinkedPositionalList incoming edges
         */
        public LinkedPositionalList < Edge < E >> getIncoming() {
            return this.incoming;
        }

        /**
         * Validates that this edge instance belongs to the given graph.
         */
        public boolean validate(Graph < V, E > graph) {
            return AdjListGraph.this == graph && position != null;
        }
    } //------------ end of InnerVertex class ------------


    @SuppressWarnings("hiding")
    private class InnerEdge < E > implements Edge < E > {

        private E element;
        private Position < Edge < E >> [] incidence;
        private Vertex < V > [] endpoints;
        protected Position < Edge < E >> position;

        /**
         * Constructs InnerEdge instance from u to v, storing the given element.
         *
         * @param u
         * @param v
         * @param element
         */
        @SuppressWarnings("unchecked")
        public InnerEdge(Vertex < V > u, Vertex < V > v, E element) {
            this.element = element;
            endpoints = (InnerVertex < V > []) new InnerVertex[2];
            endpoints[0] = (InnerVertex < V > ) u;
            endpoints[1] = (InnerVertex < V > ) v;
            incidence = (Position < Edge < E >> []) new Position[2];
        }
        /**
         * Returns the Element stored in the edge of type E
         * @return E the element in the edge 
         */
        @Override
        public E getElement() {
            return this.element;
        }
        /**
         * Sets the Positions of the edge to the given positions 
         * Corresponding to the right list of incoming an outgoing edges 
         * 
         * @param origin
         * @param dest
         */
        public void setIncidences(Position < Edge < E >> origin, Position < Edge < E >> dest) {
            this.incidence[0] = origin;
            this.incidence[1] = dest;
        }
        /**
         * Returns the positions of the edge inn the corresponding incoming an d out going lists 
         * 
         * @return Array of Positions 
         */
        public Position < Edge < E >> [] getIncident() {
            return this.incidence;
        }
        /**
         * Returns the corresponding vertices of each end as an array
         * 
         * @return Array of the edge's end points of type Vertex<V>
         */
        public Vertex < V > [] getEndPoints() {
            return this.endpoints;
        }
        /**
         * Sets the position of the edge in the Edge list of the graph to the given position
         * 
         * @param position
         */
        public void setPosition(Position < Edge < E >> position) {
            this.position = position;
        }
        /**
         * Returns the edge's position in the edge list 
         * @return Position in the edge list 
         */
        public Position < Edge < E >> getPosition() {
            return this.position;
        }

        /**
         * Validates that this edge instance belongs to the given graph.
         */
        public boolean validate(Graph < V, E > graph) {
            return AdjListGraph.this == graph && position != null;
        }
    } //------------ end of InnerEdge class ------------

    private boolean directed;
    private LinkedPositionalList < Vertex < V >> vertices = new LinkedPositionalList < > ();
    private LinkedPositionalList < Edge < E >> edges = new LinkedPositionalList < > ();
    private Hashtable < V,
    Vertex < V >> verticesTable = new Hashtable < V,Vertex < V >> ();

    /**
     * construct an empty graph
     */
    public AdjListGraph(boolean directed) {
        this.directed = directed;
    }

    /**
     *  
     *  @return the number of vertices in the graph of type int 
     */
    @Override
    public int numVertices() {
        return vertices.size();
    }
    /**
     * returns the number of edges in the graph
     * @return number of edges in the graph of type int  
     */
    @Override
    public int numEdges() {
        return edges.size();
    }
    /**
     * returns a list of vertices in the graph
     * @return Iterable<Vertex<V>> Vertices 
     */
    @Override
    public Iterable < Vertex < V >> vertices() {

        return (Iterable < Vertex < V >> ) this.vertices;
    }
    /**
     * returns a list of edges in the graph 
     * @return Iterable<Edge<E>> Edges 
     */
    @Override
    public Iterable < Edge < E >> edges() {
        return (Iterable < Edge < E >> ) this.edges;
    }

    /**
     * returns an array of the end points of a given edge 
     * @param Edge<E>
     * @return Vertex<V>[] endpoints 
     * @throws IllegalArgumentException
     */
    @Override
    public Vertex < V > [] endVertices(Edge < E > edge) throws IllegalArgumentException {
        InnerEdge < E > e = validate(edge);
        return e.getEndPoints();
    }

    /**
     * Returns the vertex that is opposite vertex v on edge e.
     *
     * @param vertex
     * @param edge
     * @return Vertex<V> Opposite Vertex
     * @throws IllegalArgumentException
     */
    @Override
    public Vertex < V > opposite(Vertex < V > vertex, Edge < E > edge) throws IllegalArgumentException {
        validate(vertex);
        InnerEdge < E > e = validate(edge);

        Vertex < V > [] endpoints = e.getEndPoints();
        if (endpoints[0] == vertex) {
            return endpoints[1];
        } else if (endpoints[1] == vertex) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to this edge");
        }
    }

    /**
     * inserts and returns a vertex of given an element
     *
     * @param element
     * @return Vertex<V> V
     *
     */
    @Override
    public Vertex < V > insertVertex(V element) {
        InnerVertex < V > v = new InnerVertex < > (element);
        Position < Vertex < V >> position = vertices.addLast(v);
        verticesTable.put(v.element, v);
        v.setPosition(position);
        return v;
    }

    /**
     * inserts and returns an Edge of given end points and an element 
     *
     * @param vertex u
     * @param vertex v
     * @param E element 
     * @return Edge<E> E 
     * @throws IllegalArgumentException
     */
    @Override
    public Edge < E > insertEdge(Vertex < V > u, Vertex < V > v, E element) throws IllegalArgumentException {
        if (getEdge(u, v) == null) {
            InnerVertex < V > origin = validate(u);
            InnerVertex < V > dest = validate(v);
            InnerEdge < E > e = new InnerEdge < > (origin, dest, element);
            Position < Edge < E >> originPosition = origin.getOutgoing().addLast(e);
            Position < Edge < E >> destPosition = dest.getIncoming().addLast(e);
            e.setIncidences(originPosition, destPosition);
            Position < Edge < E >> edgePosition = edges.addLast(e);
            e.setPosition(edgePosition);

            return e;
        } else {
            throw new IllegalArgumentException("Edge from u to v exists");
        }
    }

    /**
     * removes a given vertex and any corresponding edges.
     *
     * @param vertex
     * @throws IllegalArgumentException
     */
    @Override
    public void removeVertex(Vertex < V > vertex) throws IllegalArgumentException {
        InnerVertex < V > ver = validate(vertex);
        Iterator < Edge < E >> iterOutgoing = ver.getOutgoing().iterator();
        Iterator < Edge < E >> iterIncoming = ver.getIncoming().iterator();

        // Origin 
        while (iterOutgoing.hasNext()) {
            InnerEdge < E > outEdge = (InnerEdge < E > ) iterOutgoing.next();
            this.removeEdge(outEdge);
        }
        // Dest
        if (directed) {
            while (iterIncoming.hasNext()) {
                InnerEdge < E > inEdge = (InnerEdge < E > ) iterIncoming.next();
                this.removeEdge(inEdge);
            }
        }

        this.vertices.remove(ver.getPosition());
        this.verticesTable.remove(ver.getElement());

    }

    /**
     * removes a given edge from the graph 
     *
     * @param e edge 
     * @throws IllegalArgumentException
     */
    @Override
    public void removeEdge(Edge < E > e) throws IllegalArgumentException {
        InnerEdge < E > edge = validate(e);
        Position < Edge < E >> pos = edge.getPosition();
        Vertex < V > [] endPoints = edge.getEndPoints();
        Position < Edge < E >> [] inc = edge.getIncident();
        InnerVertex < V > origin = (InnerVertex < V > ) endPoints[0];
        InnerVertex < V > dest = (InnerVertex < V > ) endPoints[1];

        origin.getOutgoing().remove(inc[0]);
        dest.getIncoming().remove(inc[1]);

        this.edges.remove(pos);
    }

    /**
     * validates a given vertex.
     *
     * @param vertex
     */
    private InnerVertex < V > validate(Vertex < V > v) {
        if (!(v instanceof InnerVertex)) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        InnerVertex < V > vert = (InnerVertex < V > ) v; // safe cast
        if (!vert.validate(this)) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        return vert;
    }

    /**
     * validates a given edge
     *
     * @param edge e
     */
    private InnerEdge < E > validate(Edge < E > e) {
        if (!(e instanceof InnerEdge)) {
            throw new IllegalArgumentException("Invalid edge");
        }
        InnerEdge < E > edge = (InnerEdge < E > ) e; // safe cast
        if (!edge.validate(this)) {
            throw new IllegalArgumentException("Invalid edge");
        }
        return edge;
    }

    /**
     * @return edge from u to v or null if it doesn't exist 
     * @param Vertex<V> u
     * @param Vertec<V> v
     * @throwsIllegalArgumentException
     * 
     */
    @Override
    public Edge < E > getEdge(Vertex < V > u, Vertex < V > v) throws IllegalArgumentException {
        InnerVertex < V > origin = (InnerVertex < V > ) validate(u);
        InnerVertex < V > dest = (InnerVertex < V > ) validate(v);

        Iterator < Edge < E >> iter = origin.getOutgoing().iterator();
        while (iter.hasNext()) {
            InnerEdge < E > edge = (InnerEdge < E > ) iter.next();
            if (edge.endpoints[1].getElement().equals(dest.getElement())) {
                return edge;
            }
        }
        return null;
    }
    /**
     * @return edge from u to v or null if it doesn't exist 
     * @param Vertex<V> u
     * @param Vertec<V> v
     * @throwsIllegalArgumentException
     * 
     */
    public Edge < E > getEdge(Vertex < V > u, Vertex < V > v, E e) throws IllegalArgumentException {
        InnerVertex < V > origin = (InnerVertex < V > ) validate(u);
        InnerVertex < V > dest = (InnerVertex < V > ) validate(v);

        Iterator < Edge < E >> iter = origin.getOutgoing().iterator();
        while (iter.hasNext()) {
            InnerEdge < E > edge = (InnerEdge < E > ) iter.next();
            if (edge.endpoints[1].getElement().equals(dest.getElement())) {
                return edge;
            }
        }
        return null;
    }
    /**
     * returns the vertex from the hash table given the key.
     * @param v
     * @return Vertex<V> v from hashtable.
     */
    public Vertex < V > getVertex(V v) {
        if (verticesTable.containsKey(v)) {
            return verticesTable.get(v);
        } else
            return null;

    }

    @Override
    public int outDegree(Vertex < V > v) throws IllegalArgumentException {
        InnerVertex < V > vert = (InnerVertex < V > ) validate(v);
        return vert.outgoing.size();
    }

    @Override
    public int inDegree(Vertex < V > v) throws IllegalArgumentException {
        InnerVertex < V > vert = (InnerVertex < V > ) validate(v);
        return vert.incoming.size();
    }

    @Override
    public Iterable < Edge < E >> outgoingEdges(Vertex < V > v) throws IllegalArgumentException {
        InnerVertex < V > vert = (InnerVertex < V > ) validate(v);
        return (Iterable < Edge < E >> ) vert.getOutgoing();
    }

    @Override
    public Iterable < Edge < E >> incomingEdges(Vertex < V > v) throws IllegalArgumentException {
        InnerVertex < V > vert = (InnerVertex < V > ) validate(v);
        return (Iterable < Edge < E >> ) vert.getIncoming();
    }
}