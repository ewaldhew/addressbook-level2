package seedu.addressbook.data.person;

/**
 * Represents a block number in a Person's address in the address book.
 * Guarantees: immutable;
 */
public class Block extends AddressElement {

    /**
     * Make a new Block object with the given value.
     * Values must be validated before creating.
     */
    public Block(String value) {
        super(value);
    }
}
