import status.Status;
import templateTask.Epic;
import templateTask.Task;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        ManageTask manage = new ManageTask();
        Task taskOne = new Task("Задача 1", "Описание з1");
        Task taskTwo = new Task("Задача 2", "Описание з2");
        Epic epicOne = new Epic("Эпик 1", "Описание эп1");
        Epic epicTwo = new Epic("Эпик 2", "Описание эп2");
        epicOne.createSubtask("Подзадача 1 эп 1","Описание п1 эп1");
        epicOne.createSubtask("Подзадача 2 эп 1","Описание п2 эп1");
        epicTwo.createSubtask("Подзадача 1 эп 2","Описание п1 эп2");

        manage.addTask(taskOne);
        manage.addTask(taskTwo);
        manage.addEpic(epicOne);
        manage.addEpic(epicTwo);
        System.out.println("\n-----Созданные объекты-----\n");
        System.out.println(manage.getDataEpic());
        System.out.println(manage.getDataTask());
        System.out.println(manage.getSubtaskEpic(1));
        System.out.println(manage.getSubtaskEpic(2));

        taskOne.setStatus(Status.IN_PROGRESS.getCode());
        taskTwo.setStatus(Status.DONE.getCode());
        epicOne.updateStatusSubtask(1, Status.IN_PROGRESS.getCode());
        epicOne.updateStatusSubtask(2, Status.DONE.getCode());
        epicTwo.updateStatusSubtask(1, Status.DONE.getCode());
        System.out.println("\n-----Изменили статусы у задач и подзадач-----\n");
        System.out.println(manage.getDataEpic());
        System.out.println(manage.getDataTask());
        System.out.println(manage.getSubtaskEpic(1));
        System.out.println(manage.getSubtaskEpic(2));
        System.out.println();

        manage.deleteTask(2);
        manage.deleteEpic(1);
        System.out.println("\n-----Удалили задачу и эпик-----\n");
        System.out.println(manage.getDataEpic());
        System.out.println(manage.getDataTask());
        System.out.println("\n-----Конец теста-----");
    }
}
