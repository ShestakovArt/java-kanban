package tests;

import enums.Status;
import enums.TypeTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templateTask.Task;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static templateTask.Task.setCounter;

class TaskTest {
    Task testTask;

    @BeforeEach
    void setUp(){
        testTask = new Task("Тестовая задача", "Описание тестовой задачи");
    }

    @AfterEach
    void tearDown(){
        setCounter(1);
    }

    @Test
    void getNameTaskTest(){
        assertEquals(testTask.getNameTask(), "Тестовая задача",
                String.format("Значение имени задачи '%s' не совпадает c значением '%s'", testTask.getNameTask(), "Тестовая задача"));
    }

    @Test
    void setNameTaskTest(){
        String nameForCheck = "Проверка метода setNameTask()";
        testTask.setNameTask(nameForCheck);
        assertEquals(testTask.getNameTask(), nameForCheck,
                String.format("Значение имени задачи '%s' не совпадает c значением '%s'", testTask.getNameTask(), nameForCheck));
    }

    @Test
    void getDescriptionTest(){
        assertEquals(testTask.getDescription(), "Описание тестовой задачи",
                String.format("Значение имени задачи '%s' не совпадает c значением '%s'", testTask.getDescription(), "Описание тестовой задачи"));
    }

    @Test
    void setDescriptionTest(){
        String descriptionForCheck = "Проверка метода setDescription()";
        testTask.setDescription(descriptionForCheck);
        assertEquals(testTask.getDescription(), descriptionForCheck,
                String.format("Значение имени задачи '%s' не совпадает c значением '%s'", testTask.getDescription(), descriptionForCheck));
    }

    @Test
    void getStatusTest() {
        assertEquals(testTask.getStatus(), Status.NEW.getCode(),
                String.format("Значение статуса задачи '%s' не совпадает c значением '%s'", testTask.getStatus(), Status.NEW.getCode()));
    }

    @Test
    void setStatusInProgressTest() {
        testTask.setStatus(Status.IN_PROGRESS.getCode());
        assertEquals(testTask.getStatus(), Status.IN_PROGRESS.getCode(),
                String.format("Значение статуса задачи '%s' не совпадает c значением '%s'", testTask.getStatus(), Status.IN_PROGRESS.getCode()));
    }

    @Test
    void setStatusDoneTest() {
        testTask.setStatus(Status.DONE.getCode());
        assertEquals(testTask.getStatus(), Status.DONE.getCode(),
                String.format("Значение статуса задачи '%s' не совпадает c значением '%s'", testTask.getStatus(), Status.DONE.getCode()));
    }

    @Test
    void getIdFirstTaskTest() {
        assertEquals(testTask.getId(), 1,
                String.format("Значение ID задачи '%s' не совпадает c значением '%s'", testTask.getId(), 1));
    }

    @Test
    void getIdSecondTaskTest() {
        Task testSecondTask = new Task("Тестовая задача", "Описание тестовой задачи");
        assertEquals(testSecondTask.getId(), 2,
                String.format("Значение ID задачи '%s' не совпадает c значением '%s'", testSecondTask.getId(), 2));
    }

    @Test
    void getTypeTaskTest() {
        assertEquals(testTask.getTypeTask(), TypeTask.TASK.getCode(),
                String.format("Значение типа задачи '%s' не совпадает c значением '%s'", testTask.getTypeTask(), TypeTask.TASK.getCode()));
    }

    @Test
    void getCounterTest() {
        assertEquals(testTask.getCounter(), 2,
                String.format("Значение счетчика задачи '%s' не совпадает c значением '%s'", testTask.getCounter(), 2));
    }

    @Test
    void setCounterTest() {
        setCounter(5);
        assertEquals(testTask.getCounter(), 5,
                String.format("Значение счетчика задачи '%s' не совпадает c значением '%s'", testTask.getCounter(), 5));
    }

    @Test
    void getDurationTest() {
        assertEquals(testTask.getDuration().toMinutes(), 0,
                String.format("Значение продолжительности задачи '%s' не совпадает c значением '%s'", testTask.getDuration(), 0));
    }

    @Test
    void setDurationTest() {
        testTask.setDuration(500);
        assertEquals(testTask.getDuration().toMinutes(), 500,
                String.format("Значение продолжительности задачи '%s' не совпадает c значением '%s'", testTask.getDuration(), 500));
    }

    @Test
    void getStartTimeTest() {
        assertNull(testTask.getStartTime(), "Значение по умолчанию не null");
        LocalDateTime timeNow = LocalDateTime.now();
        testTask.setStartTime(timeNow);
        assertEquals(testTask.getStartTime(), timeNow,
                String.format("Значение времени начала задачи '%s' не совпадает c значением '%s'", testTask.getStartTime(), timeNow));
    }

    @Test
    void setStartTimeTest() {
        LocalDateTime timeNow = LocalDateTime.now();
        testTask.setStartTime(timeNow);
        assertEquals(testTask.getStartTime(), timeNow,
                String.format("Значение времени начала задачи '%s' не совпадает c значением '%s'", testTask.getStartTime(), timeNow));
    }

    @Test
    void getEndTimeStartTimeNotNullTest() {
        LocalDateTime timeNow = LocalDateTime.now();
        Long minutes = 45L;
        testTask.setStartTime(timeNow);
        testTask.setDuration(minutes);
        LocalDateTime endTimeForCheck = timeNow.plusMinutes(minutes);
        assertEquals(testTask.getEndTime(), endTimeForCheck,
                String.format("Значение времени окончания задачи '%s' не совпадает c значением '%s'", testTask.getEndTime(), endTimeForCheck));
    }

    @Test
    void getEndTimeStartTimeNullTest() {
        assertNull(testTask.getEndTime(),"Значение по умолчанию не null");
    }
}