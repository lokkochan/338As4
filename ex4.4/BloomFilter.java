import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class BloomFilter {
    private final int bitsetsize;
    private final int refreshcount;
    private final int numHashFunctions;
    private final BitSet bitset;
    private int insertionCount;
    private final Random random;
    private final MessageDigest[] hashFunctions;

    public BloomFilter(int bitsetsize,int refreshcount){
        this.bitsetsize =bitsetsize;
        this.refreshcount =refreshcount;
        this.numHashFunctions = 3;
        this.bitset =new BitSet(bitsetsize);
        this.insertionCount =0;
        this.random =new Random();
        this.hashFunctions = new MessageDigest[numHashFunctions];
        try{
            hashFunctions[0] =MessageDigest.getInstance("MD5");
            hashFunctions[1] =MessageDigest.getInstance("SHA-1");
            hashFunctions[2] =MessageDigest.getInstance("SHA-256");
    }
    catch(NoSuchAlgorithmException e){
        e.printStackTrace();
    }}
    public void record(String s){
        for(int i=0;i<numHashFunctions;i++){
            int hash=getHash(s,i);
            bitset.set(hash%bitsetsize,true);
        }
        insertionCount++;
        if(insertionCount % refreshcount==0){
            bitset.clear();
            System.out.println("Cleared");
            this.insertionCount=0;
        }
    }
    public boolean lookup(String s){
        for(int i=0;i<numHashFunctions;i++){
            int hash=getHash(s,i);
            if(!bitset.get(hash%bitsetsize)){
                return false;
            }
        }
        return true;
    }
    private int getHash(String s,int i){
        byte[] bytes =s.getBytes();
        bytes[0] ^=i;
        byte[] hashBytes=hashFunctions[i].digest(bytes);
        int hash =Math.abs(Arrays.hashCode(hashBytes));
        return Math.abs(hash);
    }
    public int getInsertionCount(){
        return insertionCount;
    }
    public static void main(String[] args){
        BloomFilter bloomFilterSample = new BloomFilter(1000, 10);
    bloomFilterSample.record("apple");
    bloomFilterSample.record("banana");
    bloomFilterSample.record("cherry");

    System.out.println(bloomFilterSample.lookup("apple")); // true
    System.out.println(bloomFilterSample.lookup("banana")); // true
    System.out.println(bloomFilterSample.lookup("cherry")); // true
    System.out.println(bloomFilterSample.lookup("orange")); // false

    bloomFilterSample.record("orange");
    bloomFilterSample.record("grape");
    bloomFilterSample.record("kiwi");

    System.out.println(bloomFilterSample.lookup("orange")); // true
    System.out.println(bloomFilterSample.lookup("grape")); // true
    System.out.println(bloomFilterSample.lookup("kiwi")); // true

    // check that the Bloom filter resets after 10 insertions
    for (int i = 0; i < 4; i++) {
      bloomFilterSample.record("strawberry" + i);
    }
    System.out.println(bloomFilterSample.getInsertionCount()); // 0
    System.out.println(bloomFilterSample.lookup("apple")); // false (reset)
    System.out.println(bloomFilterSample.lookup("banana")); // false (reset)
    System.out.println(bloomFilterSample.lookup("cherry")); // false (reset)
    System.out.println(bloomFilterSample.lookup("orange")); // false (reset)
    System.out.println(bloomFilterSample.lookup("grape")); // false (reset)
    System.out.println(bloomFilterSample.lookup("kiwi")); // false (reset)
    System.out.println(bloomFilterSample.lookup("strawberry5")); // true (new insertion)

    BloomFilter bloomFilterSample2 = new BloomFilter(1000, 10);
    bloomFilterSample2.record("Apple");
    bloomFilterSample2.record("Samsung");
    bloomFilterSample2.record("Huawei");
    System.out.println(bloomFilterSample2.lookup("Apple")); // true
    System.out.println(bloomFilterSample2.lookup("Samsung")); // true
    System.out.println(bloomFilterSample2.lookup("Oppo")); // true
      
    }}