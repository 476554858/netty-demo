package com.zjx.opds;

import com.aliyun.odps.Column;
import com.aliyun.odps.Odps;
import com.aliyun.odps.TableSchema;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordWriter;
import com.aliyun.odps.tunnel.TableTunnel;

import java.util.ArrayList;
import java.util.List;

public class UploadForOdps {
    public static List<String[]> datas = new ArrayList<String[]>();
    static {
        String[] data1 = new String[]{"张三","27","一班"};
        datas.add(data1);
    }

    public static void main(String[] args) throws Exception{
        Account account = new AliyunAccount("H2TqEM2WkGc54QMp", "rYlbDIbJe10sbt6WsZ0lFKnXJA9Xg3");
        Odps odps = new Odps(account);
        //http://service.cn-hangzhou.maxcompute.aliyun.com/api
        String odpsUrl = "http://service.odpsstg.aliyun-inc.com/stgnew";
        odps.setEndpoint(odpsUrl);

        TableTunnel tunnel = new TableTunnel(odps);

        TableTunnel.UploadSession uploadSession = tunnel.createUploadSession("bigdataLab","zjx_test_table1");

        TableSchema schema = uploadSession.getSchema();
        RecordWriter recordWriter = uploadSession.openRecordWriter(0);
        Record record = uploadSession.newRecord();

        List<Column> columns = schema.getColumns();
        for(String[] data:datas){
            int total = data.length <= columns.size() ? data.length : columns.size();
            for(int i=0;i<total;i++){
                record.setString(i,data[i]==null ? "" : data[i]);
            }
            recordWriter.write(record);
        }
        recordWriter.close();
        uploadSession.commit();

    }
}
