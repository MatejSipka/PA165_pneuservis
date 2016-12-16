package cz.fi.muni.pa165.pneuservis.serviceTest;

import cz.fi.muni.pa165.pneuservis.dto.*;
import cz.fi.muni.pa165.pneuservis.entity.Order;
import cz.fi.muni.pa165.pneuservis.entity.Services;
import cz.fi.muni.pa165.pneuservis.entity.Tire;
import cz.fi.muni.pa165.pneuservis.enums.PaymentType;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.configuration.ServiceConfiguration;
import cz.fi.muni.pa165.pneuservis.services.BeanMappingService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.pa165.pneuservis.services.BillingItem;
import cz.fi.muni.pa165.pneuservis.services.OrderBilling;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Ivan Moscovic
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    private Services service;

    private ServiceDTO serviceDTO;

    private Tire tire;

    private TireDTO tireDTO;

    private Order order;

    private OrderDTO orderDTO;

    private CreateOrderDTO createOrderDTO;

    private UpdateOrderDTO updateOrderDTO;

    private OrderBilling orderBilling;

    private BillingItem billingItem;

    @BeforeClass
    public void setUp() {

        service = new Services();
        service.setDuration(5);
        service.setNameOfService("change of gear");
        service.setOwnParts(true);

        serviceDTO = new ServiceDTO();
        serviceDTO.setOwnParts(true);
        serviceDTO.setNameOfService("change of gear");
        serviceDTO.setDuration(5);

        tire = new Tire();
        tire.setManufacturer(TireManufacturer.BARUM);
        tire.setCatalogNumber(12223);
        tire.setTireSize(255);

        tireDTO = new TireDTO();
        tireDTO.setManufacturer(TireManufacturer.BARUM);
        tireDTO.setCatalogNumber(12223);
        tireDTO.setTireSize(255);

        List<Services> services = new ArrayList<>();
        services.add(service);
        List<Tire> tires = new ArrayList<>();
        tires.add(tire);
        List<ServiceDTO> serviceDTOs = new ArrayList<>();
        serviceDTOs.add(serviceDTO);
        List<TireDTO> tireDTOs = new ArrayList<>();
        tireDTOs.add(tireDTO);

        order = new Order();
        order.setPaymentConfirmed(true);
        order.setShipped(false);
        order.setPaymentType(PaymentType.COD);
        order.setNote("Test");
        order.setClientId(1L);
        order.setListOfServices(services);
        order.setListOfTires(tires);

        orderDTO = new OrderDTO();
        orderDTO.setPaymentConfirmed(true);
        orderDTO.setShipped(false);
        orderDTO.setPaymentType(PaymentType.COD);
        orderDTO.setNote("Test");
        orderDTO.setClientId(1L);
        orderDTO.setListOfServices(serviceDTOs);

        billingItem = new BillingItem();
        billingItem.setPrice(new BigDecimal(79));
        billingItem.setPriceWithVAT(new BigDecimal(100));
        billingItem.setDescription("Test");
        billingItem.setVAT(21);


        List<BillingItem> items = new ArrayList<>();
        items.add(billingItem);

        orderBilling = new OrderBilling();
        orderBilling.setPrice(new BigDecimal(79));
        orderBilling.setPriceWithVAT(new BigDecimal(100));
        orderBilling.setBillingItems(items);

        createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setClientId(1L);
        createOrderDTO.setNote("Test");
        createOrderDTO.setPaymentType(PaymentType.COD);
        createOrderDTO.setListOfServices(serviceDTOs);
        createOrderDTO.setListOfTires(tireDTOs);

        updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setId(1L);
        updateOrderDTO.setClientId(1L);
        updateOrderDTO.setNote("Test");
        updateOrderDTO.setPaymentType(PaymentType.COD);
        updateOrderDTO.setListOfServices(serviceDTOs);
        updateOrderDTO.setListOfTires(tireDTOs);
    }

    @Test
    public void serviceToServiceDTOTest() {

        final Services result = beanMappingService.mapTo(serviceDTO, Services.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(serviceDTO.getDuration(), result.getDuration());
        Assert.assertEquals(serviceDTO.getNameOfService(), result.getNameOfService());
    }

    @Test
    public void serviceDTOToServiceTest() {

        final ServiceDTO result = beanMappingService.mapTo(service, ServiceDTO.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(service.getDuration(), result.getDuration());
        Assert.assertEquals(service.getNameOfService(), result.getNameOfService());
    }

    @Test
    public void tireToTireDTOTest() {

        final Tire result = beanMappingService.mapTo(tireDTO, Tire.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(tireDTO.getManufacturer(), result.getManufacturer());
        Assert.assertEquals(tireDTO.getCatalogNumber(), result.getCatalogNumber());
    }

    @Test
    public void tireDTOToTireTest() {

        final TireDTO result = beanMappingService.mapTo(tireDTO, TireDTO.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(tire.getManufacturer(), result.getManufacturer());
        Assert.assertEquals(tire.getCatalogNumber(), result.getCatalogNumber());
    }

    @Test
    public void orderToOrderDTOTest() {
        final OrderDTO orderDTO = beanMappingService.mapTo(order, OrderDTO.class);
        Assert.assertNotNull(orderDTO);
        Assert.assertEquals(orderDTO.getId(), order.getId());
        Assert.assertEquals(orderDTO.getClientId(), order.getClientId());
        Assert.assertEquals(orderDTO.getNote(), order.getNote());
        Assert.assertEquals(orderDTO.getPaymentType(), order.getPaymentType());
    }

    @Test
    public void orderDTOToOrderTest() {
        final Order order1 = beanMappingService.mapTo(orderDTO, Order.class);
        Assert.assertNotNull(order1);
        Assert.assertEquals(orderDTO.getId(), order1.getId());
        Assert.assertEquals(orderDTO.getClientId(), order1.getClientId());
        Assert.assertEquals(orderDTO.getNote(), order1.getNote());
        Assert.assertEquals(orderDTO.getPaymentType(), order1.getPaymentType());
    }

    @Test
    public void createOrderDTOToOrderTest() {
        final Order order1 = beanMappingService.mapTo(createOrderDTO, Order.class);
        Assert.assertNotNull(order1);
        Assert.assertEquals(createOrderDTO.getClientId(), order1.getClientId());
        Assert.assertEquals(createOrderDTO.getNote(), order1.getNote());
        Assert.assertEquals(createOrderDTO.getPaymentType(), order1.getPaymentType());
        Assert.assertNotNull(order1.getListOfServices());
        Assert.assertEquals(createOrderDTO.getListOfServices().size(), order1.getListOfServices().size());
        Assert.assertNotNull(order1.getListOfTires());
        Assert.assertEquals(createOrderDTO.getListOfTires().size(), order1.getListOfTires().size());
    }

    @Test
    public void updateOrderDTOToOrderTest() {
        final Order order1 = beanMappingService.mapTo(updateOrderDTO, Order.class);
        Assert.assertNotNull(order1);
        Assert.assertEquals(updateOrderDTO.getId(), order1.getId());
        Assert.assertEquals(updateOrderDTO.getClientId(), order1.getClientId());
        Assert.assertEquals(updateOrderDTO.getNote(), order1.getNote());
        Assert.assertEquals(updateOrderDTO.getPaymentType(), order1.getPaymentType());
        Assert.assertNotNull(order1.getListOfServices());
        Assert.assertEquals(updateOrderDTO.getListOfServices().size(), order1.getListOfServices().size());
        Assert.assertNotNull(order1.getListOfTires());
        Assert.assertEquals(updateOrderDTO.getListOfTires().size(), order1.getListOfTires().size());
    }

    @Test
    public void orderBillingToOrderBillingDTOTest() {
        final OrderBillingDTO orderBillingDTO = beanMappingService.mapTo(orderBilling, OrderBillingDTO.class);
        Assert.assertNotNull(orderBillingDTO);
        Assert.assertEquals(orderBillingDTO.getPrice(), orderBilling.getPrice());
        Assert.assertEquals(orderBillingDTO.getPriceWithVAT(), orderBilling.getPriceWithVAT());
        Assert.assertNotNull(orderBillingDTO.getBillingItems());
        Assert.assertEquals(orderBillingDTO.getBillingItems().size(), orderBilling.getBillingItems().size());
    }

    @Test
    public void billingItemToBillingItemDTOTest() {
        final BillingItemDTO billingItemDTO = beanMappingService.mapTo(billingItem, BillingItemDTO.class);
        Assert.assertNotNull(billingItemDTO);
        Assert.assertEquals(billingItemDTO.getPriceWithVAT(), billingItem.getPriceWithVAT());
        Assert.assertEquals(billingItemDTO.getPrice(), billingItem.getPrice());
        Assert.assertEquals(billingItemDTO.getDescription(), billingItem.getDescription());
        Assert.assertEquals(billingItemDTO.getVAT(), billingItem.getVAT());
    }
}
