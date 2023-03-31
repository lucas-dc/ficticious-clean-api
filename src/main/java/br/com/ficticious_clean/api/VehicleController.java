package br.com.ficticious_clean.api;

import br.com.ficticious_clean.api.data.FuelConsumptionRequestDTO;
import br.com.ficticious_clean.api.data.FuelConsumptionResponseDTO;
import br.com.ficticious_clean.api.data.Vehicle;
import br.com.ficticious_clean.api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> findAll() {
        return vehicleService.findAll();
    }

    @GetMapping("/fuel-cost-forecast")
    public List<FuelConsumptionResponseDTO> listVehicles(@Valid @RequestBody FuelConsumptionRequestDTO fuelConsumptionRequestDTO) {
        return vehicleService.getFuelCostForecast(fuelConsumptionRequestDTO);
    }

    @PostMapping
    public Vehicle save(@Valid @RequestBody Vehicle vehicle) {
        return vehicleService.save(vehicle);
    }
}
