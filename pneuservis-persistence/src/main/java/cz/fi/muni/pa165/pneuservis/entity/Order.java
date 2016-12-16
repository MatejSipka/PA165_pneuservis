/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.entity;

import cz.fi.muni.pa165.pneuservis.enums.PaymentType;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author xbonco1 Order class
 */
@Entity
@Table(name = "PROJECT_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="ORDER_ID")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long clientId;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name="ORDER_SERVICE", joinColumns=@JoinColumn(name="ORDER_ID"), inverseJoinColumns=@JoinColumn(name="SERVICE_ID"))
    private Set<Services> services;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name="ORDER_TIRE", joinColumns=@JoinColumn(name="ORDER_ID"), inverseJoinColumns=@JoinColumn(name="TIRE_ID"))
    private Set<Tire> tires;

    @Basic(optional = true)
    private String note;

    private boolean paymentConfirmed;

    private boolean shipped;

    private PaymentType paymentType;

    public Order(Long clientId, Set<Services> services, Set<Tire> tires, String note, boolean paymentConfirmed, boolean shipped, PaymentType paymentType) {
        this.clientId = clientId;
        this.services = services;
        this.tires = tires;
        this.note = note;
        this.paymentConfirmed = paymentConfirmed;
        this.shipped = shipped;
        this.paymentType = paymentType;
    }

    public Order() {
        tires = new HashSet<>();
        services = new HashSet<>();
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

    public void addService(Services service) {
        services.add(service);
    }

    public void addTire(Tire tire) {
        tires.add(tire);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setServices(Set<Services> services) {
        this.services = services;
    }

    public Set<Services> getServices() {
        return this.services;
    }

    public void setTires(Set<Tire> tires) {
        this.tires = tires;
    }

    public Set<Tire> getTires() {
        return this.tires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Order)) return false;
        Order order = (Order) o;
        return isPaymentConfirmed() == order.isPaymentConfirmed() &&
                isShipped() == order.isShipped() &&
                Objects.equals(getId(), order.getId()) &&
                Objects.equals(getClientId(), order.getClientId()) &&
                Objects.equals(getNote(), order.getNote()) &&
                getPaymentType() == order.getPaymentType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClientId(), getNote(), isPaymentConfirmed(), isShipped(), getPaymentType());
    }
}
