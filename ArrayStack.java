/* Description: Array-based bounded Stack ADT (LIFO). Holds integers in a fixed-size
   array (maximum capacity of 10) and provides push, pop, peek, isEmpty, isFull,
   size and clear. Contains NO GUI code.

   Programmed by: <your name> <course> <cn> <subject>

   Last Modified: <date of latest revision>

   Version: 1.0

   Acknowledgements: <list of sites and/or generative AI used, with the prompt>
*/

import java.util.Arrays;

public class ArrayStack {

    public static final int MAX_CAPACITY = 10;

    private final int[] data;   // internal storage - private, GUI cannot touch it
    private int top;            // index of the top element, -1 when empty

    public ArrayStack() {
        this(MAX_CAPACITY);
    }

    public ArrayStack(int capacity) {
        if (capacity < 1 || capacity > MAX_CAPACITY) {
            throw new IllegalArgumentException("Capacity must be between 1 and " + MAX_CAPACITY);
        }
        data = new int[capacity];
        top = -1;
    }

    /** Adds a value on top of the stack. Throws if the stack is full. */
    public void push(int value) {
        if (isFull()) {
            throw new IllegalStateException("STACK OVERFLOW: Cannot push, stack is full");
        }
        data[++top] = value;
    }

    /** Removes and returns the top value. Throws if the stack is empty. */
    public int pop() {
        if (isEmpty()) {
            throw new IllegalStateException("STACK UNDERFLOW: Cannot pop, stack is empty");
        }
        return data[top--];
    }

    /** Returns the top value WITHOUT removing it. Throws if the stack is empty. */
    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("STACK EMPTY: Nothing to peek, stack is empty");
        }
        return data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == data.length - 1;
    }

    public int size() {
        return top + 1;
    }

    public int capacity() {
        return data.length;
    }

    /** Resets the stack to empty. */
    public void clear() {
        top = -1;
    }

    /**
     * Returns a COPY of the current contents, bottom to top.
     * The GUI uses this read-only snapshot for drawing, so it never
     * touches the internal array.
     */
    public int[] toArray() {
        return Arrays.copyOf(data, size());
    }
}
