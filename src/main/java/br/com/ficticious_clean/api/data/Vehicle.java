package br.com.ficticious_clean.api.data;


import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="'name' parameter is mandatory and cannot be null nor empty")
    private String name;
    @NotBlank(message="'make' parameter is mandatory and cannot be null nor empty")
    private String make;
    @NotBlank(message="'model' parameter is mandatory and cannot be null nor empty")
    private String model;

    @Range(min = 1880L, max = 2025L, message="'productionYear' parameter must be between 1880 and 2025")
    @NotNull(message="'productionYear' parameter is mandatory and cannot be null")
    private Integer productionYear;

    @DecimalMin(value = "0.0", inclusive = false, message="'cityAverageFuelConsumption' parameter must be greater than 0")
    @NotNull(message="'cityAverageFuelConsumption' parameter cannot be null")
    private BigDecimal cityAverageFuelConsumption;

    @DecimalMin(value = "0.0", inclusive = false, message="'highwayAverageFuelConsumption' parameter must be greater than 0")
    @NotNull(message="'highwayAverageFuelConsumption' parameter cannot be null")
    private BigDecimal highwayAverageFuelConsumption;

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

    public BigDecimal getCityAverageFuelConsumption() {
        return cityAverageFuelConsumption;
    }

    public void setCityAverageFuelConsumption(BigDecimal cityAverageFuelConsumption) {
        this.cityAverageFuelConsumption = cityAverageFuelConsumption;
    }

    public BigDecimal getHighwayAverageFuelConsumption() {
        return highwayAverageFuelConsumption;
    }

    public void setHighwayAverageFuelConsumption(BigDecimal highwayAverageFuelConsumption) {
        this.highwayAverageFuelConsumption = highwayAverageFuelConsumption;
    }
}
