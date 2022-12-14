package org.project.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Item {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "itemID", nullable = false)
    private int itemId;
    @Basic
    @Column(name = "itemName", nullable = false, length = 45)
    private String itemName;
    @Basic
    @Column(name = "itemCost", nullable = false, precision = 0)
    private double itemCost;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private transient List<Order> orders;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId && Double.compare(item.itemCost, itemCost) == 0 && Objects.equals(itemName, item.itemName);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemCost=" + itemCost +
                ", orders=" + orders +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, itemCost);
    }
}
