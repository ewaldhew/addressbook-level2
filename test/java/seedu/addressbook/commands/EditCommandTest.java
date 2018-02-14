package seedu.addressbook.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.data.tag.UniqueTagList;
import seedu.addressbook.ui.TextUi;
import seedu.addressbook.util.TestUtil;

public class EditCommandTest {
    private AddressBook emptyAddressBook;
    private AddressBook addressBook;

    private List<ReadOnlyPerson> emptyDisplayList;
    private List<ReadOnlyPerson> listWithEveryone;
    private List<ReadOnlyPerson> listWithSurnameDoe;

    @Before
    public void setUp() throws Exception {
        Person johnDoe = new Person(new Name("John Doe"), new Phone("61234567", false),
                new Email("john@doe.com", false), new Address("395C Ben Road", false), new UniqueTagList());
        Person janeDoe = new Person(new Name("Jane Doe"), new Phone("91234567", false),
                new Email("jane@doe.com", false), new Address("33G Ohm Road", false), new UniqueTagList());
        Person samDoe = new Person(new Name("Sam Doe"), new Phone("63345566", false),
                new Email("sam@doe.com", false), new Address("55G Abc Road", false), new UniqueTagList());
        Person davidGrant = new Person(new Name("David Grant"), new Phone("61121122", false),
                new Email("david@grant.com", false), new Address("44H Define Road", false),
                new UniqueTagList());

        emptyAddressBook = TestUtil.createAddressBook();
        addressBook = TestUtil.createAddressBook(johnDoe, janeDoe, davidGrant, samDoe);

        emptyDisplayList = TestUtil.createList();

        listWithEveryone = TestUtil.createList(johnDoe, janeDoe, davidGrant, samDoe);
        listWithSurnameDoe = TestUtil.createList(johnDoe, janeDoe, samDoe);
    }

    /**
     * Creates a new edit command.
     */
    private EditCommand createEditCommand(int index, AddressBook addressBook,
                                          List<ReadOnlyPerson> displayList) {

        EditCommand command;

        try {
            command = new EditCommand(index, "100110", false, "tester@com.com", false, "Test Drive 2018", false);
        } catch (IllegalValueException e) {
            throw new RuntimeException();
        }
        command.setData(addressBook, displayList);

        return command;
    }

    /**
     * Creates a new edit command with specified replacement.
     */
    private EditCommand createEditCommand(int index, Person replacement, AddressBook addressBook,
                                          List<ReadOnlyPerson> displayList) {

        EditCommand command;

        try {
            command = new EditCommand(index,
                    replacement.getPhone().value, replacement.getPhone().isPrivate(),
                    replacement.getEmail().value, replacement.getEmail().isPrivate(),
                    replacement.getAddress().value, replacement.getAddress().isPrivate());
        } catch (IllegalValueException e) {
            throw new RuntimeException();
        }
        command.setData(addressBook, displayList);

        return command;
    }

    @Test
    public void editCommand_invalidPhone_throwsException() {
        final String[] invalidNumbers = {"", " ", "1234-5678", "[]\\[;]", "abc", "a123", "+651234"};
        for (String number : invalidNumbers) {
            assertConstructingInvalidEditCmdThrowsException(1, number, false, Email.EXAMPLE, true,
                    Address.EXAMPLE, false);
        }
    }

    @Test
    public void editCommand_invalidEmail_throwsException() {
        final String[] invalidEmails = {"", " ", "def.com", "@", "@def", "@def.com", "abc@",
                "@invalid@email", "invalid@email!", "!invalid@email"};
        for (String email : invalidEmails) {
            assertConstructingInvalidEditCmdThrowsException(1, Phone.EXAMPLE, false, email, false,
                    Address.EXAMPLE, false);
        }
    }

    @Test
    public void editCommand_invalidAddress_throwsException() {
        final String[] invalidAddresses = {"", " "};
        for (String address : invalidAddresses) {
            assertConstructingInvalidEditCmdThrowsException(1, Phone.EXAMPLE, true, Email.EXAMPLE,
                    true, address, true);
        }
    }

    /**
     * Asserts that attempting to construct an edit command with the supplied
     * invalid data throws an IllegalValueException
     */
    private void assertConstructingInvalidEditCmdThrowsException(int index,
                                                                 String phone, boolean isPhonePrivate,
                                                                 String email, boolean isEmailPrivate,
                                                                 String address, boolean isAddressPrivate) {
        try {
            new EditCommand(index, phone, isPhonePrivate, email, isEmailPrivate, address, isAddressPrivate);
        } catch (IllegalValueException e) {
            return;
        }
        String error = String.format(
                "An edit command was successfully constructed with invalid input: %s %s %s %s %s %s",
                index, phone, isPhonePrivate, email, isEmailPrivate, address, isAddressPrivate);
        fail(error);
    }

    @Test
    public void editCommand_addressBookAlreadyContainsPerson_addressBookUnmodified() throws Exception {
        Person p = TestUtil.generateTestPerson();
        addressBook.addPerson(p);
        List<ReadOnlyPerson> peopleList = addressBook.getAllPersons().immutableListView();
        int index = peopleList.size();
        ReadOnlyPerson dupe = peopleList.get(0);
        EditCommand command = new EditCommand(index, 
                                              dupe.getPhone().toString(), dupe.getPhone().isPrivate(),
                                              dupe.getEmail().toString(), dupe.getEmail().isPrivate(),
                                              dupe.getAddress().toString(), dupe.getAddress().isPrivate());
        command.setData(addressBook, peopleList);
        CommandResult result = command.execute();

        assertFalse(result.getRelevantPersons().isPresent());
        assertEquals(EditCommand.MESSAGE_DUPLICATE_PERSON, result.feedbackToUser);
        assertTrue(peopleList.contains(p));
        assertEquals(index, peopleList.size());
    }

    @Test
    public void execute_emptyAddressBook_returnsPersonNotFoundMessage() {
        assertEditFailsDueToNoSuchPerson(1, emptyAddressBook, listWithEveryone);
    }

    @Test
    public void execute_noPersonDisplayed_returnsInvalidIndexMessage() {
        assertEditFailsDueToInvalidIndex(1, addressBook, emptyDisplayList);
    }

    @Test
    public void execute_invalidIndex_returnsInvalidIndexMessage() {
        assertEditFailsDueToInvalidIndex(0, addressBook, listWithEveryone);
        assertEditFailsDueToInvalidIndex(-1, addressBook, listWithEveryone);
        assertEditFailsDueToInvalidIndex(listWithEveryone.size() + 1, addressBook, listWithEveryone);
    }

    @Test
    public void execute_validIndex_personIsEdited() throws Exception {
        assertEditSuccessful(1, addressBook, listWithSurnameDoe);
        assertEditSuccessful(listWithSurnameDoe.size(), addressBook, listWithSurnameDoe);

        int middleIndex = (listWithSurnameDoe.size() / 2) + 1;
        assertEditSuccessful(middleIndex, addressBook, listWithSurnameDoe);
    }

    /**
     * Executes the command, and checks that the execution was what we had expected.
     */
    private void assertCommandBehaviour(EditCommand editCommand, String expectedMessage,
                                        AddressBook expectedAddressBook, AddressBook actualAddressBook) {

        CommandResult result = editCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook.getAllPersons(), actualAddressBook.getAllPersons());
    }

    /**
     * Asserts that the index is not valid for the given display list.
     */
    private void assertEditFailsDueToInvalidIndex(int invalidVisibleIndex, AddressBook addressBook,
                                                  List<ReadOnlyPerson> displayList) {

        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        EditCommand command = createEditCommand(invalidVisibleIndex, addressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, addressBook, addressBook);
    }

    /**
     * Asserts that the person at the specified index cannot be edited, because that person
     * is not in the address book.
     */
    private void assertEditFailsDueToNoSuchPerson(int visibleIndex, AddressBook addressBook,
                                                  List<ReadOnlyPerson> displayList) {

        String expectedMessage = Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK;

        EditCommand command = createEditCommand(visibleIndex, addressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, addressBook, addressBook);
    }

    /**
     * Asserts that the person at the specified index can be successfully edited.
     * <p>
     * The addressBook passed in will not be modified (no side effects).
     *
     * @throws PersonNotFoundException if the selected person is not in the address book
     */
    private void assertEditSuccessful(int targetVisibleIndex, AddressBook addressBook,
                                      List<ReadOnlyPerson> displayList)
            throws IllegalValueException, PersonNotFoundException {

        ReadOnlyPerson targetPerson = displayList.get(targetVisibleIndex - TextUi.DISPLAYED_INDEX_OFFSET);
        Person replacement = new Person(targetPerson.getName(), new Phone("11111", false),
                new Email("new@doe.com", false), new Address("55G New Road", false), new UniqueTagList());

        AddressBook expectedAddressBook = TestUtil.clone(addressBook);
        expectedAddressBook.editPerson(targetPerson, replacement);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, targetPerson, replacement);

        AddressBook actualAddressBook = TestUtil.clone(addressBook);

        EditCommand command = createEditCommand(targetVisibleIndex, replacement, actualAddressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, expectedAddressBook, actualAddressBook);
    }
}
