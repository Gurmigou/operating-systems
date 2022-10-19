package Lab_1.util;

public enum CommunicationCommand {
    SOFT_ERROR("SOFT_ERROR"),
    HARD_ERROR("HARD_ERROR"),
    RESULT_F("RESULT_F"),
    RESULT_G("RESULT_G");

    private final String msg;

    CommunicationCommand(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
