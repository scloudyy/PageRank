# Page Rank

use `scrapy` to collect pages relationship information and build page rank dataset

use `hadoop` and dataset collected by `scrapy` to implement page rank algorithm

# Collect Page Rank Dataset

We use `scrapy` to collect page rank dataset. The related code locates in the `scrapy\` dir

## Usage

1. install scrapy first

```shell
pip install scrapy
```

2. run `scrapy` inside `scrapy\`

```shell
cd scrapy
scrapy crawl pagerank
```
3. change start_urls and allowed_domains (option)

the default `start_urls=['https://github.com/']`, we can change it through:

```shell
scrapy crawl pagerank -a start='https://stackoverflow.com/'
```

we can restrict the collecting range into one domain:

```shell
scrapy crawl pagerank -a start='https://stackoverflow.com/' -a domain='stackoverflow.com'
```

Be careful, `domain` should fit with `start`; If you don't set `start`, it should fit with the default `start_urls`

## Dataset

The data collected by spider will be stored into `keyvalue`, `transition` and `pr0` respectively.

`keyvalue` records the each url and its key, splited by '\t':

```
https://github.com/\t0\n
https://github.com/features\t1\n
https://github.com/business\t2\n
https://github.com/explore\t3\n
...
```

`transtion` records the relationship betwenn pages:

```
0\t0,1,2,3,4,5,6,7,8,9\n
...
```

page of id 0 points to pages of id 0,1,2,3,4,5,6,7,8,9, they are splited by '\t'

`pr0` is the initial page rank value for each page:

```
0\t1\n
1\t1\n
2\t1\n
...
```

Later, we need to put `transition` and `pr0` into `hdfs` and use `hadoop` to calculate page rank.

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
hdfs dfs -mkdir -p pagerank/pagerank/pr0
hdfs dfs -put ../scrapy/pr0 pagerank/pagerank/pr0/
hdfs dfs -mkdir -p pagerank/keyvalue/
hdfs dfs -put ../scrapy/keyvalue pagerank/keyvalue/
hdfs dfs -mkdir -p pagerank/cache
```

3. deal with MySQL

- create MySQL database

``` shell
mysql -uroot -p
create database PageRank;
use PageRank;
create table output(page_id VARCHAR(250), page_url VARCHAR(250), page_rank DOUBLE);
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'password' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```

- download connector and put it into HDFS

```shell
hdfs dfs -mkdir /mysql 
hdfs dfs -put mysql-connector-java-*.jar /mysql/
```

4. run hadoop project

```shell
hadoop jar page-rank.jar Driver pagerank/transition pagerank/pagerank/pr pagerank/keyvalue pagerank/cache/unit 10
```

- `Driver` is the entry class
- `pagerank/transition/` is the dir of transition in hdfs
- `pagerank/pagerank/pr` is the base dir of pr0 in hdfs
- `pagerank/keyvalue` is the dir of keyvalue in hdfs
- `pagerank/unit` is the dir of middle results
- `10` is the times of convergence


## Reference

- [The PageRank Citation Ranking: Bringing Order to the Web, Page, Lawrence and Brin, Sergey and Motwani, Rajeev and Winograd, Terry](http://ilpubs.stanford.edu:8090/422/)
- [CONVERGENCE ANALYSIS OF AN IMPROVED PAGERANK ALGORITHM, Ilse C. F , Ipsen , Steve Kirkland](http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.330.8697)
