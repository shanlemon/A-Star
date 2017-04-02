package v2;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Node {
	
	public static int amountPerSide = Grid.amountPerSide;
	
	public static int nodeWidth;
	public static int nodeHeight;
	
	public int gridX;
	public int gridY;
	
	Color bg = Color.WHITE;
	Color stroke = Color.BLACK;
	
	public int gCost; //to start
	public int hCost; //to target 
	public int fCost; // G + H
	
	public boolean isAvaliabe = true;
	
	public static void updateSize(){
		nodeWidth = Grid.frameX / amountPerSide;
		nodeHeight = Grid.frameY / amountPerSide;
	}
	
	public Node(int x, int y, GraphicsContext gc){
		gridX = x;
		gridY = y;
		draw(gc);
	}
	
	
	public void findCosts(Node currentNode, Node targetNode){
		gCost = getDistance(gridX, gridY, currentNode.gridX, currentNode.gridY);
		hCost = getDistance(gridX, gridY, targetNode.gridX, targetNode.gridX);
		fCost = gCost + hCost;
		
	}
	
	public void draw(GraphicsContext gc){
		updateSize();
		gc.setFill(bg);
		gc.setStroke(stroke);
		gc.fillRect(gridX * nodeWidth, gridY * nodeHeight, nodeWidth, nodeHeight);
		gc.strokeRect(gridX * nodeWidth, gridY * nodeHeight, nodeWidth, nodeWidth);
		gc.setFill(stroke);
		gc.setFont(new Font(15));
		gc.fillText("G: "+ gCost + " H: " + hCost ,(gridX * nodeWidth) + 10, (gridY * nodeHeight) + (nodeHeight/2));
		gc.fillText("F: "+ fCost,(gridX * nodeWidth) + 10, (gridY * nodeHeight) + (nodeHeight/3));

	}
	
	public void setBackground(Color c){
		bg = c;
	}
	
	public void setStroke(Color c){
		stroke  = c;
	}
	
	public int getDistance(int x1, int y1, int x2, int y2){
		int distance = (int) (Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)) * 10);
		return distance;
	}
}
