package org.project.TCP;

import org.project.enums.RequestType;

public class Request {
    private RequestType requestType;
    private String requestMessage;

    public Request(RequestType requestType, String requestMessage) {
        this.requestType = requestType;
        this.requestMessage = requestMessage;
    }

    public Request() {
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

}
