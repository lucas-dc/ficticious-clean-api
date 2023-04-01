package br.com.ficticious_clean.api;

import br.com.ficticious_clean.api.data.Vehicle;
import br.com.ficticious_clean.api.exception.RestExceptionHandler;
import br.com.ficticious_clean.api.service.VehicleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class VehicleControllerTest {

    private MockMvc mvc;
    private final Gson gson = new GsonBuilder().serializeNulls().create();

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    @Before
    public void setup() {
        Vehicle vehicle = createVehicle();
        vehicle.setId(1L);

        mvc = MockMvcBuilders.standaloneSetup(vehicleController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();

        when(vehicleService.save(any(Vehicle.class))).thenReturn(vehicle);
        when(vehicleService.findAll()).thenReturn(Collections.singletonList(vehicle));
    }

    @Test
    public void saveVehicle() throws Exception {
        Vehicle vehicle = createVehicle();
        String json = gson.toJson(vehicle);

        mvc.perform(
                        post(new URI("/api/vehicles"))
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("Lightning McQueen"))
                .andExpect(jsonPath("make").value("Volkswagen"))
                .andExpect(jsonPath("model").value("Fusca"))
                .andExpect(jsonPath("productionYear").value(1970))
                .andExpect(jsonPath("cityAverageFuelConsumption").value(8.6))
                .andExpect(jsonPath("highwayAverageFuelConsumption").value(10.5));
    }

    @Test
    public void findAllVehicles() throws Exception {
        String fistElementFromJson = "$.[0].";

        mvc.perform(
                        get(new URI("/api/vehicles"))
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
}