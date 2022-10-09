package Lab_1.client;

import java.io.IOException;

public class ClientF extends AbstractClient  {
    public ClientF() throws IOException {
        super();
    }

    public static void main(String[] args) throws IOException {
        ClientF clientF = new ClientF();
        clientF.startClient();
    }

    @Override
    public String getClientName() {
        return "F";
    }
}
