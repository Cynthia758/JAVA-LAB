import java.util.Arrays;
import java.util.EmptyStackException;

class Stack<T> {
    private Object[] elements;
    private int size;

    public Stack(int capacity) {
        elements = new Object[capacity];
    }

    public void push(T value) {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2 + 1);
        }
        elements[size++] = value;
    }
    @SuppressWarnings("unchecked")
    public T pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        T value = (T) elements[--size];
        elements[size] = null;
        return value;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
public class GenericStack {
    public static void main(String[] args) {
        Stack<Integer> numbers = new Stack<>(2);
        numbers.push(10);
        numbers.push(20);
        System.out.println("Popped number: " + numbers.pop());

        Stack<String> words = new Stack<>(2);
        words.push("Java");
        words.push("Generics");
        System.out.println("Popped word: " + words.pop());

        System.out.println("Numbers stack empty: " + numbers.isEmpty());
        System.out.println("Words stack size: " + words.size());

        try {
            words.pop();
            words.pop();
        } catch (EmptyStackException e) {
            System.out.println("Stack is empty");
        }
    }
}