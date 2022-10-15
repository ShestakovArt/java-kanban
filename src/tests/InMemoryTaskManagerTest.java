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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static templateTask.Task.setCounter;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    private Task taskOne;
    private Epic epicOne;
    private Subtask subTaskOne;

    @BeforeEach
    void setUp() {
        taskManagerTest = new InMemoryTaskManager();
        taskOne = new Task("Задача 1", "Описание з1");
        epicOne = new Epic("Эпик 1", "Описание эп1");
        subTaskOne = new Subtask("Подзадача 1 эп 1","Описание п1 эп1");

        taskManagerTest.addTask(taskOne);
        taskManagerTest.addTask(epicOne);
        taskManagerTest.addTask(epicOne.getId(), subTaskOne);
    }

    @AfterEach
    void tearDown() {
        InMemoryHistoryManager testHistoryManager = new InMemoryHistoryManager();
        List<Task> taskList = testHistoryManager.getHistory();
        for(Task task : taskList){
            testHistoryManager.remove(task.getId());
        }
        setCounter(1);
    }

    @Test
    void setStartTimeForTackTest() {
        LocalDateTime timeNow = LocalDateTime.now();
        Long minutes =50L;
        LocalDateTime timeForCheck = timeNow.plusMinutes(minutes);
        taskManagerTest.setStartTimeForTack(taskOne, timeNow, minutes);
        assertEquals(taskManagerTest.getTask(taskOne.getId()).getEndTime(), timeForCheck,
                String.format("Значение времени окончания задачи '%s' не совпадает c значением '%s'", taskOne.getEndTime(), timeForCheck));
    }

    @Test
    void getPrioritizedTasksTest() {
        LocalDateTime timeNow = LocalDateTime.now();
        Long minutes =50L;
        subTaskOne.setStartTime(timeNow);
        subTaskOne.setDuration(minutes);
        List<Task> listPrioritized = taskManagerTest.getPrioritizedTasks();
        assertEquals(listPrioritized.get(0), subTaskOne,
                String.format("Первый элемент списка приоритетных задач '%s' не соответствует '%s'", listPrioritized.get(0), subTaskOne));
    }

    @Test
    void addTaskTest() {
        int originalSize = taskManagerTest.getDataTask().size();
        Task taskTwo = new Task("Задача 2", "Описание з2");
        taskManagerTest.addTask(taskTwo);
        assertEquals(taskManagerTest.getDataTask().size(), originalSize+1,
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'", taskManagerTest.getDataTask().size(), originalSize+1));

        assertEquals(taskTwo, taskManagerTest.getDataTask().get(taskTwo.getId()),
                String.format("Элемент списка задач '%s' не соответствует '%s'", taskManagerTest.getDataTask().get(taskTwo.getId()), taskTwo));
    }

    @Test
    void addTaskEpicTest() {
        int originalSize = taskManagerTest.getDataTask().size();
        int originalSizeEpicData = taskManagerTest.getDataEpic().size();
        Epic epicTwo = new Epic("Эпик 2", "Описание э2");
        taskManagerTest.addTask(epicTwo);
        assertEquals(taskManagerTest.getDataTask().size(), originalSize+1,
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'", taskManagerTest.getDataTask().size(), originalSize+1));

        assertEquals(taskManagerTest.getDataTask().get(epicTwo.getId()), epicTwo,
                String.format("Элемент списка задач '%s' не соответствует '%s'", taskManagerTest.getDataTask().get(epicTwo.getId()), epicTwo));

        assertEquals(taskManagerTest.getDataEpic().size(), originalSizeEpicData+1,
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'", taskManagerTest.getDataTask().size(), originalSize+1));

        assertEquals(taskManagerTest.getDataEpic().get(epicTwo.getId()), epicTwo,
                String.format("Элемент списка задач '%s' не соответствует '%s'", taskManagerTest.getDataEpic().get(epicTwo.getId()), epicTwo));
    }

    @Test
    void addTaskSubtaskTest() {
        int originalSize = taskManagerTest.getDataTask().size();
        int originalSizeEpicOneData = taskManagerTest.getDataEpic().get(epicOne.getId()).getDataSubtask().size();
        Subtask subtaskTwo = new Subtask("Эпик 2", "Описание э2");
        taskManagerTest.addTask(epicOne.getId(), subtaskTwo);
        assertEquals(taskManagerTest.getDataTask().size(), originalSize+1,
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'", taskManagerTest.getDataTask().size(), originalSize+1));

        assertEquals(taskManagerTest.getDataTask().get(subtaskTwo.getId()), subtaskTwo,
                String.format("Элемент списка задач '%s' не соответствует '%s'",
                        taskManagerTest.getDataTask().get(subtaskTwo.getId()), subtaskTwo));

        assertEquals(taskManagerTest.getDataEpic().get(epicOne.getId()).getDataSubtask().size(), originalSizeEpicOneData+1,
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataEpic().get(epicOne.getId()).getDataSubtask().size(), originalSizeEpicOneData+1));

        assertEquals(taskManagerTest.getDataEpic().get(epicOne.getId()).getDataSubtask().get(subtaskTwo.getId()), subtaskTwo,
                String.format("Элемент списка задач '%s' не соответствует '%s'",
                        taskManagerTest.getDataEpic().get(epicOne.getId()).getDataSubtask().get(subtaskTwo.getId()), subtaskTwo));
    }

    @Test
    void getTaskTest() {
        assertEquals(taskManagerTest.getTask(taskOne.getId()), taskOne,
                String.format("Элемент '%s' не соответствует '%s'", taskManagerTest.getTask(taskOne.getId()), taskOne));
    }

    @Test
    void deleteTaskTest() {
        int originalSize = taskManagerTest.getDataTask().size();
        taskManagerTest.deleteTask(taskOne.getId());
        assertEquals(originalSize-1,taskManagerTest.getDataTask().size(),
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataTask().size(), originalSize-1));
        assertThrows(NullPointerException.class,
                () -> taskManagerTest.getTask(taskOne.getId()),
                String.format("Элемента '%s' есть в списке", taskOne));
    }

    @Test
    void deleteAllTaskTest() {
        taskManagerTest.deleteAllTask();
        assertEquals(0, taskManagerTest.getDataTask().size(),
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataTask().size(), 0));
    }

    @Test
    void deleteEpicTest() {
        Epic epicTwo = new Epic("Эпик 2", "Описание э2");
        taskManagerTest.addTask(epicTwo);
        int originalSize = taskManagerTest.getDataTask().size();
        int countEpic = taskManagerTest.getDataEpic().size();
        taskManagerTest.deleteEpic(epicTwo.getId());
        System.out.println(taskManagerTest.getDataTask());
        assertEquals(originalSize - 1, taskManagerTest.getDataTask().size(),
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataTask().size(), originalSize-1));
        assertEquals(countEpic - 1, taskManagerTest.getDataEpic().size(),
                String.format("Количество элементов списка эпиков '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataEpic().size(), countEpic-1));
    }

    @Test
    void deleteAllEpicTest() {
        taskManagerTest.deleteAllSubtask(epicOne.getId());
        int originalSize = taskManagerTest.getDataTask().size();
        int countEpic = taskManagerTest.getDataEpic().size();
        taskManagerTest.deleteAllEpic();
        assertEquals(originalSize - 1, taskManagerTest.getDataTask().size(),
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataTask().size(), originalSize-1));
        assertEquals(countEpic - 1, taskManagerTest.getDataEpic().size(),
                String.format("Количество элементов списка эпиков '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataEpic().size(), countEpic-1));
    }

    @Test
    void deleteSubtaskTest() {
        int originalSize = taskManagerTest.getDataTask().size();
        int countSubtaskEpic = taskManagerTest.getDataEpic().get(epicOne.getId()).getDataSubtask().size();
        taskManagerTest.deleteSubtask(epicOne.getId(),subTaskOne.getId());
        assertEquals(originalSize - 1, taskManagerTest.getDataTask().size(),
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataTask().size(), originalSize-1));
        assertEquals(countSubtaskEpic - 1, taskManagerTest.getDataEpic().get(epicOne.getId()).getDataSubtask().size(),
                String.format("Количество элементов списка эпиков '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataEpic().get(epicOne.getId()).getDataSubtask().size(), countSubtaskEpic-1));
    }

    @Test
    void deleteAllSubtaskTest() {
        taskManagerTest.deleteAllSubtask(epicOne.getId());
        assertEquals(0, taskManagerTest.getDataEpic().get(epicOne.getId()).getDataSubtask().size(),
                String.format("Количество элементов списка эпиков '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataEpic().get(epicOne.getId()).getDataSubtask().size(), 0));
    }

    @Test
    void getDataTaskTest() {
        int originalSizeDataTask = taskManagerTest.getDataTask().size();
        Task taskTwo = new Task("Задача 2", "Описание з2");
        taskManagerTest.addTask(taskTwo);
        assertNotEquals(0, originalSizeDataTask, "Количество элементов списка задач равно 0");
        assertEquals(originalSizeDataTask+1, taskManagerTest.getDataTask().size(),
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataTask().size(), originalSizeDataTask+1));
        assertEquals(taskTwo, taskManagerTest.getDataTask().get(taskTwo.getId()),
                String.format("Элемент списка задач '%s' не соответствует '%s'", taskManagerTest.getDataTask().get(taskTwo.getId()), taskTwo));
    }

    @Test
    void getDataEpicTest() {
        int originalSizeDataTask = taskManagerTest.getDataEpic().size();
        Epic epicTwo = new Epic("Эпик 2", "Описание э2");
        taskManagerTest.addTask(epicTwo);
        assertNotEquals(0, originalSizeDataTask, "Количество элементов списка задач эпика равно 0");
        assertEquals(originalSizeDataTask+1, taskManagerTest.getDataEpic().size(),
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getDataEpic().size(), originalSizeDataTask+1));
        assertEquals(epicTwo, taskManagerTest.getDataEpic().get(epicTwo.getId()),
                String.format("Элемент списка задач '%s' не соответствует '%s'", taskManagerTest.getDataEpic().get(epicTwo.getId()), epicTwo));
    }

    @Test
    void getSubtaskEpicTest() {
        int originalSizeDataTask = taskManagerTest.getSubtaskEpic(epicOne.getId()).size();
        Subtask subtaskTwo = new Subtask("Подзадача 2", "Описание п2э1");
        taskManagerTest.addTask(epicOne.getId(), subtaskTwo);
        assertNotEquals(0, originalSizeDataTask, "Количество элементов списка подзадач эпика равно 0");
        assertEquals(originalSizeDataTask+1, taskManagerTest.getSubtaskEpic(epicOne.getId()).size(),
                String.format("Количество элементов списка задач '%d' не соответствует расчетному '%d'",
                        taskManagerTest.getSubtaskEpic(epicOne.getId()).size(), originalSizeDataTask+1));
        assertEquals(subtaskTwo, taskManagerTest.getSubtaskEpic(epicOne.getId()).get(subtaskTwo.getId()),
                String.format("Элемент списка задач '%s' не соответствует '%s'", taskManagerTest.getSubtaskEpic(epicOne.getId()).get(subtaskTwo.getId()), subtaskTwo));
    }

    @Test
    void getHistoryTest() {
        int originalSizeDataTask = taskManagerTest.getHistory().size();
        assertEquals(0, originalSizeDataTask, "Количество элементов списка просмотра задач не равно 0");
        taskManagerTest.getTask(taskOne.getId());
        assertEquals(originalSizeDataTask + 1, taskManagerTest.getHistory().size(),
                String.format("Количество элементов списка просмотра задач не увеличилось на 1"));
    }
}