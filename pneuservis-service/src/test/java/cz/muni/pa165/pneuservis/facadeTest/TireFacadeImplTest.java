/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.pa165.pneuservis.facadeTest;

import cz.fi.muni.pa165.pneuservis.dto.TireDTO;
import cz.fi.muni.pa165.pneuservis.entity.Tire;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import cz.fi.muni.pa165.pneuservis.facade.TireFacade;
import cz.fi.muni.pa165.pneuservis.service.configuration.ServiceConfiguration;
import cz.fi.muni.pa165.pneuservis.service.exception.PneuservisPortalDataAccessException;
import cz.fi.muni.pa165.pneuservis.service.facade.TireFacadeImpl;
import cz.fi.muni.pa165.pneuservis.service.services.BeanMappingService;
import cz.fi.muni.pa165.pneuservis.service.services.BeanMappingServiceImpl;
import cz.fi.muni.pa165.pneuservis.service.services.TireService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sun.java2d.loops.SurfaceType;

/**
 *
 * @author Matej Sipka
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TireFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TireService tireService;

    @InjectMocks
    private TireFacade tireFacade = new TireFacadeImpl();

    @Spy
    @Autowired
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Tire tire1;
    private Tire tire2;

    private TireDTO tireDto1;
    private TireDTO tireDto2;

    @BeforeMethod
    public void init() {

        tire1 = new Tire();
        tire2 = new Tire();

        tireDto1 = new TireDTO();
        tireDto2 = new TireDTO();

        tire1.setId(1L);
        tire1.setCatalogNumber(12);
        tire1.setDescription("none");
        tire1.setDiameter(17);
        tire1.setManufacturer(TireManufacturer.HANKOOK);
        tire1.setPrice(BigDecimal.TEN);
        tire1.setProfile(55);
        tire1.setTireSize(255);
        tire1.setType(TireType.SUMMER);
        tire1.setTypeOfCar("personal");

        tire2.setCatalogNumber(13);
        tire2.setDescription("none");
        tire2.setDiameter(18);
        tire2.setManufacturer(TireManufacturer.FALKEN);
        tire2.setPrice(BigDecimal.TEN);
        tire2.setProfile(55);
        tire2.setTireSize(205);
        tire2.setType(TireType.WINTER);
        tire2.setTypeOfCar("truck");

        tireDto1.setCatalogNumber(tire1.getCatalogNumber());
        tireDto1.setDescription(tire1.getDescription());
        tireDto1.setDiameter(tire1.getDiameter());
        tireDto1.setManufacturer(tire1.getManufacturer());
        tireDto1.setPrice(tire1.getPrice());
        tireDto1.setProfile(tire1.getProfile());
        tireDto1.setTireSize(tire1.getTireSize());
        tireDto1.setType(tire1.getType());
        tireDto1.setTypeOfCar(tire1.getTypeOfCar());

        tireDto2.setCatalogNumber(tire2.getCatalogNumber());
        tireDto2.setDescription(tire2.getDescription());
        tireDto2.setDiameter(tire2.getDiameter());
        tireDto2.setManufacturer(tire2.getManufacturer());
        tireDto2.setPrice(tire2.getPrice());
        tireDto2.setProfile(tire2.getProfile());
        tireDto2.setTireSize(tire2.getTireSize());
        tireDto2.setType(tire2.getType());
        tireDto2.setTypeOfCar(tire2.getTypeOfCar());

    }

    @Test
    public void createService() {
        when(tireService.create(tire1)).thenReturn(tire1);
        tireFacade.create(tireDto1);
        verify(tireService).create(any(Tire.class));
    }

    @Test
    public void findById() {
        when(tireService.findById(1L)).thenReturn(tire1);
        TireDTO tireDTO = tireFacade.findById(tire1.getId());
        assertNotNull(tireDTO);
        assertEquals(tireDTO.getId(), tireDTO.getId());
    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    @SuppressWarnings("unchecked")
    public void findByWrongId() {
        when(tireService.findById(4L)).thenThrow(PneuservisPortalDataAccessException.class);
        TireDTO tireDTO = tireFacade.findById(4L);
        assertNull(tireDTO);
    }

    @Test
    public void deleteTest() {
        tireFacade.delete(tireDto2);
        verify(tireService).delete(tire2);
    }

    @Test
    public void getAllServiceTest() {
        when(tireService.findAll()).thenReturn(Arrays.asList(tire1, tire2));
        List<TireDTO> tires = tireFacade.findAll();
        verify(tireService).findAll();
        assertNotNull(tires);
        assertEquals(tires.get(0).getId(), tire1.getId());
        assertEquals(tires.get(1).getId(), tire2.getId());
    }

    @Test
    public void findByCatalogNumberTest() {
        when(tireService.findByCatalogNumber(tire1.getCatalogNumber())).thenReturn(Arrays.asList(tire1));
        List<TireDTO> tires = tireFacade.findByCatalogNumber(tire1.getCatalogNumber());
        verify(tireService).findByCatalogNumber(any(Integer.class));
        assertNotNull(tires);
        assertEquals(tires.get(0).getCatalogNumber(), tire1.getCatalogNumber());
    }

    @Test
    public void findByProfileTest() {
        when(tireService.findByProfile(tire1.getProfile())).thenReturn(Arrays.asList(tire1, tire2));
        List<TireDTO> tires = tireFacade.findByProfile(tire1.getProfile());
        verify(tireService).findByProfile(tire1.getProfile());
        assertNotNull(tires);
        assertEquals(tires.get(0).getProfile(), tire1.getProfile());
        assertEquals(tires.get(1).getProfile(), tire2.getProfile());
    }

    @Test
    public void updateTest() {
        tireFacade.update(tireDto2);
        verify(tireService).update(any(Tire.class));
    }
}
