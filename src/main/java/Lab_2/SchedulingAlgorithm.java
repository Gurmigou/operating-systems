package Lab_2;// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

    public static Results Run(int runtime, Vector<sProcess> processVector, Results result, int quantum) {
        // counter of time
        int comptime = 0;

        // a number of completed processes
        int completed = 0;

        String resultsFile = "src\\main\\java\\Lab_2\\Summary-Processes";
        result.schedulingType = "Interactive (Preemptive)";
        result.schedulingName = "Round robin";

        Queue<sProcess> scheduleQueue = new LinkedList<>(processVector);

        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(resultsFile));

            sProcess curProcess = scheduleQueue.remove();
            logRegisterProcess(out, curProcess);

            while (comptime < runtime) {
                if (checkProcessCompleted(curProcess)) {
                    logCompletedProcess(out, curProcess);
                    completed++;

                    if (completed == processVector.size()) {
                        out.close();

                        // time of completion
                        result.compuTime = comptime;
                        return result;
                    }

                    curProcess = scheduleQueue.remove();
                    logRegisterProcess(out, curProcess);
                }
                if (checkQuantumTimeIsOver(quantum, curProcess)) {
                    logQuantumIsOver(out, curProcess);

                    curProcess.quantumnext = 0;
                    curProcess.numblocked++;

                    scheduleQueue.add(curProcess);

                    curProcess = scheduleQueue.remove();
                    logRegisterProcess(out, curProcess);
                }
                if (checkShouldBlock(curProcess)) {
                    logProcessBlocked(out, curProcess);

                    curProcess.quantumnext = 0;
                    curProcess.numblocked++;
                    curProcess.ionext = 0;

                    scheduleQueue.add(curProcess);

                    curProcess = scheduleQueue.remove();
                    logRegisterProcess(out, curProcess);
                }

                comptime++;

                curProcess.cpudone++;
                curProcess.quantumnext++;
                curProcess.ionext++;
            }

            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        result.compuTime = comptime;
        return result;
    }

    private static void logRegisterProcess(PrintWriter out, sProcess process) {
        String log = String.format("Process: %d registered: (%d %d %d)",
                process.index, process.cputime, process.ioblocking, process.cpudone);
        out.println(log);
    }

    private static void logCompletedProcess(PrintWriter out, sProcess process) {
        String log = String.format("Process: %d completed: (%d %d %d)",
                process.index, process.cputime, process.ioblocking, process.cpudone);
        out.println(log);
    }

    private static void logQuantumIsOver(PrintWriter out, sProcess process) {
        String log = String.format("Process: %d used quantum time: (%d %d %d)",
                process.index, process.cputime, process.ioblocking, process.cpudone);
        out.println(log);
    }

    private static void logProcessBlocked(PrintWriter out, sProcess process) {
        String log = String.format("Process: %d I/O blocked: (%d %d %d)",
                process.index, process.cputime, process.ioblocking, process.cpudone);
        out.println(log);
    }

    private static boolean checkProcessCompleted(sProcess process) {
        return process.cputime == process.cpudone;
    }

    private static boolean checkQuantumTimeIsOver(int quantum, sProcess process) {
        return process.quantumnext == quantum;
    }

    private static boolean checkShouldBlock(sProcess process) {
        return process.ioblocking == process.ionext;
    }
}
