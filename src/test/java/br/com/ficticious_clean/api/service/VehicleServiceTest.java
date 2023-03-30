package br.com.ficticious_clean.api.service;

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
        assertEquals(new BigDecimal("8.6"), savedVehicle.getCityAverageGasConsumption());
        assertEquals(new BigDecimal("10.5"), savedVehicle.getHighwayAverageGasConsumption());
    }

    private Vehicle createVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Lightning McQueen");
        vehicle.setMake("Volkswagen");
        vehicle.setModel("Fusca");
        vehicle.setProductionYear(1970);
        vehicle.setCityAverageGasConsumption(new BigDecimal("8.6"));
        vehicle.setHighwayAverageGasConsumption(new BigDecimal("10.5"));
        return vehicle;
    }
}
