/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.entity;

import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 *
 * @author Ivan Moscovic
 */
@Entity
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String description;

    @NotNull
    @Column(nullable = false)
    private String typeOfCar;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private BigDecimal price;
    
    @NotNull
    @Column(nullable = false)
    @Min(0)
    private int duration;

    @NotNull
    @Column(nullable = false)
    private boolean ownParts;

    @NotNull
    @Column(nullable = false)
    @Size(min=1, max=250)
    private String nameOfService;

    public Services(int duration, boolean ownParts, String nameOfService,
                   BigDecimal price, String description, String typeOfCar) {
        this.duration = duration;
        this.ownParts = ownParts;
        this.nameOfService = nameOfService;
        this.price = price;
        this.description = description;
        this.typeOfCar = typeOfCar;
    }

    public Services() {
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeOfCar() {
        return typeOfCar;
    }

    public void setTypeOfCar(String typeOfCar) {
        this.typeOfCar = typeOfCar;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean hasOwnParts() {
        return ownParts;
    }

    public void setOwnParts(boolean ownParts) {
        this.ownParts = ownParts;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getNameOfService() {
        return nameOfService;
    }

    public void setNameOfService(String nameOfService) {
        this.nameOfService = nameOfService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Services)) {
            return false;
        }

        final Services service = (Services) o;

        if (!Objects.equals(this.nameOfService, service.getNameOfService())) {
            return false;
        }
        if (!Objects.equals(this.duration, service.getDuration())) {
            return false;
        }
        if (!Objects.equals(this.ownParts, service.hasOwnParts())) {
            return false;
        }
        if (!Objects.equals(this.price, service.price)) {
            return false;
        }
        if (!Objects.equals(this.typeOfCar, service.getTypeOfCar())) {
            return false;
        }
        if (!Objects.equals(this.id, service.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.duration);
        hash = 89 * hash + Objects.hashCode(this.ownParts);
        hash = 89 * hash + Objects.hashCode(this.nameOfService);
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.typeOfCar);
        hash = 89 * hash + Objects.hashCode(this.price);

        return hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
