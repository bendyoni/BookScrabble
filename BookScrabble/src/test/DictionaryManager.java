package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
    private static DictionaryManager instance;  // for singleton
    private final Map<String, DictionaryProxy> dictionaryMap;

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

    private ArrayList<DictionaryProxy> getDictionaryProxies(String[] fileNames) {
        ArrayList<DictionaryProxy> proxies = new ArrayList<DictionaryProxy>();
        for (String file : fileNames){
            if (!dictionaryMap.containsKey(file)) {  // saves the files in cach so i dont need to duplicate them - Flyweight pattern
                DictionaryProxy pDictionary = new DictionaryProxy(new Dictionary(file));
                dictionaryMap.put(file, pDictionary);
            }
            proxies.add(dictionaryMap.get(file));
        }

        return proxies;
    }

    public boolean query(String... args) {
        boolean exist = false;
        String[] fileNames = new String[args.length - 1];
        String searchWord = args[args.length - 1];  // the word i want to search for
        System.arraycopy(args, 0, fileNames, 0, fileNames.length);
        ArrayList<DictionaryProxy> proxis = getDictionaryProxies(fileNames);
        for (DictionaryProxy proxy : proxis) {
            if(proxy.query(searchWord))
                exist = true;
        }
        return exist;
    }

    public boolean challenge (String... args) {
        boolean exist = false;
        String[] fileNames = new String[args.length - 1];
        String searchWord = args[args.length - 1];  // the word i want to search for
        System.arraycopy(args, 0, fileNames, 0, fileNames.length);

        ArrayList<DictionaryProxy> proxies = getDictionaryProxies(fileNames);
        for (DictionaryProxy proxy : proxies) {
           if(proxy.challenge(searchWord))
            exist = true;
        }
        return exist;
    }

    int getSize() {
        int size = 0;
        for (DictionaryProxy pDic : dictionaryMap.values()) {
            if (pDic != null)
                size++;
        }
        return size;
    }


   //  A class that represents a proxy for the original Dictionary class 
    public class DictionaryProxy extends Dictionary {
        private final Dictionary dictionary;

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
