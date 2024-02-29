import java.util.NoSuchElementException;


/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index inputted for this method is invalid because the index is"
                    + " a negative number.");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("The index inputted for this method is invalid because its"
                    + " greater than the current amount of elements in the list.");
        } else if (data == null) {
            throw new IllegalArgumentException("The data that was used for this method is null");
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else if (index >= (size / 2)) {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = size - 1; i > index; i--) {
                curr = curr.getPrevious();
            }
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            newNode.setPrevious(curr.getPrevious());
            newNode.setNext(curr);
            curr.getPrevious().setNext(newNode);
            curr.setPrevious(newNode);
            size++;
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            newNode.setPrevious(curr.getPrevious());
            newNode.setNext(curr);

            curr.getPrevious().setNext(newNode);
            curr.setPrevious(newNode);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that is trying to be placed in the front of the Doubly "
                    + "Linked List is null or invalid");
        } else if (isEmpty()) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, null, head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that is trying to be placed at the back of the Doubly "
                    + "Linked List is null or invalid");
        } else if (isEmpty()) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, tail, null);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, tail, null);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {

        if (index < 0) {
            throw new IndexOutOfBoundsException("The index inputted is a negative number.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index is greater than the elements in the list.");
        } else if (isEmpty()) {
            return null;
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            DoublyLinkedListNode<T> curr;
            if ((size / 2) >= index) {
                curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
                (curr.getNext()).setPrevious(curr.getPrevious());
                (curr.getPrevious()).setNext(curr.getNext());
            } else {
                curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
                (curr.getPrevious()).setNext(curr.getNext());
                (curr.getNext()).setPrevious(curr.getPrevious());
            }
            size--;
            return curr.getData();

        }

    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("The Doubly Linked List is empty, so there"
                    + "is nothing that can be removed");
        }
        DoublyLinkedListNode<T> returnNode = head;
        if (size == 1) {
            clear();
        } else {
            head = head.getNext();
            size--;
            head.setPrevious(null);
        }
        return (returnNode.getData());
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("The Doubly Linked List is empty, so there"
                    + "is nothing that can be removed");
        } else {
            DoublyLinkedListNode<T> returnNode = tail;
            if (size == 1) {
                clear();
            } else {
                tail = tail.getPrevious();
                size--;
                tail.setNext(null);
            }
            return (returnNode.getData());
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Since the index is either negative"
                    + ", the index cannot be accessed.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Since the index is greater than"
                    + "the size of the Linked List, the index cannot be accessed.");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> curr;
            if ((size / 2) > index) {
                curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
            } else {
                curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
            }
            return curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that is trying to be found in the Doubly Linked List"
                    + " is equal to null, thus invalid ");
        } else if (isEmpty()) {
            throw new NoSuchElementException("The list empty and no last occurrences could be removed");
        } else if (size == 1) {
            if (head.getData().equals(data)) {
                DoublyLinkedListNode<T> returnNode = head;
                clear();
                return returnNode.getData();
            } else {
                throw new NoSuchElementException("The element doesn't exist in the Linked List.");
            }
        } else {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = size - 1; i > -1; i--) {
                if (curr.getData().equals(data)) {
                    if (i == size - 1) {
                        return removeFromBack();
                    } else if (i == 0) {
                        return removeFromFront();
                    } else {
                        (curr.getNext()).setPrevious(curr.getPrevious());
                        (curr.getPrevious()).setNext(curr.getNext());
                        size--;
                        return curr.getData();
                    }
                }
                curr = curr.getPrevious();
            }
            throw new NoSuchElementException("The element " + data + " doesn't exist in the Doubly Linked List.");
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        T[] returnArray = (T[]) new Object[size];
        if (!isEmpty()) {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < size; i++) {
                returnArray[i] = curr.getData();
                curr = curr.getNext();
            }
        }
        return returnArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
