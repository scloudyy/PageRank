# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html


from scrapy.exporters import JsonLinesItemExporter


class MatrixPipeline(object):
    def __init__(self):
        self.seen = {}
        self.id = 0

    def open_spider(self, spider):
        self.transition = open('transition', 'wt')
        self.pr0 = open('pr0', 'wt')
        self.keyvalue = open('keyvalue', 'wt')

    def close_spider(self, spider):
        self.transition.close()
        self.pr0.close()
        self.keyvalue.close()

    def process_item(self, item, spider):
        origin = item.get('origin')[0]
        if origin in self.seen:
            origin_id = self.seen[origin]
        else:
            origin_id = self.id
            self.seen[origin] = origin_id
            self.id = self.id + 1
            self.pr0.write(str(origin_id) + '\t' + '1\n')
            self.keyvalue.write(str(origin_id) + '\t' + origin + '\n')

        trans_str = str(origin_id) + '\t'

        tos = item.get('to')
        if tos is not None:
            for to in tos:
                if to in self.seen:
                    to_id = self.seen[to]
                else:
                    to_id = self.id
                    self.seen[to] = to_id
                    self.id = self.id + 1
                    self.pr0.write(str(to_id) + '\t' + '1\n')
                    self.keyvalue.write(str(to_id) + '\t' + to + '\n')
                trans_str += str(to_id) + ','
            trans_str = trans_str[:-1]
        self.transition.write(trans_str + '\n')
        return item


class JsonLinePipeline(object):
    def __init__(self):
        self.exporters = {}
        self.seen = set()

    def open_spider(self, spider):
        f = open('%s.json' % spider.name, 'wb')
        exporter = JsonLinesItemExporter(f)
        exporter.fields_to_export = ['origin', 'to']
        exporter.start_exporting()
        self.exporters[spider.name] = exporter

    def close_spider(self, spider):
        exporter = self.exporters[spider.name]
        exporter.finish_exporting()
        exporter.file.close()

    def process_item(self, item, spider):
        exporter = self.exporters[spider.name]
        exporter.export_item(item)
        return item
