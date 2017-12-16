package EchoServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class EchoServer {
	public int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

		while(true){
			Socket socket = serverSocket.accept(); 
			EchoProducer producer = new EchoProducer(socket); // Create an EchoProducer every time a client accept and this EchoProducer will serve that cilent. 
			producer.start();
		}
	}
	class EchoProducer extends Thread{
		private Socket socket;

		EchoProducer(Socket socket){
			this.socket = socket;
		}

		public void run(){
			try {
				InputStream	inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				int b;
				while ((b = inputStream.read())!= -1) {
					outputStream.write(b);
				}
				System.out.flush();
				socket.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}