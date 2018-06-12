import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    private int clientNum = 0;
    private Card topCard = null; // Temporally used; for checking object interchange
    ServerSocket ss = null;
    Server(){
        this.clientNum = 0;
        try {
            this.ss = new ServerSocket(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Server(int portNum){
        this.clientNum = 0;
        try {
            this.ss = new ServerSocket(portNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){
            if(!this.acceptClient()){
                break;
            }
        }

        System.out.println("closing Server..");
    }

    private boolean acceptClient(){

        if(this.clientNum >= 2){
            System.out.println("clientNum >= 2, break");
            return false;
        }

        Socket soc = null;
        try {
            soc = ss.accept();

            System.out.println("New client socket arrived");
            OutputStream out = soc.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);

            dos.writeUTF("message from server");

            this.clientNum++;

            dos.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}