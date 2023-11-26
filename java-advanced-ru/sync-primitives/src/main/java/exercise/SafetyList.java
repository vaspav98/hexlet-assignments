package exercise;

class SafetyList {
    // BEGIN
    private int[] array = new int[1];
    private int size = 0;

    public synchronized void add(int num) {
        if (array.length == size) {
            int[] oldArr = array;
            array = new int[size * 2];
            for (int i = 0; i < oldArr.length; i++) {
                array[i] = oldArr[i];
            }
        }
        array[size++] = num;
    }

    public int get(int index) {
        return array[index];
    }

    public int getSize() {
        return this.size;
    }
    // END
}
