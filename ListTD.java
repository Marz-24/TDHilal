class NodeList {
    String data;
    NodeList next;

    public NodeList() {
        
    }

    public NodeList(String data) {
        this.data = data;
        this.next = null;
    }
}

public class ListTD {
    NodeList head, tail;
    int size;

    public ListTD() {
        head = tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addLast(String data) {
        NodeList baru = new NodeList(data);

        if (isEmpty()) {
            head = tail = baru;
        } else {
            tail.next = baru;
            tail = baru;
        }
        size++;
    }
}
