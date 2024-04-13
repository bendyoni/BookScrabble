package test;

import java.util.HashSet;
import java.util.Set;

public class CacheManager {
	static int cacheSize;
	//private int cacheSize;
	private final Set<String> cacheWords;
	private final CacheReplacementPolicy crp;

	public CacheManager(int size, CacheReplacementPolicy crp) {
		CacheManager.cacheSize = size;
		//this.cacheSize = size;
		this.crp = crp;
		this.cacheWords  = new HashSet<>();
	}

	public boolean query (String word) {
		return cacheWords.contains(word);
	}

	public void add(String word) {
		if (!query(word)) {
			crp.add(word); // Override add()
			cacheWords.add(word);
		}
		if (cacheSize < cacheWords.size()) {
			String victimWord = crp.remove(); // Override remove()
			cacheWords.remove(victimWord); // regular Set Remove()
		}
	}

	public int getCacheSize() {
		return cacheSize;
	}

}
