package br.com.ficticious_clean.api.service;

import br.com.ficticious_clean.api.data.FuelConsumptionRequestDTO;
import br.com.ficticious_clean.api.data.FuelConsumptionResponseDTO;
import br.com.ficticious_clean.api.data.Vehicle;
import br.com.ficticious_clean.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public List<FuelConsumptionResponseDTO> getFuelCostForecast(FuelConsumptionRequestDTO fuelConsumptionRequestDTO) {
        List<FuelConsumptionResponseDTO> responseDTO = new ArrayList<>();

        List<Vehicle> vehicles = vehicleRepository.findAll();

        Optional.of(vehicles)
                .orElse(Collections.emptyList())
                .forEach(e -> {
                    FuelConsumptionResponseDTO fuelConsumptionResponseDTO = new FuelConsumptionResponseDTO();

                    BigDecimal cityConsumption = calculateFuelConsumption(fuelConsumptionRequestDTO.getCityRouteDistance(), e.getCityAverageFuelConsumption());
                    BigDecimal highwayConsumption = calculateFuelConsumption(fuelConsumptionRequestDTO.getHighwayRouteDistance(), e.getHighwayAverageFuelConsumption());
                    BigDecimal totalFuelConsumption = cityConsumption.add(highwayConsumption, new MathContext(3, RoundingMode.FLOOR));
                    BigDecimal totalFuelCost = calculateFuelCost(totalFuelConsumption, fuelConsumptionRequestDTO.getFuelPrice());

                    fuelConsumptionResponseDTO.setName(e.getName());
                    fuelConsumptionResponseDTO.setMake(e.getMake());
                    fuelConsumptionResponseDTO.setModel(e.getModel());
                    fuelConsumptionResponseDTO.setProductionYear(e.getProductionYear());
                    fuelConsumptionResponseDTO.setTotalFuelConsumption(totalFuelConsumption);
                    fuelConsumptionResponseDTO.setTotalFuelCost(totalFuelCost);

                    responseDTO.add(fuelConsumptionResponseDTO);
                });

        return responseDTO.stream()
                .sorted(Comparator.comparing(FuelConsumptionResponseDTO::getTotalFuelCost))
                .collect(Collectors.toList());
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    private BigDecimal calculateFuelConsumption(BigDecimal routeDistance, BigDecimal averageFuelConsumption) {
        return routeDistance.divide(averageFuelConsumption, 2, RoundingMode.FLOOR);
    }

    private BigDecimal calculateFuelCost(BigDecimal fuelConsumption, BigDecimal fuelPrice) {
        return fuelConsumption.multiply(fuelPrice, new MathContext(3, RoundingMode.FLOOR));
    }

}
