# Collect Page Rank Dataset

We use `scrapy` to collect page rank dataset. The related code locates in the `scrapy\` dir

## usage

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

## dataset

The data collected by spider will be stored into `keyvalue`, `transition` and `pr0` respectively.

`keyvalue` records the each url and its key:

```
https://github.com/	0
https://github.com/features	1
https://github.com/business	2
https://github.com/explore	3
...
```

`transtion` records the relationship betwenn pages:

```
0:0,1,2,3,4,5,6,7,8,9
...
```

which means page of id 0 points to pages of id 0,1,2,3,4,5,6,7,8,9

`pr0` is the initial page rank value for each page:

```
0:1
1:1
2:1
...
```

Later, we need to put `transition` and `pr0` into `hdfs` and use `hadoop` to calculate page rank.

# Implement Page Rank Algorithm

We use `hadoop` to implement the page rank algorithm

1. compile hadoop project code

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
```

3. run hadoop project
