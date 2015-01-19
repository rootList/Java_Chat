package im.javachat.entity;

/**
 * 自定义键值对实体，实现在TreeSet中自动排序
 * */
public class MyEntry<K,V> implements Comparable<MyEntry<K,V>>{

	private K key;//键
	private V value;//值
	
	/**
	 * 对字符串进行比较
	 * */
	@Override
	public int compareTo(MyEntry<K, V> o) {
		Object kek = key;
		Object keo = o.getKey();
		if(kek instanceof String && keo instanceof String){
			String kek1 = (String)kek;
			String keo1 = (String) keo;
			if(kek1.compareTo(keo1)>0){
				return 1;
			}else if(kek1.compareTo(keo1)<0) {
				return -1;
			}
			
		}
		return 0;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	
}
