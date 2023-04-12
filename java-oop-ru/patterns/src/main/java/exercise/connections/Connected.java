package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Connected implements Connection {

    private TcpConnection tcpConnection;
    private final String stateName = "connected";

    public Connected(TcpConnection tcpConnection) {
        this.tcpConnection = tcpConnection;
    }

    @Override
    public String getCurrentState() {
        return this.stateName;
    }

    @Override
    public void connect() {
        System.out.println("Error! The connection is already there.");
    }

    @Override
    public void disconnect() {
        this.tcpConnection.setState(new Disconnected(this.tcpConnection));
    }

    @Override
    public void write(String data) {
        this.tcpConnection.addDataToBuffer(data);
    }
}
// END
