package test;

import java.util.HashSet;
import java.util.Set;

public class CacheManager {
	static int cacheSize;
	Set<String> cacheWords = new HashSet<>();
	CacheReplacementPolicy crp;

	public CacheManager(int size, CacheReplacementPolicy crp) {
		CacheManager.cacheSize = size;
		this.crp = crp;
	}

	boolean query (String word) {
		return cacheWords.contains(word);
	}

	void add(String word) {
		crp.add(word); // Override add()
		if (cacheWords.size() == cacheSize) {
			String victimWord = crp.remove(); // Override remove()
			cacheWords.remove(victimWord); // regular Set Remove()
		}
	}
}
