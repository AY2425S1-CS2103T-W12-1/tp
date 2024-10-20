package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IG;
import static seedu.address.model.Model.PREDICATE_DO_NOT_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.SocialMedia;

public class SocialMediaCommand extends Command {

    public static final String COMMAND_WORD = "socialMedia";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Attaches a social media handle to the person specified.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_IG + "INSTAGRAM] "
            + "[" + PREFIX_FB + "FACEBOOK] "
            + "[" + PREFIX_CS + "CAROUSELL] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_IG + "myInstaUsername";

    public static final String MESSAGE_SOCIAL_MEDIA_SUCCESS = "Social media handle added: %1$s";
    public static final String MESSAGE_SOCIAL_MEDIA_EXISTING = "Contact already has an existing handle";

    private String handle;
    private SocialMedia.Platform platform;
    private Index index;

    public SocialMediaCommand(String handle, SocialMedia.Platform platform, Index index) {
        this.handle = handle;
        this.platform = platform;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (personToEdit.hasSocialMedia()) {
            throw new CommandException(MESSAGE_SOCIAL_MEDIA_EXISTING);
        }

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags());
        SocialMedia socialMediaToAdd = new SocialMedia(handle, platform);
        editedPerson.setSocialMedia(socialMediaToAdd);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SOCIAL_MEDIA_SUCCESS, Messages.format(personToEdit)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SocialMediaCommand)) {
            return false;
        }

        SocialMediaCommand otherSocialMediaCommand = (SocialMediaCommand) other;
        return (this.platform.equals(otherSocialMediaCommand.platform))
                && (this.handle.equals(otherSocialMediaCommand.handle));
    }
}
