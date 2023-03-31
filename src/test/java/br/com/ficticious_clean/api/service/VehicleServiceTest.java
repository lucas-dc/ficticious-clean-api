package br.com.ficticious_clean.api.service;

import br.com.ficticious_clean.api.data.FuelConsumptionRequestDTO;
import br.com.ficticious_clean.api.data.FuelConsumptionResponseDTO;
import br.com.ficticious_clean.api.data.Vehicle;
import br.com.ficticious_clean.api.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Before
    public void setup() {
        Vehicle vehicle = createVehicle();
        vehicle.setId(1L);
        given(vehicleService.save(any())).willReturn(vehicle);
        given(vehicleService.findAll()).willReturn(Collections.singletonList(vehicle));
    }

    @Test
    public void testSaveVehicle() {
        Vehicle vehicle = createVehicle();

        Vehicle savedVehicle = vehicleService.save(vehicle);

        assertEquals(1L, savedVehicle.getId().longValue());
        assertEquals("Lightning McQueen", savedVehicle.getName());
        assertEquals("Volkswagen", savedVehicle.getMake());
        assertEquals("Fusca", savedVehicle.getModel());
        assertEquals(1970, savedVehicle.getProductionYear().intValue());
        assertEquals(new BigDecimal("8.6"), savedVehicle.getCityAverageFuelConsumption());
        assertEquals(new BigDecimal("10.5"), savedVehicle.getHighwayAverageFuelConsumption());
    }

    @Test
    public void testFuelCostForecast() {
        FuelConsumptionRequestDTO requestDTO = createRequestDTO();
        List<FuelConsumptionResponseDTO> response = vehicleService.getFuelCostForecast(requestDTO);

        /*
            Consumo na cidade: 8.6km/L
            Trajeto percorrido na cidade: 10km
            Consumo total (em litros): 1,16L

            Consumo em rodovia: 10.5km/L
            Trajeto percorrido em rodovia: 1km
            Consumo total (em litros): 0,09L

            Preço da gasolina: R$5,5

            Total (em L): 1,25L
            Total (em R$): R%6,87
        */

        assertEquals("Lightning McQueen", response.get(0).getName());
        assertEquals("Volkswagen", response.get(0).getMake());
        assertEquals("Fusca", response.get(0).getModel());
        assertEquals(1970, response.get(0).getProductionYear().intValue());
        assertEquals(new BigDecimal("1.25"), response.get(0).getTotalFuelConsumption());
        assertEquals(new BigDecimal("6.87"), response.get(0).getTotalFuelCost());
    }

    private Vehicle createVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Lightning McQueen");
        vehicle.setMake("Volkswagen");
        vehicle.setModel("Fusca");
        vehicle.setProductionYear(1970);
        vehicle.setCityAverageFuelConsumption(new BigDecimal("8.6"));
        vehicle.setHighwayAverageFuelConsumption(new BigDecimal("10.5"));
        return vehicle;
    }

    private FuelConsumptionRequestDTO createRequestDTO() {
        FuelConsumptionRequestDTO requestDTO = new FuelConsumptionRequestDTO();
        requestDTO.setCityRouteDistance(BigDecimal.TEN);
        requestDTO.setHighwayRouteDistance(BigDecimal.ONE);
        requestDTO.setFuelPrice(new BigDecimal("5.5"));

        return requestDTO;
    }
}
