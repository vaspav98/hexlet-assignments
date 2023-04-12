package exercise;
import exercise.connections.Connection;
import exercise.connections.Disconnected;
import java.util.List;
import java.util.ArrayList;

// BEGIN
public class TcpConnection {

    private String ip;
    private int port;
    private Connection state;

    private List<String> buffer = new ArrayList<>();

    public TcpConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.state = new Disconnected(this);
    }

    public void setState(Connection newState) {
        this.state = newState;
    }

    public void addDataToBuffer(String data) {
        this.buffer.add(data);
    }

    public String getCurrentState() {
        return this.state.getCurrentState();
    }

    public void connect() {
        this.state.connect();
    }

    public void disconnect() {
        this.state.disconnect();
    }

    public void write(String data) {
        this.state.write(data);
    }
}
// END
