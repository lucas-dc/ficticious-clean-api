package br.com.ficticious_clean.api.service;

import br.com.ficticious_clean.api.data.FuelCostForecastRequestDTO;
import br.com.ficticious_clean.api.data.FuelCostForecastResponseDTO;
import br.com.ficticious_clean.api.data.Vehicle;
import br.com.ficticious_clean.api.repository.VehicleRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {

    private static ValidatorFactory validatorFactory;
    private static Validator parametersValidator;

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        parametersValidator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Before
    public void setup() {
        Vehicle vehicle = createVehicle();
        vehicle.setId(1L);

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
        when(vehicleService.findAll()).thenReturn(Collections.singletonList(vehicle));
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
        FuelCostForecastRequestDTO requestDTO = createRequestDTO();
        List<FuelCostForecastResponseDTO> response = vehicleService.getFuelCostForecast(requestDTO);

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

    @Test
    public void shouldReturnError_WhenVehicle_WithNullName() {
        Vehicle vehicle = createVehicle();
        vehicle.setName(null);

        Set<ConstraintViolation<Object>> violations = parametersValidator.validate(vehicle);
        String errorMessage = violations.iterator().next().getMessage();

        assertEquals("'name' parameter is mandatory and cannot be null nor empty", errorMessage);
    }

    @Test
    public void shouldReturnError_WhenSavingVehicle_WithNullMake() {
        Vehicle vehicle = createVehicle();
        vehicle.setMake(null);

        Set<ConstraintViolation<Object>> violations = parametersValidator.validate(vehicle);
        String errorMessage = violations.iterator().next().getMessage();

        assertEquals("'make' parameter is mandatory and cannot be null nor empty", errorMessage);
    }

    @Test
    public void shouldReturnError_WhenSavingVehicle_WithHighwayAverageFuelConsumptionZero() {
        Vehicle vehicle = createVehicle();
        vehicle.setHighwayAverageFuelConsumption(BigDecimal.ZERO);

        Set<ConstraintViolation<Object>> violations = parametersValidator.validate(vehicle);
        String errorMessage = violations.iterator().next().getMessage();

        assertEquals("'highwayAverageFuelConsumption' parameter must be greater than 0", errorMessage);
    }

    @Test
    public void shouldReturnError_WhenFuelCostForecasting_WithFuelPriceZero() {
        FuelCostForecastRequestDTO requestDTO = createRequestDTO();
        requestDTO.setFuelPrice(BigDecimal.ZERO);

        Set<ConstraintViolation<Object>> violations = parametersValidator.validate(requestDTO);
        String errorMessage = violations.iterator().next().getMessage();

        assertEquals("'fuelPrice' parameter must be greater than 0", errorMessage);
    }

    @Test
    public void shouldReturnError_WhenFuelCostForecasting_WithCityRouteDistanceNull() {
        FuelCostForecastRequestDTO requestDTO = createRequestDTO();
        requestDTO.setCityRouteDistance(null);

        Set<ConstraintViolation<Object>> violations = parametersValidator.validate(requestDTO);
        String errorMessage = violations.iterator().next().getMessage();

        assertEquals("'cityRouteDistance' parameter cannot be null", errorMessage);
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

    private FuelCostForecastRequestDTO createRequestDTO() {
        FuelCostForecastRequestDTO requestDTO = new FuelCostForecastRequestDTO();
        requestDTO.setCityRouteDistance(BigDecimal.TEN);
        requestDTO.setHighwayRouteDistance(BigDecimal.ONE);
        requestDTO.setFuelPrice(new BigDecimal("5.5"));

        return requestDTO;
    }
}
