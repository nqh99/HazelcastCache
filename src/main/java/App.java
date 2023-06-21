import com.fasterxml.jackson.core.JsonProcessingException;
import com.hazelcast.core.HazelcastInstance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private static ServerSocket server;

    public static void main(String[] args) throws JsonProcessingException {
        try {
            server = new ServerSocket(9090);
            server.setSoTimeout(0);
            server.setReuseAddress(true);
            System.out.println("Server is running...");

            Socket client;
            while (true) {
                client = server.accept();
                System.out.println("New client connected: " + client.getInetAddress().getHostAddress());
                threadPool.execute(new ClientHandler(client));
            }
        } catch (IOException e) {
            System.out.println("Server cannot running...");
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            HazelcastInstance
        }
    }
}
