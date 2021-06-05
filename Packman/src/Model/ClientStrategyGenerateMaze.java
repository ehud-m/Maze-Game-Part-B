package Model;

import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;

import java.io.*;

public class ClientStrategyGenerateMaze implements IClientStrategy {

    private int rows;
    private int cols;
    private Maze maze;

    public ClientStrategyGenerateMaze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
            //IMazeGenerator gen = new MyMazeGenerator();
            int[] rowCol ={rows,cols};
            toServer.writeObject(rowCol);
            toServer.flush();
            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
            byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with
            //MyCompressor) from server

            ByteArrayInputStream byteIn = new ByteArrayInputStream(compressedMaze);
            InputStream is = new MyDecompressorInputStream(byteIn);
            byte[] decompressedMaze = new byte[1500000 /*CHANGE
SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed
            //maze -
            is.read(decompressedMaze); //Fill decompressedMaze
            maze = new Maze (decompressedMaze);
            is.close();
            fromServer.close();
            toServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Maze getMaze(){
        return maze;
    }


}
