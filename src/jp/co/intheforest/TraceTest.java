package jp.co.intheforest;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;

public class TraceTest {

    public static void main(String[] args) {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder()
                    .addContactPoint("127.0.0.1")
                    .build();
            Session session = cluster.connect();

            ResultSet rs = session.execute("select release_version from system.local");    // (3)
            Row row = rs.one();
            System.out.println(row.getString("release_version"));
        } finally {
            if (cluster != null) cluster.close();
        }
    }

}
