package br.com.ficticious_clean.api.service;

import br.com.ficticious_clean.api.data.FuelCostForecastRequestDTO;
import br.com.ficticious_clean.api.data.FuelCostForecastResponseDTO;
import br.com.ficticious_clean.api.data.Vehicle;
import br.com.ficticious_clean.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private Validator validator;

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public List<FuelCostForecastResponseDTO> getFuelCostForecast(FuelCostForecastRequestDTO fuelCostForecastRequestDTO) {
        validate(fuelCostForecastRequestDTO);

        List<FuelCostForecastResponseDTO> responseDTO = new ArrayList<>();
        List<Vehicle> vehicles = vehicleRepository.findAll();

        Optional.of(vehicles)
                .orElse(Collections.emptyList())
                .forEach(e -> {
                    FuelCostForecastResponseDTO fuelCostForecastResponseDTO = new FuelCostForecastResponseDTO();

                    BigDecimal cityConsumption = calculateFuelConsumption(fuelCostForecastRequestDTO.getCityRouteDistance(), e.getCityAverageFuelConsumption());
                    BigDecimal highwayConsumption = calculateFuelConsumption(fuelCostForecastRequestDTO.getHighwayRouteDistance(), e.getHighwayAverageFuelConsumption());
                    BigDecimal totalFuelConsumption = cityConsumption.add(highwayConsumption, new MathContext(3, RoundingMode.FLOOR));
                    BigDecimal totalFuelCost = calculateFuelCost(totalFuelConsumption, fuelCostForecastRequestDTO.getFuelPrice());

                    fuelCostForecastResponseDTO.setName(e.getName());
                    fuelCostForecastResponseDTO.setMake(e.getMake());
                    fuelCostForecastResponseDTO.setModel(e.getModel());
                    fuelCostForecastResponseDTO.setProductionYear(e.getProductionYear());
                    fuelCostForecastResponseDTO.setTotalFuelConsumption(totalFuelConsumption);
                    fuelCostForecastResponseDTO.setTotalFuelCost(totalFuelCost);

                    responseDTO.add(fuelCostForecastResponseDTO);
                });

        return responseDTO.stream()
                .sorted(Comparator.comparing(FuelCostForecastResponseDTO::getTotalFuelCost))
                .collect(Collectors.toList());
    }

    public Vehicle save(Vehicle vehicle) {
        validate(vehicle);
        return vehicleRepository.save(vehicle);
    }

    private BigDecimal calculateFuelConsumption(BigDecimal routeDistance, BigDecimal averageFuelConsumption) {
        return routeDistance.divide(averageFuelConsumption, 2, RoundingMode.FLOOR);
    }

    private BigDecimal calculateFuelCost(BigDecimal fuelConsumption, BigDecimal fuelPrice) {
        return fuelConsumption.multiply(fuelPrice, new MathContext(3, RoundingMode.FLOOR));
    }

    private void validate(Object obj) {
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
