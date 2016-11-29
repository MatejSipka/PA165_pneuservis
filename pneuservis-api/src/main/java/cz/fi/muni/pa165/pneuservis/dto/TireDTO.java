/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.dto;

import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Matej Sipka
 */
public class TireDTO {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String description;

    private String typeOfCar;

    private BigDecimal price;

    private TireType type;

    private int catalogNumber;

    private int tireSize;

    private int profile;

    private int diameter;

    private TireManufacturer manufacturer;

    public TireDTO(TireType type, int catalogNumber, int tireSize, int diameter,
            TireManufacturer manufacturer, BigDecimal price, String description, String typeOfVehicle) {
        this.type = type;
        this.catalogNumber = catalogNumber;
        this.tireSize = tireSize;
        this.diameter = diameter;
        this.manufacturer = manufacturer;
        this.price = price;
        this.description = description;
        this.typeOfCar = typeOfVehicle;
    }

    public TireDTO() {

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

    public TireType getType() {
        return type;
    }

    public void setType(TireType type) {
        this.type = type;
    }

    public int getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(int catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public int getTireSize() {
        return tireSize;
    }

    public void setTireSize(int tireSize) {
        this.tireSize = tireSize;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public TireManufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(TireManufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + this.catalogNumber;
        hash = 29 * hash + this.tireSize;
        hash = 29 * hash + this.profile;
        hash = 29 * hash + this.diameter;
        hash = 29 * hash + Objects.hashCode(this.manufacturer);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null ||  !(obj instanceof TireDTO)) return false;
        final TireDTO other = (TireDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (this.catalogNumber != other.catalogNumber) {
            return false;
        }
        if (this.tireSize != other.tireSize) {
            return false;
        }
        if (this.profile != other.profile) {
            return false;
        }
        if (this.diameter != other.diameter) {
            return false;
        }
        if (this.manufacturer != other.manufacturer) {
            return false;
        }
        return true;
    }

}
