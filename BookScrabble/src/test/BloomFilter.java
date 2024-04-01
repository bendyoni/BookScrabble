package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BloomFilter {
    private BitSet bits;
    private String[] hashAlgs;

    BloomFilter(int size, String...algs){
        bits = new BitSet(size);
        hashAlgs = algs;
    }    

    private int getHashCode(String word, String algo) {
        try{
        MessageDigest md = MessageDigest.getInstance(algo);
        byte[] bytes = md.digest(word.getBytes());
        BigInteger bi = new BigInteger(1,bytes);
        return bi.intValue();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void add(String word) {
       for (String algo : hashAlgs) {
            int haschCode = getHashCode(word, algo);
            int index = Math.abs(haschCode % bits.size());
            bits.set(index);
       }
    }

    public boolean contains (String word) {
        for (String algo : hashAlgs) {
            int hashCode = getHashCode(word, algo);
            if (bits.get(Math.abs(hashCode % bits.size())));
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<bits.size(); i++)
            if (bits.get(i))
                sb.append('1');
            else
            sb.append('0');
        
            return sb.toString();
    }

} 
