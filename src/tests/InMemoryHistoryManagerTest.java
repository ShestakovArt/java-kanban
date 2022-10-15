package tests;

import managerUtil.InMemoryHistoryManager;
import managerUtil.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;
import tests.utilForTest.TaskManagerTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static templateTask.Task.setCounter;

class InMemoryHistoryManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    private Task taskOne;
    private Epic epicOne;
    private Subtask subTaskOne;
    InMemoryHistoryManager testHistoryManager;

    @BeforeEach
    void setUp() {
        taskManagerTest = new InMemoryTaskManager();
        testHistoryManager = new InMemoryHistoryManager();
        taskOne = new Task("Задача 1", "Описание з1");
        epicOne = new Epic("Эпик 1", "Описание эп1");
        subTaskOne = new Subtask("Подзадача 1 эп 1","Описание п1 эп1");

        taskManagerTest.addTask(taskOne);
        taskManagerTest.addTask(epicOne);
        taskManagerTest.addTask(epicOne.getId(), subTaskOne);
    }

    @AfterEach
    void tearDown() {
        List<Task> taskList = testHistoryManager.getHistory();
        for(Task task : taskList){
            testHistoryManager.remove(task.getId());
        }
        setCounter(1);
    }

    @Test
    void add() {
        int originalSizeHistoryManager = testHistoryManager.getHistory().size();
        testHistoryManager.add(taskOne);
        assertEquals(originalSizeHistoryManager+1, testHistoryManager.getHistory().size(),
                String.format("Размер менеджера историй не изменился"));
    }

    @Test
    void getHistory() {
        int originalSizeHistoryManager = testHistoryManager.getHistory().size();
        testHistoryManager.add(taskOne);
        testHistoryManager.add(epicOne);
        testHistoryManager.add(subTaskOne);
        assertEquals(originalSizeHistoryManager+3, testHistoryManager.getHistory().size(),
                String.format("Размер менеджера историй не изменился"));
    }

    @Test
    void remove() {
        int originalSizeHistoryManager = testHistoryManager.getHistory().size();
        testHistoryManager.add(epicOne);
        assertEquals(originalSizeHistoryManager+1, testHistoryManager.getHistory().size(),
                String.format("Размер менеджера историй не изменился"));
        testHistoryManager.remove(epicOne.getId());
        assertEquals(originalSizeHistoryManager, testHistoryManager.getHistory().size(),
                String.format("Размер менеджера историй не изменился"));
    }

    @Test
    void isEmptyHistoryTest(){
        assertEquals(0, testHistoryManager.getHistory().size(), "Список менеджера историй не пустой");
    }

    @Test
    void addTwoViewTaskOneTest(){
        int originalSizeHistoryManager = testHistoryManager.getHistory().size();
        testHistoryManager.add(taskOne);
        testHistoryManager.add(taskOne);
        assertEquals(originalSizeHistoryManager+1, testHistoryManager.getHistory().size(),
                String.format("Размер менеджера не корректный"));
    }
}