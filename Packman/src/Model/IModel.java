package Model;


import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observer;

public interface IModel {

    void saveMaze(File filetosave) throws IOException;
    void loadMaze(Maze maze);
    void generateMaze(int rows,int cols) throws UnknownHostException; // generate a maze
    Maze getMaze(); //will return the maze that the model creates
    void solveMaze() throws UnknownHostException; //the model already have the maze as a data member
    Solution getSolution();
    void updatePlayerLocation(MovementDirection direction); //tells when im done with the movement (check if possible, check if finished maze)
    int getPlayerRow();
    int getPlayerCol();
    void assignObserver(Observer o);

    void updateLocation(int newplayerRow, int newplayerCol);
    boolean isSolved();
    void stop();
}
