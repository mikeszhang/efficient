# efficient :+1:
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

common library for java developer

you can found all sample of use in the unit test code.

## proposition logic
there are full support for proposition logic representation, compute and transform.

```java
Proposition p=PropositionUtils.parseProposition("~a && (b || ~c) -> c");    // define proposition logic string.
Map<AtomicProposition,Boolean> map=new HashMap<AtomicProposition, Boolean>();
map.put(new AtomicProposition("a"),false);
map.put(new AtomicProposition("b"),false);
map.put(new AtomicProposition("c"),false);
Assert.assertFalse(p.value(map, true));	// assign boolean values and compute result.
Assert.assertTrue(p.toCNF().isCNF());	// transform into conjunction normal form.
Assert.assertTrue(p.toDNF().isDNF());	// transform into disjunction normal form.
Assert.assertFalse(p.isCNF());
```

## utilities for common data structure with function programming style.
### version representation and operation
version class support following version string:

* 5.6.2 (at most three digital, 5 is major, 6 is middle, 2 is minor)
* 5.6 (5 is major, 6 is middle)
* 5 (5 is major)

example is as following:

```java
String v=Version.covertString(40201);   // v is 4.2.1, arg is int and low two position for minor and middle two position for middle.
Assert.assertEquals("4.2.1",v);
int vint=Version.convertInt("0.4.3");   // vint is 403
Assert.assertEquals(403,vint);
v=Version.covertString(vint);
Assert.assertEquals("0.4.3",v);
// create Version instance with version string. exception is never throw out, instead, 0.0.0 is construct by default.
Version version=new Version("4.9");
Assert.assertTrue(version.compareTo(null)>0);
Assert.assertEquals(version,version.clone());
Assert.assertEquals(50566,Version.convertInt("5.5.566"));
```

### number and string parse
in java, we can parse number from string using such as Integer.parseInt("12"). However, runtime exception for parsing will make us crazy.

ParseUtils will shoot the pain:

```java
int a=ParseUtils.parseInt("sda",0); // when parsing failed, default value will be returned.
Assert.assertEquals(0,a);
// batch parse int and no exception out, failed parsing will be ignored.
List<Integer> intList=ParseUtils.batchParseInt(Arrays.asList("s","11","21"),false);
Assert.assertArrayEquals(Arrays.asList(11,21).toArray(),intList.toArray());
// parsing geo position with separator default values.
Pair<Double,Double> latlng=ParseUtils.parsePosition("123.221,233.32", ",", 0, 0);
Assert.assertEquals(Pair.of(123.221, 233.32), latlng);
```

### CollectionPlus
You may have used the apache collection utils. It is quite effective but still need improve.

CollectionPlus provides some useful methods apache doesn't have. Use it with apache, your work will be more efficient.

```java
Set<Integer> set= CollectionPlus.asSet(23,45);  // just like Arrays.asList(23,45)
Map<String,Integer> moneyMap=CollectionPlus.asMap("mikes",100,"jack",400,"woods",1000); // create map in one line.
// a functional style loop without 'for', count the total money in map but ensure not more than 600.
Integer total=CollectionPlus.foreach(moneyMap, 0, new KVProcessor<String, Integer, Integer>() {
       @Override
       public Integer process(String key, Integer value, Integer state) {
           Integer total=value+state;
           if (total>600){
               CollectionPlus.quitForeach();    // stop foreach function, just like break in conventional for loop.
           }
           return total;
       }
});
```

### BucketHashMap and MultipleMap
The two new data structures are quite simple but useful. BucketHashMap is key with a list of values. The list of values is ordered.

MultipleMap is a map with two keys in an entry: the primary key and secondary key. You should provide both keys to get the specific value.
Or you will get another key when only provide a primary key.

```java
// create bucket hash map and put some key-values
BucketHashMap<String,Integer> bucketHashMap=new BucketHashMap<String, Integer>();
bucketHashMap.append("mikes",13);
bucketHashMap.append("mikes",14);
Assert.assertEquals(2,bucketHashMap.sizeOf("mikes"));   // the key of "mikes" has two value.
// create multiple map and put keys-value
MultipleMap<String,Integer,String> multipleMap=new MultipleMap<String, Integer, String>();
multipleMap.put("mikes",1,"tank");
Assert.assertTrue(multipleMap.containsKey("mikes",1));  // surely true.
```