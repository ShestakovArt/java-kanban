package tests;

import enums.Status;
import managerUtil.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static templateTask.Task.setCounter;

class EpicTest{

    Epic testEpic;
    Subtask subtaskEpicTest;

    @BeforeEach
    void setUp(){
        testEpic = new Epic("Тестовый Эпик", "Описание тестового эпика");
        subtaskEpicTest = new Subtask("Тестовая подзадача", "Описание тестовой подзадачи");
        testEpic.addSubTask(subtaskEpicTest);
    }

    @AfterEach
    void tearDown(){
        setCounter(1);
    }

    @Test
    void getStatusNoSubtaskTest() {
        assertEquals(testEpic.getStatus(), Status.NEW.getCode(),
                String.format("Значение статуса эпика '%s' не совпадает c значением '%s'", testEpic.getStatus(), Status.NEW.getCode()));
    }

    @Test
    void getStatusOneSubtaskNewTest() {
        assertEquals(testEpic.getStatus(), Status.NEW.getCode(),
                String.format("Значение статуса эпика '%s' не совпадает c значением '%s'", testEpic.getStatus(), Status.NEW.getCode()));
    }

    @Test
    void getStatusOneSubtaskInProgressTest() {
        testEpic.getSubtask(subtaskEpicTest.getId()).setStatus(Status.IN_PROGRESS.getCode());
        assertEquals(testEpic.getStatus(), Status.IN_PROGRESS.getCode(),
                String.format("Значение статуса эпика '%s' не совпадает c значением '%s'", testEpic.getStatus(), Status.IN_PROGRESS.getCode()));
    }

    @Test
    void getStatusOneSubtaskDoneTest() {;
        testEpic.getSubtask(subtaskEpicTest.getId()).setStatus(Status.DONE.getCode());
        assertEquals(testEpic.getStatus(), Status.DONE.getCode(),
                String.format("Значение статуса эпика '%s' не совпадает c значением '%s'", testEpic.getStatus(), Status.DONE.getCode()));
    }

    @Test
    void getStatusTwoSubtaskNewTest() {
        Subtask secondSubtaskEpicTest = new Subtask("Тестовая подзадача", "Описание тестовой подзадачи");
        testEpic.addSubTask(secondSubtaskEpicTest);
        assertEquals(testEpic.getStatus(), Status.NEW.getCode(),
                String.format("Значение статуса эпика '%s' не совпадает c значением '%s'", testEpic.getStatus(), Status.NEW.getCode()));
    }

    @Test
    void getStatusTwoSubtaskNewAndInProgressTest() {
        Subtask secondSubtaskEpicTest = new Subtask("Тестовая подзадача", "Описание тестовой подзадачи");
        testEpic.addSubTask(secondSubtaskEpicTest);
        testEpic.getSubtask(secondSubtaskEpicTest.getId()).setStatus(Status.IN_PROGRESS.getCode());
        assertEquals(testEpic.getStatus(), Status.IN_PROGRESS.getCode(),
                String.format("Значение статуса эпика '%s' не совпадает c значением '%s'", testEpic.getStatus(), Status.IN_PROGRESS.getCode()));
    }

    @Test
    void getStatusTwoSubtaskDoneTest() {
        Subtask secondSubtaskEpicTest = new Subtask("Тестовая подзадача", "Описание тестовой подзадачи");
        testEpic.addSubTask(secondSubtaskEpicTest);
        testEpic.getSubtask(secondSubtaskEpicTest.getId()).setStatus(Status.DONE.getCode());
        testEpic.getSubtask(subtaskEpicTest.getId()).setStatus(Status.DONE.getCode());
        assertEquals(testEpic.getStatus(), Status.DONE.getCode(),
                String.format("Значение статуса эпика '%s' не совпадает c значением '%s'", testEpic.getStatus(), Status.DONE.getCode()));
    }

    @Test
    void getEndTimeNoSubtaskTest() {
        assertNull(testEpic.getEndTime(), "Значение по умолчанию не null");
    }

    @Test
    void getEndTimeSubtaskNoEndTimeTest() {
        assertNull(testEpic.getEndTime(), "Значение по умолчанию не null");
    }

    @Test
    void getEndTimeTest() {
        LocalDateTime timeNow = LocalDateTime.now();
        Long minutes = 300L;
        LocalDateTime endTimeForCheck = timeNow.plusMinutes(minutes);
        testEpic.getSubtask(subtaskEpicTest.getId()).setStartTime(timeNow);
        testEpic.getSubtask(subtaskEpicTest.getId()).setDuration(minutes);
        assertEquals(testEpic.getEndTime(), endTimeForCheck,
                String.format("Значение времени окончания задачи '%s' не совпадает c значением '%s'", testEpic.getEndTime(), endTimeForCheck));
    }

    @Test
    void addSubTaskTest() {
        assertEquals(testEpic.getDataSubtask().size(), 1,
                String.format("Количество подзадач эпика '%s' не совпадает c значением '%s'", testEpic.getDataSubtask().size(), 1));
    }

    @Test
    void getSubtaskTest() {
        assertEquals(testEpic.getSubtask(subtaskEpicTest.getId()), subtaskEpicTest,
                String.format("Подзадача эпика '%s' не совпадает c переданной подзадачей '%s'", testEpic.getSubtask(subtaskEpicTest.getId()), subtaskEpicTest));
    }


    @Test
    void getDataSubtaskTest() {
        Subtask secondSubtaskEpicTest = new Subtask("Тестовая подзадача", "Описание тестовой подзадачи");
        testEpic.addSubTask(secondSubtaskEpicTest);
        testEpic.getSubtask(secondSubtaskEpicTest.getId()).setStatus(Status.IN_PROGRESS.getCode());
        assertEquals(testEpic.getDataSubtask().size(), 2,
                String.format("Количество подзадач эпика '%s' не совпадает c значением '%s'", testEpic.getDataSubtask().size(), 2));
        assertEquals(testEpic.getSubtask(subtaskEpicTest.getId()), subtaskEpicTest,
                String.format("Подзадача эпика '%s' не совпадает c переданной подзадачей '%s'", testEpic.getSubtask(subtaskEpicTest.getId()), subtaskEpicTest));
        assertEquals(testEpic.getSubtask(secondSubtaskEpicTest.getId()), secondSubtaskEpicTest,
                String.format("Подзадача эпика '%s' не совпадает c переданной подзадачей '%s'", testEpic.getSubtask(secondSubtaskEpicTest.getId()), secondSubtaskEpicTest));
    }

    @Test
    void deleteSubtaskTest() {
        Subtask secondSubtaskEpicTest = new Subtask("Тестовая подзадача", "Описание тестовой подзадачи");
        testEpic.addSubTask(secondSubtaskEpicTest);
        testEpic.getSubtask(secondSubtaskEpicTest.getId()).setStatus(Status.IN_PROGRESS.getCode());
        assertEquals(testEpic.getDataSubtask().size(), 2,
                String.format("Количество подзадач эпика '%s' не совпадает c значением '%s'", testEpic.getDataSubtask().size(), 2));
        testEpic.deleteSubtask(subtaskEpicTest.getId());
        assertEquals(testEpic.getDataSubtask().size(), 1,
                String.format("Количество подзадач эпика '%s' не совпадает c значением '%s'", testEpic.getDataSubtask().size(), 1));
    }

    @Test
    void deleteAllSubtaskTest() {
        assertEquals(testEpic.getDataSubtask().size(), 1,
                String.format("Количество подзадач эпика '%s' не совпадает c значением '%s'", testEpic.getDataSubtask().size(), 2));
        testEpic.deleteAllSubtask();
        assertEquals(testEpic.getDataSubtask().size(), 0,
                String.format("Количество подзадач эпика '%s' не совпадает c значением '%s'", testEpic.getDataSubtask().size(), 0));
    }
}