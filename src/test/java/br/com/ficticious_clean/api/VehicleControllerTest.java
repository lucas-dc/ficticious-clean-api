package br.com.ficticious_clean.api;

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
    private JacksonTester<Vehicle> json;

    @MockBean
    private VehicleService vehicleService;

    @Before
    public void setup() {
        Vehicle vehicle = createVehicle();
        vehicle.setId(1L);
        given(vehicleService.save(any())).willReturn(vehicle);
        given(vehicleService.findAll()).willReturn(Collections.singletonList(vehicle));
    }

    @Test
    public void saveVehicle() throws Exception {
        Vehicle vehicle = createVehicle();
        mvc.perform(
                        post(new URI("/vehicles"))
                                .content(json.write(vehicle).getJson())
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
                .andExpect(jsonPath(fistElementFromJson+".id").value(1))
                .andExpect(jsonPath(fistElementFromJson+".name").value("Lightning McQueen"))
                .andExpect(jsonPath(fistElementFromJson+".make").value("Volkswagen"))
                .andExpect(jsonPath(fistElementFromJson+".model").value("Fusca"))
                .andExpect(jsonPath(fistElementFromJson+".productionYear").value(1970))
                .andExpect(jsonPath(fistElementFromJson+".cityAverageGasConsumption").value(8.6))
                .andExpect(jsonPath(fistElementFromJson+".highwayAverageGasConsumption").value(10.5));

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