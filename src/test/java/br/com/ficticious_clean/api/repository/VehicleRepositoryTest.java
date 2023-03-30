package br.com.ficticious_clean.api.repository;

import br.com.ficticious_clean.api.data.Vehicle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    public void testSaveVehicle() {
        Vehicle vehicle = new Vehicle();

        vehicleRepository.save(vehicle);

        assertNotNull(vehicle.getId());
    }
}
