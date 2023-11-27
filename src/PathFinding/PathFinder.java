package PathFinding;

import main.GamePanel;

import java.util.ArrayList;

/**
 * The PathFinder class provides functionality for pathfinding using the A* algorithm.
 */
public class PathFinder {
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean pathReached = false;
    int step = 0;

    /**
     * Constructs a new PathFinder associated with the given GamePanel.
     *
     * @param gp The GamePanel instance.
     */
    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instanciateNode();
    }

    /**
     * Initializes the 2D array of nodes representing the world map.
     */

    public void instanciateNode() {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row] = new Node(col, row);
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Resets the state of nodes, open list, path list, and path-reached flag.
     */
    public void resetNodes() {
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        pathReached = false;
        step = 0;
    }

    /**
     * Sets the start and goal nodes for pathfinding, along with the costs of each node.
     *
     * @param startCol The column position of the starting node.
     * @param startRow The row position of the starting node.
     * @param goalCol  The column position of the goal node.
     * @param goalRow  The row position of the goal node.
     */
    public void setNode(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        //Set Start and Goal
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            int tileNum = gp.tileM.mapTileNum[col][row];
            if (gp.tileM.tile[tileNum].collision) {
                node[col][row].solid = true;
            }

            //Set Cost
            getCost(node[col][row]);
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    //Algoritmo de A* para obtener los costes del Pathfinding y saber cual es la ruta con mejor coste

    /**
     * Calculates the cost values (gCost, hCost, fCost) for the given node based on the start and goal nodes.
     *
     * @param node The node for which to calculate the costs.
     */
    public void getCost(Node node) {
        //G cost
        int x = Math.abs(node.col - startNode.col);
        int y = Math.abs(node.row - startNode.row);
        node.gCost = x + y;
        //H cost
        x = Math.abs(node.col - goalNode.col);
        y = Math.abs(node.row - goalNode.row);
        node.hCost = x + y;
        //F cost
        node.fCost = node.gCost + node.hCost;
    }

    /**
     * Performs the A* search algorithm to find the optimal path.
     *
     * @return True if the path is reached; false otherwise.
     */
    public boolean search() {

        while (!pathReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            //Check CNode
            currentNode.checked = true;
            openList.remove(currentNode);

            //Open the UpNode
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            //Open the LeftNode
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }
            //Open the DownNode
            if (row + 1 < gp.maxWorldRow) {
                openNode(node[col][row + 1]);
            }
            //Open the RightNode
            if (col + 1 < gp.maxWorldCol) {
                openNode(node[col + 1][row]);
            }

            //Find Optimal Node
            int bestNodeIndex = 0;
            int bestNodeCostF = 999;

            for (int i = 0; i < openList.size(); i++) {
                //IF F cost is lower
                if (openList.get(i).fCost < bestNodeCostF) {
                    bestNodeIndex = i;
                    bestNodeCostF = openList.get(i).fCost;
                }
                //IF F cost is the same, check G cost
                else if (openList.get(i).fCost == bestNodeCostF) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            //Case no Node in openList
            if (openList.isEmpty()) {
                break;
            }
            //Set currentNode
            currentNode = openList.get(bestNodeIndex);
            //Check if GoalNode is reached
            if (currentNode == goalNode) {
                pathReached = true;
                trackPath();
            }
            step++;
        }
        return pathReached;
    }

    /**
     * Opens the given node for consideration in the pathfinding process.
     *
     * @param node The node to be opened.
     */
    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {

            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * Tracks the final path by backtracking from the goal node to the start node.
     */
    public void trackPath() {

        Node current = goalNode;

        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }

}
