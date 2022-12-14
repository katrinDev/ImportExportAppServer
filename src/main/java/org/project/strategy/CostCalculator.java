package org.project.strategy;

import org.project.entities.Order;

import java.util.List;

public class CostCalculator {

    CalcStrategy calcStrategy;


    public CostCalculator(CalcStrategy calcStrategy) {
        this.calcStrategy = calcStrategy;
    }

    public double countCost(List<Order> orders){
        return calcStrategy.calculate(orders);
    }

    public void setCalcStrategy(CalcStrategy calcStrategy) {
        this.calcStrategy = calcStrategy;
    }
}
