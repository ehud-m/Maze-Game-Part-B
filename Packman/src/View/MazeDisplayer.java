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

import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    private Solution solution;

    private boolean[][] randomAddings;
    // player position:
    private int playerRow;
    private int playerCol;
    // wall and player images:
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameSea = new SimpleStringProperty();
    StringProperty imageFileNameSolution = new SimpleStringProperty();
    StringProperty imageFileNameWallAdding = new SimpleStringProperty();
    StringProperty imageFileNameGoal = new SimpleStringProperty();

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String getImageFileNameGoal() {
        return imageFileNameGoal.get();
    }

    public String getImageFileNameWallAdding() {
        return imageFileNameWallAdding.get();
    }
    public String getImageFileNameSea() {
        return imageFileNameSea.get();
    }

    public String getImageFileNameSolution() {
        return imageFileNameSolution.get();
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.imageFileNameGoal.set(imageFileNameGoal);
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public void setImageFileNameSea(String imageFileNameSea) {
        this.imageFileNameSea.set(imageFileNameSea);
    }

    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.imageFileNameSolution.set(imageFileNameSolution);
    }

    public void setImageFileNameWallAdding(String imageFileNameWallAdding) {
        this.imageFileNameWallAdding.set(imageFileNameWallAdding);
    }

    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerPosition(int row, int col) {
        drawOldPlayer(getGraphicsContext2D(),getHeight()/maze.getRows(),getWidth()/maze.getCols());
        this.playerRow = row;
        this.playerCol = col;
        drawPlayer(getGraphicsContext2D(),getHeight()/maze.getRows(),getWidth()/maze.getCols());
    }

    public void drawMaze(Maze maze) {
        this.maze = maze;
        this.solution=null;
        this.playerRow = maze.getStartPosition().getRowIndex();
        this.playerCol = maze.getStartPosition().getColumnIndex();

        Random rnd  = new Random();
        this.randomAddings = new boolean[maze.getRows()][maze.getCols()];
        for (int i =0;i<maze.getRows();i++)
            for (int j = 0 ; j<maze.getCols();j++)
                randomAddings[i][j]=(rnd.nextInt(5)==0);

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

            drawMazeGoal(graphicsContext, cellHeight, cellWidth);
            drawPlayer(graphicsContext, cellHeight, cellWidth);
        }
    }

    private void drawMazeGoal(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = maze.getGoalPosition().getColumnIndex() * cellWidth +cellWidth/6;
        double y = maze.getGoalPosition().getRowIndex() * cellHeight + cellHeight/6;
        graphicsContext.setFill(Color.GREEN);

        Image goalImage = null;
        try {
            goalImage = new Image(new FileInputStream(getImageFileNameGoal()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if (goalImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(goalImage, x, y, cellWidth/3*2, cellHeight/3*2);

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
        for (int i = 0; i <solution.getSolutionPath().size()-1;i++){
        //for (AState s:solution.getSolutionPath()) {
            AState s = solution.getSolutionPath().get(i);
            double x = ((MazeState)s).getPosition().getColumnIndex() * cellWidth;
            double y = ((MazeState)s).getPosition().getRowIndex() * cellHeight;
            graphicsContext.drawImage(solutionImage,x,y,cellWidth,cellHeight);
        }
    }
    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        graphicsContext.setFill(Color.RED);

        Image wallImage = null;
        Image seaImage = null;
        Image wallAddingImage=null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
            seaImage = new Image(new FileInputStream(getImageFileNameSea()));
            wallAddingImage = new Image(new FileInputStream(getImageFileNameWallAdding()));
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
                        if (wallAddingImage!=null && randomAddings[i][j])
                            graphicsContext.drawImage(wallAddingImage, x, y, cellWidth, cellHeight);
                }
                else{
                    graphicsContext.drawImage(seaImage,x,y,cellWidth,cellHeight);
                }
            }
        }
    }

        private void drawOldPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
            double x = getPlayerCol() * cellWidth;
            double y = getPlayerRow() * cellHeight;
            graphicsContext.setFill(Color.GREEN);

            Image seaImage = null;
            try {
                seaImage = new Image(new FileInputStream(getImageFileNameSea()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no player image file");
            }
            if(seaImage == null)
                graphicsContext.fillRect(x, y, cellWidth, cellHeight);
            else
                graphicsContext.drawImage(seaImage, x, y, cellWidth, cellHeight);
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
