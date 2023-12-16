import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocketVotacao {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        System.out.println("Conexão com o servidor de votação estabelecida.");

        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        Scanner scanner = new Scanner(System.in);
        System.out.println(input.readUTF());
        int voto = scanner.nextInt();

        output.writeInt(voto);
        output.flush();

        int votosCandidato1 = input.readInt();
        int votosCandidato2 = input.readInt();

        System.out.println("Quantidade de votos para o Candidato 1: " + votosCandidato1);
        System.out.println("Quantidade de votos para o Candidato 2: " + votosCandidato2);

        input.close();
        output.close();
        socket.close();
        scanner.close();
    }
}