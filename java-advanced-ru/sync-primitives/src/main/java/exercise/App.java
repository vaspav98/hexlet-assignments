package exercise;

class App {

    public static void main(String[] args) {
        // BEGIN
        SafetyList safetyList = new SafetyList();
        Thread thread1 = new ListThread(safetyList);
        Thread thread2 = new ListThread(safetyList);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(safetyList.getSize());
        // END
    }
}

