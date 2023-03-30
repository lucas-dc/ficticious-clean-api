package br.com.ficticious_clean.api.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String make;
    private String model;
    private Integer productionYear;
    private BigDecimal cityAverageGasConsumption;
    private BigDecimal highwayAverageGasConsumption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public BigDecimal getCityAverageGasConsumption() {
        return cityAverageGasConsumption;
    }

    public void setCityAverageGasConsumption(BigDecimal cityAverageGasConsumption) {
        this.cityAverageGasConsumption = cityAverageGasConsumption;
    }

    public BigDecimal getHighwayAverageGasConsumption() {
        return highwayAverageGasConsumption;
    }

    public void setHighwayAverageGasConsumption(BigDecimal highwayAverageGasConsumption) {
        this.highwayAverageGasConsumption = highwayAverageGasConsumption;
    }
}
