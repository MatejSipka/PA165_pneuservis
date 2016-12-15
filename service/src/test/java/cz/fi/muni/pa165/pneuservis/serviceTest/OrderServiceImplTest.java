package cz.fi.muni.pa165.pneuservis.serviceTest;

import cz.fi.muni.pa165.pneuservis.dao.OrderDAO;
import cz.fi.muni.pa165.pneuservis.entity.Order;
import cz.fi.muni.pa165.pneuservis.entity.Services;
import cz.fi.muni.pa165.pneuservis.entity.Tire;
import cz.fi.muni.pa165.pneuservis.enums.PaymentType;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import cz.fi.muni.pa165.pneuservis.configuration.ServiceConfiguration;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;
import cz.fi.muni.pa165.pneuservis.services.OrderBilling;
import cz.fi.muni.pa165.pneuservis.services.OrderService;
import cz.fi.muni.pa165.pneuservis.services.OrderServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author vit.holasek on 25.11.2016.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class OrderServiceImplTest extends AbstractTestNGSpringContextTests {
    @Mock
    private OrderDAO orderDAO;

    @InjectMocks
    private OrderService orderService = new OrderServiceImpl();

    private Order order1;
    private Order order2;
    private Order order3;
    private Order invalidOrder;
    private Services service1;
    private Tire tire1;
    private Tire tire2;
    private List<Order> allOrders;
    private List<Order> clientOrders;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTest() throws ServiceException {
        service1 = new Services();
        service1.setId(1L);
        service1.setTypeOfCar("Audi");
        service1.setDuration(10);
        service1.setPrice(new BigDecimal(79));
        service1.setDescription("Test");
        service1.setNameOfService("Tire change");
        service1.setOwnParts(false);

        tire1 = new Tire();
        tire1.setId(1L);
        tire1.setPrice(new BigDecimal(790));
        tire1.setDescription("Audi winter tire");
        tire1.setTypeOfCar("Audi");
        tire1.setType(TireType.WINTER);
        tire1.setCatalogNumber(10);
        tire1.setDiameter(10);
        tire1.setManufacturer(TireManufacturer.GOODYEAR);
        tire1.setProfile(10);
        tire1.setTireSize(40);

        tire2 = new Tire();
        tire2.setId(2L);
        tire2.setPrice(new BigDecimal(1580));
        tire2.setDescription("Audi summer tire");
        tire2.setTypeOfCar("Audi");
        tire2.setType(TireType.SUMMER);
        tire2.setCatalogNumber(10);
        tire2.setDiameter(8);
        tire2.setManufacturer(TireManufacturer.BARUM);
        tire2.setProfile(12);
        tire2.setTireSize(42);

        order1 = new Order();
        order1.setId(1L);
        order1.setPaymentConfirmed(true);
        order1.setShipped(true);
        order1.setPaymentType(PaymentType.COD);
        order1.setClientId(1L);
        order1.setNote("Test");
        order1.getListOfTires().add(tire1);
        order1.getListOfTires().add(tire2);
        order1.getListOfServices().add(service1);

        order2 = new Order();
        order2.setId(2L);
        order2.setPaymentConfirmed(false);
        order2.setShipped(false);
        order2.setPaymentType(PaymentType.CARD);
        order2.setClientId(2L);
        order2.setNote("Test1");
        order2.getListOfServices().add(service1);

        order3 = new Order();
        order3.setId(8L);
        order3.setPaymentConfirmed(true);
        order3.setShipped(true);
        order3.setPaymentType(PaymentType.CARD);
        order3.setClientId(2L);
        order3.setNote("Test1");
        order3.getListOfTires().add(tire2);

        invalidOrder = new Order();

        allOrders = new ArrayList<>();
        allOrders.add(order1);
        allOrders.add(order2);

        clientOrders = new ArrayList<>();
        clientOrders.add(order1);

        when(orderDAO.findById(1L)).thenReturn(order1);
        when(orderDAO.findById(2L)).thenReturn(order2);
        when(orderDAO.findById(8L)).thenReturn(order3);
        when(orderDAO.findById(3L)).thenReturn(null);
        when(orderDAO.findAll()).thenReturn(allOrders);
        when(orderDAO.findByClientId(1L)).thenReturn(clientOrders);
        when(orderDAO.findByClientId(3L)).thenReturn(new ArrayList<>());
        doAnswer(invoke -> {
            Order order = invoke.getArgumentAt(0, Order.class);
            if (order.getId() == null) throw new IllegalArgumentException();
            return order;
        }).when(orderDAO).delete(null);
        doAnswer(invoke -> {
            Order order = invoke.getArgumentAt(0, Order.class);
            if (order.getClientId() == null) throw new IllegalArgumentException();
            return order;
        }).when(orderDAO).create(any(Order.class));
        doAnswer(invoke -> {
            Order order = invoke.getArgumentAt(0, Order.class);
            if (order.getClientId() == null) throw new IllegalArgumentException();
            return order;
        }).when(orderDAO).update(any(Order.class));
    }

    @Test
    public void findOrderByIdTest() {
        Assert.assertEquals(orderService.findOrderById(1L), order1);
        verify(orderDAO).findById(1L);
    }

    @Test
    public void findOrderByIdInvalidIdTest() {
        Assert.assertEquals(orderService.findOrderById(3L), null);
        verify(orderDAO).findById(3L);
    }

    @Test
    public void findAllTest() {
        Assert.assertEquals(orderService.findAllOrders(), allOrders);
        verify(orderDAO).findAll();
    }

    @Test
    public void findClientOrdersTest() {
        Assert.assertEquals(orderService.findClientOrders(1L), clientOrders);
        verify(orderDAO).findByClientId(1L);
    }

    @Test
    public void findClientOrdersInvalidIdTest() {
        Assert.assertEquals(orderService.findClientOrders(3L), new ArrayList<Order>());
        verify(orderDAO).findByClientId(3L);
    }

    @Test
    public void getOrderBillingTest() {
        OrderBilling orderBilling = orderService.getOrderBilling(1L);
        Assert.assertNotNull(orderBilling);
        Assert.assertEquals(orderBilling.getPriceWithVAT(), new BigDecimal(3100));
        Assert.assertEquals(orderBilling.getPrice(), new BigDecimal(2449));
        Assert.assertEquals(orderBilling.getOrder(), order1);
        Assert.assertNotNull(orderBilling.getBillingItems());
        Assert.assertEquals(orderBilling.getBillingItems().size(), 3);
    }

    @Test
    public void getOrderBillingTestInvalidId() {
        Assert.assertEquals(orderService.getOrderBilling(3L), null);
    }

    @Test
    public void calculateProfitTest(){
        when(orderDAO.findAll()).thenReturn(Arrays.asList(order1, order2, order3));
        BigDecimal result = orderService.calculateProfit();
        Assert.assertEquals(result, new BigDecimal(4029));
        reset(orderDAO);
    }

    @Test
    public void createTest() {
        orderService.create(order1);
        verify(orderDAO).create(order1);
    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    public void createTestInvalidOrder() {
        orderService.create(invalidOrder);
    }

    @Test
    public void updateTest() {
        orderService.update(order1);
        verify(orderDAO).update(order1);
    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    public void updateTestInvalidOrder() {
        orderService.update(invalidOrder);
    }

    @Test
    public void deleteTest() {
        orderService.delete(order1);
        verify(orderDAO).delete(order1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteTestNullOrder() {
        orderService.delete(null);
    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    public void deleteTestInvalidOrder() {
        orderService.update(new Order());
    }
}
