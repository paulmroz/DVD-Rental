package sample;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

public class ClientSocket {
    private static final Logger log = Logger.getLogger(ClientSocket.class.getName());

    static Socket socket;

    static PrintWriter printWriter;
    static BufferedReader bufferedReader;

    static String msgFromServer;

    /**
     * Funkcja odpowiedzialna za nawiazanie połączenia z serwerem
     * @return boolean czy udało się nawiązać  połączenie z serwerem
     * @throws Exception
     */
    public static boolean connect() throws Exception {
        try {
            socket = new Socket("localhost", 4999);
            printWriter = new PrintWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (SocketException e) {
            System.out.println("Nie można nawiązać połączenia z serwerem.");
            return false;
        }
        log.info("Connection with server created");
        return true;
    }

    /**
     * Funkcja wysyłajaca wiadomość do serwera
     * @param msg wiadomość wysyłana do serwera
     */
    public static void sendMsg(String msg) {
        printWriter.println(msg);
        printWriter.flush();
    }

    /**
     * Funkcja odbierająca wiadomość z serwera
     * @return wiadomość odebrana z serwera
     * @throws IOException
     */
    public static String receiveMsg() throws IOException {
        try {
            msgFromServer = bufferedReader.readLine();
        } catch(SocketException e) {
            log.info("Utracono połączenie z serwerem");
            socket.close();
            printWriter.close();
            bufferedReader.close();
            System.exit(0);
        }
        return msgFromServer;
    }
}
