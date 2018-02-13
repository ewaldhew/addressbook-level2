package seedu.addressbook.commands;

public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits details for the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX [p]p/NEW_PHONE [p]e/NEW_EMAIL [p]a/NEW_ADDRESS\n"
            + "Example: " + COMMAND_WORD
            + " 1 p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS =
            "Person edited: %1$s\n -> %2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "A person like this already exists in the address book.\n"
            + "Changes not saved! ";

}