class NodeStack {
    int x, y;
    NodeStack next, prev;

    public NodeStack() {
        
    }

    public NodeStack(int x, int y) {
        this.x = x;
        this.y = y;
        this.next = null;
        this.prev = null;
    }
}

public class StackTD {
    NodeStack head, tail;
    int size;

    public StackTD() {
        head = tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int x() {
        return tail.x;
    }

    public int y() {
        return tail.y;
    }

    public void push(int x, int y) {
        NodeStack baru = new NodeStack(x, y);

        if (isEmpty()) {
            head = tail = baru;
        } else {
            baru.prev = tail;
            tail.next = baru;
            tail = baru;
        }
        size++;
    }

    public void pop() {
        if (!isEmpty())  {
            if (head == tail) {
                head = tail = null;
            } else {
                tail = tail.prev;
                tail.next = null;
            }
            size--;
        }
    }
}