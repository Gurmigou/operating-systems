package Lab_1.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Manager {
    private ServerSocket serverSocket;
    private static final String PROCESS_DIR = "C:\\Users\\Yehor\\IdeaProjects\\operating-systems\\target\\classes";

    public void initComputation(String parameter) throws IOException {
        initServer();
        startComputationProcess("F", parameter);
//        startComputationProcess("G", parameter);
    }

    public static void stopComputation() {
        System.out.println("Computation stopped. Closing server...");
    }

    public static void handleHardError(String message) {
        System.out.println("Happened hard error. Reason: " + ((message != null && !message.isEmpty()) ? message : "unknown"));
    }

    public static void handleSoftError(String clientName) {
        System.out.println("Happened soft error on client " + clientName + ". Computation wasn't finished.");
    }

    private void initServer() throws IOException {
        final int PORT = 8080;
        final int MAX_CONNECTIONS = 2;
        this.serverSocket = new ServerSocket(PORT, MAX_CONNECTIONS);
    }

    private void startComputationProcess(String clientName, String parameter) throws IOException {
        ProcessBuilder builtProcess = createProcess(clientName);
        builtProcess.start();

        createProcessToServerConnection(clientName, parameter);
    }

    private ProcessBuilder createProcess(String clientName) {
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File(PROCESS_DIR));
        pb.command("java", "Lab_1.client.Client" + clientName);
        return pb;
    }

    private void createProcessToServerConnection(String clientName, String parameter) throws IOException {
        Socket connection = this.serverSocket.accept();
        ClientHandler clientHandler = new ClientHandler(connection, parameter, clientName);
//        clientHandler.start();
    }
}
