package org.project.TCP;

import org.project.enums.ResponseStatus;

public class Response {
    private ResponseStatus responseStatus;
    private String responseMessage;
    private String responseData;

    public Response() {
    }

    public Response(ResponseStatus responseStatus, String responseMessage, String responseData) {
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
        this.responseData = responseData;
    }

    public String getResponseData() {
        return responseData;
    }

    public Response(ResponseStatus responseStatus, String responseMessage) {
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

}
