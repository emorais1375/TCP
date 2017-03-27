import java.net.*;
import java.io.*;
import java.util.Scanner;
public class TCPClient{
  public static void main(String args[]){
    Socket s = null;
    String data, login;
    int op;
    Scanner ler = new Scanner(System.in);
    try{
      s = new Socket("127.0.0.1", 7896);
      DataInputStream in = new DataInputStream(s.getInputStream());
      DataOutputStream out = new DataOutputStream(s.getOutputStream());
      System.out.println("Bem vindo ao Sistema de Salas");
      System.out.print("Login:");
      login = ler.nextLine();
      out.writeUTF(login);
      data = in.readUTF();
      for (int i = 0; i < 50; ++i) System.out.println ();
      if(data.equals("0") == false){
        while(true){
          System.out.println("Bem vindo ao Sistema de Salas, "+login+"!");
          System.out.println("=> Consultar sala");
          System.out.println("Horário  |   SALA 1   |   SALA 2   |   SALA 3");
          System.out.print(data);
          System.out.println("");
          System.out.println("=> Menu");
          System.out.println("1 - Reservar.");
          System.out.println("2 - Cancelar reserva.");
          System.out.println("3 - Atualizar consulta.");
          System.out.println("4 - Sair.");
          System.out.print("Opção: ");
          data = ler.nextLine();
          if(data.equals("1") || data.equals("2")){
            System.out.print("Digite o número da sala(Ex:. 1): ");
            data += ler.nextLine();
            System.out.print("Digite o horário da sala(Ex:. 13:00): ");
            data += ler.nextLine();
          }
          else if(data.equals("4")){
            out.writeUTF(data); // Informa o servidor para finalizar o socket
            break; // finaliza o cliente
          }
          out.writeUTF(data);
          data = in.readUTF();          
          for (int i = 0; i < 50; ++i) System.out.println ();
        }
      }
    }
    catch(ArrayIndexOutOfBoundsException e){
      System.out.println("Array:" + e.getMessage());
    }
    catch(UnknownHostException e){
      System.out.println("Sock:" + e.getMessage());
    }
    catch(EOFException e){
      System.out.println("EOF:" + e.getMessage());
    }
    catch(IOException e){
      System.out.println("IO:" + e.getMessage());
    }
    finally{
      if(s != null){
        try{
          System.out.println("Cliente finalizado");
          s.close();
        }
        catch(IOException e){
          System.out.println("close:" + e.getMessage());
        }
      }
    }
  }
}
