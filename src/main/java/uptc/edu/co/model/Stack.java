package uptc.edu.co.model;

import java.util.ArrayList;

public class Stack<T> {
    private ArrayList<T> items;

    public Stack() {
        this.items = new ArrayList<>();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void push(T item) {
        items.add(item);
    }

    public T pop() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Pop from empty stack");
        }
        return items.remove(items.size() - 1);
    }

    public T peek() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Peek from empty stack");
        }
        return items.get(items.size() - 1);
    }

    public int size() {
        return items.size();
    }
}
