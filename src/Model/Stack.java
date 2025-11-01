package Model;


public class Stack {
	private Object element;
    private Stack next;

    public Stack(Object element) {
        this.element = element; 
        this.next = null;
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public Stack getNext() {
        return next;
    }

    public void setNext(Stack next) {
        this.next = next;
    }
}