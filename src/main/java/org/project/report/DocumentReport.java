package org.project.report;

import java.io.*;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.*;
import org.project.entities.Order;
import org.project.entities.Person;
import org.project.entities.TradeOperation;


public class DocumentReport {
    void makeDocument(int tradesAmount, List<TradeOperation> exports, List<TradeOperation> imports) throws IOException {

        XWPFDocument document = new XWPFDocument();

        FileOutputStream out = new FileOutputStream(new File("report.docx"));

        XWPFParagraph paragraph = document.createParagraph();

        XWPFRun line1 = paragraph.createRun();


        line1.setFontSize(17);
        line1.setText("Общее количество сделок: " + tradesAmount);
        line1.addBreak();


        XWPFRun line2 = paragraph.createRun();

        line2.setText("Сделки с наибольшей прибылью (экспорт): ");
        line2.setFontSize(17);
        line2.setItalic(true);
        line2.addBreak();
        line2.setBold(true);

        XWPFTable table = document.createTable();

        fillTable(exports, table);

        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun line3 = paragraph1.createRun();

        line3.setFontSize(17);
        line3.setText("Наибольший расход (импорт): ");

        XWPFTable table1 = document.createTable();

        fillTable(imports, table1);

        document.write(out);


        out.close();
        document.close();


        System.out.println("Word Document успешно создан!");
    }

    void fillTable(List<TradeOperation> imports, XWPFTable table) {

        XWPFTableRow tableRowOne = table.getRow(0);

        tableRowOne.getCell(0).setText(" Компания ");

        tableRowOne.addNewTableCell().setText(" Общая стоимость ");
        tableRowOne.addNewTableCell().setText(" Дата поставки ");
        tableRowOne.addNewTableCell().setText(" Сотрудник ");
        tableRowOne.addNewTableCell().setText(" Число товаров ");

        for(int i = 0; i < 5; i++){
            for(TradeOperation item : imports){
                XWPFTableRow tableRow = table.createRow();

                Person person = item.getUsers().stream().findFirst().get().getPerson();

                int itemsAmount = 0;
                for(Order order : item.getOrders()){
                    itemsAmount += order.getItemAmount();
                }
                tableRow.getCell(0).setText(item.getCompany().getCompanyName());
                tableRow.getCell(1).setText(item.getFullCost().toString());
                tableRow.getCell(2).setText(item.getSupplyDate());
                tableRow.getCell(3).setText(person.getSurname() + person.getName());
                tableRow.getCell(4).setText(String.valueOf(itemsAmount));
            }

        }
    }


}
