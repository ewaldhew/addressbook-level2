package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;


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

    private final Phone replacementPhone;
    private final Email replacementEmail;
    private final Address replacementAddress;

    /**
     * Construct the edit command.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetVisibleIndex,
                       String phone, boolean isPhonePrivate,
                       String email, boolean isEmailPrivate,
                       String address, boolean isAddressPrivate) throws IllegalValueException {
        super(targetVisibleIndex);

        replacementPhone = new Phone(phone, isPhonePrivate);
        replacementEmail = new Email(email, isEmailPrivate);
        replacementAddress = new Address(address, isAddressPrivate);
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            final Person replacement = new Person(
                    target.getName(),
                    replacementPhone,
                    replacementEmail,
                    replacementAddress,
                    target.getTags()
            );

            addressBook.editPerson(target, replacement);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, target, replacement));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (DuplicatePersonException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException e) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }
}
