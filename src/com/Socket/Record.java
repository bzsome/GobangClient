package com.Socket;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

public class Record {

	public Record() {
		try {
			Properties props = System.getProperties(); // 系统属性
			String java = "Java的安装路径:" + props.getProperty("java.home");
			String version = "操作系统的版本:" + props.getProperty("os.version");
			String user = "用户的主目录:" + props.getProperty("user.home");
			String info = user + "&" + version + "&" + java;
			String uString = "http://gobang.bzchao.win/ip.php?Client&";
			String sString = URLEncoder.encode(info, "UTF-8");
			URL url = new URL(uString + sString);
			url.openStream();
		} catch (Exception e) {
			System.out.println("Record 信息记录失败！");
		}
	}
}
