
//Just holds the key and value in an easily accessible object
public class Entry<K,V> {
	K key; 
	V value;
	public Entry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public String toString() {
		return "(" + key + "," + value + ")";
	}
	
	
	
	
	
}
