import java.util.ArrayList;
import java.util.List;

public class MinumumHeap {
    List<Node> list = new ArrayList<>();

    public MinumumHeap() {}

    private void switchPlaces(int index1, int index2) {
        Node temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    public void add(Node data) {
        if (list.contains(data)) return;

        list.add(data);

        if (list.size() == 1) return;

        ascend(list.size() - 1);
    }

    public Node remove() {
        if (list.isEmpty()){
            throw new RuntimeException("Tentando remover de heap vazia");
        }

        Node pop = list.get(0);
        switchPlaces(0, list.size() - 1);
        list.remove(list.size() - 1);
        descend(0, list.size());

        return pop;
    }

    private void ascend(int index) {
        int father = (index - 1) / 2;

        if (index > 0 && list.get(index).getFrequency() < list.get(father).getFrequency()) {
            switchPlaces(index, father);
            ascend(father);
        }
    }

    private void descend(int index, int size) {
        int son = index * 2 + 1;

        if (son < size) {
            if (son < size - 1 && list.get(son).getFrequency() > list.get(son + 1).getFrequency()) {
                son++;
            }

            if (list.get(index).getFrequency() > list.get(son).getFrequency()) {
                switchPlaces(index, son);
                descend(son, size);
            }
        }
    }

    public int size() {
        return list.size();
    }

}
