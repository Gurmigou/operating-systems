package Lab_1.server;

import os.lab1.compfuncs.advanced.IntOps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

import static Lab_1.util.CommunicationCommand.*;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final String clientName;
    private final PrintWriter out;
    private final BufferedReader in;

    public ClientHandler(Socket clientSocket, String parameter, String clientName) throws IOException {
        this.clientSocket = clientSocket;
        this.clientName = clientName;

        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // send a parameter to the client
        this.out.println(parameter);

        this.setDaemon(true);
        this.start();
    }

    public static void main(String[] args) throws InterruptedException {
        Optional<Optional<Integer>> optional = IntOps.trialF(1);
        System.out.println(optional);
    }

    @Override
    public void run() {
        try {
            boolean iterate = true;
            String response = null;
            while (iterate) {
                response = in.readLine();
                iterate = handleClientResponse(response);
            }

            double result = Double.parseDouble(response.substring(RESULT_F.getMsg().length()));
            System.out.println("Result " + clientName + ": " + result);
            System.out.println("Computation on client: " + clientName + " is finished. Socket closed");

            synchronized (Manager.resultList) {
                if (Manager.resultList.size() == 1) {
                    Manager.handleResult(Manager.resultList.get(0), result);
                } else {
                    Manager.resultList.add(result);
                }
            }

            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            Manager.handleHardError(e.getMessage());
        }
    }

    private boolean handleClientResponse(String response) {
        if (response == null || response.isEmpty())
            return true;

        if (response.startsWith(RESULT_F.getMsg()) || response.startsWith(RESULT_G.getMsg())) {
            // RESULT_F.length is equal to RESULT_G.length
            return false;
        }
        if (response.startsWith(SOFT_ERROR.getMsg())) {
            String attempt = response.substring(SOFT_ERROR.getMsg().length());
            System.out.println("Soft Error happened on client: " + clientName + ". Trying to recompute: " + attempt);
            return true;
        }
        if (response.startsWith(HARD_ERROR.getMsg())) {
            String message = createHardErrorMessage(response);
            Manager.handleHardError(message);
            return false;
        }

        System.out.println(response);
        return true;
    }

    private String createHardErrorMessage(String response) {
        if (response.length() > HARD_ERROR.getMsg().length()) {
            return response.substring(HARD_ERROR.getMsg().length());
        }
        return "";
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
