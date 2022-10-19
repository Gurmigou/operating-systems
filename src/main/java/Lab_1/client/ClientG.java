package Lab_1.client;

import Lab_1.function.AdvancedIntOps;

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
        int attempts = 0;
        justSleep(2000L);

        while (attempts < MAX_ATTEMPTS) {
            Optional<Optional<Integer>> optionalResult = AdvancedIntOps.trialG(Integer.parseInt(parameter));

            if (optionalResult.isEmpty()) {
                out.println(SOFT_ERROR.getMsg());
                attempts++;
            } else {
                Optional<Integer> result = optionalResult.get();
                if (result.isEmpty())
                    out.println(HARD_ERROR.getMsg() + "Function G is not defined on argument " + parameter);
                else
                    out.println(RESULT_G.getMsg() + result.get());
                break;
            }

            int randomNum = random.nextInt(5) + 1;
            justSleep(650L * randomNum);
        }

        out.println(HARD_ERROR.getMsg() + "Function G failed " +
                MAX_ATTEMPTS + " times to compute a result");
    }


//        int attempts = 0;
//        while (attempts < MAX_ATTEMPTS) {
//            int number = random.nextInt(10) + 1;
//            int random = AbstractClient.random.nextInt(10) + 1;
//
//            if (random <= 5) {
//                ExecutorService es = Executors.newSingleThreadExecutor();
//                Future<Optional<Integer>> future = es.submit(() -> IntOps.trialG(Integer.parseInt(parameter)));
//
//                try {
//                    Optional<Integer> optional = future.get(5, TimeUnit.SECONDS);
//
//                    optional.ifPresent(result -> out.println(RESULT_G.getMsg() + result));
//
//                } catch (InterruptedException | ExecutionException | TimeoutException e) {
//                    out.println(HARD_ERROR.getMsg() + "Function G is not defined on argument " + parameter);
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
//        out.println(HARD_ERROR.getMsg() + "Function G failed " +
//                MAX_ATTEMPTS + " times to compute a result");
//    }
}