package v2;

import java.util.ArrayList;
import java.util.List;

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

	public static int frameX = 750;
	public static int frameY = 750;
	public static int amountPerSide = 10;
	
	public Node[][] node;
	
	Node currentNode;
	Node targetNode;
	List<Node> neighbors = new ArrayList<Node>();
	List<Node> track = new ArrayList<Node>();
	
	int nodeWidth = frameX / amountPerSide;
	int nodeHeight = frameY / amountPerSide;

	@Override
	public void start(Stage stage) throws Exception {
		StackPane root = new StackPane();
		Canvas canvas = new Canvas(frameX, frameY);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("A-Star 2");
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// create grid
		Node.updateSize();
		node = new Node[amountPerSide][amountPerSide];
		for (int x = 0; x < amountPerSide; x++) {
			for (int y = 0; y < amountPerSide; y++) {
				node[x][y] = new Node(x, y, gc);
			}
		}

		setCurrentNode(node[2][8]);
		setTargetNode(node[amountPerSide - 1][amountPerSide - 1]);

		
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int gridCX = (int) (e.getSceneX() / nodeWidth);
				int gridCY = (int) (e.getSceneY() / nodeHeight);
				System.out.println("" + gridCX + " " + gridCY);
				if (gridCX <= amountPerSide - 1 && gridCX >= 0 && gridCY <= amountPerSide - 1 && gridCX >= 0){
					node[gridCX][gridCY].setBackground(Color.GREY);
					node[gridCX][gridCY].isAvaliabe = false;
				}
			}

		});
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.A){
					AStar();
				}
			}
			
		});
		
		new AnimationTimer() {

			@Override
			public void handle(long now) {

				for (int x = 0; x < amountPerSide; x++)
					for (int y = 0; y < amountPerSide; y++) {
						node[x][y].draw(gc);
					}
			}
		}.start();

		stage.show();
	}

	public void AStar() {
		// find neighbors of currentNode
		//while(currentNode.gridX != targetNode.gridX && currentNode.gridY != targetNode.gridY){
		currentNode.findCosts(currentNode, targetNode);

			for (int i = 0; i < neighbors.size(); i++) {
				// find costs of neighbors
				neighbors.get(i).findCosts(currentNode, targetNode);
				
//				System.out.println("N: " + neighbors.get(i).fCost);
//				System.out.println("C: " + currentNode.fCost);
				
				if (neighbors.get(i).fCost < currentNode.fCost) {
					System.out.println("X: " + neighbors.get(i).gridX + " Y: " + neighbors.get(i).gridY);
					setCurrentNode(neighbors.get(i));
				}else if(neighbors.get(i).hCost < currentNode.hCost && isTheLowestH(neighbors,neighbors.get(i))){
					System.out.println("X: " + neighbors.get(i).gridX + " Y: " + neighbors.get(i).gridY);
					setCurrentNode(neighbors.get(i));
				}
				
			}

		}

	//}
	
	
	public boolean isTheLowestH(List<Node> array, Node n){
		for(int i = 0; i < array.size(); i++){
			if(array.get(i).hCost < n.hCost){
				return false;
			}
		}
		return true;
	}

	public void getNeighbors(Node currentNode) {
		int x = currentNode.gridX;
		int y = currentNode.gridY;
		
		if(neighbors.size() > 0)
			 neighbors.clear();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				if (isInGrid(currentNode.gridX + i, currentNode.gridY + j))
					if(node[currentNode.gridX + i][currentNode.gridY + j].isAvaliabe)
					neighbors.add(node[currentNode.gridX + i][currentNode.gridY + j]);
			}
		}
		// for(int i =0 ; i< neighbors.size(); i++){
		// System.out.println("Neighbor #" + i + " X: "+ neighbors.get(i).gridX +
		// " Y: " + neighbors.get(i).gridY);
		// }

	}
	
	
	public void setCurrentNode(Node node) {
		currentNode = node;
		getNeighbors(currentNode);
		currentNode.setBackground(Color.GREEN);
	}

	public void setTargetNode(Node node) {
		targetNode = node;
		targetNode.setBackground(Color.RED);
	}

	public boolean isInGrid(int x, int y) {
		// Check if a position is valid in the grid
		if (x < 0 || y < 0)
			return false;
		if (x > (amountPerSide -1) || y > (amountPerSide -1))
			return false;
		return true;
	}
	


	public static void main(String[] args) {
		launch(args);
	}

}
