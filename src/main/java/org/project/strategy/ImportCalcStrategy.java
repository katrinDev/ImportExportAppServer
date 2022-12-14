package org.project.strategy;

import org.project.entities.Order;

import java.util.List;

public class ImportCalcStrategy implements CalcStrategy{
    private double cost;
    private static final int VAT = 20;

    @Override
    public double calculate(List<Order> orders){
        if(cost == 0.00){
            for(Order i : orders){
                cost += i.getItem().getItemCost() * i.getItemAmount();
            }
            cost += cost * VAT * 0.01;
        }

        return cost;
    }
}

