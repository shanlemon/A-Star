import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Grid extends Application {

	public static final int FRAME_X = 1000;
	public static final int FRAME_Y = 700;

	public Node currentNode;
	public Node targetNode;

	ArrayList<Node> openSet = new ArrayList<Node>();
	ArrayList<Node> closedSet = new ArrayList<Node>();
	ArrayList<Node> neighbors = new ArrayList<Node>();

	int area;
	// public boolean[][] closedSet = new
	// boolean[Node.AMOUNTPERSIDE][Node.AMOUNTPERSIDE];
	// public boolean[][] openSet = new
	// boolean[Node.AMOUNTPERSIDE][Node.AMOUNTPERSIDE];

	public static Node[][] grid = new Node[Node.AMOUNTPERSIDE][Node.AMOUNTPERSIDE];

	public static void main(String[] args) {
		launch(args);
	}

	public void setHGCost(){
		for(int x = 0; x < Node.AMOUNTPERSIDE; x++){
			for(int y = 0; y < Node.AMOUNTPERSIDE; y++){
				grid[x][y].setHCost(getDistance(grid[x][y].getGridX(),grid[x][y].getGridY(), targetNode.getGridX(),targetNode.getGridY()));
				grid[x][y].setGCost(getDistance(grid[x][y].getGridX(),grid[x][y].getGridY(), currentNode.getGridX(),currentNode.getGridY()));
				grid[x][y].drawHGCost();
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Create Frame
		StackPane root = new StackPane();
		Canvas canvas = new Canvas(FRAME_X, FRAME_Y);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("A* Algorithm");

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Make Grid
		for (int x = 0; x < Node.AMOUNTPERSIDE; x++) {
			int nodex = x * Node.NODE_WIDTH;
			for (int y = 0; y < Node.AMOUNTPERSIDE; y++) {
				int nodey = y * Node.NODE_HEIGHT;
				grid[x][y] = new Node(nodex, nodey, FRAME_X, FRAME_Y, gc, Color.WHITE, scene);
				openSet.add(grid[x][y]);
			}
		}
		area  = (int) Math.pow(Node.AMOUNTPERSIDE, 2);
		// targetNode = grid[9][9];
		// currentNode = grid[0][0];
		// openSet[0][0] = true;
		targetNode = grid[2][4];

		
		
		
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (int x = 0; x < grid[0].length; x++) {
					for (int y = 0; y < grid[1].length; y++) {
						if (event.getSceneX() > grid[x][y].NODE_X
								&& event.getSceneX() < grid[x][y].NODE_X + grid[x][y].NODE_WIDTH
								&& event.getSceneY() > grid[x][y].NODE_Y
								&& event.getSceneY() < grid[x][y].NODE_Y + grid[x][y].NODE_HEIGHT) {

							System.out.println("clicked node: " + grid[x][y].getGridX() + ", " + grid[x][y].getGridY());
							openSet.remove(grid[x][y]);
							closedSet.add(grid[x][y]);
							grid[x][y].setColor(Color.RED);
							//setHGCost();
						}
					}
				}
			}
		});
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.A){
					System.out.println("A pressed");
					aStar();
				}
			}
			
		});

		// Loop
		new AnimationTimer() {

			long lastNanoTime = System.nanoTime();

			@Override
			public void handle(long currentNanoTime) {
				double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;

			}

		}.start();

		stage.show();

	}
	public void aStar(){
		//while (openSet.size() > 0) {
			currentNode = openSet.get(0);
			currentNode.setColor(Color.GREEN);
			setHGCost();
			
			for (int i = 1; i < openSet.size(); i++) {
				System.out.println(openSet.size());
				if (openSet.get(i).hCost < currentNode.hCost && openSet.get(i).fCost() < currentNode.fCost() 
						|| (openSet.get(i).fCost() == currentNode.fCost()
						&& openSet.get(i).hCost < currentNode.hCost)) {
					currentNode = openSet.get(i);
					currentNode.setColor(Color.GREEN);
					setHGCost();

					
				}
			}
			currentNode.setColor(Color.BLUE);
			openSet.remove(currentNode);
			closedSet.add(currentNode);
			// if (currentNode.getGridX() == targetNode.getGridX() &&
			// currentNode.getGridY() == targetNode.getGridY()) {
			// System.out.println("Found target");
			// }
			if (currentNode == targetNode) {
				System.out.println("Found target");
			}
		}
		//}
	public int getDistance(int x1, int y1, int x2, int y2){
		int distance = (int) (Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)) * 10);
		return distance;
	}

}
