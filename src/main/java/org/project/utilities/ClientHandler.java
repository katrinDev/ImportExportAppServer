package org.project.utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.project.TCP.Request;
import org.project.TCP.Response;
import org.project.entities.*;
import org.project.enums.ResponseStatus;
import org.project.report.CountReport;
import org.project.serverMain.Main;
import org.project.services.*;
import org.project.strategy.CalcStrategy;
import org.project.strategy.CostCalculator;
import org.project.strategy.ExportCalcStrategy;
import org.project.strategy.ImportCalcStrategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private Request request;
    private Response response;
    private Gson gson;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket clientSocket) {
        try{response = new Response();
            request = new Request();
            this.clientSocket = clientSocket;
            gson = new Gson();

            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch(IOException e){
            removeClient(this.clientSocket);
        }
    }


    @Override
    public void run(){
        UserService userService = new UserService();
        PersonService personService = new PersonService();
        CompanyService companyService = new CompanyService();
        OperationService operationService = new OperationService();
        ItemService itemService = new ItemService();
        OrderService orderService = new OrderService();


        try{
            while (clientSocket.isConnected()) {

                String message = String.valueOf(in.readObject());
                request = gson.fromJson(message, Request.class);
                System.out.println(message);

                switch(request.getRequestType()) {
                    case LOGIN: {
                        User requestUser = gson.fromJson(request.getRequestMessage(), User.class);
                        System.out.println(requestUser);
                        if(userService.findAllEntities().stream().anyMatch(x -> x.getLogin().equals(requestUser.getLogin())) && userService.findAllEntities().stream().anyMatch(x -> x.getPassword().equals(requestUser.getPassword()))){
                            User user = userService.findAllEntities().stream().filter(x -> Objects.equals(x.getLogin(), requestUser.getLogin())).collect(Collectors.toList()).get(0);

                            System.out.println(user);
                            response = new Response(ResponseStatus.OK, "Аутентификация прошла успешно!", new Gson().toJson(user));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Неправильный логин или пароль!", "");
                        }

                        break;
                    }
                    case SIGNUP: {
                        User registerUser = gson.fromJson(request.getRequestMessage(), User.class);
                        System.out.println(registerUser);

                        personService = new PersonService();
                        if(personService.findAllEntities().stream().anyMatch(x -> x.getSurname().equals(registerUser.getPerson().getSurname())) && personService.findAllEntities().stream().anyMatch(x -> x.getName().equals(registerUser.getPerson().getName()))){
                            if(userService.findAllEntities().stream().noneMatch(x -> x.getLogin().equals(registerUser.getLogin()))){
                                Person person = personService.findAllEntities().stream().filter(x -> x.getSurname().equals(registerUser.getPerson().getSurname()) && x.getName().equals(registerUser.getPerson().getName())).collect(Collectors.toList()).get(0);
                                registerUser.setPerson(person);
                                Role employeeRole = new Role(2, "employee");
                                registerUser.setRole(employeeRole);

                                userService.saveEntity(registerUser);
                                System.out.println(registerUser);
                                response = new Response(ResponseStatus.OK, "Регистрация прошла успешно!", new Gson().toJson(registerUser));
                            } else {
                                response = new Response(ResponseStatus.ERROR, "Пользователь с данным логином уже существует!", "");
                            }
                        } else{
                            response = new Response(ResponseStatus.ERROR, "Указанного сотрудника не существует!", "");
                        }

                        break;
                    }
                    case SHOWUSERS:{
                        ArrayList<User> allUsers = (ArrayList<User>) userService.findAllEntities();
                        if(allUsers != null){
                            response = new Response(ResponseStatus.OK, "Список пользователей", new Gson().toJson(allUsers));
                        } else{
                            response = new Response(ResponseStatus.ERROR, "Список пользователей пуст!", "");
                        }

                        break;
                    }
                    case CHANGEROLE:{
                        User updateUser = gson.fromJson(request.getRequestMessage(), User.class);
                        System.out.println(updateUser);
                        User fullUser = userService.findEntity(updateUser.getUserId());

                        if(fullUser.getRole().getRoleId() == 1){
                            fullUser.setRole(new Role(2, "employee"));
                        } else {
                            fullUser.setRole(new Role(1, "admin"));
                        }
                        userService.updateEntity(fullUser);

                        if(fullUser != null){
                            response = new Response(ResponseStatus.OK, "Роль изменена", gson.toJson(fullUser));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Такого аккаунта не существует", "");
                        }

                        break;
                    }

                    case DELETEUSER:{
                        User delUser = gson.fromJson(request.getRequestMessage(), User.class);
                        System.out.println(delUser);

                        User fullUser = userService.findEntity(delUser.getUserId());
                        userService.deleteEntity(fullUser);

                        if(fullUser != null){
                            response = new Response(ResponseStatus.OK, "Аккаунт успешно удален", gson.toJson(fullUser));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Такого аккаунта не существует", "");
                        }

                        break;
                    }
                    case SHOWPEOPLE:{
                        ArrayList<Person> allPeople = (ArrayList<Person>) personService.findAllEntities();
                        if(allPeople != null){
                            response = new Response(ResponseStatus.OK, "Список сотрудников", new Gson().toJson(allPeople));
                        } else{
                            response = new Response(ResponseStatus.ERROR, "Список сотрудников пуст!", "");
                        }
                        break;
                    }
                    case CHANGEPERSON: {
                        Person updatePerson = gson.fromJson(request.getRequestMessage(), Person.class);
                        System.out.println(updatePerson);

                        personService.updateEntity(updatePerson);

                        if(updatePerson != null){
                            response = new Response(ResponseStatus.OK, "Информация о сотруднике изменена", gson.toJson(updatePerson));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Такого сотрудника не существует", "");
                        }
                        break;
                    }
                    case DELETEPERSON: {
                        Person delPerson = gson.fromJson(request.getRequestMessage(), Person.class);
                        System.out.println(delPerson);

                        personService.deleteEntity(delPerson);

                        if(delPerson != null){
                            response = new Response(ResponseStatus.OK, "Сотрудник успешно удален", gson.toJson(delPerson));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Такого сотрудника не существует", "");
                        }

                        break;
                    }
                    case ADDPERON:{
                        Person addPerson = gson.fromJson(request.getRequestMessage(), Person.class);
                        System.out.println("\n\n" + addPerson);

                        personService.saveEntity(addPerson);

                        if(addPerson != null){
                            response = new Response(ResponseStatus.OK, "Сотрудник успешно добавлен", gson.toJson(addPerson));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Не удалось добавить сотрудника!", "");
                        }

                        break;
                    }
                    case SHOWCOMPANIES: {
                        ArrayList<Company> allCompanies = (ArrayList<Company>) companyService.findAllEntities();
                        if(allCompanies != null){
                            response = new Response(ResponseStatus.OK, "Список компаний", new Gson().toJson(allCompanies));
                        } else{
                            response = new Response(ResponseStatus.ERROR, "Список компаний пуст!", "");
                        }
                        break;
                    }
                    //!!!!!!!!!!!!
                    case DELETECOMPANY: {
                        Company delCompany = gson.fromJson(request.getRequestMessage(), Company.class);
                        System.out.println(delCompany);

                        if(operationService.findAllEntities().stream().anyMatch(x -> x.getCompany().equals(delCompany))){
                            response = new Response(ResponseStatus.ERROR, "Компания участвовала во многих внешнеторговых операциях! Сначала необходимо удалить их!", "");
                        } else {
                            companyService.deleteEntity(delCompany);

                            if(delCompany != null){
                                response = new Response(ResponseStatus.OK, "Компания-партнер успешно удалена", gson.toJson(delCompany));
                            } else {
                                response = new Response(ResponseStatus.ERROR, "Данная компания не найдена", "");
                            }
                        }

                        break;
                    }

                    case ADDCOMPANY: {
                        Company addCompany = gson.fromJson(request.getRequestMessage(), Company.class);
                        System.out.println("\n\n" + addCompany);

                        companyService.saveEntity(addCompany);

                        if(addCompany != null){
                            response = new Response(ResponseStatus.OK, "Компания успешно добавлена", gson.toJson(addCompany));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Не удалось добавить компанию!", "");
                        }
                        break;
                    }

                    case SHOWITEMS:{
                        ArrayList<Item> allItems = (ArrayList<Item>) itemService.findAllEntities();
                        if(allItems != null){
                            response = new Response(ResponseStatus.OK, "Список товаров", new Gson().toJson(allItems));
                        } else{
                            response = new Response(ResponseStatus.ERROR, "Список товаров пуст!", "");
                        }
                        break;
                    }
                    case ADDITEM:{
                        Item addItem = gson.fromJson(request.getRequestMessage(), Item.class);
                        System.out.println("\n\n" + addItem);

                        itemService.saveEntity(addItem);

                        if(addItem != null){
                            response = new Response(ResponseStatus.OK, "Товар успешно добавлен", gson.toJson(addItem));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Не удалось добавить товар!", "");
                        }
                        break;
                    }
                    case CHANGEITEM:{
                        Item updateItem = gson.fromJson(request.getRequestMessage(), Item.class);
                        System.out.println(updateItem);

                        itemService.updateEntity(updateItem);

                        if(updateItem != null){
                            response = new Response(ResponseStatus.OK, "Товар успешно изменен", gson.toJson(updateItem));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Такого товара не существует", "");
                        }
                        break;
                    }
                    case DELETEITEM:{
                        Item delItem = gson.fromJson(request.getRequestMessage(), Item.class);
                        System.out.println(delItem);

                        itemService.deleteEntity(delItem);

                        if(delItem != null){
                            response = new Response(ResponseStatus.OK, "Товар успешно удален", gson.toJson(delItem));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Такого товара не существует", "");
                        }
                        break;
                    }
                    case ADDTRADE:{

                        TradeOperation addOperation = gson.fromJson(request.getRequestMessage(), TradeOperation.class);
                        System.out.println("\n\n" + addOperation);


                        Company company = companyService.findAllEntities().stream().filter(x -> Objects.equals(x.getCompanyName(), addOperation.getCompany().getCompanyName())).collect(Collectors.toList()).get(0);

                        addOperation.setCompany(company);

                        System.out.println("\n\n" + addOperation);


                        operationService.saveEntity(addOperation);

                        if(addOperation != null){
                            response = new Response(ResponseStatus.OK, "Операция успешно добавлена", gson.toJson(addOperation));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Не удалось добавить операцию!", "");
                        }
                        break;
                    }
                    case ADDTRADEORDERS: {
                        Type type = new TypeToken<List<Order>>(){}.getType();
                        List<Order> tradeOrders = new Gson().fromJson(request.getRequestMessage(), type);

                        int tradeId = OperationService.getTradeId();
                        TradeOperation operation = operationService.findEntity(tradeId);

                        ArrayList<Integer> ids = new ArrayList<>();
                        for(Order i : tradeOrders){

                            i.setOperation(operation);
                            ids.add(orderService.saveEntity(i));
                        }

                        if(!ids.isEmpty()){
                            response = new Response(ResponseStatus.OK, "Заказы успешно добавлены", gson.toJson(ids));
                        } else{
                            response = new Response(ResponseStatus.ERROR, "Не удалось добавить заказы!", "");
                        }

                        CalcStrategy calcStrategy = null;

                        if(operation.getOperationType().equals("export")){
                            calcStrategy = new ExportCalcStrategy();
                        } else if (operation.getOperationType().equals("import")){
                            calcStrategy = new ImportCalcStrategy();
                        }

                        CostCalculator costCalculator = new CostCalculator(calcStrategy);
                        operation.setFullCost(costCalculator.countCost(tradeOrders));

                        operationService.updateEntity(operation);

                        break;
                    }

                    case ADDTRADEUSER:{
                        User tradeUser = new Gson().fromJson(request.getRequestMessage(), User.class);
                        int tradeId = OperationService.getTradeId();
                        TradeOperation operation = operationService.findEntity(tradeId);

                        tradeUser.getOperations().add(operation);
                        operation.getUsers().add(tradeUser);

                        userService.updateEntity(tradeUser);
                        operationService.updateEntity(operation);
                        break;
                    }

                    case SHOWMYEXPTRADES:{
                        User user = new Gson().fromJson(request.getRequestMessage(), User.class);

                        List<TradeOperation> expTrades = operationService.findAllEntities().stream().filter(x -> (x.getUsers().contains(user) && x.getOperationType().equals("export"))).collect(Collectors.toList());

                        for(TradeOperation i : expTrades){
                            System.out.println(i);
                        }


                        if(expTrades != null){
                            response = new Response(ResponseStatus.OK, "Список экспортных операций", new Gson().toJson(expTrades));
                        } else{
                            response = new Response(ResponseStatus.ERROR, "Список экспортных операций пуст!", "");
                        }
                        break;
                    }

                    case SHOWMYIMPTRADES:{
                        User user = new Gson().fromJson(request.getRequestMessage(), User.class);

                        List<TradeOperation> impTrades = operationService.findAllEntities().stream().filter(x -> (x.getUsers().contains(user) && x.getOperationType().equals("import"))).collect(Collectors.toList());

                        for(TradeOperation i : impTrades){
                            System.out.println(i);
                        }


                        if(impTrades != null){
                            response = new Response(ResponseStatus.OK, "Список импортных операций", new Gson().toJson(impTrades));
                        } else{
                            response = new Response(ResponseStatus.ERROR, "Список импортных операций пуст!", "");
                        }
                        break;
                    }
                    case DELETETRADE:{

                        TradeOperation delTrade = gson.fromJson(request.getRequestMessage(), TradeOperation.class);
                        System.out.println(delTrade);

                        operationService.deleteEntity(delTrade);

                        if(delTrade != null){
                            response = new Response(ResponseStatus.OK, "Операция успешно удалена", gson.toJson(delTrade));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Такой операции нет в списке", "");
                        }
                        break;
                    }
                    case SHOWMYPARTNERS:{
                        User user = new Gson().fromJson(request.getRequestMessage(), User.class);
                        List<Company> companies = operationService.findAllEntities().stream().filter(x -> x.getUsers().contains(user)).collect(Collectors.toList()).stream().map(TradeOperation::getCompany).collect(Collectors.toList());
                        for(Company i : companies){
                            System.out.println(i);
                        }

                        if(companies != null){
                            response = new Response(ResponseStatus.OK, "Список Ваших партнеров", new Gson().toJson(companies));
                        } else{
                            response = new Response(ResponseStatus.ERROR, "Список пуст!", "");
                        }
                        break;
                    }

                    case CREATEREPORT:{

                        CountReport.countReportData();
                        response = new Response(ResponseStatus.OK, "Отчет успешно создан и добавлен в файл!", "");

                        break;
                    }

                }

                out.writeObject(new Gson().toJson(response));
                out.flush();

                System.out.println(response.getResponseMessage());
            }

        } catch(Exception e){
            System.out.println(e.getMessage());
            e.getStackTrace();
        } finally{
            closeEverything();
            removeClient(clientSocket);
        }
    }

    public void closeEverything() {
        try{
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
            if(clientSocket != null){
                clientSocket.close();
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void removeClient(Socket clientSocket){
        System.out.println("\nКлиент" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " завершил работу");
        Main.getCurrentSockets().remove(clientSocket);
        System.out.println("Количество действующих подключений: " + (long) Main.getCurrentSockets().size());
    }
}
