package Lab_1.client;

import java.io.IOException;

public class ClientG extends AbstractClient {
    public ClientG() throws IOException {
        super();
    }

    public static void main(String[] args) throws IOException {
        ClientG clientG = new ClientG();
        clientG.startClient();
    }

    @Override
    public String getClientName() {
        return "G";
    }
}