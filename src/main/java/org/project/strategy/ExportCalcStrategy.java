package org.project.strategy;

import org.project.entities.Order;

import java.util.List;

public class ExportCalcStrategy implements CalcStrategy{
    private double cost;

    @Override
    public double calculate(List<Order> orders){
        if(cost == 0.00){
            for(Order i : orders){
                cost += i.getItem().getItemCost() * i.getItemAmount();
            }
        }

        return cost;
    }

}
