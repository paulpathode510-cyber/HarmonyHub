//This is the DTO(Data Transfer Object) for Receiving the Integer Id of the person, To whom we want to send the connection-Request(ReceiverId (Database Term))

package com.soft.dto;

//DTO helps us to avoid direct Contact Between User and databases
public class ConnectionRequestDTO {
    private Integer receiver_id;

    public Integer getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(Integer receiver_id) {
        this.receiver_id = receiver_id;
    }
    
}
