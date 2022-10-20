package Lab_1.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    private ServerSocket serverSocket;
    private static final String PROCESS_DIR = "C:\\Users\\Yehor\\IdeaProjects\\operating-systems\\target\\classes";
    private static final String JAR_DIR = "C:\\Users\\Yehor\\IdeaProjects\\operating-systems\\src\\main\\resources\\lab1.jar";
    private final List<ClientHandler> serverThreads = new ArrayList<>();
    private static final List<Process> processList = new ArrayList<>();
    protected static final List<Double> resultList = new ArrayList<>();

    public void initComputation(String parameter) throws IOException, InterruptedException {
        initServer();
        startComputationProcess("F", parameter);
        startComputationProcess("G", parameter);

        for (ClientHandler ch : serverThreads) {
            ch.join();
        }
    }

    public static void stopComputation() {
        System.out.println("Server closed.");

        for (Process process : processList) {
            process.destroy();
        }

        System.exit(0);
    }

    public static void handleHardError(String info) {
        System.out.println("Happened Hard Error: " + ((info != null && !info.isEmpty()) ? info : "unknown"));
        stopComputation();
    }

    public static void handleResult(double op1, double op2) {
        System.out.println("Final result: " + op1 + " * " + op2 + " = " + (op1 * op2));
        stopComputation();
    }

    private void initServer() throws IOException {
        final int PORT = 4005;
        final int MAX_CONNECTIONS = 2;
        this.serverSocket = new ServerSocket(PORT, MAX_CONNECTIONS);
        System.out.println("Server started");
    }

    private void startComputationProcess(String clientName, String parameter) throws IOException {
        ProcessBuilder builtProcess = createProcess(clientName);
        Process startedProcess = builtProcess.start();
        processList.add(startedProcess);

        createProcessToServerConnection(clientName, parameter);
    }

    private ProcessBuilder createProcess(String clientName) {
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File(PROCESS_DIR));
        pb.command("java", "-cp", ";" + JAR_DIR, "Lab_1.client.Client" + clientName);
        return pb;
    }

    private void createProcessToServerConnection(String clientName, String parameter) throws IOException {
        Socket connection = this.serverSocket.accept();
        ClientHandler clientHandler = new ClientHandler(connection, parameter, clientName);
        this.serverThreads.add(clientHandler);
    }
}
