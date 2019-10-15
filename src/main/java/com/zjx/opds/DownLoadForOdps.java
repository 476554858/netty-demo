package com.zjx.opds;

import com.aliyun.odps.Column;
import com.aliyun.odps.Odps;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordReader;
import com.aliyun.odps.tunnel.TableTunnel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DownLoadForOdps {
    public static void main(String[] args) throws Exception{
        Account account = new AliyunAccount("H2TqEM2WkGc54QMp", "rYlbDIbJe10sbt6WsZ0lFKnXJA9Xg3");
        Odps odps = new Odps(account);
        //http://service.cn-hangzhou.maxcompute.aliyun.com/api
        String odpsUrl = "http://service.odpsstg.aliyun-inc.com/stgnew";
        odps.setEndpoint(odpsUrl);

        TableTunnel tunnel = new TableTunnel(odps);
        //tunnel.setEndpoint(odpsUrl);

        TableTunnel.DownloadSession downloadSession = tunnel.createDownloadSession("bigdataLab","zjx_test_table1");
        RecordReader recordReader = downloadSession.openRecordReader(0,downloadSession.getRecordCount());
        Record record;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        int recordCounter = 0;
        while ((record = recordReader.read()) != null){
            recordCounter++;
            Column[] columns = record.getColumns();
            for(int i=0,len=columns.length;i<len;i++){
                String colValue = null;
                Column column = columns[i];
                switch (column.getType()) {
                    case BIGINT: {
                        Long v = record.getBigint(i) == null ? null : record.getBigint(i);
                        colValue = v == null ? "null" : v.toString();
                        break;
                    }
                    case BOOLEAN: {
                        Boolean v = record.getBoolean(i) == null ? null : record.getBoolean(i);
                        colValue = v == null ? "null" : v.toString();
                        break;
                    }
                    case DATETIME: {
                        Date v = record.getDatetime(i) == null ? null : record.getDatetime(i);
                        colValue = v == null ? "null" : "'" + format.format(v) + "'";
                        break;
                    }
                    case DOUBLE: {
                        Double v = record.getDouble(i) == null ? null : record.getDouble(i);
                        colValue = v == null ? "null" : v.toString();
                        break;
                    }
                    case STRING: {
                        String v = record.getString(i) == null ? null : record.getString(i);
                        //特殊字符处理
                        v = processSpecialCharacte(v);
                        colValue = v == null ? "null" : "'" + v + "'";
                        break;
                    }
                    case DECIMAL: {
                        BigDecimal v = record.getDecimal(i) == null ? null : record.getDecimal(i);
                        colValue = v == null ? "null" : v.toString();
                        break;
                    }
                    default:
                        throw new RuntimeException("Unknown column type: " + column.getType());
                }
                System.out.print(colValue+" ");
            }
            System.out.println("");
        }
    }


    private static String processSpecialCharacte(String colValue) {
        if (colValue.contains( "\\")) {
            colValue = colValue.replace( "\\", "\\\\");
        }

        if (colValue.contains( "\'")) {
            colValue = colValue.replace( "\'", "\\'");
        }
        return colValue;
    }
}
