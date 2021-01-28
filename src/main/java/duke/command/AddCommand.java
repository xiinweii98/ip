package duke.command;

import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.storage.Storage;

import java.io.IOException;

public class AddCommand extends Command {
    private final Task toAdd;

    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException {
        taskList.addTask(this.toAdd);
        storage.update(taskList);
        ui.printAddTaskMessage(this.toAdd, taskList);
    }
}