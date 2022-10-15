package tests;

import managerUtil.FileBackedTasksManager;
import managerUtil.InMemoryHistoryManager;
import managerUtil.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;
import tests.utilForTest.TaskManagerTest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static templateTask.Task.setCounter;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private String pathDir = "src\\tests\\testRecourse";
    private String nameFile = "testDataFile.csv";
    private Path sourceFile;

    @BeforeEach
    void setUp() {
        sourceFile = Paths.get(pathDir, nameFile);
        taskManagerTest = new FileBackedTasksManager(sourceFile);
    }

    @AfterEach
    void tearDown() {
        InMemoryHistoryManager testHistoryManager = new InMemoryHistoryManager();
        List<Task> taskList = testHistoryManager.getHistory();
        for(Task task : taskList){
            testHistoryManager.remove(task.getId());
        }
        setCounter(1);
        File files = new File(sourceFile.toString());
        File dir = new File(pathDir);
        if (files.delete()){
            dir.delete();
        }
    }

    @Test
    void loadFromFileTest() {
        Task taskOne = new Task("Задача 1", "Описание з1");
        Epic epicOne = new Epic("Эпик 1", "Описание эп1");
        Subtask subTaskOne = new Subtask("Подзадача 1 эп 1","Описание п1 эп1");

        taskManagerTest.addTask(taskOne);
        taskManagerTest.addTask(epicOne);
        taskManagerTest.addTask(epicOne.getId(), subTaskOne);
        taskManagerTest.getTask(taskOne.getId());
        final HashMap<Integer, Task> finalOriginalDataTask = taskManagerTest.getDataTask();
        FileBackedTasksManager fileManagerTwo = new FileBackedTasksManager(sourceFile);
        assertEquals(finalOriginalDataTask, fileManagerTwo.getDataTask(),
                String.format("Загруженные данные '%s' не совпадают изначальными '%s'", fileManagerTwo.getDataTask(), finalOriginalDataTask));
    }

    @Test
    void isEmptyTaskListTest() {
        final HashMap<Integer, Task> finalOriginalDataTask = taskManagerTest.getDataTask();
        FileBackedTasksManager fileManagerTwo = new FileBackedTasksManager(sourceFile);
        assertEquals(finalOriginalDataTask, fileManagerTwo.getDataTask(),
                String.format("Загруженные данные '%s' не совпадают изначальными '%s'", fileManagerTwo.getDataTask(), finalOriginalDataTask));
    }

    @Test
    void isEmptyHistoryListTest() {
        Task taskOne = new Task("Задача 1", "Описание з1");
        Epic epicOne = new Epic("Эпик 1", "Описание эп1");
        Subtask subTaskOne = new Subtask("Подзадача 1 эп 1","Описание п1 эп1");

        taskManagerTest.addTask(taskOne);
        taskManagerTest.addTask(epicOne);
        taskManagerTest.addTask(epicOne.getId(), subTaskOne);
        final HashMap<Integer, Task> finalOriginalDataTask = taskManagerTest.getDataTask();
        FileBackedTasksManager fileManagerTwo = new FileBackedTasksManager(sourceFile);
        assertEquals(finalOriginalDataTask, fileManagerTwo.getDataTask(),
                String.format("Загруженные данные '%s' не совпадают изначальными '%s'", fileManagerTwo.getDataTask(), finalOriginalDataTask));
    }

    @Test
    void epicNoSubtaskTest() {
        Task taskOne = new Task("Задача 1", "Описание з1");
        Epic epicOne = new Epic("Эпик 1", "Описание эп1");

        taskManagerTest.addTask(taskOne);
        taskManagerTest.addTask(epicOne);
        taskManagerTest.getTask(taskOne.getId());
        taskManagerTest.getTask(epicOne.getId());
        final HashMap<Integer, Task> finalOriginalDataTask = taskManagerTest.getDataTask();
        FileBackedTasksManager fileManagerTwo = new FileBackedTasksManager(sourceFile);
        assertEquals(finalOriginalDataTask, fileManagerTwo.getDataTask(),
                String.format("Загруженные данные '%s' не совпадают изначальными '%s'", fileManagerTwo.getDataTask(), finalOriginalDataTask));
    }
}