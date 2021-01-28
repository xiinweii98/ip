package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDate at;

    public Event(String name, LocalDate at) {
        super(name);
        this.at = at;
    }

    public Event(String name, LocalDate at, boolean done) {
        super(name, done);
        this.at = at;
    }

    public LocalDate getAt() {
        return this.at;
    }

    @Override
    public String getStatus() {
        return "[E]" + super.getStatus() + " (at: " + this.at.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}