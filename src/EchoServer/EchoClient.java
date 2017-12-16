package EchoServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}
	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();
		Thread thread1 = new Thread(){ //Create 1st thread
			public void run(){
				try{
					int readByte;
					while ((readByte = System.in.read()) != -1) {
						socketOutputStream.write(readByte); 
						int socketByte = socketInputStream.read();
						System.out.write(socketByte);
					}
					socketOutputStream.flush();
				  socket.shutdownOutput(); //Shutdown the output but never close the socket in order not to cover the information send back from the server.
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		};
		thread1.start();
		Thread thread2 = new Thread(){ //Create 2nd thread
			public void run(){
				int socketByte;
				try {
					while ((socketByte = socketInputStream.read()) != -1) {
						System.out.write(socketByte); 
					}
					System.out.flush();
					socket.close(); //Close the socket at the end of the second thread down reading from server.
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread2.start();
	}
}