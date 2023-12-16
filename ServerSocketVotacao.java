import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketVotacao {
    static int candidato1Votes = 0;
    static int candidato2Votes = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);
        System.out.println("Servidor de votação pronto para receber votos.");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

            new VoteHandler(clientSocket).start();
        }
    }

    static synchronized void voteForCandidato1() {
        candidato1Votes++;
    }

    static synchronized void voteForCandidato2() {
        candidato2Votes++;
    }
    
}

class VoteHandler extends Thread {
    private Socket clientSocket;

    public VoteHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

            output.writeUTF("Digite 1 para votar no candidato 1 ou 2 para votar no candidato 2:");
            output.flush();

            int candidato = input.readInt();

            if (candidato == 1) {
                ServerSocketVotacao.voteForCandidato1();
            } else if (candidato == 2) {
                ServerSocketVotacao.voteForCandidato2();
            }

            output.writeInt(ServerSocketVotacao.candidato1Votes);
            output.writeInt(ServerSocketVotacao.candidato2Votes);
            output.flush();

            input.close();
            output.close();
            clientSocket.close();
            

        } catch (IOException e) {
            System.out.println("Erro no servidor! " + e);
        }
    }
}