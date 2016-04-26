package jp.co.intheforest;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ExecutionInfo;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.QueryTrace;


public class TraceTest {

    public static void main(String[] args) {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder()
                    .addContactPoint("127.0.0.1")
                    .build();
            Session session = cluster.connect();
//            String sql = "select * from pathee_frontend.\"INVERTED_BARRELS_JP_000\"";
//            sql = sql + " where \"WORDID\" = ? and \"GKLAT\" = ? AND \"GKLNG\" = ? ";
            String sql = "select release_version from system.local";
            PreparedStatement prepared = session.prepare(sql).enableTracing();
            //BoundStatement bound = prepared.bind("イベント","0635500","1278000");
            BoundStatement bound = prepared.bind();
            ResultSet rs = session.execute(bound);
            ExecutionInfo executionInfo = rs.getExecutionInfo();
            QueryTrace queryTrace = executionInfo.getQueryTrace();
            Row row = rs.one();
            System.out.println(row.toString());
            System.out.println(queryTrace.getEvents());
        } finally {
            if (cluster != null) cluster.close();
        }
    }

}
