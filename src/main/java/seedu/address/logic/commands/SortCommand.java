package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.model.Model;

/**
 * Sorts the list of contacts in alphabetical order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts list in ascending or descending alphabetical order\n"
            + "If order is not provided, it will sort in ascending order by default.\n"
            + "Parameters: String\n"
            + "Example: " + COMMAND_WORD + " asc/desc/ascending/desc (case insensitive)";
    public static final String MESSAGE_SUCCESS = "Successfully sorted";
    public String order;
    public SortCommand(String order) {
        this.order = order;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortFilteredPersonList(order);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
