package br.com.ficticious_clean.api;

import br.com.ficticious_clean.api.data.FuelConsumptionRequestDTO;
import br.com.ficticious_clean.api.data.Vehicle;
import br.com.ficticious_clean.api.service.VehicleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Vehicle> vehicleJson;

    @Autowired
    private JacksonTester<FuelConsumptionRequestDTO> fuelConsumptionRequestJson;

    @MockBean
    private VehicleService vehicleService;

    @Before
    public void setup() {
        Vehicle vehicle = createVehicle();
        vehicle.setId(1L);
        FuelConsumptionRequestDTO requestDTO = createRequestDTO();
        given(vehicleService.save(any())).willReturn(vehicle);
        given(vehicleService.findAll()).willReturn(Collections.singletonList(vehicle));
    }

    @Test
    public void saveVehicle() throws Exception {
        Vehicle vehicle = createVehicle();
        mvc.perform(
                        post(new URI("/vehicles"))
                                .content(vehicleJson.write(vehicle).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllVehicles() throws Exception {
        String fistElementFromJson = "$.[0].";

        mvc.perform(
                        get(new URI("/vehicles"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(fistElementFromJson+"id").value(1))
                .andExpect(jsonPath(fistElementFromJson+"name").value("Lightning McQueen"))
                .andExpect(jsonPath(fistElementFromJson+"make").value("Volkswagen"))
                .andExpect(jsonPath(fistElementFromJson+"model").value("Fusca"))
                .andExpect(jsonPath(fistElementFromJson+"productionYear").value(1970))
                .andExpect(jsonPath(fistElementFromJson+"cityAverageFuelConsumption").value(8.6))
                .andExpect(jsonPath(fistElementFromJson+"highwayAverageFuelConsumption").value(10.5));

    }

    @Test
    public void shouldReturnError_WhenSavingVehicle_WithNullName() throws Exception {
        Vehicle vehicle = createVehicle();
        vehicle.setName(null);

        String response = mvc.perform(
                        post(new URI("/vehicles"))
                                .content(vehicleJson.write(vehicle).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("'name' parameter is mandatory and cannot be null nor empty", response);
    }

    @Test
    public void shouldReturnError_WhenSavingVehicle_WithNullMake() throws Exception {
        Vehicle vehicle = createVehicle();
        vehicle.setMake(null);

        String response = mvc.perform(
                        post(new URI("/vehicles"))
                                .content(vehicleJson.write(vehicle).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("'make' parameter is mandatory and cannot be null nor empty", response);
    }

    @Test
    public void shouldReturnError_WhenSavingVehicle_WithHighwayAverageFuelConsumptionZero() throws Exception {
        Vehicle vehicle = createVehicle();
        vehicle.setHighwayAverageFuelConsumption(BigDecimal.ZERO);

        String response = mvc.perform(
                        post(new URI("/vehicles"))
                                .content(vehicleJson.write(vehicle).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("'highwayAverageFuelConsumption' parameter must be greater than 0", response);
    }

    @Test
    public void shouldReturnError_WhenFuelCostForecasting_WithFuelPriceZero() throws Exception {
        FuelConsumptionRequestDTO requestDTO = createRequestDTO();
        requestDTO.setFuelPrice(BigDecimal.ZERO);

        String response = mvc.perform(
                        get(new URI("/vehicles/fuel-cost-forecast"))
                                .content(fuelConsumptionRequestJson.write(requestDTO).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("'fuelPrice' parameter must be greater than 0", response);
    }

    @Test
    public void shouldReturnError_WhenFuelCostForecasting_WithCityRouteDistanceNull() throws Exception {
        FuelConsumptionRequestDTO requestDTO = createRequestDTO();
        requestDTO.setCityRouteDistance(new BigDecimal("-1.1"));

        String response = mvc.perform(
                        get(new URI("/vehicles/fuel-cost-forecast"))
                                .content(fuelConsumptionRequestJson.write(requestDTO).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("'cityRouteDistance' must be greater than or equal to 0", response);
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