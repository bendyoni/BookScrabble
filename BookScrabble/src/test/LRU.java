package test;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class LRU implements CacheReplacementPolicy{
    private final Set<String> set = new HashSet<>();
    private final Deque <String> queue = new LinkedList<>();

    @Override
    public void add(String word) {
        if (set.contains(word)) {
            queue.remove(word);  //We asked about the word so its priority for remove is less
        }
        else {
            if ((CacheManager.cacheSize < set.size()) && (CacheManager.cacheSize != 0)) {
                String victimWord = queue.removeLast();
                set.remove(victimWord); // Override remove()
                //set.add(word);
            }
        }
        queue.addFirst(word); // adds the word that was asked at the top of the queue ( will be last to be removed)
        set.add(word);
    }

    @Override
    public String remove(){
        if (queue.isEmpty())
            return null;
        String victimWord = queue.removeLast();
        set.remove(victimWord);
        return victimWord;
    }
}

