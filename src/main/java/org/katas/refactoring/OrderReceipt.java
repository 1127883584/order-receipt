package org.katas.refactoring;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderReceipt prints the details of order including customer name, address, description, quantity,
 * price and amount. It also calculates the sales tax @ 10% and prints as part
 * of order. It computes the total order amount (amount of individual lineItems +
 * total sales tax) and prints it.
 */
public class OrderReceipt {
    private final static Double RATE = .10;
    private Order order;

    public OrderReceipt(Order order) {
        this.order = order;
    }

    public String printReceipt() {
        StringBuilder output = new StringBuilder();


        output.append("======Printing Orders======\n");

        output.append(order.getCustomerName());
        output.append(order.getCustomerAddress());


        double totSalesTx = printLineItems(order, output).get(0);
        double tot = printLineItems(order, output).get(1);


        output.append("Sales Tax").append('\t').append(totSalesTx);


        output.append("Total Amount").append('\t').append(tot);
        return output.toString();
    }

    public List<Double> printLineItems(Order order, StringBuilder output) {
        List<Double> list = new ArrayList<>();
        double totSalesTx = 0;
        double tot = 0;
        for (LineItem lineItem : order.getLineItems()) {
            appendLineItem(output, lineItem);
            double salesTax = getSalesTax(lineItem);
            totSalesTx = getTot(totSalesTx, salesTax);
            tot = getTot(tot, lineItem.totalAmount() + salesTax);
        }
        list.add(totSalesTx);
        list.add(tot);
        return list;
    }

    private void appendLineItem(StringBuilder output, LineItem lineItem) {
        output.append(lineItem.getDescription());
        output.append('\t');
        output.append(lineItem.getPrice());
        output.append('\t');
        output.append(lineItem.getQuantity());
        output.append('\t');
        output.append(lineItem.totalAmount());
        output.append('\n');
    }

    private double getSalesTax(LineItem lineItem) {
        return lineItem.totalAmount() * RATE;
    }

    private double getTot(double tot, double v) {
        tot += v;
        return tot;
    }
}