package cz.fi.muni.pa165.pneuservis.facadeTest;

import cz.fi.muni.pa165.pneuservis.services.BeanMappingServiceImpl;
import cz.fi.muni.pa165.pneuservis.services.OrderService;
import cz.fi.muni.pa165.pneuservis.services.BillingItem;
import cz.fi.muni.pa165.pneuservis.services.BeanMappingService;
import cz.fi.muni.pa165.pneuservis.services.OrderBilling;
import cz.fi.muni.pa165.pneuservis.dto.*;
import cz.fi.muni.pa165.pneuservis.entity.Order;
import cz.fi.muni.pa165.pneuservis.entity.Service;
import cz.fi.muni.pa165.pneuservis.enums.PaymentType;
import cz.fi.muni.pa165.pneuservis.facade.OrderFacade;
import cz.fi.muni.pa165.pneuservis.configuration.ServiceConfiguration;
import cz.fi.muni.pa165.pneuservis.facade.OrderFacadeImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author vit.holasek on 25.11.2016.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class OrderFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderFacade orderFacade = new OrderFacadeImpl();

    @Spy
    @Autowired
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    private Order order1;
    private Order order2;
    private CreateOrderDTO newOrderDTO;
    private ServiceDTO serviceDTO;
    private Service service1;
    private List<Order> allOrders;
    private List<Order> clientOrders;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTest() throws ServiceException {
        service1 = new Service();
        service1.setId(1L);
        service1.setTypeOfCar("Audi");
        service1.setDuration(10);
        service1.setPrice(new BigDecimal(79));
        service1.setDescription("Test");
        service1.setNameOfService("Tire change");
        service1.setOwnParts(false);

        order1 = new Order();
        order1.setId(1L);
        order1.setPaymentConfirmed(true);
        order1.setShipped(true);
        order1.setPaymentType(PaymentType.COD);
        order1.setClientId(1L);
        order1.setNote("Test");
        order1.getListOfServices().add(service1);

        order2 = new Order();
        order2.setId(2L);
        order2.setPaymentConfirmed(false);
        order2.setShipped(false);
        order2.setPaymentType(PaymentType.CARD);
        order2.setClientId(2L);
        order2.setNote("Test1");
        order2.getListOfServices().add(service1);

        serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setDuration(5);
        serviceDTO.setNameOfService("Change of gear");
        serviceDTO.setOwnParts(false);
        serviceDTO.setTypeOfCar("Honda");
        serviceDTO.setDescription("Test");
        serviceDTO.setPrice(BigDecimal.valueOf(1400));

        newOrderDTO = new CreateOrderDTO();
        newOrderDTO.setPaymentType(PaymentType.CARD);
        newOrderDTO.setClientId(2L);
        newOrderDTO.setNote("Test1");
        newOrderDTO.setListOfServices(new ArrayList<>());
        newOrderDTO.setListOfTires(new ArrayList<>());
        newOrderDTO.getListOfServices().add(serviceDTO);

        allOrders = new ArrayList<>();
        allOrders.add(order1);
        allOrders.add(order2);

        clientOrders = new ArrayList<>();
        clientOrders.add(order1);
    }

    @Captor
    ArgumentCaptor<Order> captor;

    @Test
    public void createTest() {
        when(orderService.create(any(Order.class))).thenAnswer(invoke -> {
            Order order = invoke.getArgumentAt(0, Order.class);
            order.setId(1L);
            return order;
        });
        OrderDTO orderDTO = orderFacade.create(newOrderDTO);
        verify(orderService).create(captor.capture());
        Assert.assertEquals(orderDTO.getId(), new Long(1));
        Assert.assertNotNull(captor.getValue());
        Order capturedOrder = captor.getValue();
        Assert.assertEquals(capturedOrder.getClientId(), newOrderDTO.getClientId());
        Assert.assertNotNull(capturedOrder.getListOfServices());
        Assert.assertEquals(capturedOrder.getListOfServices().size(), newOrderDTO.getListOfServices().size());
    }

    @Test
    public void updateTest() {
        UpdateOrderDTO orderDTO = beanMappingService.mapTo(order1, UpdateOrderDTO.class);
        orderFacade.update(orderDTO);
        verify(orderService).update(captor.capture());
        Assert.assertEquals(orderDTO.getId(), order1.getId());
        Assert.assertNotNull(captor.getValue());
        Assert.assertEquals(captor.getValue(), order1);
    }

    @Test
    public void deleteTest() {
        OrderDTO orderDTO = beanMappingService.mapTo(order1, OrderDTO.class);
        orderFacade.delete(orderDTO);
        verify(orderService).delete(captor.capture());
        Assert.assertNotNull(captor.getValue());
        Assert.assertEquals(captor.getValue().getId(), order1.getId());
    }

    @Test
    public void findOrderByIdTest() {
        when(orderService.findOrderById(1L)).thenReturn(order1);
        OrderDTO gatheredOrderDTO = orderFacade.findOrderById(1L);
        verify(orderService).findOrderById(1L);
        Assert.assertNotNull(gatheredOrderDTO);
        Assert.assertEquals(beanMappingService.mapTo(order1, OrderDTO.class), gatheredOrderDTO);
    }

    @Test
    public void findAllOrdersTest() {
        when(orderService.findAllOrders()).thenReturn(allOrders);
        List<OrderDTO> orderDTOs = orderFacade.findAllOrders();
        verify(orderService).findAllOrders();
        Assert.assertNotNull(orderDTOs);
        Assert.assertEquals(allOrders.size(), orderDTOs.size());
    }

    @Test
    public void findClientOrdersTest() {
        when(orderService.findClientOrders(1L)).thenReturn(clientOrders);
        List<OrderDTO> orderDTOs = orderFacade.findClientOrders(1L);
        verify(orderService).findClientOrders(1L);
        Assert.assertNotNull(orderDTOs);
        Assert.assertEquals(clientOrders.size(), orderDTOs.size());
    }

    @Test
    public void getOrderBillingTest() {
        OrderBilling orderBilling = new OrderBilling();
        orderBilling.setOrder(order1);
        orderBilling.setPrice(new BigDecimal(100));
        orderBilling.setPriceWithVAT(new BigDecimal(126));
        List<BillingItem> items = new ArrayList<>();
        items.add(new BillingItem("Test", 21,  new BigDecimal(100), new BigDecimal(126)));
        orderBilling.setBillingItems(items);
        when(orderService.getOrderBilling(1L)).thenReturn(orderBilling);
        OrderBillingDTO orderBillingDTO = orderFacade.getOrderBilling(1L);
        verify(orderService).getOrderBilling(1L);
        Assert.assertNotNull(orderBillingDTO);
        OrderBillingDTO orderBillingDTO1 = beanMappingService.mapTo(orderBilling, OrderBillingDTO.class);
        orderBillingDTO1.setOrderId(1L);
        Assert.assertEquals(orderBillingDTO1, orderBillingDTO);
    }
}
