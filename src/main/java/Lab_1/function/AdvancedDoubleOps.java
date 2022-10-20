package Lab_1.function;

import os.lab1.compfuncs.advanced.DoubleOps;

import java.util.Optional;
import java.util.Random;

public class AdvancedDoubleOps {
    private static final Random random = new Random();

    public static Optional<Optional<Double>> trialF(int param) throws InterruptedException {
        int randVal = random.nextInt(10);
        if (randVal < 5) {
            // soft fail
            return Optional.empty();
        }
        return DoubleOps.trialF(param);
    }

    public static Optional<Optional<Double>> trialG(int param) throws InterruptedException {
        int randVal = random.nextInt(10);
        if (randVal < 5) {
            // soft fail
            return Optional.empty();
        }
        return DoubleOps.trialG(param);
    }
}
