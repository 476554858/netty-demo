package com.zjx.opds;

import com.aliyun.odps.Odps;
import com.aliyun.odps.Table;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.tunnel.TableTunnel;

public class OpdsDemo {

    public static void main(String[] args) throws Exception{
        Account account = new AliyunAccount("H2TqEM2WkGc54QMp", "rYlbDIbJe10sbt6WsZ0lFKnXJA9Xg3");
        Odps odps = new Odps(account);
        //http://service.cn-hangzhou.maxcompute.aliyun.com/api
        String odpsUrl = "http://service.odpsstg.aliyun-inc.com/stgnew";
        odps.setEndpoint(odpsUrl);
        /*odps.setDefaultProject("bigdataLab");*/


      /*  Table table = odps.tables().get("zjx_test_table1");
        RecordReader reader = table.read(4);

        Record record = reader.read();

        Column[] columns =  record.getColumns();*/


        TableTunnel tunnel = new TableTunnel(odps);
        //tunnel.setEndpoint(odpsUrl);

        TableTunnel.UploadSession uploadSession = tunnel.createUploadSession("bigdataLab","zjx_test_table1");
    }
}
