package org.project.report;

import java.io.*;

import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.xwpf.usermodel.*;
import org.project.entities.Order;
import org.project.entities.Person;
import org.project.entities.TradeOperation;
import org.project.services.OrderService;


public class DocumentReport {

    void makeDocument(int tradesAmount, List<TradeOperation> exports, List<TradeOperation> imports) throws IOException {

        XWPFDocument document = new XWPFDocument();

        FileOutputStream out = new FileOutputStream("report.docx");

        XWPFParagraph paragraph = document.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun line1 = paragraph.createRun();
        line1.setFontSize(17);
        line1.setText("Отчет внешнеторговых сделок компании SmartTech");
        line1.setItalic(true);
        line1.setBold(true);
        line1.addBreak();

        XWPFParagraph paragraph1 = document.createParagraph();

        XWPFRun line2 = paragraph1.createRun();

        line2.setFontSize(17);
        line2.setText("Общее количество сделок: " + tradesAmount);
        line2.addBreak();
        line2.addBreak();


        XWPFRun line3 = paragraph1.createRun();

        line3.setText("Сделки с наибольшей прибылью (экспорт): ");
        line3.setFontSize(17);
        line3.setItalic(true);
        line3.setBold(true);

        XWPFTable table = document.createTable();

        fillTable(exports, table);

        XWPFParagraph paragraph2 = document.createParagraph();
        XWPFRun line4 = paragraph2.createRun();

        line4.addBreak();
        line4.setFontSize(17);
        line4.setText("Наибольший расход (импорт): ");
        line4.setItalic(true);
        line4.setBold(true);

        XWPFTable table1 = document.createTable();

        fillTable(imports, table1);

        document.write(out);

        out.close();
        document.close();


        System.out.println("Word Document успешно создан!");
    }

    static void fillTable(List<TradeOperation> operations, XWPFTable table) {

        XWPFTableRow tableRowOne = table.getRow(0);

        tableRowOne.getCell(0).setText(" Компания ");

        tableRowOne.addNewTableCell().setText("   Общая стоимость   ");
        tableRowOne.addNewTableCell().setText("   Дата поставки   ");
        tableRowOne.addNewTableCell().setText("   Сотрудник   ");
        tableRowOne.addNewTableCell().setText("   Число товаров   ");


        int i = 0;
        for (TradeOperation item : operations) {
            i++;
            XWPFTableRow tableRow = table.createRow();
            System.out.println(item);


            Person person = item.getUsers().stream().findFirst().get().getPerson();

            OrderService orderService = new OrderService();

            List<Order> orders = orderService.findAllEntities().stream().
                    filter(x -> x.getOperation().getOperationId() == item.getOperationId()).collect(Collectors.toList());

            int itemsAmount = 0;
            for (Order order : orders) {
                itemsAmount += order.getItemAmount();
            }
            System.out.println(itemsAmount);
            tableRow.getCell(0).setText(item.getCompany().getCompanyName());
            tableRow.getCell(1).setText(item.getFullCost().toString());
            tableRow.getCell(2).setText(item.getSupplyDate());
            tableRow.getCell(3).setText(person.getSurname() + " " + person.getName());
            tableRow.getCell(4).setText(String.valueOf(itemsAmount));

            if(i == 5){
                break;
            }
        }

    }
}
