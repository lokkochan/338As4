import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
public class DLeftHashTable {
    private int buckets;
    private List<List<Dictionary>> leftTable;
    private List<List<Dictionary>> rightTable;
    private int size;
    private class Dictionary{
        String key;
        int value;
        public Dictionary(String key, int value){
            this.key = key;
            this.value = value;
        }
    }
    public DLeftHashTable(int buckets){
        this.buckets=buckets;
        this.leftTable = new ArrayList<>(buckets);
        this.rightTable = new ArrayList<>(buckets);
        for(int i=0;i<buckets;i++){
            leftTable.add(new ArrayList<>());
            rightTable.add(new ArrayList<>());
        }
        this.size=0;
        
    }
    public void insert(String key, int value){
        int leftHash = hash(key,"left");
        int rightHash = hash(key,"right");
        List<Dictionary>preferredTable;
        List<Dictionary> otherTable;
        if(leftTable.get(leftHash).size()<=rightTable.get(rightHash).size()){
            preferredTable = leftTable.get(leftHash);
            otherTable = rightTable.get(rightHash);
        }else{
            preferredTable = rightTable.get(rightHash);
            otherTable = leftTable.get(leftHash);
        }
        for(Dictionary d:preferredTable){
            if(d.key.equals(key)){
                d.value = value;
                return;
            }
        }
        for(Dictionary d:otherTable){
            if(d.key.equals(key)){
                d.value = value;
                return;
            }
        }
        preferredTable.add(new Dictionary(key,value));
        size++;
        if(size>2*buckets){
            rehash();
        }
        
    }
    public int lookup(String key){
        int leftHash = hash(key,"left");
        int rightHash = hash(key,"right");
        for(Dictionary d:leftTable.get(leftHash)){
            if(d.key.equals(key)){
                return d.value;
            }
        }
        for(Dictionary d:rightTable.get(rightHash)){
            if(d.key.equals(key)){
                return d.value;
            }
        }
        return 0;
    }
    private int hash(String key, String type){
        int hash = 0;
        if(type.equals("left")){
            hash=key.hashCode()%buckets;
    }
    else if(type.equals("right")){
        
        hash = (key.hashCode()/buckets)%buckets;
    }
    return Math.abs(hash);}

    private void rehash(){
        List<List<Dictionary>> newLeftTable = new ArrayList<>(buckets*2);
        List<List<Dictionary>> newRightTable = new ArrayList<>(buckets*2);
        for(int i=0;i<buckets*2;i++){
            newLeftTable.add(new ArrayList<>());
            newRightTable.add(new ArrayList<>());

        }
        for (List<Dictionary>bucket:leftTable){
            for(Dictionary d:bucket){
                int leftHash = hash(d.key,"left");
                newLeftTable.get(leftHash).add(d);
            }

        }
        for (List<Dictionary>bucket:rightTable){
            for(Dictionary d:bucket){
                int righttHash = hash(d.key,"right");
                newRightTable.get(righttHash).add(d);
            }

        }

        leftTable = newLeftTable;
        rightTable = newRightTable;
        buckets = buckets*2;
        }
    public void printLeftTable() {
        for(int i = 0; i < buckets; i++) {
            System.out.print("Bucket "+ i + " => ");
            for(Dictionary d : leftTable.get(i)) {
                System.out.print("("+ d.key + " : " + d.value + ")");
            }
            System.out.println();
        }
    }
    
    public void printRightTable() {
        for(int i = 0; i < buckets; i++) {
            System.out.print("Bucket "+ i + " => ");
            for(Dictionary d : rightTable.get(i)) {
                System.out.print("("+ d.key + " : " + d.value + ")");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        DLeftHashTable table = new DLeftHashTable(4);
        table.insert("apple", 1);
        table.insert("banana", 2);
        table.insert("cherry", 3);
        table.insert("date", 4);
        table.insert("elderberry", 5);
        table.insert("fig", 6);
        table.insert("orange", 7);
        table.insert("pear", 8);
        table.insert("pineapple", 9);
        table.insert("plum", 10);
    
        System.out.println(table.lookup("apple")); // Output: 1
        System.out.println(table.lookup("banana")); // Output: 2
        System.out.println(table.lookup("cherry")); // Output: 3
        System.out.println(table.lookup("date")); // Output: 4
        System.out.println(table.lookup("elderberry")); // Output: 5
        System.out.println(table.lookup("fig")); // Output: 6
        System.out.println(table.lookup("grape")); // Output: null
        table.printLeftTable();
        System.out.println("--------------------");
        table.printRightTable();





    }

}

    
