package tests;

import com.google.gson.internal.bind.util.ISO8601Utils;
import http.HttpTaskServer;
import http.KVServer;
import managerUtil.HttpTaskManager;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest {
    static KVServer kvServer;
    static HttpTaskServer server;
    static String url = "http://localhost:8080/";
    static String kvUrl = "http://localhost:8078/";
    static String nameTask;
    static String descriptionTask;
    static String nameEpic;
    static String descriptionEpic;
    static String nameSubtask;
    static String descriptionSubtask;


    @BeforeAll
    static void runKVServer() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        server = new HttpTaskServer();
        server.start();
        HttpClient httpClient = HttpClient.newHttpClient();
        String prefixTask = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmm"));
        nameTask = String.format("Таск %s", prefixTask);
        descriptionTask = String.format("Описание таски %s", prefixTask);
        nameEpic = String.format("Эпик %s", prefixTask);
        descriptionEpic = String.format("Описание эпика %s", prefixTask);
        nameSubtask = String.format("Сабтаска %s", prefixTask);
        descriptionSubtask = String.format("Описание сабтаски %s", prefixTask);

        //отправляем таску на сервер 8080
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/task"))
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"nameTask\": \"%s\",\"description\": \"%s\"}", nameTask, descriptionTask)))
                .build();
        httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/epic"))
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"nameTask\": \"%s\",\"description\": \"%s\"}", nameEpic, descriptionEpic)))
                .build();
        httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/epic/subtask/?id=2"))
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"nameTask\": \"%s\",\"description\": \"%s\"}", nameSubtask, descriptionSubtask)))
                .build();
        httpClient.send(build, HttpResponse.BodyHandlers.ofString());
    }

    @AfterAll
    static void stopKVServer(){
        server.stop();
        kvServer.stop();
    }

    @Test
    void saveTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        //получаем ключ регистрации от сервера 8078
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(kvUrl + "register"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        String apiToken = response.body();
        //отправляем запрос на проверку сохранения данных на сервере 8078
        build = HttpRequest.newBuilder()
                .uri(URI.create(kvUrl + String.format("load/%s?API_TOKEN=%s", "task", apiToken)))
                .GET()
                .build();
        response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(!response.body().isEmpty(), "Тело ответа не должно быть пустым");
        assertTrue(response.body().contains(nameTask), "Тело не содержит название созданной задачи");
        assertTrue(response.body().contains(descriptionTask), "Тело не содержит описание созданной задачи");
    }

    @Test
    void endpointTasksGetRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
        build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/task"))
                .GET()
                .build();
        response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }

    @Test
    void endpointTasksEpicsGetRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/epics"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }

    @Test
    void endpointTasksHistoryGetRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/history"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }

    @Test
    void endpointTasksPriorityGetRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/priority"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }

    @Test
    void endpointTasksTaskIdGetRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/task/?id=1"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
        assertTrue(!response.body().isEmpty(), "Тело ответа не должно быть пустым");
        assertTrue(response.body().contains(nameTask), "Тело не содержит название созданной задачи");
        assertTrue(response.body().contains(descriptionTask), "Тело не содержит описание созданной задачи");
    }

    @Test
    void endpointTasksEpicIdGetRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/epic/?id=2"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
        assertTrue(!response.body().isEmpty(), "Тело ответа не должно быть пустым");
        assertTrue(response.body().contains(nameEpic), "Тело не содержит название созданного эпика");
        assertTrue(response.body().contains(descriptionEpic), "Тело не содержит описание созданного эпика");
    }

    @Test
    void endpointTasksSubtaskIdGetRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/subtask/?id=3"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
        assertTrue(!response.body().isEmpty(), "Тело ответа не должно быть пустым");
        assertTrue(response.body().contains(nameSubtask), "Тело не содержит название созданной подзадачи");
        assertTrue(response.body().contains(descriptionSubtask), "Тело не содержит описание созданной подзадачи");
    }

    @Test
    void endpointTasksSubtasksFromEpicIdGetRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/epic/subtasks/?id=2"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
        assertTrue(!response.body().isEmpty(), "Тело ответа не должно быть пустым");
        assertTrue(response.body().contains(nameSubtask), "Тело не содержит название созданной подзадачи");
        assertTrue(response.body().contains(descriptionSubtask), "Тело не содержит описание созданной подзадачи");
    }

    @Test
    void endpointTasksTaskPostRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String prefixPost = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmm"));
        String nameTaskPost = String.format("ТаскПОСТ %s", prefixPost);
        String descriptionTaskPost = String.format("Описание таскПОСТ %s", prefixPost);

        //отправляем таску на сервер 8080
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/task"))
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"nameTask\": \"%s\",\"description\": \"%s\"}", nameTaskPost, descriptionTaskPost)))
                .build();
        httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 201, "При успешном запросе возвращается статус код: 201");
    }

    @Test
    void endpointTasksEpicPostRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String prefixPost = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmm"));
        String nameEpicPost = String.format("ЭпикПОСТ %s", prefixPost);
        String descriptionEpicPost = String.format("Описание ЭпикПОСТ %s", prefixPost);

        //отправляем таску на сервер 8080
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/epic"))
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"nameTask\": \"%s\",\"description\": \"%s\"}", nameEpicPost, descriptionEpicPost)))
                .build();
        httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 201, "При успешном запросе возвращается статус код: 201");
    }

    @Test
    void endpointTasksSubtaskPostRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String prefixPost = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmm"));
        String nameSubtaskPost = String.format("СабтаскПОСТ %s", prefixPost);
        String descriptionSubtaskPost = String.format("Описание СабтаскПОСТ %s", prefixPost);

        //отправляем таску на сервер 8080
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/epic/subtask/?id=2"))
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"nameTask\": \"%s\",\"description\": \"%s\"}", nameSubtaskPost, descriptionSubtaskPost)))
                .build();
        httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 201, "При успешном запросе возвращается статус код: 201");
    }

    @Test
    void endpointTasksSetStartTimePostRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String startTime = LocalDateTime.now().toString();
        startTime = startTime.substring(0, startTime.indexOf("."));
        Long duration = 60L;

        //отправляем таску на сервер 8080
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/task/setStartTime/?id=1"))
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"startTime\": \"%s\",\"duration\": \"%d\"}", startTime, duration)))
                .build();
        httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 201, "При успешном запросе возвращается статус код: 201");
    }

    @Test
    void endpointTasksAllDeleteRequestTest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        //отправляем таску на сервер 8080
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks/task"))
                .DELETE()
                .build();
        httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }
}