package br.com.ficticious_clean.api.data;

import java.math.BigDecimal;

public class FuelCostForecastResponseDTO {

    private String name;
    private String make;
    private String model;
    private Integer productionYear;
    private BigDecimal totalFuelConsumption;
    private BigDecimal totalFuelCost;

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

    public BigDecimal getTotalFuelConsumption() {
        return totalFuelConsumption;
    }

    public void setTotalFuelConsumption(BigDecimal totalFuelConsumption) {
        this.totalFuelConsumption = totalFuelConsumption;
    }

    public BigDecimal getTotalFuelCost() {
        return totalFuelCost;
    }

    public void setTotalFuelCost(BigDecimal totalFuelCost) {
        this.totalFuelCost = totalFuelCost;
    }
}
