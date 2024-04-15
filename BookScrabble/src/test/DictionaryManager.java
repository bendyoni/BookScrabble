package test;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
    private static DictionaryManager instance;  // for singleton
    private Map<String, DictionaryProxy> dictionaryMap;

    private DictionaryManager() {
        dictionaryMap = new HashMap<>();
    }

    public static DictionaryManager get() {  // for singleton
        if (instance == null) 
            instance = new DictionaryManager();

        return instance;
    }


    /* A method that checks whether a dictionary with the same file list already exists.
       If it exists, the method returns the existing dictionary from the given structure. 
       If not, it creates a new dictionary and adds it to the data structure. (The key of the dictionarys are defined by their file list) */

    private DictionaryProxy getDictionary(String[] fileNames) {
        String key = String.join(",", fileNames); 
        if (!dictionaryMap.containsKey(key)) {
            DictionaryProxy pDictionary = new DictionaryProxy(new Dictionary(fileNames));
            dictionaryMap.put(key,pDictionary);
        }

        return dictionaryMap.get(key);
    }

    public boolean query(String... args) {
        String[] fileNames = new String[args.length - 1];
        String searchWord = args[args.length - 1];  // the word i want to search for
        System.arraycopy(args, 0, fileNames, 0, fileNames.length);
        DictionaryProxy pDictinary = getDictionary(fileNames);
        return pDictinary.query(searchWord);
    }

    // public boolean query(String... args) {
    //     String[] fileNames = new String[args.length - 1];
    //     String searchWord = args[args.length - 1];  // the word i want to search for
        // boolean found = false;

        // for (DictionaryProxy pDic : dictionaryMap.values()) {
        //     if (pDic.query(searchWord))
        //         found = true;
        // }
        // return found;
    //}

    public boolean challenge (String... args) {
        String[] fileNames = new String[args.length - 1];
        String searchWord = args[args.length - 1];  // the word i want to search for
        System.arraycopy(args, 0, fileNames, 0, fileNames.length);

        DictionaryProxy pDictionary = getDictionary(fileNames);
        return pDictionary.challenge(searchWord);
    }

    // int getSize() {
    //     return dictionaryMap.size();
    // }

    int getSize() {
        int size = 0;
        for (DictionaryProxy pDic : dictionaryMap.values()) {
            if (pDic != null)
                size++;
        }
        return size;
    }

   //  A class called DictionaryProxy that represents a proxy for the original Dictionary class 
    public class DictionaryProxy extends Dictionary {
        private Dictionary dictionary;

        public DictionaryProxy(Dictionary dictionary) {
            this.dictionary = dictionary;
        }

        @Override
        public boolean query(String word) {
            return dictionary.query(word);
        }

        @Override
        public boolean challenge(String word) {
            return dictionary.challenge(word);
        }
    }
     



}
