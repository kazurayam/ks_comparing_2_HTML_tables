package com.kazurayam.ks.keyword

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject

class HTMLTableComparator {

	@Keyword
	List<Record> sortTableBody(TestObject table, Range<Integer> keyRange) {
	}

	@Keyword
	Set<RowKey> rowKeys(TestObject table, Range<Integer> keyRange) {
	}

	@Keyword
	Boolean tablesHaveSameKeys(TestObject table1, TestObject table2, Range<Integer> keyRange) {
	}

	/**
	 * 
	 */
	static class Record {
		private final RowValues rowValues;
		private final Range<Integer> keyRange;
		private final RowKey rowKey;
		Record(List<String> row, Range<Integer> keyRange) {
			this.row = row;
			int width = row.size();
			if (validateRange(row.size(), keyRange)) {
				throw new IllegalArgumentException("keyRange(${keyRange}) must be in the range of 0..<" + row.size());
			}
			this.keyRange = keyRange;
		}
		private boolean validateRange(Integer width, Range<Integer> keyRange) {
			if (keyRange.getFrom() <= keyRange.getTo() &&
			0 <= keyRange.getFrom() && keyRange.getFrom() < width &&
			0 <= keyRange.getTo() && keyRange.getTo() < width) {
				return true
			} else {
				return false
			}
		}
		Range<Integer> keyRange() {
			return this.keyRange;
		}
		RowValues rowValues() {
			return this.rowValues;
		}
		RowKey rowKey() {
			return new RowKey(rowValues, keyRange())
		}
		@Override
		boolean equals(Object obj) {
			if (!(obj instanceof Record)) {
				return false
			}
			Record other = (Record)obj
			return this.rowValues().equals(other.rowValues())
		}
		@Override
		int hashCode() {
			int hash = 7;
			hash = 31 * hash + this.rowValues().hashCode();
			return hash;
		}
		@Override
		String toString() {
			throw new UnsupportedOperationException("TODO");
		}
	}

	/**
	 * 
	 */
	public static class RowKey implements Comparable<RowKey> {
		private final List<String> keyElements = new ArrayList<>()
		private final Range<Integer> keyRange;

		RowKey(List<String> row, Range keyRange) {
			Objects.requireNonNull(row)
			Objects.requireNonNull(keyRange)
			validateParams(row, keyRange)
			this.keyElements.addAll(getKeyElements(row, keyRange))
			this.keyRange = keyRange
		}

		private void validateParams(List<String> row, Range keyRange) {
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
			StringBuilder sb = new StringBuilder()
			sb.append("[");
			int count = 0;
			for (String s in this.keyElements()) {
				if (count > 0) {
					sb.append(",")
				}
				sb.append(s)
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
	public static class RowValues {
		private final List<String> values;

		RowValues(List<String> values) {
			Objects.requireNonNull(values)
			this.values = values;
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
			StringBuilder sb = new StringBuilder()
			sb.append("[")
			int count = 0;
			for (String s in this.values()) {
				if (count > 0) {
					sb.append(",")
				}
				sb.append(s)
				count += 1
			}
			sb.append("]")
			return sb.toString()
		}
	}
}