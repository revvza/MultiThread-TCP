import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Client {

    public static void main(String[] args) {
        // membangun koneksi dengan menyediakan host dan port
        // number
        try (Socket socket = new Socket("localhost", 9999)) {

            // menulis untuk server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // membaca dari server
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // object untuk scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;

            while (!"exit".equalsIgnoreCase(line)) {

                // membaca dari user
                line = sc.nextLine();

                // menhirim input user ke server
                out.println(line);
                out.flush();

                // menampilkan balasan server
                System.out.println("Server replied "
                        + in.readLine());
            }

            // menutup scanner object
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
