import javafx.beans.binding.StringBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Deals with storing and retrieving tasks from a saved file in the hard drive.
 */
public class Storage {
    private final File file;

    /**
     * Constructor to create a saved file in the hard drive if it does not already exist.
     * @param path The path where the saved file is located.
     * @throws IOException If error occur when creating the new file.
     * @throws DukeException If error occur when creating the folder/file.
     */
    public Storage(String path) throws IOException, DukeException {
        String[] splits = path.split("/");
        File dir = new File(splits[0]);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new DukeException("Error while creating ./data folder.");
            }
        }

        File savedTasks = new File(path);
        if (!savedTasks.exists()) {
            if (!savedTasks.createNewFile()) {
                throw new DukeException("Error while creating savedTask.txt file.");
            }
        }

        this.file = savedTasks;
    }

    /**
     * Loads in the data from the saved file.
     * @return An ArrayList of Tasks retrieved from the saved file.
     * @throws FileNotFoundException If the saved file cannot be found.
     * @throws DukeException If the saved file contains data that cannot be read.
     */
    public ArrayList<Task> loadNormalTasks() throws FileNotFoundException, DukeException {
        Scanner sc = new Scanner(this.file);
        ArrayList<Task> tasks = new ArrayList<>();
        while (sc.hasNext()) {
            String current = sc.nextLine();
            if (current.equals("snooze")) {
                break;
            }
            String[] splits = current.split(" ", 3);
            switch (splits[0]) {
            case "T": {
                Task toAdd = new Todo(splits[2], (splits[1].equals("1")));
                tasks.add(toAdd);
                break;
            }
            case "E": {
                Task toAdd = new Event(splits[2], LocalDate.parse(sc.nextLine()), splits[1].equals("1"));
                tasks.add(toAdd);
                break;
            }
            case "D": {
                Task toAdd = new Deadline(splits[2], LocalDate.parse(sc.nextLine()), splits[1].equals("1"));
                tasks.add(toAdd);
                break;
            }
            default: {
                throw new DukeException("Error occurred when reading data from storage file.\n"
                        + "Therefore, creating a new task list.");
            }
            }
        }
        return tasks;
    }

    /**
     * Loads in the data from the saved file.
     * @return An ArrayList of Snoozed Tasks retrieved from the saved file.
     * @throws FileNotFoundException If the saved file cannot be found.
     * @throws DukeException If the saved file contains data that cannot be read.
     */
    public ArrayList<Task> loadSnoozedTasks() throws FileNotFoundException, DukeException {
        Scanner sc = new Scanner(this.file);
        ArrayList<Task> tasks = new ArrayList<>();
        while (sc.hasNext()) {
            String current = sc.nextLine();
            if (!current.equals("snooze")) {
                continue;
            } else {
                break;
            }
        }
        while (sc.hasNext()) {
            String current = sc.nextLine();
            String[] splits = current.split(" ", 3);
            switch (splits[0]) {
            case "T": {
                Task toAdd = new Todo(splits[2], (splits[1].equals("1")));
                tasks.add(toAdd);
                break;
            }
            case "E": {
                Task toAdd = new Event(splits[2], LocalDate.parse(sc.nextLine()), splits[1].equals("1"));
                tasks.add(toAdd);
                break;
            }
            case "D": {
                Task toAdd = new Deadline(splits[2], LocalDate.parse(sc.nextLine()), splits[1].equals("1"));
                tasks.add(toAdd);
                break;
            }
            default: {
                throw new DukeException("Error occurred when reading data from storage file.\n"
                        + "Therefore, creating a new task list.");
            }
            }
        }
        return tasks;
    }

    /**
     * Updates the saved file in hard drive according to what is in the current TaskList.
     * @param taskList Contains the tasks at hand.
     * @throws IOException If a FileWriter cannot be created.
     */
    public void update(TaskList taskList) throws IOException {
        FileWriter fw = new FileWriter(this.file);
        String textToAdd = translateToString(taskList.getNormalTasks()) +
                "snooze\n" +
                translateToString(taskList.getSnoozedTasks());
        fw.write(textToAdd);
        fw.close();
    }

    /**
     * Translate Tasks in TaskList to String form for storage in hard drive.
     * @param taskList TaskList containing the tasks.
     * @return A String format of the Tasks inside the TaskList.
     */
    public String translateToString(ArrayList<Task> taskList) {
        StringBuilder textToAdd = new StringBuilder();
        for (Task curr : taskList) {
            if (curr instanceof Todo) {
                textToAdd.append(caseTodo((Todo) curr));
            } else if (curr instanceof Deadline) {
                textToAdd.append(caseDeadline((Deadline) curr));
            } else if (curr instanceof Event) {
                textToAdd.append(caseEvent((Event) curr));
            }
        }
        return textToAdd.toString();
    }

    public String caseTodo(Todo todo) {
        String toReturn = "";
        toReturn += "T ";
        toReturn += todo.isDone() ? "1 " : "0 ";
        toReturn += todo.getName() + "\n";
        return toReturn;
    }

    public String caseDeadline(Deadline deadline) {
        String toReturn = "";
        toReturn += "D ";
        toReturn += deadline.isDone() ? "1 " : "0 ";
        toReturn += deadline.getName() + "\n";
        toReturn += deadline.getBy() + "\n";
        return toReturn;
    }

    public String caseEvent(Event event) {
        String toReturn = "";
        toReturn += "E ";
        toReturn += event.isDone() ? "1 " : "0 ";
        toReturn += event.getName() + "\n";
        toReturn += event.getAt() + "\n";
        return toReturn;
    }
}
