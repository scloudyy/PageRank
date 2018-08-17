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

`keyvalue` records the each url and its key:

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