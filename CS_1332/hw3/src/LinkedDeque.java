import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
 *
 * @author Jadon Co
 * @version 1.0
 * @userid jadonco101
 * @GTID 903725118
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data attempting to be inputted at the front of the queue is null");
        } else if (size == 0) {
            LinkedNode<T> singleNode = new LinkedNode<>(data);
            head = singleNode;
            tail = singleNode;
            size++;
        } else {
            LinkedNode<T> front = new LinkedNode<>(data, null, head);
            head.setPrevious(front);
            head = front;
            size++;
        }
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data attempting to be inputted at the back of the queue is null");
        } else if (head == null) {
            LinkedNode<T> singleNode = new LinkedNode<>(data, null, null);
            head = singleNode;
            tail = singleNode;
            size++;
        } else {
            LinkedNode<T> back = new LinkedNode<>(data, tail, null);
            tail.setNext(back);
            tail = back;
            size++;
        }
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty, meaning that there is nothing in the collection.");
        } else if (size == 1) {
            LinkedNode<T> oldFront = new LinkedNode<>(head.getData());
            head = null;
            tail = null;
            size--;
            return oldFront.getData();
        } else {
            LinkedNode<T> oldFront = new LinkedNode<>(head.getData());
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return oldFront.getData();
        }
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty, meaning that there is nothing in the collection.");
        } else if (size == 1) {
            LinkedNode<T> oldBack = new LinkedNode<>(tail.getData());
            head = null;
            tail = null;
            size--;
            return oldBack.getData();
        } else {
            LinkedNode<T> oldBack = new LinkedNode<>(tail.getData());
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return oldBack.getData();
        }
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty, meaning that there is nothing in the collection.");
        }
        return head.getData();
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty, meaning that there is nothing in the collection.");
        }
        return tail.getData();
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
