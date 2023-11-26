package exercise;

// BEGIN
public class ListThread extends Thread {

    private SafetyList list;

    public ListThread(SafetyList list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 1000; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(i);
        }
    }
}
// END
