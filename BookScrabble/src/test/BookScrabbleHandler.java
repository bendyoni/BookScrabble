package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler{
    PrintWriter out;
    private Scanner in;
    private boolean exist;

    @Override
    public void handleClient(InputStream inFromClient,OutputStream outToClient) {
        out=new PrintWriter(outToClient);
        in = new Scanner(inFromClient);
        String text = in.nextLine();
        String[] words = text.split(",");
        String wantedSearch = words[0];

        String[] fileNames = new String[words.length - 1];
        System.arraycopy(words, 1, fileNames, 0, fileNames.length);

        DictionaryManager dm=DictionaryManager.get();
        if (wantedSearch.equals("Q")) 
            exist = dm.query(fileNames);
        
        else if(wantedSearch.equals("C"))
            exist = dm.challenge(fileNames);
        
        out.print(exist);
		out.flush();
    }

@Override
    public void close() {
        in.close();
        out.close();
    }
}
