package cz.fi.muni.pa165.pneuservis.serviceTest;

import cz.fi.muni.pa165.pneuservis.dao.ServiceDAO;
import cz.fi.muni.pa165.pneuservis.entity.Service;
import cz.fi.muni.pa165.pneuservis.configuration.ServiceConfiguration;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;
import cz.fi.muni.pa165.pneuservis.services.ServiceService;
import cz.fi.muni.pa165.pneuservis.services.ServiceServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Ivan Moscovic on 24.11.2016.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ServiceServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ServiceDAO serviceDAO;

    @InjectMocks
    private ServiceService serviceService = new ServiceServiceImpl();

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Service service1;
    private Service service2;
    private Service service3;

    @BeforeMethod
    public void prepareTest() {
        service1 = new Service();
        service2 = new Service();
        service3 = new Service();

        service1.setDuration(5);
        service1.setNameOfService("change of oil");
        service1.setOwnParts(true);
        service1.setPrice(BigDecimal.valueOf(100));
        service1.setDescription("Jozko allocated for this job");
        service1.setTypeOfCar("Audi");

        service2.setDuration(25);
        service2.setNameOfService("change of transmission");
        service2.setOwnParts(false);
        service2.setPrice(BigDecimal.valueOf(3000));
        service2.setDescription("Some cool description");
        service2.setTypeOfCar("BMW");

        service3.setDuration(-25);
        service3.setNameOfService("change of transmission");
        service3.setPrice(BigDecimal.valueOf(-3000));
        service3.setDescription("Some cool description");
        service3.setTypeOfCar("BMW");

    }

    @Test
    public void createServiceTest() {
        serviceService.create(service1);
        verify(serviceDAO).create(service1);
    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    public void createIllegalServiceTest(){
        when(serviceDAO.create(service3)).thenThrow(IllegalArgumentException.class);
        serviceService.create(service3);
    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    public void findPersonByNullIdTest() {
        when(serviceDAO.findById(null)).thenThrow(IllegalArgumentException.class);
        serviceService.findById(null);
    }

    @Test
    public void findPersonByIdTest() {
        when(serviceDAO.findById(1L)).thenReturn(service1);
        Assert.assertEquals(serviceService.findById(1L), service1);

        when(serviceDAO.findById(0L)).thenReturn(null);
        Assert.assertEquals(serviceService.findById(0L), null);
    }

    @Test
    public void findPersonByWrongIdTest() {
        when(serviceDAO.findById(4L)).thenReturn(null);
        Assert.assertEquals(serviceService.findById(4L), null);
    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    public void deleteNullServiceTest() {
        doThrow(new IllegalArgumentException()).when(serviceDAO).delete(null);
        serviceService.delete(null);
    }

    @Test
    public void findAllServicesTest() {
        when(serviceDAO.findAllServices()).thenReturn(Arrays.asList(service1, service2));
        final List<Service> cars = serviceService.findAllServices();
        Assert.assertNotNull(cars);
        Assert.assertEquals(2, cars.size());
    }

    @Test
    public void findServiceByName(){
        when(serviceDAO.findByName(service1.getNameOfService())).thenReturn(Arrays.asList(service1));
        List<Service> services = serviceService.findByName(service1.getNameOfService());
        Assert.assertEquals(service1, services.get(0));
    }

    @Test
    public void findServiceByWrongName(){
        when(serviceDAO.findByName("neexistujem")).thenReturn(null);
        List<Service> services = serviceService.findByName("neexistujem");
        Assert.assertEquals(null, services);
    }

    @Test
    public void updateServiceTest() {
        serviceService.update(service1);
        verify(serviceDAO).update(any(Service.class));
    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    public void updateWithWrongArgs(){
        doThrow(new IllegalArgumentException()).when(serviceDAO).update(service3);
        serviceService.update(service3);
    }

}
