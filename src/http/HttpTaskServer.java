package http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import enums.TypeTask;
import managerUtil.Managers;
import managerUtil.TaskManager;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HttpTaskServer {
    private static TaskManager taskManager = new Managers().getDefault();
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final int PORT = 8080;
    private final HttpServer server;
    private static Gson gson = new Gson();

    public HttpTaskServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler());
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }

    public void stop() {
        System.out.println("Останавливаем сервер http://localhost:" + PORT + "/");
        server.stop(0);
    }

    public void loadFromKVServer(){

    }

    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = null;
            URI requestURI = httpExchange.getRequestURI();
            String path = requestURI.toString();
            String method = httpExchange.getRequestMethod();
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println(LocalDateTime.now().format(formatter) + String.format(" %s %s запрос от клиента", method, path));
            switch(method) {
                case "GET":
                    if(path.matches("^\\/tasks\\/$") || path.matches("^\\/tasks$")
                            || path.matches("^\\/tasks\\/task\\/$") || path.matches("^\\/tasks\\/task$")){
                        response = gson.toJson(taskManager.getDataTask());
                        httpExchange.sendResponseHeaders(200, 0);
                    }
                    if(path.matches("^\\/tasks/epics\\/$") || path.matches("^\\/tasks/epics$")){
                        response = gson.toJson(taskManager.getDataEpic());
                        httpExchange.sendResponseHeaders(200, 0);
                    }
                    if(path.matches("^\\/tasks\\/history\\/$") || path.matches("^\\/tasks\\/history$")){
                        response = gson.toJson(taskManager.getHistory());
                        httpExchange.sendResponseHeaders(200, 0);
                    }
                    if(path.matches("^\\/tasks\\/priority\\/$") || path.matches("^\\/tasks\\/priority$")){
                        response = gson.toJson(taskManager.getPrioritizedTasks());
                        httpExchange.sendResponseHeaders(200, 0);
                    }
                    if(path.matches("^\\/tasks\\/task\\/\\?id=\\d*$")
                            || path.matches("^\\/tasks\\/epic\\/\\?id=\\d*$")
                            || path.matches("^\\/tasks\\/subtask\\/\\?id=\\d*$")
                            || path.matches("^\\/tasks\\/epic\\/subtasks\\/\\?id=\\d*$")){
                        int startIndex = path.indexOf("=");
                        String id = null;
                        if(startIndex > -1){
                            id = path.substring(startIndex+1);
                        }
                        if(path.matches("^\\/tasks\\/task\\/\\?id=\\d*$")
                                && taskManager.getTask(Integer.parseInt(id)).getTypeTask().equals(TypeTask.TASK.getCode())){
                            response = gson.toJson(taskManager.getTask(Integer.parseInt(id)));
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                        else if(path.matches("^\\/tasks\\/epic\\/\\?id=\\d*$")
                                && taskManager.getTask(Integer.parseInt(id)).getTypeTask().equals(TypeTask.EPIC.getCode())){
                            response = gson.toJson(taskManager.getTask(Integer.parseInt(id)));
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                        else if(path.matches("^\\/tasks\\/subtask\\/\\?id=\\d*$")
                                && taskManager.getTask(Integer.parseInt(id)).getTypeTask().equals(TypeTask.SUBTASK.getCode())){
                            response = gson.toJson(taskManager.getTask(Integer.parseInt(id)));
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                        else if(path.matches("^\\/tasks\\/epic\\/subtasks\\/\\?id=\\d*$")
                                && taskManager.getTask(Integer.parseInt(id)).getTypeTask().equals(TypeTask.EPIC.getCode())){
                            response = gson.toJson(taskManager.getSubtaskEpic(Integer.parseInt(id)));
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                        else{
                            response = "Не корректный запрос!";
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    }
                    break;
                case "POST":
                    if(path.matches("^\\/tasks\\/task\\/$")
                            || path.matches("^\\/tasks\\/task$")
                            || path.matches("^\\/tasks\\/epic\\/$")
                            || path.matches("^\\/tasks\\/epic$")
                            || path.matches("^\\/tasks\\/epic\\/subtask\\/\\?id=\\d*$")){
                        int startIndex = path.indexOf("=");
                        String id = null;
                        if(startIndex > -1){
                            id = path.substring(startIndex+1);
                        }
                        JsonElement taskJsonElement = JsonParser.parseString(body);
                        if(path.matches("^\\/tasks\\/task\\/$") || path.matches("^\\/tasks\\/task$")){
                            taskManager.addTask(new Task(taskJsonElement.getAsJsonObject().get("nameTask").getAsString(),
                                    taskJsonElement.getAsJsonObject().get("description").getAsString()));
                            httpExchange.sendResponseHeaders(201, 0);
                        }
                        else if(path.matches("^\\/tasks\\/epic\\/$") || path.matches("^\\/tasks\\/epic$")){
                            taskManager.addTask(new Epic(taskJsonElement.getAsJsonObject().get("nameTask").getAsString(),
                                    taskJsonElement.getAsJsonObject().get("description").getAsString()));
                            httpExchange.sendResponseHeaders(201, 0);
                        }
                        else if(path.matches("^\\/tasks\\/epic\\/subtask\\/\\?id=\\d*$")
                                && taskManager.getTask(Integer.parseInt(id)).getTypeTask().equals(TypeTask.EPIC.getCode())){
                            taskManager.addTask(Integer.parseInt(id), new Subtask(taskJsonElement.getAsJsonObject().get("nameTask").getAsString(),
                                    taskJsonElement.getAsJsonObject().get("description").getAsString()));
                            httpExchange.sendResponseHeaders(201, 0);
                        }
                        else{
                            response = "Не корректный запрос!";
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    }
                    break;
                case "DELETE":
                    if(path.matches("^\\/tasks\\/task\\/$")
                            || path.matches("^\\/tasks\\/task$")
                            || path.matches("^\\/tasks\\/task\\/\\?id=\\d*$")
                            || path.matches("^\\/tasks\\/epic\\/\\?id=\\d*$")
                            || path.matches("^\\/tasks\\/epic\\/$")
                            || path.matches("^\\/tasks\\/subtask\\/\\?idSubtask=\\d*$")
                            || path.matches("^\\/tasks\\/epic\\/subtasks\\/\\?idEpic=\\d*$")) {
                        int startIndex = path.indexOf("=");
                        String id = null;
                        if (startIndex > -1) {
                            id = path.substring(startIndex + 1);
                        }
                        JsonElement taskJsonElement = JsonParser.parseString(body);
                        if (path.matches("^\\/tasks\\/task\\/$") || path.matches("^\\/tasks\\/task$")) {
                            taskManager.deleteAllTask();
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (path.matches("^\\/tasks\\/task\\/\\?id=\\d*$")
                                && taskManager.getTask(Integer.parseInt(id)).getTypeTask().equals(TypeTask.TASK.getCode())) {
                            taskManager.deleteTask(Integer.parseInt(id));
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (path.matches("^\\/tasks\\/epic\\/$") || path.matches("^\\/tasks\\/epic$")) {
                            taskManager.deleteAllEpic();
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (path.matches("^\\/tasks\\/epic\\/\\?id=\\d*$")
                                && taskManager.getTask(Integer.parseInt(id)).getTypeTask().equals(TypeTask.EPIC.getCode())) {
                            taskManager.deleteEpic(Integer.parseInt(id));
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (path.matches("^\\/tasks\\/epic\\/subtasks\\/\\?idepic=\\d*&idSubtask=\\d*$")
                                && taskManager.getTask(Integer.parseInt(id)).getTypeTask().equals(TypeTask.EPIC.getCode())) {
                            int nextIndex = path.indexOf("=");
                            String idSubtask = null;
                            if (nextIndex > -1) {
                                idSubtask = id.substring(startIndex + 1);
                            }
                            String idEpic = id.substring(0, nextIndex);
                            taskManager.deleteSubtask(Integer.parseInt(idEpic), Integer.parseInt(idSubtask));
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (path.matches("^\\/tasks\\/epic\\/subtasks\\/\\?idepic=\\d*$")
                                && taskManager.getTask(Integer.parseInt(id)).getTypeTask().equals(TypeTask.EPIC.getCode())) {
                            taskManager.deleteAllSubtask(Integer.parseInt(id));
                            httpExchange.sendResponseHeaders(200, 0);
                        }else {
                            response = "Не корректный запрос!";
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    }
                    break;
                default:
                    response = "Не допустимый метод!";
                    httpExchange.sendResponseHeaders(404, 0);
            }

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes(DEFAULT_CHARSET));
            }
        }
    }
}
