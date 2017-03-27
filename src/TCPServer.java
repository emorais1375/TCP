import java.net.*;
import java.io.*;
public class TCPServer{
	public static void main(String args[]){
		try{
			ServerSocket listenSocket = new ServerSocket(7896);
			System.out.println("Porta 7896 aberta!");
			while(true){
				Socket clientSocket = listenSocket.accept(); // accept espera por um cliente
				System.out.println("Nova conexão com o cliente " + clientSocket.getInetAddress().getHostAddress());
				Connection c = new Connection(clientSocket);
			}
		}
		catch(IOException e){
			System.out.println("Listen:" + e.getMessage());
		}
	}
}
class Connection extends Thread{
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	public Connection(Socket aClientSocket){
		try{
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		}
		catch(IOException e){
			System.out.println("Connection:" + e.getMessage());
		}
	}
	public void run(){
		String horario =
		"13:00    |            |            |         \n"+
		"13:30    |            |            |         \n"+
		"14:00    |            |            |         \n"+
		"14:30    |            |            |         \n";
		try{
			String data = in.readUTF();
			int numSala, numHora;
			if(data.equals("Brenda")){ // 30 nome fem com 6 letras
				out.writeUTF(horario);
				while(true){
					data = in.readUTF();
					if (data.equals("4")) {
						break;
					}
					if (data.substring(0,1).equals("1") || data.substring(0,1).equals("2")) {
						numSala = Integer.parseInt(data.substring(1,2));
						numHora = Integer.parseInt(data.substring(3,4) + data.substring(5,6));
						numHora = (numHora - 30)/5;
						if (data.substring(0,1).equals("1")) { // Reservar
							horario = horario.substring(0,13*numSala+numHora*46)+"Brenda"+horario.substring(13*numSala+numHora*46+6,184);
						}
						else{ // Cancelar reserva
							horario = horario.substring(0,13*numSala+numHora*46)+"      "+horario.substring(13*numSala+numHora*46+6,184);
						}
					}
					out.writeUTF(horario);
				}
			}
			else{
				out.writeUTF("0");
			}
		}
		catch(EOFException e){
			System.out.println("EOF:" + e.getMessage());
		}
		catch(IOException e){
			System.out.println("IO:" + e.getMessage());
		}
		finally{
			try{
				System.out.println("conexão com o cliente "+clientSocket.getInetAddress().getHostAddress()+" finalizada");
				clientSocket.close();
			}
			catch(IOException e){
				System.out.println("close failed");
			}
		}
	}
}
