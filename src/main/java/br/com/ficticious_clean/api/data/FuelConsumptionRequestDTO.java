package br.com.ficticious_clean.api.data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class FuelConsumptionRequestDTO {

    @Positive(message="'fuelPrice' parameter must be greater than 0")
    @NotNull(message="'fuelPrice' parameter cannot be null")
    private BigDecimal fuelPrice;

    @PositiveOrZero(message="'cityRouteDistance' must be greater than or equal to 0")
    @NotNull(message="'cityRouteDistance' parameter cannot be null")
    private BigDecimal cityRouteDistance;

    @PositiveOrZero(message="'highwayRouteDistance' must be greater than or equal to 0")
    @NotNull(message="'highwayRouteDistance' parameter cannot be null")
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
