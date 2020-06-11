import java.util.Arrays;

public class HashTable<K,V> {
	
	private Entry<K,V>[] table;
	private int size;
	private int numDeleted;
	private static final double MAX_CAPACITY = 0.75;
	private static final Entry DELETED = new Entry(null, null); //rather than setting indices to "null", we will set to DELETED
		
	public HashTable() {
		this.table = new Entry[11]; //Why 11? Prime numbers reduce the number of collisions
		this.size = 0;
		this.numDeleted = 0;
		
		
	}
	
	
	public V put(K key, V val) {
		int index = this.find(key); //use find to find an empty spot to put the new Entry
		if(table[index] == null) {
			size++; // only increment size if that space was null
		}
		table[index] = new Entry(key, val);
		
		if( (size+numDeleted*1.0)/table.length > MAX_CAPACITY) {
			rehash();
		}
		
		return val;
	}
		
	public V get(K key) {
		int index = find(key);
		if(table[index]==null) {
			return null;
		}
		return table[index].value;
	}
	
	public boolean contains(K key) {
		int index = find(key);
		return table[index] != null;
	}
	
	public V delete(K key) {
		int index = find(key);
		V retval = null;
		if(table[index] != null) {
			retval = table[index].value;
			table[index] = DELETED; //this solves the problem of 
			size--;
			numDeleted++;
		}
		return retval;
	}
	
	
	//find is a helper method that returns the index that we want to store or find key at, or the first empty index we can find
	//Two objects that are equal will always have the same hashcode. BUT, two different objects can possibly have the same hashcode
	private int find(K key) {
		int index = key.hashCode() % table.length;
		//we'll need to use "open addressing" to take care of collisions
		 
		//else, we have a collision
		while(table[index] != null && !key.equals(table[index].key)) {
				index = (index + 1) % table.length; //we do this so we can wrap around the array if we reach the last index and still haven't found available space 
		}
		
		return index;
		
		
	}
	
	//this will resize the array when it gets too full. Only resize when we add
	private void rehash() {
		Entry<K,V>[] oldTable = table;
		table = new Entry[table.length * 2 + 1];
		size = 0;
		
		for(int i = 0; i < oldTable.length; i++) {
			if(oldTable[i] == null || oldTable[i] == DELETED) {
				continue;
			}else {
				K key = oldTable[i].key;
				V val = oldTable[i].value;
				
				this.put(key, val);
			}
		}
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		for(Entry entry : table) {
			if(entry==null || entry == DELETED) {
				continue;
			}
			output.append(entry + " ");
		}
		return output.toString();
	}
	
	public static void main(String[] args) {
		HashTable<Integer, String> ht = new HashTable<Integer, String>();
		for(int i = 0; i < 5; i++){
			ht.put(i, "" + i);
		}
		System.out.println(ht);
	}
	
	
	
}
