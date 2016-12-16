package cz.fi.muni.pa165.pneuservis.dto;

import cz.fi.muni.pa165.pneuservis.enums.PaymentType;

import java.util.List;
import java.util.Objects;

/**
 * @author vit.holasek on 23.11.2016.
 */
public class OrderDTO {
    private Long id;

    private Long clientId;

    private String note;

    private PaymentType paymentType;

    private boolean paymentConfirmed;

    private boolean shipped;

    private List<ServiceDTO> listOfServices;

    private List<TireDTO> listOfTires;

    public OrderDTO(Long id, Long clientId, String note, PaymentType paymentType, boolean paymentConfirmed, boolean shipped, List<ServiceDTO> listOfServices, List<TireDTO> listOfTires) {
        this.id = id;
        this.clientId = clientId;
        this.note = note;
        this.paymentType = paymentType;
        this.paymentConfirmed = paymentConfirmed;
        this.shipped = shipped;
        this.listOfServices = listOfServices;
        this.listOfTires = listOfTires;
    }

    public OrderDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isPaymentConfirmed() {
        return paymentConfirmed;
    }

    public void setPaymentConfirmed(boolean paymentConfirmed) {
        this.paymentConfirmed = paymentConfirmed;
    }

    public boolean isShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    public List<TireDTO> getListOfTires() {
        return listOfTires;
    }

    public void setListOfTires(List<TireDTO> listOfTires) {
        this.listOfTires = listOfTires;
    }

    public List<ServiceDTO> getListOfServices() {
        return listOfServices;
    }

    public void setListOfServices(List<ServiceDTO> listOfServices) {
        this.listOfServices = listOfServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ||  !(o instanceof OrderDTO)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return isPaymentConfirmed() == orderDTO.isPaymentConfirmed() &&
                isShipped() == orderDTO.isShipped() &&
                Objects.equals(getId(), orderDTO.getId()) &&
                Objects.equals(getClientId(), orderDTO.getClientId()) &&
                Objects.equals(getNote(), orderDTO.getNote()) &&
                getPaymentType() == orderDTO.getPaymentType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClientId(), getNote(), getPaymentType(), isPaymentConfirmed(), isShipped());
    }
}
