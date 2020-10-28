package com.java.activiti.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    private DatagramSocket client;

    public static void UDPClient(){
        UDPClient client = new UDPClient();
        //������ѭ�������Ϸ�������
        while(true){
            String msg = "hello world!";
            if("##".equals(msg))
                break;
            //��ӡ��Ӧ������
            System.out.println(client.sendAndReceive("127.0.0.1",10101,msg));
        }
    }

    private String sendAndReceive(String ip, int port, String msg) {
        String responseMsg = "";

        try {
            //�����ͻ��˵�DatagramSocket���󣬲��ش����ַ�Ͷ���
            client = new DatagramSocket();
            byte[] sendBytes = msg.getBytes();
            //��װҪ����Ŀ��ĵ�ַ
            InetAddress address = InetAddress.getByName(ip);
            //��װҪ���͵�DatagramPacket�Ķ�������Ҫ���͵�Ŀ������������Ҫ���ϵ�ַ�Ͷ˿ں�
            DatagramPacket sendPacket = new DatagramPacket(sendBytes,sendBytes.length,address,port);

            try {
                //��������
                client.send(sendPacket);
            }catch (Exception e){
                e.printStackTrace();
            }

            byte[] responseBytes = new byte[2048];
            //������Ӧ��Ϣ��DatagramPacket����
            DatagramPacket responsePacket = new DatagramPacket(responseBytes,responseBytes.length);
            try {
                //�ȴ���Ӧ��Ϣ��ͬ�����һ�����ͻ���Ҳ������һ��������ֱ���յ�һ�����ݰ�
                client.receive(responsePacket);
            }catch (Exception e){
                e.printStackTrace();
            }

            //�������ݰ�����
            responseMsg = new String(responsePacket.getData(),0,responsePacket.getLength());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //�رտͻ���
            if(client != null){
                client.close();
                client = null;
            }
        }

        return responseMsg;
    }
}
