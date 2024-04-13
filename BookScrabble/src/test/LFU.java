package test;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFU implements CacheReplacementPolicy{
    private final Map<String, Integer> usingMap = new HashMap<>(); // a Map of all of the words and there using times
    private final Map<Integer, LinkedHashSet<String>> usingQueue = new HashMap<>(); // a Map that gives a LinkedHashSet of words
    //for every key of using times, by order

    @Override
    public void add(String word) {
        int sumOfUse = usingMap.getOrDefault(word, 0);
        usingMap.put(word, sumOfUse+1);

        LinkedHashSet<String> set = usingQueue.get(sumOfUse);
        if (set != null)
            set.remove(word); //remove the first word of this set of using times

        set = usingQueue.computeIfAbsent(sumOfUse+1, k -> new LinkedHashSet<>());
        set.add(word);

        if (CacheManager.cacheSize < usingQueue.size() && CacheManager.cacheSize != 0){ //If the number of the usingQueue is greater than the cache,   
            //then the first word with this frequency is excluded from the lowest frequency set
            int minUsing = Collections.min(usingQueue.keySet());
            LinkedHashSet<String> minUsingSet = usingQueue.get(minUsing);
            String victimWord = minUsingSet.iterator().next();
            minUsingSet.remove(victimWord);
            if (minUsingSet.isEmpty()) //if the lowest frequency set is fineshd - go it is removed from the usingQueue
                usingQueue.remove(minUsing);

            usingMap.remove(victimWord);
        }
    }

        @Override
        public String remove() {
            if (usingQueue.isEmpty())
                return null;
            int minused = Collections.min(usingQueue.keySet());
            LinkedHashSet<String> minUsingSet = usingQueue.get(minused);
            String victimWord = minUsingSet.iterator().next();
            minUsingSet.remove(victimWord);
            if (minUsingSet.isEmpty())
                usingQueue.remove(minused);
            
            usingMap.remove(victimWord);
            return victimWord;
        }

}
