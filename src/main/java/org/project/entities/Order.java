package org.project.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_", schema = "import_export_db")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_ID", nullable = false)
    private int orderId;
    @Basic
    @Column(name = "itemAmount", nullable = false)
    private int itemAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="itemID")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="operationID")
    private TradeOperation operation;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public TradeOperation getOperation() {
        return operation;
    }

    public void setOperation(TradeOperation operation) {
        this.operation = operation;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&  itemAmount == order.itemAmount && Objects.equals(item, order.item) && Objects.equals(operation, order.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, itemAmount, item, operation);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", itemAmount=" + itemAmount +
                ", item=" + item +
                ", operation=" + operation +
                '}';
    }
}
