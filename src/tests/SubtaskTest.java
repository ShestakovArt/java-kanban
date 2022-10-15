package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templateTask.Epic;
import templateTask.Subtask;

import static org.junit.jupiter.api.Assertions.*;
import static templateTask.Task.setCounter;

class SubtaskTest {

    Epic testEpic;
    Subtask subtaskTest;

    @BeforeEach
    void setUp(){
        testEpic = new Epic("Тестовый Эпик", "Описание тестового эпика");
        subtaskTest = new Subtask("Тестовая подзадача", "Описание тестовой подзадачи");
        testEpic.addSubTask(subtaskTest);
    }

    @AfterEach
    void tearDown(){
        setCounter(1);
    }

    @Test
    void getIdEpicTest() {
        assertEquals(subtaskTest.getIdEpic(), testEpic.getId(),
                String.format("ID эпика '%s' не совпадает с id эпика подзадачи '%s'", testEpic.getId(), subtaskTest.getIdEpic()));
    }

    @Test
    void setIdEpicTest() {
        int idForCheck = 99;
        subtaskTest.setIdEpic(idForCheck);
        assertEquals(subtaskTest.getIdEpic(), idForCheck,
                String.format("Id эпика подзадачи '%s', не соответствует установленному '%s'", subtaskTest.getIdEpic(), idForCheck));
    }
}