package cn.zjnu.myjournalquery;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.lang.String;

public class Connection {
    public static String ReceiveInfo(String str) throws Exception {
    	/*
       //String host = "10.7.88.29";
       String host = "192.168.119.128";
       int port = 1010;
       // �����˽�������
       Socket socket = new Socket(host, port);
       System.out.println(socket.isConnected());
       // �������Ӻ��������

       OutputStream outputStream = socket.getOutputStream();//���͸�������//1��ʾ��ȫ�� # �����
       socket.getOutputStream().write(str.getBytes("UTF-8"));
       //outputStream.flush();

       InputStream inputStream = socket.getInputStream();//���շ�������Ϣ
       byte[] bytes = new byte[1024];
       int len;
       StringBuilder sb = new StringBuilder();
       //StringBuffer sb = new StringBuffer();
       len = inputStream.read(bytes);
       sb.append(new String(bytes, 0, len, "UTF-8"));//������ƴ�ɴ�
       
       outputStream.close();
       inputStream.close();
       socket.close();
       
       return sb.toString();*/
      return "1#(1,1,'abc','abc','abc')";
    }
    
}
