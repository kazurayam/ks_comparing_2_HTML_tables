import com.kms.katalon.core.util.KeywordUtil

assert data1 != null
assert data2 != null

Map<String, Record> m1 = new TreeMap<>();
for (List<String> row in data1) {
	Record rc = new Record(row[0], row[1], row[2]);
	m1.put(rc.key(), rc);
}
Set<String> keySet1 = m1.keySet()
//println "keySet1: " + keySet1

Map<String, Record> m2 = new TreeMap<>();
for (List<String> row in data2) {
	Record rc = new Record(row[0], row[1]);
	m2.put(rc.key(), rc);
}
Set<String> keySet2 = m2.keySet()
//println "keySet2: " + keySet2

// print the datasets
println "-------- data 1 --------"
for (String key in keySet1) {
	println m1.get(key)
}
println ""
println "-------- data 2 --------"
for (String key in keySet2) {
	println m2.get(key)
}
println ""

// comparing the key set of the input1 and the input2
Set<String> s1 = new TreeSet<>(keySet1);
Set<String> s2 = new TreeSet<>(keySet2);
s1.removeAll(s2)
if (s1.size() > 0) {
	for (String k1 in s1) {
		System.err.println "\"${k1}\" is contained in the data1, but is missing in the data2"
		
	}
	KeywordUtil.markFailed("data1 > data2")
}

println ""

s1 = new TreeSet<>(keySet1);
s2 = new TreeSet<>(keySet2);
s2.removeAll(s1)
if (s2.size() > 0) {
	for (String k2 in s2) {
		System.err.println "\"${k2}\" is contained in the data2, but is missing in the data1"
	}
	KeywordUtil.markFailed("data1 < data2")
}


/**
 *
 */
class Record implements Comparable<Record> {
	private final String CUSTOMER;
	private final String CHANNEL;
	private final String BRANCHES;
	Record(String customer, String channel) {
		this(customer, channel, '');
	}
	Record(String customer, String channel, String branches) {
		this.CUSTOMER = customer.trim();
		this.CHANNEL = channel.trim();
		this.BRANCHES = branches.trim();
	}
	String CUSTOMER() {
		return this.CUSTOMER;
	}
	String CHANNEL() {
		return this.CHANNEL;
	}
	String BRANCHES() {
		return this.BRANCHES;
	}
	String key() {
		return this.CUSTOMER() + "|" + this.CHANNEL()
	}

	@Override
	boolean equals(Object obj) {
		if (! obj instanceof Record) {
			return false;
		}
		Record other = (Record)obj;
		return this.CUSTOMER() == other.CUSTOMER() &&
				this.CHANNEL() == other.CHANNEL() &&
				this.BRANCHES() == other.BRANCHES()
	}

	@Override
	int hashCode() {
		int hash = 7;
		hash = 31 * hash + this.CUSTOMER().hashCode();
		hash = 31 * hash + this.CHANNEL().hashCode();
		hash = 31 * hash + this.BRANCHES().hashCode();
		return hash;
	}

	@Override
	String toString() {
		return "[" + this.CUSTOMER() + "|" + this.CHANNEL() + "|" + this.BRANCHES() + "]";
	}

	@Override
	int compareTo(Record other) {
		int result = this.CUSTOMER() <=> other.CUSTOMER();
		if (result != 0) {
			return result;
		} else {
			result = this.CHANNEL() <=> other.CHANNEL();
			if (result != 0) {
				return result;
			} else {
				result = this.BRANCHES() <=> other.BRANCHES();
				return result;
			}
		}
	}
}