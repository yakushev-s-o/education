package thinkingInJava.c6_access_control;

class Connection {
    private Connection() {
        System.out.println("Connection()");
    }

    public static Connection createConnection() {
        return new Connection();
    }
}

public class E8_ConnectionManager {
    private static int count = 0;
    private static final int CAPACITY = 4;
    private static final Connection[] connections = new Connection[CAPACITY];

    public static Connection getConnection() {
        if (count == 0) {
            for (int i = 0; i < CAPACITY; i++) {
                connections[i] = Connection.createConnection();
            }
        }

        if (count < CAPACITY) {
            return connections[count++];
        }
        System.out.println("null");
        return null;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 256; i++){
            if (getConnection() == null) {
                return;
            }
            getConnection();
        }
    }
}