# Implement Page Rank Algorithm

We use `hadoop` to implement the page rank algorithm

1. compile and package hadoop project by maven

```shell
cd hadoop
mvn package -DskipTests
```

2. put `transition` and `pr0` produced by spider into `hdfs`

```shell
hdfs dfs -mkdir -p pagerank/transition/
hdfs dfs -put ../scrapy/transition pagerank/transition/
hdfs dfs -mkdir -p pagerank/pagerank/pr
hdfs dfs -put ../scrapy/pr0 pagerank/pagerank/pr
hdfs dfs -mkdir -p pagerank/unit
```

3. run hadoop project

```shell
hadoop jar page-rank.jar Driver pagerank/transition/ pagerank/pagerank/pr pagerank/unit 10
```

- `Driver` is the entry class
- `pagerank/transition/` is the dir of transition in hdfs
- `pagerank/pagerank/pr` is the dir of pr0 in hdfs
- `pagerank/unit` is the dir of middle results
- `10` is the times of convergence

## Reference

- [The PageRank Citation Ranking: Bringing Order to the Web, Page, Lawrence and Brin, Sergey and Motwani, Rajeev and Winograd, Terry](http://ilpubs.stanford.edu:8090/422/)
- [CONVERGENCE ANALYSIS OF AN IMPROVED PAGERANK ALGORITHM, Ilse C. F , Ipsen , Steve Kirkland](http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.330.8697)