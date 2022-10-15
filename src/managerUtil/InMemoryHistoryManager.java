package managerUtil;

import templateTask.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private static CustomLinkedList<Task> dataHistory = new CustomLinkedList<>();
    private static HashMap<Integer, Task> dataMap = new HashMap<>();

    @Override
    public void add(Task task){
        if (dataMap.containsKey(task.getId())){
            remove(task.getId());
        }
        dataMap.put(task.getId(), task);
        dataHistory.linkLast(task);
    }

    @Override
    public List<Task> getHistory(){
        return dataHistory.getTasks();
    }

    @Override
    public void remove(int id){
        if (dataMap.containsKey(id)){
            dataHistory.remove(dataMap.get(id));
        }
    }

    static class CustomLinkedList<T>{
        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        public void linkLast (T element) {
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(tail, element, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else
                oldTail.next = newNode;
            size++;
        }

        public List<T> getTasks() {
            List<T> result = new ArrayList<>();
            for (Node<T> x = head; x != null; x = x.next)
                result.add(x.data);
            return result;
        }

        public void remove(T e){
            for (Node<T> x = head; x != null; x = x.next) {
                if (e.equals(x.data)) {
                    removeNode(x);
                }
            }
        }

        private void removeNode(Node<T> e){
            final Node<T> next = e.next;
            final Node<T> prev = e.prev;

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
                e.prev = null;
            }

            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
                e.next = null;
            }

            e.data = null;
            size--;
        }
    }
}


