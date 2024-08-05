public class Hash271 {

    /** Default size for foundation array */
    private static final int DEFAULT_SIZE = 4;
    
    /** Default threshold for rehashing */
    private static final double DEFAULT_THRESHOLD = 0.75; 

    /** Measures the load factor */
    private double loadFactor;

    /** Threshold for rehashing */
    private double threshold; 

    /** Foundation array of node objects */
    Node[] foundation;

    /** Basic constructor */
    public Hash271(int size, double threshold) {
        this.foundation = new Node[size];
        this.threshold = threshold;
        this.loadFactor = 0.0;
    } // basic constructor

    /** Basic constructor with default threshold */
    public Hash271(int size) {
        this(size, DEFAULT_THRESHOLD);
    } // constructor with default threshold

    /** Default constructor */
    public Hash271() {
        this(DEFAULT_SIZE, DEFAULT_THRESHOLD);
    } // default constructor

    /**
     * Map an integer number to one of the positions of the underlying array. This
     * will come handy we need to find the place to chain a node.
     * 
     * @param value int to map to one of the array positions
     * @return int with the integer division remainder between the input value and
     *         the length of the array
     */
    private int computeArrayPosition(int value) {
        return value % this.foundation.length;
    } // method computeArrayPosition

    /**
     * Chain a node to the underlying array
     * 
     * @param node Node to chain to the underlying array
     */
    public void put(Node node) {
        // Operate only is node is not null
        if (node != null) {
            // Use the node's hashcode to determine is position in
            // the underlying array
            int destination = computeArrayPosition(node.hashCode());
            // If the position in the array is occupied by another node,
            // place that node under the new node we wish to insert
            if (this.foundation[destination] != null) {
                node.setNext(this.foundation[destination]);
            }
            // Put the new node to the array position
            this.foundation[destination] = node;

            // Update the load factor
            updateLoadFactor();

            // Rehash if load factor exceeds threshold
            if (this.loadFactor > this.threshold) {
                rehash();
            }
        }
    } // method put

    /**
     * Wrapper for put(Node). Accepts a string, creates a Node object and passes it
     * to the put(Node) method.
     * 
     * @param string String to create a node for, then chain that node to the
     *               underlying array.
     */
    public void put(String string) {
        if (string != null && string.length() > 0) {
            Node node = new Node(string);
            this.put(node);
        }
    } // method put

    /** String representation of this object */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.foundation.length; i++) {
            sb.append(String.format("[ %03d ]: ", i));
            Node current = this.foundation[i];
            while (current != null) {
                sb.append(String.format("<%s> ", current.toString()));
                current = current.getNext();
            }
            sb.append("\n");
        }
        return sb.toString();
    } // method toString

    /**
     * Rehashes the nodes in the current foundation array to a new foundation array
     * with double the original size. This method is called when the load factor
     * exceeds the specified threshold.
     * 
     * The method iterates through each index of the old foundation array, and for
     * each node at that index, it calculates the new position in the new foundation
     * array and inserts the node there. The load factor is reset after the rehash.
 */
    private void rehash() {
    Node[] oldFoundation = this.foundation;
    this.foundation = new Node[oldFoundation.length * 2];
    this.loadFactor = 0.0;

    int index = 0;
    while (index < oldFoundation.length) {
        Node head = oldFoundation[index];
        Node current = head;
        while (current != null) {
            Node next = current.getNext();
            current.setNext(null);
            put(current);
            current = next;
        }
        index++;
    }
}
    } // method rehash

    /**
     * Updates the load factor of the hash table. The load factor is calculated
     * as the ratio of the total number of nodes present in the foundation array
     * to the length of the foundation array.
     */
private void updateLoadFactor() {
    int nodeCount = 0;
    int index = 0;

    while (index < this.foundation.length) {
        Node current = this.foundation[index];
        while (current != null) {
            nodeCount++;
            current = current.getNext();
        }
        index++;
    }

    this.loadFactor = (double) nodeCount / this.foundation.length;
}

    /** Driver code */
    public static void main(String[] args) {
        Hash271 h = new Hash271();
        h.put(new Node("Java"));
        h.put(new Node("Python"));
        h.put(new Node("Lisp"));
        h.put(new Node("Fortran"));
        h.put(new Node("Prolog"));
        h.put(new Node("Cobol"));
        h.put(new Node("C++"));
        h.put(new Node("C"));
        h.put(new Node("C#"));
        System.out.println(h);
    }
}
