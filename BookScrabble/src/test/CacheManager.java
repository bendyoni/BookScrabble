package test;

import java.util.HashSet;
import java.util.Set;

public class CacheManager {
	static int cacheSize;
	Set<String> cacheWords;
	CacheReplacementPolicy crp;

	CacheManager(int size, CacheReplacementPolicy crp) {
		CacheManager.cacheSize = size;
		this.crp = crp;
		this.cacheWords  = new HashSet<>();
	}

	boolean query (String word) {
		return cacheWords.contains(word);
	}

	void add(String word) {
		if (!query(word)) {
			crp.add(word); // Override add()
			cacheWords.add(word);
		}
;		if (cacheSize < cacheWords.size()) {
			String victimWord = crp.remove(); // Override remove()
			cacheWords.remove(victimWord); // regular Set Remove()
		}
	}
}
