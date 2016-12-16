package cz.fi.muni.pa165.pneuservis.dto;

import cz.fi.muni.pa165.pneuservis.enums.PaymentType;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author vit.holasek on 24.11.2016.
 */
public class UpdateOrderDTO {
    private Long id;

    private Long clientId;

    private Set<ServiceDTO> services;

    private Set<TireDTO> tires;

    private String note;

    private PaymentType paymentType;

    private boolean paymentConfirmed;

    private boolean shipped;

    public UpdateOrderDTO(Long id, Long clientId, Set<ServiceDTO> services, Set<TireDTO> tires, String note, PaymentType paymentType, boolean paymentConfirmed, boolean shipped) {
        this.id = id;
        this.clientId = clientId;
        this.services = services;
        this.tires = tires;
        this.note = note;
        this.paymentType = paymentType;
        this.paymentConfirmed = paymentConfirmed;
        this.shipped = shipped;
    }

    public UpdateOrderDTO() { }

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

    public boolean getPaymentConfirmed() {
        return paymentConfirmed;
    }

    public void setPaymentConfirmed(boolean paymentConfirmed) {
        this.paymentConfirmed = paymentConfirmed;
    }

    public boolean getShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ||  !(o instanceof UpdateOrderDTO)) return false;
        UpdateOrderDTO that = (UpdateOrderDTO) o;
        return getPaymentConfirmed() == that.getPaymentConfirmed() &&
                getShipped() == that.getShipped() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getClientId(), that.getClientId()) &&
                Objects.equals(getServices(), that.getServices()) &&
                Objects.equals(getTires(), that.getTires()) &&
                Objects.equals(getNote(), that.getNote()) &&
                getPaymentType() == that.getPaymentType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClientId(), getServices(), getTires(), getNote(), getPaymentType(), getPaymentConfirmed(), getShipped());
    }
}
