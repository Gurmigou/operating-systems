package Lab_1.function;

import java.util.Optional;
import java.util.Random;

public class AdvancedIntOps {
    private static final Random random = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < 105; i++) {
            System.out.println(trialG(i));
        }
    }

    public static Optional<Optional<Integer>> trialF(int param) {
        int randVal = random.nextInt(10);
        if (randVal < 5) {
            // soft fail
            return Optional.empty();
        }

        if (isUndefinedFuncF(param)) {
            // hard fail
            return Optional.of(Optional.empty());
        } else {
            // result
            return Optional.of(Optional.of(funcF(param)));
        }
    }

    private static boolean isUndefinedFuncF(int param) {
        return param < 0 || param == 1 || param == 3 || param == 9 || param > 100;
    }

    private static int funcF(int param) {
        return param * param + 1;
    }

    public static Optional<Optional<Integer>> trialG(int param) {
        int randVal = random.nextInt(10);
        if (randVal < 5) {
            // soft fail
            return Optional.empty();
        }

        if (isUndefinedFuncG(param)) {
            // hard fail
            return Optional.of(Optional.empty());
        } else {
            // result
            return Optional.of(Optional.of(funcG(param)));
        }
    }

    private static boolean isUndefinedFuncG(int param) {
        return param < 0 || param == 2 || param == 3 || param == 15 || param == 20;
    }

    private static int funcG(int param) {
        return (param * 2 + 3) * 2 - 1;
    }
}
