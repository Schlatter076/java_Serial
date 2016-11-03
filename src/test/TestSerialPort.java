package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import serialException.NoSuchPort;
import serialException.NotASerialPort;
import serialException.PortInUse;
import serialException.SerialPortParameterFailure;
import serialException.TooManyListeners;
import serialPort.SerialTool;

public class TestSerialPort {
	
	private List<String> commList = null;	//������ö˿ں�
	private SerialPort serialPort = null;	//���洮�ڶ���
	
	/**
	 * ��Ĺ��췽��
	 */
	public TestSerialPort() {
		commList = SerialTool.findPort();;	//������ö˿ں�commList
		System.out.println("���õĶ˿�Ϊ:"+commList.toString());
		
	}
	public void  open() {
	
		try {
			//��ȡָ���˿����������ʵĴ��ڶ���
			serialPort = SerialTool.openPort(commList.get(0), 9600);
			//�ڸô��ڶ�������Ӽ�����
			SerialTool.addListener(serialPort, new SerialListener());
			
			
		} catch (SerialPortParameterFailure e) {
			
			e.printStackTrace();
		} catch (NotASerialPort e) {
			
			e.printStackTrace();
		} catch (NoSuchPort e) {
		
			e.printStackTrace();
		} catch (PortInUse e) {
			
			e.printStackTrace();
		} catch (TooManyListeners e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TestSerialPort testSerialPort=new TestSerialPort();
		testSerialPort.open();
	}
	
	
	/**
	 * ���ڲ�����ʽ����һ�����ڼ�����
	 * @author zhong
	 *
	 */

	private class SerialListener implements SerialPortEventListener {
		
		/**
		 * �����ص��Ĵ����¼�
		 */
	    public void serialEvent(SerialPortEvent serialPortEvent) {
	    	
	        switch (serialPortEvent.getEventType()) {

	            case SerialPortEvent.BI: // 10 ͨѶ�ж�
	            	System.out.println("�봮���豸ͨѶ�ж�");
	            	break;

	            case SerialPortEvent.OE: // 7 ��λ�����������

	            case SerialPortEvent.FE: // 9 ֡����

	            case SerialPortEvent.PE: // 8 ��żУ�����

	            case SerialPortEvent.CD: // 6 �ز����

	            case SerialPortEvent.CTS: // 3 �������������

	            case SerialPortEvent.DSR: // 4 ����������׼������

	            case SerialPortEvent.RI: // 5 ����ָʾ

	            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 ��������������
	            	break;
	            
	            case SerialPortEvent.DATA_AVAILABLE: // 1 ���ڴ��ڿ�������
	            	
	            	System.out.println("found data");
					byte[] data = null;
					
					try {
						if (serialPort == null) {
							System.out.println("���ڶ���Ϊ�գ�����ʧ�ܣ�");
						}
						else {
							data = SerialTool.readFromPort(serialPort);	//��ȡ���ݣ������ֽ�����
							//System.out.println(new String(data));
							
							//�Զ����������
							if (data == null || data.length < 1) {	//��������Ƿ��ȡ��ȷ	
								System.out.println( "��ȡ���ݹ�����δ��ȡ����Ч���ݣ������豸�����");
								System.exit(0);
							}
							else {
								String dataOriginal = new String(data);	//���ֽ���������ת��λΪ������ԭʼ���ݵ��ַ���
								String dataValid = "";	//��Ч���ݣ���������ԭʼ�����ַ���ȥ���ͷ*���Ժ���ַ�����
								String[] elements = null;	//�������水�ո���ԭʼ�ַ�����õ����ַ�������	
								System.out.println("�յ�������:"+dataOriginal);
								//�����ݷŽ����ݿ�
								String now=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
								Db Dbtool =new Db();
								Dbtool.insertTem(now, dataOriginal);
								
							}
							
						}						
						
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(0);	//������ȡ����ʱ��ʾ������Ϣ���˳�ϵͳ
					}	
		            
					break;

	        }

	    }

	}

}




