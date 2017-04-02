import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Node {
	int NODE_X;
	int NODE_Y;
	public static int NODE_WIDTH;
	public static int NODE_HEIGHT;
	public int gCost; // distance from starting node
	public int hCost; // distance from end node
	public int fCost;

	int FRAME_X;
	int FRAME_Y;
	public static int AMOUNTPERSIDE = 10;
	GraphicsContext gc;
	Color c;
	Scene scene;

	public Node(int x, int y, int framex, int framey, GraphicsContext gc, Color c, Scene scene) {
		this.FRAME_X = framex;
		this.FRAME_Y = framey;
		this.NODE_X = x;
		this.NODE_Y = y;
		this.gc = gc;
		this.c = c;
		this.scene = scene;

		NODE_WIDTH = FRAME_X / AMOUNTPERSIDE;
		NODE_HEIGHT = FRAME_Y / AMOUNTPERSIDE;
		gc.setFill(c);
		gc.strokeRect(x, y, NODE_WIDTH, NODE_HEIGHT);
		gc.fillRect(x, y, NODE_WIDTH, NODE_HEIGHT);

		gc.setFont(new Font(10));
		gc.strokeText(getGridX() + " , " + getGridY(), NODE_X + NODE_WIDTH / 3, NODE_Y + NODE_HEIGHT / 2);
		//drawHGCost();



	}
	public void setHCost(int hCost){
		this.hCost = hCost;
		fCost = hCost + gCost;
	}
	public void setGCost(int gCost){
		this.gCost = gCost;
		fCost = hCost + gCost;
	}
	public void drawHGCost(){
		gc.setFill(c);
		gc.strokeRect(NODE_X, NODE_Y, NODE_WIDTH, NODE_HEIGHT);
		gc.fillRect(NODE_X, NODE_Y, NODE_WIDTH, NODE_HEIGHT);
		gc.strokeText(getGridX() + " , " + getGridY(), NODE_X + NODE_WIDTH / 3, NODE_Y + NODE_HEIGHT / 2);
		gc.setStroke(Color.RED);
		gc.strokeText(""+gCost + "  ,  " + hCost, NODE_X + NODE_WIDTH / 3, NODE_Y + NODE_HEIGHT / 4);
		gc.strokeText(""+fCost, NODE_X + NODE_WIDTH / 3, NODE_Y + NODE_HEIGHT - 10);
		gc.setStroke(Color.BLACK);
	}
	public int fCost(){
		return gCost + hCost;
	}

	public int getGridX() {
		int gridX = NODE_X / NODE_WIDTH;
		return gridX;
	}

	public int getGridY() {
		int gridY = NODE_Y / NODE_HEIGHT;
		return gridY;
	}

	public void setColor(Color c) {
		this.c = c;
		drawHGCost();
	}

	public void setTargetNode() {
		gc.setFill(Color.RED);
		gc.strokeRect(NODE_X, NODE_Y, NODE_WIDTH, NODE_HEIGHT);
		gc.fillRect(NODE_X, NODE_Y, NODE_WIDTH, NODE_HEIGHT);
		gc.strokeText(getGridX() + " , " + getGridY(), NODE_X + NODE_WIDTH / 3, NODE_Y + NODE_HEIGHT / 2);
		drawHGCost();
	}

	public void setBeginNode() {
		drawHGCost();
		gc.setFill(Color.GREEN);
		gc.strokeRect(NODE_X, NODE_Y, NODE_WIDTH, NODE_HEIGHT);
		gc.fillRect(NODE_X, NODE_Y, NODE_WIDTH, NODE_HEIGHT);
		gc.strokeText(getGridX() + " , " + getGridY(), NODE_X + NODE_WIDTH / 3, NODE_Y + NODE_HEIGHT / 2);
		gc.strokeText(gCost + " , " + hCost, NODE_X + NODE_WIDTH / 4, NODE_Y + NODE_HEIGHT / 4);
	}
	
	

	// public void isMouseDown(){
	// boolean isMousePressed = false;
	// scene.setOnMousePressed(new EventHandler<MouseEvent>(){
	// @Override
	// public void handle(MouseEvent event) {
	// if(event.getSceneX() > NODE_X && event.getSceneX() < NODE_X + NODE_WIDTH
	// && event.getSceneY() > NODE_Y && event.getSceneY() < NODE_Y +
	// NODE_HEIGHT){
	// System.out.println("clicked node" + getGridX() + " , " + getGridY());
	// }
	//
	// }
	//
	// });
	// }

	public void collides(double x, double y) {
		if (x > NODE_X && x < NODE_X + NODE_WIDTH && y > NODE_Y && y < NODE_Y + NODE_HEIGHT) {
			System.out.println("clicked node" + getGridX() + " , " + getGridY());
		}
	}

}
