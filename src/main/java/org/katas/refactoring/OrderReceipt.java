package org.katas.refactoring;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderReceipt prints the details of order including customer name, address, description, quantity,
 * price and amount. It also calculates the sales tax @ 10% and prints as part
 * of order. It computes the total order amount (amount of individual lineItems +
 * total sales tax) and prints it.
 */
public class OrderReceipt {
    private Order order;

    public OrderReceipt(Order order) {
        this.order = order;
    }

    public String printReceipt() {
        final String customerName = order.getCustomerName();
        final String customerAddress = order.getCustomerAddress();

        final double taxRate = .10;
        final double totalSalesTax = getTotalAmount() * taxRate;
        final double totalAmountWithTax = getTotalAmount() + getTotalSalesTx();

        return print(customerName, customerAddress, getLineItemsAsString(), totalSalesTax, totalAmountWithTax);
    }

    private String print(String customerName, String customerAddress, String lineItemsAsString, double totalSalesTax, double totalAmount){
        return MessageFormat.format("======Printing Orders======\n {0} {1} {2}\nSales Tax\t{3}Total Amount\t{4}",
                customerName,
                customerAddress,
                lineItemsAsString,
                totalSalesTax,
                totalAmount);
    }

    private double getTotalAmount(){
        return order.getLineItems().stream().mapToDouble(LineItem::totalAmount).sum();
    }

    private String getLineItemsAsString(){
        String lineItemsFormat = "%s\t%.1f\t%d\t%.1f";
        return order.getLineItems().stream().map(lineItem ->
                String.format(lineItemsFormat,lineItem.getDescription(),lineItem.getPrice(),lineItem.getQuantity(),lineItem.totalAmount()))
                .reduce("",(result,element)->result + element + "\n" );
    }

    private double getTotalSalesTx() {
        return order.getLineItems().stream().mapToDouble(x -> x.totalAmount() * .10).sum();
    }
}