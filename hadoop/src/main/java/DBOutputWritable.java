import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOutputWritable implements DBWritable {
    private String page_id;
    private String page_url;
    private double page_rank;

    public DBOutputWritable (String page_id, String page_url, double page_rank) {
        this.page_id = page_id;
        this.page_url = page_url;
        this.page_rank = page_rank;
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, page_id);
        preparedStatement.setString(2, page_url);
        preparedStatement.setDouble(3, page_rank);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.page_id = resultSet.getString(1);
        this.page_url = resultSet.getString(2);
        this.page_rank = resultSet.getDouble(3);
    }
}
