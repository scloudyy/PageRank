import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class UnitCellMultiplication {
    public static class TransitionMapper extends Mapper<Object, Text, Text, Text> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] fromTo = value.toString().trim().split("\t");
            String outputKey = fromTo[0];
            String[] tos = fromTo[1].split(",");

            // if there is no tos, then this website is a dead end
            if (tos.length == 0) {
                return;
            }

            double probability = 1.0 / tos.length;
            for (String to : tos) {
                String outputValue = to + "=" + String.valueOf(probability);
                context.write(new Text(outputKey), new Text(outputValue));
            }
        }
    }

    public static class PRMapper extends Mapper<Object, Text, Text, Text> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] idPr = value.toString().trim().split("\t");
            String outputKey = idPr[0];
            String outputValue = idPr[1];
            context.write(new Text(outputKey), new Text(outputValue));
        }
    }
}
