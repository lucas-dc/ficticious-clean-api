package br.com.ficticious_clean.api.data;

import java.math.BigDecimal;

public class FuelConsumptionRequestDTO {

    private BigDecimal fuelPrice;
    private BigDecimal cityRouteDistance;
    private BigDecimal highwayRouteDistance;

    public BigDecimal getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(BigDecimal fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public BigDecimal getCityRouteDistance() {
        return cityRouteDistance;
    }

    public void setCityRouteDistance(BigDecimal cityRouteDistance) {
        this.cityRouteDistance = cityRouteDistance;
    }

    public BigDecimal getHighwayRouteDistance() {
        return highwayRouteDistance;
    }

    public void setHighwayRouteDistance(BigDecimal highwayRouteDistance) {
        this.highwayRouteDistance = highwayRouteDistance;
    }
}
