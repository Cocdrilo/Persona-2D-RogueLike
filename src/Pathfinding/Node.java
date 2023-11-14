package Pathfinding;

/**
 * Represents a node in a pathfinding grid.
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
     * Initializes a new Node with the specified column and row.
     *
     * @param col The column index of the node.
     * @param row The row index of the node.
     */
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }

}
