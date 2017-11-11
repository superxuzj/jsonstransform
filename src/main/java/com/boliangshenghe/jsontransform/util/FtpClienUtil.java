package com.boliangshenghe.jsontransform.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

public class FtpClienUtil {

	public static FtpClient ftp;

	/***
	 * 连接ftp
	 * 
	 * @param url
	 *            必须是 192.168.8.1 否则提示异常
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 */
	public static FtpClient connectFTP() {
		// FtpClient ftp = connectFTP("10.9.105.107", 21, "zhibansubao",
		// "IU4!iI5puo!EXElU");
		String url = "10.9.105.107";
		int port = 21;
		String username = "zhibansubao";
		String password = "IU4!iI5puo!EXElU";
		// 创建ftp
		try {
			// 创建地址
			SocketAddress addr = new InetSocketAddress(url, port);
			// 连接
			ftp = FtpClient.create();
			ftp.connect(addr);
			// 登陆
			ftp.login(username, password.toCharArray());
			ftp.setBinaryType();
			System.out.println("connection");
			
		} catch (FtpProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ftp;
	}

	public static String download(String ftpFile) {
		String rs = "";
		String str = "";
		InputStream is = null;
		BufferedReader br = null;
		try {
			// 获取ftp上的文件
			is = ftp.getFileStream(ftpFile);
			// 转为字节流
			br = new BufferedReader(new InputStreamReader(is, "GBK"));
			while ((str = br.readLine()) != null) {
				rs = rs + str;
			}
			br.close();
		} catch (FtpProtocolException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 返回FTP目录下的文件列表
	 * 
	 * @param ftpDirectory
	 * @return
	 */
	public static List<String> getFileNameList(String ftpDirectory) {
		List<String> list = new ArrayList<String>();
		try {
			
			DataInputStream dis = new DataInputStream(
					ftp.nameList(ftpDirectory));
			String filename = "";
			while ((filename = dis.readLine()) != null) {
				list.add(filename);
			}
			System.out.println("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		connectFTP();
		List<String> names = getFileNameList("");
		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i));
			String list = download(names.get(i));
			System.out.println(list);
		}
		/**/

	}

}
