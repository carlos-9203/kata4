package software.ulpgc.kata4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqliteTrackLoader implements TrackLoader{
    private final Connection connection;
    private final  static  String SQL = "select * from dato where platform = \"Wii\"";

    public SqliteTrackLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Track> loadAll() {
        try {
            return load(queryAll());
        } catch (SQLException e){
            return Collections.emptyList();
        }
    }

    private List<Track> load(ResultSet resultSet) throws SQLException {
        List<Track> result = new ArrayList<>();
        while (resultSet.next())
            result.add(trackFrom(resultSet));
        return result;
    }

    private Track trackFrom(ResultSet resultSet) throws SQLException {
        return new Track(
                resultSet.getString("Platform"),
                resultSet.getString("Genre"),
                resultSet.getString("Publisher"),
                resultSet.getDouble("NA_Sales"),
                resultSet.getDouble("EU_Sales"),
                resultSet.getDouble("JP_Sales"),
                resultSet.getDouble("Other_Sales"),
                resultSet.getDouble("Global_Sales"),
                resultSet.getString("Rating"),
                resultSet.getString("Critic_Score_Class")
        );
    }

    private ResultSet queryAll() throws SQLException {
        return connection.createStatement().executeQuery(SQL);
    }
}
