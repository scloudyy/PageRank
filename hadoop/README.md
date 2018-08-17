# Implement Page Rank Algorithm

We use `hadoop` to implement the page rank algorithm

1. compile hadoop project code

```shell
cd hadoop
mvn package -DskipTests
```

2. put `transition` and `pr0` produced by spider into `hdfs`

```shell
hdfs dfs -mkdir -p pagerank/input
hdfs dfs -put ../scrapy/transition pagerank/input/
hdfs dfs -put ../scrapy/pr0 pagerank/input/
```

3. run hadoop project
