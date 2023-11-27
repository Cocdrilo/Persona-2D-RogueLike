package PathFinding;

/**
 * The Node class represents a node used in pathfinding algorithms.
 */
public class Node {
    Node parent;
    public int col;
    public int row;
    int gCost;
    int hCost;
    int fCost;
    boolean solid;
    boolean open;
    boolean checked;

    /**
     * Constructs a new Node with the specified column and row positions.
     *
     * @param col The column position of the node.
     * @param row The row position of the node.
     */
    public Node (int col, int row){
        this.col = col;
        this.row = row;
    }

}
