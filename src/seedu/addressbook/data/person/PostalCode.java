package seedu.addressbook.data.person;

public class PostalCode {

    private final String value;

    /**
     * Extract postcode from an address. Address should be validated already.
     *
     * @param address full address string.
     */
    public PostalCode(String address) {
        value = extractAddress(address);
    }

    public String extractAddress(String address) {
        return address.split(",")[3];
    }

    @Override
    public String toString() {
        return value;
    }
}
