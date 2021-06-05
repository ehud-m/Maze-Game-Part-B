package Model;

import Client.IClientStrategy;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ClientStrategySolveMaze implements IClientStrategy {

    private Maze maze;
    private Solution solution;

    public ClientStrategySolveMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
            toServer.writeObject(maze);
            toServer.flush();
            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
            solution = (Solution) fromServer.readObject();
            fromServer.close();
            toServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Solution getSolution(){
        return solution;
    }
}
