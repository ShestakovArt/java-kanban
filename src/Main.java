import status.Status;
import templateTask.Epic;
import templateTask.Task;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        ManageTask manage = new ManageTask();
        manage.createTask("Задача 1", "Описание 1");
        manage.createTask("Задача 2", "Описание 2");
        manage.createEpic("Эпик 1", "Описание эпика 1");
        manage.updateDescriptionTask(1, "Был апдейт описания задачи 1");
        manage.updateStatusTask(2, Status.IN_PROGRESS);
        System.out.println(manage.getDataTask());
        manage.addSubtaskEpic(1,"Подзадача 1 эпика 1", "Описание подзадачи 1 эпика 1");
        manage.addSubtaskEpic(1,"Подзадача 2 эпика 1", "Описание подзадачи 2 эпика 1");
        manage.createEpic("Эпик 2", "Описание эпика 2");
        manage.addSubtaskEpic(2,"Подзадача 1 эпика 2", "Описание подзадачи 1 эпика 2");
        manage.addSubtaskEpic(2, manage.getDataTask().get(1));
        manage.updateNameTask(2, "Был апдейт имени задачи 2");
        System.out.println(manage.getDataTask());
        System.out.println(manage.getDataEpic().get(2));
    }
}
