package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher {

    static boolean search(String word, String... fileName) {
        BufferedReader bufR = null;
        try{
        for (String file : fileName) {
           // try (BufferedReader bufR = new BufferedReader( new FileReader(file))) {
            bufR = new BufferedReader( new FileReader(file)); // opening the file for reading
                String line;
                while ((line = bufR.readLine()) != null) {
                    if (line.contains(word))
                    return true;
                    
                }
            }
        }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try{ // closing the file
                    if(bufR != null)
                        bufR.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            

        return false;
    }
}


