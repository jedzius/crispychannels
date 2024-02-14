package usd.jedzius.crispychannels.protocol.authorization;


public class ServerAuthorization {
    public static boolean valid(String codeServer, String codeClient) {
        return codeServer.equals(codeClient);
    }
}
