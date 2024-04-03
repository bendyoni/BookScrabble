package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {
   private CacheManager existWords;
   private CacheManager notExist;
   private BloomFilter bf;
   private String[] dm_fileNames;

    Dictionary(String... fileNames) {
        existWords = new CacheManager(400, new LRU());
        notExist = new CacheManager(100, new LFU());
        bf = new BloomFilter(256, "MD5", "SHA1");
        dm_fileNames = fileNames;

        for (String file : fileNames) {
            try (BufferedReader bufR = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = bufR.readLine()) != null) {
                    String[] words = line.split(", | \\s");
                    for (String word : words) {
                        if (!word.isEmpty())
                            bf.add(word);
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean query (String word) {
        boolean inBook = false;
        if (existWords.query(word))
        {
            inBook = true;
            return inBook;
        }
        if (notExist.query(word))
        {
            inBook = false;
            return inBook;
        }
        if (bf.contains(word)) {
            existWords.add(word);
            bf.add(word);
            inBook = true;
            return inBook;
        }
        if (!bf.contains(word)) {
            notExist.add(word);
            inBook = false;
            return inBook;
        }
        return inBook;
    }

    public boolean challenge( String word) { // Checks in the books whether the given word is in them or not. 
        //If it is, then update the Cache and the BloomFilter and return "true", if not - return "false"

        boolean inBook;
        inBook = IOSearcher.search(word, dm_fileNames);
        if (inBook) {
            existWords.add(word);
            bf.add(word);
            return true;
        }
        else {
            notExist.add(word);
            return false;
        }
    }

}
