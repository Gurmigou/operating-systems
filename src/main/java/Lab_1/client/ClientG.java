package Lab_1.client;

import os.lab1.compfuncs.advanced.DoubleOps;
import os.lab1.compfuncs.basic.IntOps;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static Lab_1.CommunicationCommand.*;

public class ClientG extends AbstractClient {
    public ClientG() throws IOException {
        super();
    }

    public static void main(String[] args) throws IOException {
        ClientG clientG = new ClientG();
        clientG.out.println("Client G connected");

        clientG.parameter = clientG.in.readLine();
        clientG.startClient();
    }

    public void startClient() {
        try {
            int attempts = 0;
            while (attempts < MAX_ATTEMPTS) {
                int number = random.nextInt(10) + 1;

                Optional<Integer> optional = IntOps.trialG(Integer.parseInt(parameter));

                if (optional.isPresent()) {
                    out.println(RESULT_G.getMsg() + optional.get());
                    break;
                } else {
                    out.println(SOFT_ERROR.getMsg());
                    attempts++;
                }

                try {
                    Thread.sleep(500L * number);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            out.println(HARD_ERROR.getMsg() + "Function G failed " +
                    MAX_ATTEMPTS + " times to compute a result");

        } catch (Throwable e) {
            out.println(HARD_ERROR.getMsg() + e.getMessage());
            this.closeConnection();
        }
    }
}