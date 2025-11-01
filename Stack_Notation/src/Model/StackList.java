package Model;

public class StackList {
	private Stack top; // Top of the stack

    public StackList() {
        top = null;
    }

    // Check if stack is empty
    public boolean isEmpty() {
        return top == null;
    }

    // Push an element to the stack
    public void push(Object element) {
        Stack newNode = new Stack(element);
        newNode.setNext(top);
        top = newNode;
    }

    // Pop the top element from the stack
    public Object pop() throws Exception {
        if (isEmpty()) {
            throw new Exception("Stack is empty, cannot pop.");
        }
        Object result = top.getElement();
        top = top.getNext();
        return result;
    }

    // Peek at the top element without removing it
    public Object peek() throws Exception {
        if (isEmpty()) {
            throw new Exception("Stack is empty, cannot peek.");
        }
        return top.getElement();
    }

    // Clear the entire stack
    public void clear() {
        Stack temp;
        while (!isEmpty()) {
            temp = top;
            top = top.getNext();
            temp.setNext(null);
        }
    }

    // Optional: display stack contents as string (top → bottom)
    public String toStringStack() {
        StringBuilder sb = new StringBuilder();
        Stack current = top;
        while (current != null) {
            sb.append(current.getElement()).append(" ");
            current = current.getNext();
        }
        return sb.toString().trim();
    }
}

