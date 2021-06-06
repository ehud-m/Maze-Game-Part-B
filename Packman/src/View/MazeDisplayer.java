package View;

import Model.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    private Solution solution;
    // player position:
    private int playerRow;
    private int playerCol;
    // wall and player images:
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameSea = new SimpleStringProperty();
    StringProperty imageFileNameSolution = new SimpleStringProperty();

    public void setImageFileNameSea(String imageFileNameSea) {
        this.imageFileNameSea.set(imageFileNameSea);
    }

    public String getImageFileNameSea() {
        return imageFileNameSea.get();
    }

    public String getImageFileNameSolution() {
        return imageFileNameSolution.get();
    }

    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.imageFileNameSolution.set(imageFileNameSolution);
    }

    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        draw();
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }


    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public void drawMaze(Maze maze) {
        this.maze = maze;
        this.solution=null;
        this.playerRow = maze.getStartPosition().getRowIndex();
        this.playerCol = maze.getStartPosition().getColumnIndex();
        draw();
    }
    public void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.getRows();
            int cols = maze.getCols();

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            if (solution != null)
                drawMazeSolution(graphicsContext, cellHeight, cellWidth); ///Implement this

            drawPlayer(graphicsContext, cellHeight, cellWidth);
        }
    }

    private void drawMazeSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        Image solutionImage = null;
        try {
            solutionImage = new Image(new FileInputStream(getImageFileNameSolution()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
       // if(playerImage == null)
       //     graphicsContext.fillRect(x, y, cellWidth, cellHeight);
       // else
        for (AState s:solution.getSolutionPath()) {
            double x = ((MazeState)s).getPosition().getColumnIndex() * cellWidth;
            double y = ((MazeState)s).getPosition().getRowIndex() * cellHeight;
            graphicsContext.drawImage(solutionImage,x,y,cellWidth,cellHeight);
        }
    }
    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        graphicsContext.setFill(Color.RED);

        Image wallImage = null;
        Image seaImage = null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
            seaImage = new Image(new FileInputStream(getImageFileNameSea()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                if(maze.getPositionValue(i,j) == 1){
                    /*if(wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else*/
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
                else{
                    graphicsContext.drawImage(seaImage,x,y,cellWidth,cellHeight);
                }

            }
        }
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        draw();
    }
}
