package org.project.report;

import org.project.entities.TradeOperation;
import org.project.services.OperationService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CountReport {
    public static void countReportData() throws IOException {

        OperationService operationService = new OperationService();
        List<TradeOperation> allOperations = operationService.findAllEntities();

        List<TradeOperation> sortedExpOperations = allOperations.stream().filter(x -> x.getOperationType().equals("export")).
                sorted(Comparator.comparingDouble(TradeOperation::getFullCost).reversed())
                .collect(Collectors.toList());

        List<TradeOperation> sortedInpOperations = allOperations.stream().
                filter(x -> x.getOperationType().equals("import")).
                sorted(Comparator.comparingDouble(TradeOperation::getFullCost).reversed())
                .collect(Collectors.toList());

        int tradesAmount = allOperations.size();

        DocumentReport documentReport = new DocumentReport();
        documentReport.makeDocument(tradesAmount, sortedExpOperations, sortedInpOperations);
    }

}
