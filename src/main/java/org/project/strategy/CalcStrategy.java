package org.project.strategy;

import org.project.entities.Order;

import java.util.List;

public interface CalcStrategy {
    double calculate(List<Order> orders);

}
