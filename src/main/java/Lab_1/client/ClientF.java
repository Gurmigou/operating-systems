package Lab_1.client;

import Lab_1.function.AdvancedIntOps;
import os.lab1.compfuncs.advanced.Conjunction;

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
        int attempts = 0;
        justSleep(2000L);

        while (attempts < MAX_ATTEMPTS) {
            Optional<Optional<Integer>> optionalResult = AdvancedIntOps.trialF(Integer.parseInt(parameter));

            if (optionalResult.isEmpty()) {
                out.println(SOFT_ERROR.getMsg());
                attempts++;
            } else {
                Optional<Integer> result = optionalResult.get();
                if (result.isEmpty())
                    out.println(HARD_ERROR.getMsg() + "Function F is not defined on argument " + parameter);
                else
                    out.println(RESULT_F.getMsg() + result.get());
                break;
            }

            int randomNum = random.nextInt(3) + 1;
            justSleep(1500L * randomNum);
        }

        out.println(HARD_ERROR.getMsg() + "Function F failed " +
                MAX_ATTEMPTS + " times to compute a result");

//        int attempts = 0;
//        while (attempts < MAX_ATTEMPTS) {
//            int number = random.nextInt(10) + 1;
//            int random = AbstractClient.random.nextInt(10) + 1;
//
//            if (random <= 5) {
//                ExecutorService es = Executors.newSingleThreadExecutor();
//                Future<Optional<Integer>> future = es.submit(() -> IntOps.trialF(Integer.parseInt(parameter)));
//
//                try {
//                    Optional<Integer> optional = future.get(5, TimeUnit.SECONDS);
//
//                    optional.ifPresent(result -> out.println(RESULT_F.getMsg() + result));
//
//                } catch (InterruptedException | ExecutionException | TimeoutException e) {
//                    out.println(HARD_ERROR.getMsg() + "Function F is not defined on argument " + parameter);
//                    this.closeConnection();
//                }
//
//            } else {
//                out.println(SOFT_ERROR.getMsg());
//                attempts++;
//            }
//
//            try {
//                Thread.sleep(650L * number);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        out.println(HARD_ERROR.getMsg() + "Function F failed " +
//                MAX_ATTEMPTS + " times to compute a result");
    }
}
