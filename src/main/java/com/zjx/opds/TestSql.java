package com.zjx.opds;

import com.aliyun.odps.Column;
import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.task.SQLTask;

import java.util.List;

public class TestSql {
    private static final String accessId = "H2TqEM2WkGc54QMp";
    private static final String accessKey = "rYlbDIbJe10sbt6WsZ0lFKnXJA9Xg3";
    private static final String endPoint = "http://service.odpsstg.aliyun-inc.com/stgnew";
    private static final String project = "bigdataLab";
    private static final String sql = "select * from zjx_test_table1;";

    public static void main(String[] args) {
        Account account = new AliyunAccount(accessId, accessKey);
        Odps odps = new Odps(account);
        odps.setEndpoint(endPoint);
        odps.setDefaultProject(project);
        Instance i;
        try {
            i = SQLTask.run(odps, sql);
            i.waitForSuccess();
            List<Record> records = SQLTask.getResult(i);
            for(Record r:records){
                for(Column column:r.getColumns()){
                    System.out.print(r.get(column.getName())+" ");
                }
                System.out.println();
            }
        } catch (OdpsException e) {
            e.printStackTrace();
        }
    }
}
