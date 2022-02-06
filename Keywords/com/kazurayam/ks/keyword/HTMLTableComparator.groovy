package com.kazurayam.ks.keyword

import com.google.gson.Gson
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject

import oracle.jdbc.replay.driver.TxnReplayableOthers


/**
 * in for a penny, in for a pound
 * 
 * @author kazuarayam
 */
class HTMLTableComparator {



	//-----------------------------------------------------------------

	static class Records {
		private final List<Record> records
		Records() {
			this.records = new ArrayList<>()
		}
		void add(Record record) {
			this.records.add(record)
		}
		void addAll(List<Record> list) {
			this.records.addAll(list)
		}
		@Override
		String toString() {
			return toJson()
		}
		String toJson() {
			StringBuilder sb = new StringBuilder()
			sb.append("[\n")
			int count = 0
			for (Record r in records) {
				if (count > 0) {
					sb.append(",\n")
				}
				sb.append(r.toJson())
				count += 1
			}
			sb.append("]\n")
			return sb.toString()
		}
	}

	/**
	 * 
	 */
	static class Record implements Comparable<Record> {
		private final RowValues rowValues
		private final Range<Integer> keyRange
		private final RowKey rowKey

		Record(List<String> row, Range<Integer> keyRange) {
			Objects.requireNonNull(row)
			Objects.requireNonNull(keyRange)
			validateRange(row, keyRange)
			this.rowValues = new RowValues(row)
			this.keyRange = keyRange
			this.rowKey = new RowKey(row, keyRange)
		}

		Record(Record source) {
			this(source.rowValues().values(), source.keyRange())
		}

		private void validateRange(List<String> row, Range<Integer> keyRange) {
			assert keyRange.getFrom() <= keyRange.getTo()
			assert 0 <= keyRange.getFrom()
			assert keyRange.getFrom() < row.size()
			assert 0 < keyRange.getTo()
			assert keyRange.getTo() < row.size()
		}

		RowValues rowValues() {
			return this.rowValues
		}

		Range<Integer> keyRange() {
			return this.keyRange
		}

		RowKey rowKey() {
			return this.rowKey
		}

		boolean rowKeyEquals(Record other) {
			return this.rowKey().equals(other.rowKey())
		}

		@Override
		boolean equals(Object obj) {
			if (!(obj instanceof Record)) {
				return false
			}
			Record other = (Record)obj
			return this.rowValues().equals(other.rowValues()) &&
					this.rowKey().equals(other.rowKey())
		}

		@Override
		int hashCode() {
			int hash = 7;
			hash = 31 * hash + this.rowValues().hashCode();
			return hash;
		}

		@Override
		String toString() {
			return toJson()
		}

		String toJson() {
			Gson gson = new Gson()
			StringBuilder sb = new StringBuilder()
			sb.append("{")
			sb.append(gson.toJson("rowKey") + ":" + gson.toJson(this.rowKey().toString()))
			sb.append(",")
			sb.append(gson.toJson("rowValues") + ":" + gson.toJson(this.rowValues().toString()))
			sb.append(",")
			sb.append(gson.toJson("keyRange") + ":" + gson.toJson(this.keyRange().toString()))
			sb.append("}")
			return sb.toString()
		}

		@Override
		int compareTo(Record other) {
			int keyComparison = this.rowKey() <=> other.rowKey()
			if (keyComparison == 0) {
				int valuesComparison = this.rowValues() <=> other.rowValues()
				return valuesComparison
			} else {
				return keyComparison
			}
		}
	}

	/**
	 * 
	 */
	public static class RowKey implements Comparable<RowKey> {
		private final List<String> keyElements = new ArrayList<>()
		private final Range<Integer> keyRange;

		RowKey(List<String> row, Range<Integer> keyRange) {
			Objects.requireNonNull(row)
			Objects.requireNonNull(keyRange)
			validateParams(row, keyRange)
			this.keyElements.addAll(getKeyElements(row, keyRange))
			this.keyRange = keyRange
		}

		RowKey(RowKey source) {
			this(source.keyElements(), source.keyRange())
		}

		private void validateParams(List<String> row, Range<Integer> keyRange) {
			assert 0 <= row.size()
			assert 0 <= keyRange.getFrom()
			assert keyRange.getFrom() <= keyRange.getTo()
			assert keyRange.getTo() < row.size()
		}

		private List<String> getKeyElements(List<String> row, Range<Integer> keyRange) {
			List<String> keyElements = new ArrayList<>();
			for (int i = keyRange.getFrom(); i <= keyRange.getTo(); i++) {
				keyElements.add(row.get(i))
			}
			return keyElements
		}

		List<String> keyElements() {
			return this.keyElements
		}

		Range keyRange() {
			return this.keyRange
		}

		@Override
		boolean equals(Object obj) {
			if (!(obj instanceof RowKey)) {
				return false
			}
			RowKey other = (RowKey)obj
			if (this.keyElements().size() != other.keyElements().size()) {
				return false
			}
			boolean equality = true
			for (int i = 0; i < this.keyElements().size(); i++) {
				if (this.keyElements().get(i) !=  other.keyElements().get(i)) {
					equality = false;
					break
				}
			}
			return equality
		}

		@Override
		int hashCode() {
			int hash = 7;
			for (int i = 0; i < this.keyElements().size(); i++) {
				hash = 31 * hash + this.keyElements().get(i).hashCode();
			}
			return hash;
		}

		@Override
		String toString() {
			return toJson()
		}

		String toJson() {
			Gson gson = new Gson()
			StringBuilder sb = new StringBuilder()
			sb.append("[");
			int count = 0;
			for (String s in this.keyElements()) {
				if (count > 0) {
					sb.append(",")
				}
				sb.append(gson.toJson(s))
				count += 1
			}
			sb.append("]")
			return sb.toString()
		}

		@Override
		int compareTo(RowKey other) {
			List<String> tke = this.keyElements()
			List<String> oke = other.keyElements()
			if (tke.size() < oke.size()) {
				return -1
			} else if (tke.size() == oke.size()) {
				for (int i = 0; i < tke.size(); i++) {
					int comparison = tke.get(i).compareTo(oke.get(i))
					if (comparison != 0) {
						return comparison
					}
				}
				return 0
			} else {
				return +1
			}
		}
	}


	/**
	 * 
	 */
	public static class RowValues implements Comparable<RowValues> {
		private final List<String> values;

		RowValues(List<String> values) {
			Objects.requireNonNull(values)
			this.values = values;
		}

		RowValues(RowValues source) {
			this(copyStringList(source.values()))
		}

		private static List<String> copyStringList(List<String> source) {
			List<String> target = new ArrayList<>()
			for (String s in source) {
				target.add(s)
			}
			return target
		}

		List<String> values() {
			return this.values
		}

		@Override
		boolean equals(Object obj) {
			if (!(obj instanceof RowValues)) {
				return false
			}
			RowValues other = (RowValues)obj;
			boolean equality = true;
			if (this.values().size() != other.values().size()) {
				return false
			}
			for (int i = 0; i < this.values().size(); i++) {
				if (this.values().get(i) != other.values().get(i)) {
					equality = false
					break
				}
			}
			return equality;
		}

		@Override
		int hashCode() {
			int hash = 7;
			for (int i = 0; i < this.values().size(); i++) {
				hash = 31 * hash + this.values().get(i).hashCode();
			}
			return hash;
		}

		@Override
		String toString() {
			return toJson()
		}

		String toJson() {
			Gson gson = new Gson()
			StringBuilder sb = new StringBuilder()
			sb.append("[")
			int count = 0;
			for (String s in this.values()) {
				if (count > 0) {
					sb.append(",")
				}
				sb.append(gson.toJson(s))
				count += 1
			}
			sb.append("]")
			return sb.toString()
		}

		@Override
		int compareTo(RowValues other) {
			if (this.values().size() < other.values().size()) {
				return -1
			} else if (this.values().size() == other.values().size()) {
				for (int i = 0; i < this.values.size(); i++) {
					int comparison = this.values().get(i) <=> other.values().get(i)
					if (comparison != 0) {
						return comparison
					}
				}
				return 0
			} else {
				return 1
			}
		}
	}
}