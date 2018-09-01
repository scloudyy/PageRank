import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DBOutput {
    public static class PageRankMapper extends Mapper<LongWritable, Text, Text, Text> {
        
    }
}
