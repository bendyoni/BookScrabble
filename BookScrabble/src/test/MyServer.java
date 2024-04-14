package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    private int port;
    private ClientHandler ch;
    private volatile boolean stop;
    private Thread threadOfServer;
    private ServerSocket server;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        stop = false;
    }

    public void start() {
        threadOfServer = new Thread(()->runServer());
        threadOfServer.start();
    }

    private void runServer() { // A method that runs the server by the Start method
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000);

            while (!stop) {
                try {   // Activating the communication mechanism injected in the ch variable
                        Socket aClient = server.accept();
                        ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                        System.out.println("connected to server");

                        aClient.getInputStream().close();
                        aClient.getOutputStream().close();
                        aClient.close();
                    } catch (SocketTimeoutException e) {} // ignore
                    catch (IOException e) {e.printStackTrace();}
            }
            server.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    void close(){
        try{
            stop = true; // stops the server

            if (threadOfServer != null) {
                threadOfServer.interrupt();
                threadOfServer.join();  //If there is an active claient, wait for him to finish
            }

            if (server != null)
                server.close();

            ch.close();
        } catch (IOException e) {e.printStackTrace();}
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        finally {  // Make sure the server closes anyway
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
    }
	
}
