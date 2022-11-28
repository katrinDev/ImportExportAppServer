package org.project.utilities;

import com.google.gson.Gson;
import org.project.TCP.Request;
import org.project.TCP.Response;
import org.project.entities.Person;
import org.project.entities.Role;
import org.project.entities.User;
import org.project.enums.ResponseStatus;
import org.project.serverMain.Main;
import org.project.services.PersonService;
import org.project.services.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
        UserService userService;
        PersonService personService;
        try{
            while (clientSocket.isConnected()) {
                String message = String.valueOf(in.readObject());
                request = gson.fromJson(message, Request.class);
                System.out.println(message);

                switch(request.getRequestType()) {
                    case LOGIN: {
                        User requestUser = gson.fromJson(request.getRequestMessage(), User.class);
                        System.out.println(requestUser);
                        userService = new UserService();
                        if(userService.findAllEntities().stream().anyMatch(x -> x.getLogin().equals(requestUser.getLogin())) && userService.findAllEntities().stream().anyMatch(x -> x.getPassword().equals(requestUser.getPassword()))){
                            User user = userService.findAllEntities().stream().filter(x -> Objects.equals(x.getLogin(), requestUser.getLogin())).collect(Collectors.toList()).get(0);

                            System.out.println(user);
                            response = new Response(ResponseStatus.OK, "Аутентификация прошла успешно!", new Gson().toJson(user.getRole()));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Неправильный логин или пароль!", "");
                        }
                        out.writeObject(new Gson().toJson(response));
                        System.out.println(response.getResponseMessage());
                        break;
                    }
                    case SIGNUP: {
                        User registerUser = gson.fromJson(request.getRequestMessage(), User.class);
                        System.out.println(registerUser);

                        personService = new PersonService();
                        if(personService.findAllEntities().stream().anyMatch(x -> x.getSurname().equals(registerUser.getPerson().getSurname())) && personService.findAllEntities().stream().anyMatch(x -> x.getName().equals(registerUser.getPerson().getName()))){
                            userService = new UserService();
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
                        out.writeObject(new Gson().toJson(response));
                        System.out.println(response.getResponseMessage());

                        break;
                    }
                }
            }
        } catch(ClassNotFoundException | IOException e){
            closeEverything();
            removeClient(clientSocket);
        } catch(NullPointerException e){
            System.out.println("");
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
