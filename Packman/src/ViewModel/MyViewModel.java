package ViewModel;

import Model.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this); // i am one of the observer of the model
    }

    @Override
    public void update(Observable observable, Object o) {
        setChanged();
        notifyObservers(o);
    }
    public void saveMaze(File filetosave) throws IOException {
        model.saveMaze(filetosave);
    }
    // we need to change it#%^$^&U^UTYJETYJERTYJRTYJRTYJ Throws exception
    public void generateMaze(int rows,int cols) throws IllegalArgumentException,UnknownHostException { ////!!!UnknownHostException {
        if (rows <1 || cols<1){
            throw new IllegalArgumentException();
        }
        model.generateMaze(rows, cols);
    }
    // we need to change it#%^$^&U^UTYJETYJERTYJRTYJRTYJ Throws exception

    public Maze getMaze(){
        return model.getMaze();
    }
    // we need to change it#%^$^&U^UTYJETYJERTYJRTYJRTYJ Throws exception
    public void solveMaze() throws UnknownHostException {
        model.solveMaze();
    }
    // we need to change it#%^$^&U^UTYJETYJERTYJRTYJRTYJ Throws exception
    public Solution getSolution(){
        return model.getSolution();
    }

    public void movePlayer(KeyEvent keyEvent){
        MovementDirection direction;
        switch (keyEvent.getCode()){
            case NUMPAD1:
                direction = MovementDirection.DownLeft;
                break;
            case NUMPAD2:
            case DOWN:
                direction = MovementDirection.Down;
                break;
            case NUMPAD3:
                direction = MovementDirection.DownRight;
                break;
            case NUMPAD4:
            case LEFT:
                direction = MovementDirection.Left;
                break;
            case NUMPAD6:
            case RIGHT:
                direction = MovementDirection.Right;
                break;
            case NUMPAD7:
                direction = MovementDirection.UPLeft;
                break;
            case NUMPAD8:
            case UP:
                direction = MovementDirection.Up;
                break;
            case NUMPAD9:
                direction = MovementDirection.UPRight;
                break;
            default:
                return;

        }
        model.updatePlayerLocation(direction);
    }

    public int getPlayerRow(){
        return model.getPlayerRow();
    }

    public int getPlayerCol(){
        return model.getPlayerCol();
    }

    public void loadMaze(File chosen) throws IOException, ClassNotFoundException, IllegalArgumentException {

        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(chosen));

        Object obj = reader.readObject();
        if (!(obj instanceof Maze))
            throw new IllegalArgumentException();
        else
            model.loadMaze((Maze)obj);

    }
}
