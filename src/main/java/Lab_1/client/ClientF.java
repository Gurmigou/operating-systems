package Lab_1.client;

import os.lab1.compfuncs.basic.IntOps;

import java.io.IOException;
import java.util.Optional;

import static Lab_1.CommunicationCommand.*;

public class ClientF extends AbstractClient {
    public ClientF() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        ClientF clientF = new ClientF();
        clientF.out.println("Client F connected");

        clientF.parameter = clientF.in.readLine();
        clientF.startClient();
    }

    public void startClient() {
        try {
            int attempts = 0;
            while (attempts < MAX_ATTEMPTS) {
                int number = random.nextInt(10) + 1;

                Optional<Integer> optional = IntOps.trialF(Integer.parseInt(parameter));

                if (optional.isPresent()) {
                    out.println(RESULT_F.getMsg() + optional.get());
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

            out.println(HARD_ERROR.getMsg() + "Function F failed " +
                    MAX_ATTEMPTS + " times to compute a result");

        } catch (Throwable e) {
            out.println(HARD_ERROR.getMsg() + e.getMessage());
            this.closeConnection();
        }
    }
}
