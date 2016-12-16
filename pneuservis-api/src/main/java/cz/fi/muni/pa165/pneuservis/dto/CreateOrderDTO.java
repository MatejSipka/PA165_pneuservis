package cz.fi.muni.pa165.pneuservis.dto;

import cz.fi.muni.pa165.pneuservis.enums.PaymentType;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *  @author vit.holasek on 24.11.2016.
 */
public class CreateOrderDTO {

    private Long clientId;

    private Set<ServiceDTO> services;

    private Set<TireDTO> tires;

    private String note;

    private PaymentType PaymentType;

    public CreateOrderDTO(Long clientId, Set<ServiceDTO> services, String note, PaymentType paymentType) {
        this.clientId = clientId;
        this.services = services;
        this.note = note;
        PaymentType = paymentType;
    }

    public CreateOrderDTO() { }

    public CreateOrderDTO(Set<ServiceDTO> services) {
        this.services = services;
    }

    public Set<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(Set<ServiceDTO> services) {
        this.services = services;
    }

    public Set<TireDTO> getTires() {
        return tires;
    }

    public void setTires(Set<TireDTO> tires) {
        this.tires = tires;
    }

    public cz.fi.muni.pa165.pneuservis.enums.PaymentType getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(cz.fi.muni.pa165.pneuservis.enums.PaymentType paymentType) {
        PaymentType = paymentType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ||  !(o instanceof CreateOrderDTO)) return false;
        CreateOrderDTO that = (CreateOrderDTO) o;
        return Objects.equals(getClientId(), that.getClientId()) &&
                Objects.equals(getNote(), that.getNote()) &&
                getPaymentType() == that.getPaymentType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientId(), getNote(), getPaymentType());
    }
}
