package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher {

    static boolean search(String word, String... fileName) {
        for (String file : fileName) {
            try (BufferedReader bufR = new BufferedReader( new FileReader(file))) {
                String line;
                while ((line = bufR.readLine()) != null) {
                    if (line.contains(word))
                    return true;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
        }

        return false;
    }
}


