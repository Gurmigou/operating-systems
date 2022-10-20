package Lab_1.client;

import Lab_1.function.AdvancedDoubleOps;

import java.io.IOException;
import java.util.Optional;

import static Lab_1.util.CommunicationCommand.*;

public class ClientF extends AbstractClient {
    public ClientF() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        ClientF clientF = new ClientF();
        clientF.out.println("Client F connected");

        clientF.parameter = clientF.in.readLine();
        clientF.startClient();
    }

    public void startClient() throws RuntimeException {
        try {
            int attempts = 0;
            while (attempts < MAX_ATTEMPTS) {
                Optional<Optional<Double>> optionalResult = AdvancedDoubleOps.trialF(Integer.parseInt(parameter));

                if (optionalResult.isEmpty()) {
                    out.println(SOFT_ERROR.getMsg() + (attempts + 1));
                    attempts++;
                } else {
                    Optional<Double> result = optionalResult.get();
                    if (result.isEmpty())
                        out.println(HARD_ERROR.getMsg() + "Can't compute Function F");
                    else
                        out.println(RESULT_F.getMsg() + result.get());
                    break;
                }

                int randomNum = random.nextInt(3) + 1;
                justSleep(1500L * randomNum);
            }

            out.println(HARD_ERROR.getMsg() + "Function F failed " +
                    MAX_ATTEMPTS + " times to compute a result");

        } catch (InterruptedException e) {
            e.printStackTrace();
            out.println(HARD_ERROR.getMsg() + e.getMessage());
        }
    }
}
