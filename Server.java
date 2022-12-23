import java.io.*;
import java.net.*;

// Server class
class Server {
    public static void main(String[] args) {
        ServerSocket server = null;

        try {

            // server menggunakan port 1234
            server = new ServerSocket(9999);
            server.setReuseAddress(true);

            // menjalankan infinite loop untuk mendapatkan
            // client request
            while (true) {

                // socket object untuk menerima client
                // requests
                Socket client = server.accept();

                // menampilkan new client sudah terhubung
                // ke server
                System.out.println("New client connected "
                        + client.getInetAddress()
                                .getHostAddress());

                // membuat thread object baru
                ClientHandler clientSock = new ClientHandler(client);

                // thread ini akan menangani client
                // terpisah
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                // mendapatkan ouputstream dari client
                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                // mendapatkan inputstream dari client
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {

                    // menulis pesan yang diterima dari
                    // client
                    System.out.printf(
                            " Sent from the client: %s\n",
                            line);
                    out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
