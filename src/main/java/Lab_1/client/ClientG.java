package Lab_1.client;

import Lab_1.function.AdvancedDoubleOps;

import java.io.IOException;
import java.util.Optional;

import static Lab_1.util.CommunicationCommand.*;

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
                Optional<Optional<Double>> optionalResult = AdvancedDoubleOps.trialG(Integer.parseInt(parameter));

                if (optionalResult.isEmpty()) {
                    out.println(SOFT_ERROR.getMsg() + (attempts + 1));
                    attempts++;
                } else {
                    Optional<Double> result = optionalResult.get();
                    if (result.isEmpty())
                        out.println(HARD_ERROR.getMsg() + "Can't compute Function G");
                    else
                        out.println(RESULT_F.getMsg() + result.get());
                    break;
                }

                int randomNum = random.nextInt(3) + 1;
                justSleep(1500L * randomNum);
            }

            out.println(HARD_ERROR.getMsg() + "Function G failed " +
                    MAX_ATTEMPTS + " times to compute a result");

        } catch (InterruptedException e) {
            e.printStackTrace();
            out.println(HARD_ERROR.getMsg() + e.getMessage());
        }
    }
}