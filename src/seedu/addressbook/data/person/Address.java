package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String EXAMPLE = "123, some street, #01-02, 654321";
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Addresses are comma-separated fields consisting of the "
            + "following: BLOCK, STREET, UNIT, POSTAL_CODE\n"
            + " BLOCK is a number with optional letter suffix,\n"
            + " STREET is a string not containing commas,\n"
            + " UNIT is a number,\n"
            + " POSTAL_CODE is a six-digit number.";
    public static final String ADDRESS_VALIDATION_REGEX = "^(?<block>[\\d\\w]\\d*\\w*)?(?:, | )?"
            + "(?<street>[^\\d]+)(?:, |$)"
            + "(?<unit>[#0-9-]+)?(?:, )?"
            + "(?<postcode>\\d{6})?";

    private static final Pattern ADDRESS_FORMAT = Pattern.compile(ADDRESS_VALIDATION_REGEX);
    private final Block block;
    private final Street street;
    private final Unit unit;
    private final PostalCode postalCode;
    private boolean isPrivate;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address, boolean isPrivate) throws IllegalValueException {
        final String trimmedAddress = address.trim();
        final Matcher matcher = ADDRESS_FORMAT.matcher(trimmedAddress);

        this.isPrivate = isPrivate;
        if (!matcher.matches()) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        block = new Block(matcher.group("block"));
        street = new Street(matcher.group("street"));
        unit = new Unit(matcher.group("unit"));
        postalCode = new PostalCode(matcher.group("postcode"));
    }

    /**
     * Returns true if a given string is a valid person address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return block.getValue(false) + street.getValue(true) + unit.getValue(true) + postalCode.getValue(true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.toString().equals(other.toString())); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
