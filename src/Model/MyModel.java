package Model;

import Client.Client;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.ClosedByInterruptException;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel{
    private Maze maze;
    private Solution solution;
    private int playerRow;
    private int playerCol;
    private Server generator;
    private Server solver;
    private boolean isSolved;

    public MyModel() {
        generator = new Server(5400,1000,new ServerStrategyGenerateMaze());
        generator.start();
        solver = new Server(5401,1000,new ServerStrategySolveSearchProblem());
        solver.start();
    }

    @Override
    public void generateMaze(int rows, int cols) throws UnknownHostException {
        //need to call server
        ClientStrategyGenerateMaze clientStrategyGenerateMaze = new ClientStrategyGenerateMaze(rows,cols);
        Client client = new Client(InetAddress.getLocalHost(),5400,clientStrategyGenerateMaze);
        client.communicateWithServer();
        isSolved=false;
        this.solution=null;
        maze = clientStrategyGenerateMaze.getMaze();
        playerRow = maze.getStartPosition().getRowIndex(); // strat pos
        playerCol = maze.getStartPosition().getColumnIndex();
        setChanged();
        notifyObservers("maze generated");
    }

    public void saveMaze(File filetosave) throws IOException {
        ObjectOutputStream ob = new ObjectOutputStream(new FileOutputStream(filetosave));
        ob.writeObject(maze);
        ob.flush();
        ob.close();
    }
    public void loadMaze(Maze maze) {
        this.maze=maze;
        this.solution=null;
        playerRow = maze.getStartPosition().getRowIndex(); // strat pos
        playerCol = maze.getStartPosition().getColumnIndex();
        setChanged();
        notifyObservers("maze generated");
    }

    public void stop() { ////////////////////////maybe need to change servers stop
        System.out.println("Servers stopped");
        generator.stop();
        solver.stop();
    }
    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void solveMaze() throws UnknownHostException {
        ClientStrategySolveMaze solver = new ClientStrategySolveMaze(maze);
        Client client = new Client(InetAddress.getLocalHost(),5401,solver);
        client.communicateWithServer();

        solution = solver.getSolution();

        setChanged();
        notifyObservers("maze solved");
    }

    @Override
    public Solution getSolution() {
        return solution;
    }

    @Override
    public int getPlayerRow() {
        return playerRow;
    }

    @Override
    public int getPlayerCol() {
        return playerCol;
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);

    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {

        switch (direction){

            case Up:
                updateLocation(playerRow-1,playerCol);
                break;
            case UPRight:
                updateLocationDiagonal(playerRow-1,playerCol+1);
                break;
            case Right:
                updateLocation(playerRow,playerCol+1);
                break;
            case DownRight:
                updateLocationDiagonal(playerRow+1,playerCol+1);
                break;
            case Down:
                updateLocation(playerRow+1,playerCol);
                break;
            case DownLeft:
                updateLocationDiagonal(playerRow+1,playerCol-1);
                break;
            case Left:
                updateLocation(playerRow,playerCol-1);
                break;
            case UPLeft:
                updateLocationDiagonal(playerRow-1,playerCol-1);
                break;
            default:
                break;
        }

    }

    private void updateLocationDiagonal(int newplayerRow, int newplayerCol) {
        if ((maze.PositionInMaze(newplayerRow,playerCol) && maze.getPositionValue(newplayerRow,playerCol)==0)||
                (maze.PositionInMaze(playerRow,newplayerCol) && maze.getPositionValue(playerRow,newplayerCol)==0))
            updateLocation(newplayerRow,newplayerCol);

    }
    public void updateLocation(int newplayerRow, int newplayerCol) {
        if(!(maze.PositionInMaze(newplayerRow,newplayerCol) && maze.getPositionValue(newplayerRow,newplayerCol) == 0))
            return;
        playerRow = newplayerRow;
        playerCol = newplayerCol;
        if (maze.getGoalPosition().getRowIndex() == playerRow && maze.getGoalPosition().getColumnIndex()==playerCol) {
            isSolved=true;
            setChanged();
            notifyObservers("goal reached");
        }
        else{
            setChanged();
            notifyObservers("player moved");
        }
    }
    public boolean isSolved() {
        return isSolved;
    }
}
