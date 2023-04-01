package br.com.ficticious_clean.api;

import br.com.ficticious_clean.api.data.FuelCostForecastRequestDTO;
import br.com.ficticious_clean.api.data.FuelCostForecastResponseDTO;
import br.com.ficticious_clean.api.data.Vehicle;
import br.com.ficticious_clean.api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> findAll() {
        return vehicleService.findAll();
    }

    @GetMapping("/fuel-cost-forecast")
    public List<FuelCostForecastResponseDTO> getFuelCostForecast(FuelCostForecastRequestDTO fuelCostForecastRequestDTO) {
        return vehicleService.getFuelCostForecast(fuelCostForecastRequestDTO);
    }

    @PostMapping
    public Vehicle save(@RequestBody Vehicle vehicle) {
        return vehicleService.save(vehicle);
    }
}
