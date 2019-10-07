package com.zjx.opds;

import com.aliyun.odps.Odps;
import com.aliyun.odps.Table;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;

public class OpdsDemo {

    public static void main(String[] args) {
        Account account = new AliyunAccount("xxx", "xxx");
        Odps odps = new Odps(account);
        String odpsUrl = "http://service.cn-hangzhou.maxcompute.aliyun.com/api";
        odps.setEndpoint(odpsUrl);
        odps.setDefaultProject("zjx_test");
        for (Table t : odps.tables()) {
            System.out.println(t.getName());
        }
    }
}
