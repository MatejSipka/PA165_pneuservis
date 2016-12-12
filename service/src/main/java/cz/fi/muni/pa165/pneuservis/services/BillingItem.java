package cz.fi.muni.pa165.pneuservis.services;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by vit.holasek on 25.11.2016.
 */
public class BillingItem {
    private String description;

    private int VAT;

    private BigDecimal price;

    private BigDecimal priceWithVAT;

    public BillingItem(String description, int VAT, BigDecimal price, BigDecimal priceWithVAT) {
        this.description = description;
        this.VAT = VAT;
        this.price = price;
        this.priceWithVAT = priceWithVAT;
    }

    public BillingItem() { }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVAT() {
        return VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillingItem that = (BillingItem) o;
        return getVAT() == that.getVAT() &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getPriceWithVAT(), that.getPriceWithVAT());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getVAT(), getPrice(), getPriceWithVAT());
    }
}
