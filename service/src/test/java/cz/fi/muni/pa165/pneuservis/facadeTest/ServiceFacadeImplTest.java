package cz.fi.muni.pa165.pneuservis.facadeTest;

import cz.fi.muni.pa165.pneuservis.dto.ServiceDTO;
import cz.fi.muni.pa165.pneuservis.entity.Service;
import cz.fi.muni.pa165.pneuservis.facade.ServiceFacade;
import cz.fi.muni.pa165.pneuservis.configuration.ServiceConfiguration;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;
import cz.fi.muni.pa165.pneuservis.facade.ServiceFacadeImpl;
import cz.fi.muni.pa165.pneuservis.services.BeanMappingService;
import cz.fi.muni.pa165.pneuservis.services.BeanMappingServiceImpl;
import cz.fi.muni.pa165.pneuservis.services.ServiceService;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author Ivan Moscovic on 24.11.2016.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ServiceFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ServiceService serviceService;

    @InjectMocks
    private ServiceFacade serviceFacade = new ServiceFacadeImpl();

    @Spy
    @Autowired
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    Service service1;
    Service service2;
    ServiceDTO serviceDTO1;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init(){

        service1 = new Service();
        service2 = new Service();

        service1.setId(1L);
        service1.setDuration(5);
        service1.setNameOfService("Change of gear");
        service1.setOwnParts(false);
        service1.setTypeOfCar("Honda");
        service1.setPrice(BigDecimal.valueOf(1400));

        service2.setId(2L);
        service2.setDuration(-2);
        service2.setNameOfService(null);
        service2.setOwnParts(true);
        service2.setTypeOfCar("Subaru");
        service2.setPrice(BigDecimal.valueOf(-100));

        serviceDTO1 = new ServiceDTO();
        serviceDTO1.setId(1L);
        serviceDTO1.setDuration(5);
        serviceDTO1.setNameOfService("Change of gear");
        serviceDTO1.setOwnParts(false);
        serviceDTO1.setTypeOfCar("Honda");
        serviceDTO1.setPrice(BigDecimal.valueOf(1400));
    }

    @Test
    public void createService() {
        when(serviceService.create(service1)).thenReturn(service1.getId());
        serviceFacade.create(serviceDTO1);
        verify(serviceService).create(any(Service.class));
    }

    @Test
    public void findById() {
        when(serviceService.findById(1L)).thenReturn(service1);
        ServiceDTO serviceDTO = serviceFacade.findById(service1.getId());
        assertNotNull(serviceDTO);
        assertEquals(serviceDTO.getId(), serviceDTO1.getId());
    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    public void findByWrongId(){
        when(serviceService.findById(8L)).thenThrow(PneuservisPortalDataAccessException.class);
        ServiceDTO serviceDTO = serviceFacade.findById(8L);
        assertNull(serviceDTO);
    }

    @Test
    public void deleteServiceTest() {
        when(serviceService.delete(service1)).thenReturn(service1.getId());
        serviceFacade.delete(serviceDTO1);
        verify(serviceService).delete(service1);
    }

    @Test
    public void getAllServiceTest() {
        when(serviceService.findAllServices()).thenReturn(Arrays.asList(service1, service2));
        List<ServiceDTO> services = serviceFacade.findAllServices();
        verify(serviceService).findAllServices();
        assertNotNull(services);
        assertEquals(services.get(0).getId(), service1.getId());
        assertEquals(services.get(1).getId(), service2.getId());

    }

    @Test
    public void findByNameTest() {
        when(serviceService.findByName(service1.getNameOfService())).thenReturn(Arrays.asList(service1));
        List<ServiceDTO> services = serviceFacade.findByName(serviceDTO1.getNameOfService());
        verify(serviceService).findByName(any(String.class));
        assertNotNull(services);
        assertEquals(services.get(0).getNameOfService(), service1.getNameOfService());
    }

    @Test void findNonExistingNameTest(){
        when(serviceService.findByName("neexistujem")).thenReturn(null);
        List<ServiceDTO> services = serviceFacade.findByName("neexistujem");
        assertNull(services);
    }


    @Test
    public void updateTest() {
        serviceFacade.update(serviceDTO1);
        verify(serviceService).update(any(Service.class));
    }


}
