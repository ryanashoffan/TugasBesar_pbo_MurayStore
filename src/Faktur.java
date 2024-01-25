public class Faktur extends Barang implements Pembayaran {
    private String invoiceNumber;
    private String customerName;
    private String NoHP;
    private String address;

    public Faktur(String invoiceNumber, String customerName, String itemName, double price, int quantity, String NoHP,
            String address) {
        super(itemName, price, quantity);
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.NoHP = NoHP;
        this.address = address;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getnoHP() {
        return NoHP;
    }

    public String getaddress() {
        return address;
    }

    @Override
    public double calculateTotal() {
        return getPrice() * getQuantity();
    }
}
