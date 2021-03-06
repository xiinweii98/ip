import java.io.IOException;

/**
 * Command to mark a task as done.
 * Inherits from the superclass Command.
 */
public class DoneCommand extends Command {
    private final int taskNum;

    /**
     * Constructor to keep track of the task number being marked as done.
     * @param taskNum The task number that is to be marked as done.
     */
    public DoneCommand(int taskNum) {
        this.taskNum = taskNum;
    }

    /**
     * Snooze the task and updates the file in hard drive accordingly.
     * @param taskList TaskList that contains all tasks at hand.
     * @param ui Ui that deals with interaction with the user.
     * @param storage Storage that deals with storing TaskList into hard drive.
     * @throws IOException If there is an error while updating the file in hard drive.
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) throws IOException {
        assert this.taskNum > 0 : "Input task number cannot be less than 1";
        assert this.taskNum <= taskList.getNormalTasks().size()
                : "Input Task number cannot be more than total number of Tasks.";
        taskList.markDone(this.taskNum - 1);
        storage.update(taskList);
        return ui.printMarkDone(taskList.getNormalTasks().get(this.taskNum - 1));
    }
}
