package uptc.edu.co.logic;

import java.util.Deque;
import java.util.LinkedList;

public class StackP<T> {
    private Deque<T> items;

    /**
     * Constructor para inicializar la pila.
     */
    public StackP() {
        this.items = new LinkedList<>();
    }

    /**
     * Verifica si la pila está vacía.
     * 
     * @return true si la pila está vacía, false de lo contrario.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Inserta un elemento en la parte superior de la pila.
     * 
     * @param item el elemento a insertar.
     */
    public void push(T item) {
        items.push(item);
    }

    /**
     * Elimina y devuelve el elemento en la parte superior de la pila.
     * 
     * @return el elemento eliminado de la parte superior de la pila.
     * @throws IndexOutOfBoundsException si la pila está vacía.
     */
    public T pop() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Pop from empty stack");
        }
        return items.pop();
    }

    /**
     * Devuelve el elemento en la parte superior de la pila sin eliminarlo.
     * 
     * @return el elemento en la parte superior de la pila.
     * @throws IndexOutOfBoundsException si la pila está vacía.
     */
    public T peek() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Peek from empty stack");
        }
        return items.peek();
    }

    /**
     * Devuelve el número de elementos en la pila.
     * 
     * @return el tamaño de la pila.
     */
    public int size() {
        return items.size();
    }
}
