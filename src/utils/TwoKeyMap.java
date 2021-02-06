package utils;

import java.util.*;

public class TwoKeyMap<K1, K2, V> {
	protected final Set<Entry<K1, K2, V>> entrySet;

	public TwoKeyMap() {
		this.entrySet = new HashSet<>();
	}
	public TwoKeyMap(TwoKeyMap<K1, K2, V> values) {
		this();
		for (Entry<K1, K2, V> entry : values.entrySet()) {
			this.entrySet.add(new Entry<>(entry.getKey1(), entry.getKey2(), entry.getValue()));
		}
	}

	public int size() {
		return this.entrySet.size();
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	public boolean containsKey1(Object key) {
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.key1Match(key)) {
				return true;
			}
		}
		return false;
	}
	public boolean containsKey2(Object key) {
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.key2Match(key)) {
				return true;
			}
		}
		return false;
	}
	public boolean containsKeys(Object key1, Object key2) {
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.keyMatch(key1, key2)) {
				return true;
			}
		}
		return false;
	}
	public boolean containsValue(Object value) {
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.valueMatch(value)) {
				return true;
			}
		}
		return false;
	}
	public boolean containsEntry(Object key1, Object key2, Object value) {
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.key1Match(key1) && entry.key2Match(key2) && entry.valueMatch(value)) {
				return true;
			}
		}
		return false;
	}

	public Map<K2, V> getFromKey1(Object key) {
		Map<K2, V> map = new HashMap<>();
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.key1Match(key)) {
				map.put(entry.getKey2(), entry.getValue());
			}
		}
		return map;
	}
	public Map<K1, V> getFromKey2(Object key) {
		Map<K1, V> map = new HashMap<>();
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.key2Match(key)) {
				map.put(entry.getKey1(), entry.getValue());
			}
		}
		return map;
	}
	public V get(Object key1, Object key2) {
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.keyMatch(key1, key2)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public Map<K2, V> putOnKey1(K1 key, Map<K2, V> values) {
		Map<K2, V> valuesClone = new HashMap<>(values);
		Map<K2, V> oldValues = new HashMap<>();
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.key1Match(key) && valuesClone.containsKey(entry.getKey2())) {
				V oldValue = entry.getValue();
				entry.setValue(valuesClone.get(entry.getKey2()));
				oldValues.put(entry.getKey2(), oldValue);
				valuesClone.remove(entry.getKey2());
			}
		}
		for (Map.Entry<K2, V> entry : valuesClone.entrySet()) {
			this.entrySet.add(new Entry<>(key, entry.getKey(), entry.getValue()));
		}
		return oldValues;
	}
	public Map<K1, V> putOnKey2(K2 key, Map<K1, V> values) {
		Map<K1, V> valuesClone = new HashMap<>(values);
		Map<K1, V> oldValues = new HashMap<>();
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.key2Match(key) && valuesClone.containsKey(entry.getKey1())) {
				V oldValue = entry.getValue();
				entry.setValue(valuesClone.get(entry.getKey1()));
				oldValues.put(entry.getKey1(), oldValue);
				valuesClone.remove(entry.getKey1());
			}
		}
		for (Map.Entry<K1, V> entry : valuesClone.entrySet()) {
			this.entrySet.add(new Entry<>(entry.getKey(), key, entry.getValue()));
		}
		return oldValues;
	}
	public V put(K1 key1, K2 key2, V value) {
		for (Entry<K1, K2, V> entry : this.entrySet) {
			if (entry.keyMatch(key1, key2)) {
				V oldValue = entry.getValue();
				entry.setValue(value);
				return oldValue;
			}
		}
		this.entrySet.add(new Entry<>(key1, key2, value));
		return null;
	}
	public void putAll(TwoKeyMap<K1, K2, V> values) {
		TwoKeyMap<K1, K2, V> valuesClone = new TwoKeyMap<>(values);
		for (Entry<K1, K2, V> entry : this.entrySet()) {
			if (valuesClone.containsKeys(entry.getKey1(), entry.getKey2())) {
				entry.setValue(valuesClone.get(entry.getKey1(), entry.getKey2()));
				valuesClone.remove(entry.getKey1(), entry.getKey2());
			}
		}
		for (Entry<K1, K2, V> entry : valuesClone.entrySet()) {
			this.entrySet.add(new Entry<>(entry.getKey1(), entry.getKey2(), entry.getValue()));
		}
	}

	public Map<K2, V> removeOnKey1(K1 key) {
		Map<K2, V> oldValues = new HashMap<>();
		Iterator<Entry<K1, K2, V>> iterator = this.entrySet.iterator();
		for (Entry<K1, K2, V> entry = iterator.next(); iterator.hasNext(); entry = iterator.next()) {
			if (entry.key1Match(key)) {
				oldValues.put(entry.getKey2(), entry.getValue());
				this.entrySet.remove(entry);
			}
		}
		return oldValues;
	}
	public Map<K1, V> removeOnKey2(Object key) {
		Map<K1, V> oldValues = new HashMap<>();
		Iterator<Entry<K1, K2, V>> iterator = this.entrySet.iterator();
		for (Entry<K1, K2, V> entry = iterator.next(); iterator.hasNext(); entry = iterator.next()) {
			if (entry.key2Match(key)) {
				oldValues.put(entry.getKey1(), entry.getValue());
				this.entrySet.remove(entry);
			}
		}
		return oldValues;
	}
	public V remove(Object key1, Object key2) {
		Iterator<Entry<K1, K2, V>> iterator = this.entrySet.iterator();
		for (Entry<K1, K2, V> entry = iterator.next(); iterator.hasNext(); entry = iterator.next()) {
			if (entry.keyMatch(key1, key2)) {
				V oldValue = entry.getValue();
				this.entrySet.remove(entry);
				return oldValue;
			}
		}
		return null;
	}

	public void clear() {
		this.entrySet.clear();
	}

	public Set<K1> key1Set() {
		Set<K1> set = new HashSet<>();
		for (Entry<K1, K2, V> entry : this.entrySet) {
			set.add(entry.getKey1());
		}
		return set;
	}
	public Set<K2> key2Set() {
		Set<K2> set = new HashSet<>();
		for (Entry<K1, K2, V> entry : this.entrySet) {
			set.add(entry.getKey2());
		}
		return set;
	}
	public Collection<V> values() {
		Collection<V> vals = new ArrayList<>();
		for (Entry<K1, K2, V> entry : this.entrySet) {
			vals.add(entry.getValue());
		}
		return vals;
	}

	public Set<Entry<K1, K2, V>> entrySet() {
		return new HashSet<>(this.entrySet);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o instanceof TwoKeyMap) {
			TwoKeyMap m = (TwoKeyMap) o;
			if (m.size() != this.size()) return false;
			if (m.size() == 0) return true;
			Iterator<Entry> iterator = m.entrySet().iterator();
			Entry e = iterator.next();
			for (; iterator.hasNext(); e = iterator.next()) {
				if (!this.containsEntry(e.getKey1(), e.getKey2(), e.getValue())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	protected class Entry<K1, K2, V> {
		private final K1 key1;
		private final K2 key2;
		private V value;

		protected Entry(K1 key1, K2 key2, V value) {
			this.key1 = key1;
			this.key2 = key2;
			this.value = value;
		}
		protected Entry(K1 key1, K2 key2) {
			this(key1, key2, null);
		}

		public K1 getKey1() {
			return this.key1;
		}

		public K2 getKey2() {
			return this.key2;
		}

		public V getValue() {
			return this.value;
		}

		void setValue(V value) {
			this.value = value;
		}

		public boolean key1Match(Object key) {
			return (key == null && this.getKey1() == null) || (key != null && key.equals(this.getKey1()));
		}
		public boolean key2Match(Object key) {
			return (key == null && this.getKey2() == null) || (key != null && key.equals(this.getKey2()));
		}
		public boolean keyMatch(Object key1, Object key2) {
			return this.key1Match(key1) && this.key2Match(key2);
		}
		public boolean valueMatch(Object value) {
			return (value == null && this.getValue() == null) || (value != null && value.equals(this.getValue()));
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Entry) {
				Entry e = (Entry) o;
				return this.key1Match(e.getKey1()) && this.key2Match(e.getKey2()) && this.valueMatch(e.getValue());
			}
			return false;
		}
	}
}
