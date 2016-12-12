package cz.fi.muni.pa165.pneuservis.services;

import cz.fi.muni.pa165.pneuservis.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by vit.holasek on 24.11.2016.
 */
public class OrderBilling {
    private BigDecimal price;

    private BigDecimal priceWithVAT;

    private List<BillingItem> billingItems;

    private Order order;

    public OrderBilling(Long id, BigDecimal price, BigDecimal priceWithVAT, List<BillingItem> billingItems, Order order) {
        this.price = price;
        this.priceWithVAT = priceWithVAT;
        this.billingItems = billingItems;
        this.order = order;
    }

    public OrderBilling() { }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithVAT() {
        return priceWithVAT;
    }

    public void setPriceWithVAT(BigDecimal priceWithVAT) {
        this.priceWithVAT = priceWithVAT;
    }

    public List<BillingItem> getBillingItems() {
        return billingItems;
    }

    public void setBillingItems(List<BillingItem> billingItems) {
        this.billingItems = billingItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderBilling that = (OrderBilling) o;
        return Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getPriceWithVAT(), that.getPriceWithVAT()) &&
                Objects.equals(getBillingItems(), that.getBillingItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice(), getPriceWithVAT(), getBillingItems());
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
