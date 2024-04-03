package test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

class BloomFilter {
    private BitSet bitVector;
    private List<MessageDigest> digesters;
 
    BloomFilter(int size, String... algs) {
        bitVector = new BitSet(size);
        digesters = new ArrayList<>();
        for (String alg : algs) {
            try {
                digesters.add(MessageDigest.getInstance(alg));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
 
    void add(String word) {
        byte[] bytes = word.getBytes(StandardCharsets.UTF_8);
        for (MessageDigest digester : digesters) {
            byte[] digest = digester.digest(bytes);
            BigInteger bigInt = new BigInteger(1, digest);
            int index = Math.abs(bigInt.intValue()) % bitVector.size();
            bitVector.set(index);
        }
    }
 
    boolean contains(String word) {
        byte[] bytes = word.getBytes(StandardCharsets.UTF_8);
        for (MessageDigest digester : digesters) {
            byte[] digest = digester.digest(bytes);
            BigInteger bigInt = new BigInteger(1, digest);
            int index = Math.abs(bigInt.intValue()) % bitVector.size();
            if (!bitVector.get(index)) {
                return false;
            }
        }
        return true;
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitVector.length(); i++) {
            sb.append(bitVector.get(i) ? '1' : '0');
        }
        return sb.toString();
    }
 }
