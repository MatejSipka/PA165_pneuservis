/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.sampledata;

import cz.fi.muni.pa165.pneuservis.entity.Order;
import cz.fi.muni.pa165.pneuservis.entity.Person;
import cz.fi.muni.pa165.pneuservis.entity.Service;
import cz.fi.muni.pa165.pneuservis.entity.Tire;
import cz.fi.muni.pa165.pneuservis.enums.PaymentType;
import cz.fi.muni.pa165.pneuservis.enums.PersonType;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import cz.fi.muni.pa165.pneuservis.services.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Maros Staurovsky
 */
@Component
@Transactional
public class SampleDataFacadeImpl implements SampleDataFacade{

    @Autowired
    private PersonService personService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private TireService tireService;
    
    @Override
    public void loadData() throws IOException {

        Service service1 = new Service(3, true, "change of oil", BigDecimal.valueOf(1500), "boring", "audi");
        Service service2 = new Service(5, true, "change of engine", BigDecimal.valueOf(10000), "yeah", "fiat");
        Service service3 = new Service(10, true, "change of gear", BigDecimal.valueOf(7500), "ou yeah", "bmw");
        Service service4 = new Service(2, true, "change of suspension", BigDecimal.valueOf(8500), "bleee", "mercedes");

        Tire tire1 = new Tire(TireType.WINTER, 258, 255, 17, TireManufacturer.FIRESTONE, BigDecimal.valueOf(1050), "dsa", "audi");
        Tire tire2 = new Tire(TireType.SUMMER, 211, 255, 18, TireManufacturer.FALKEN, BigDecimal.valueOf(1850), "dsa", "bmw");
        Tire tire3 = new Tire(TireType.WINTER, 255, 255, 19, TireManufacturer.BRIDGESTONE, BigDecimal.valueOf(2150), "dsa", "fiat");
        Tire tire4 = new Tire(TireType.ALL_SEASON, 254, 255, 20, TireManufacturer.BARUM, BigDecimal.valueOf(150), "dsa", "ford");

        Person customer = new Person("Adam", "Fero", "xFero", "12345", PersonType.CLIENT, Calendar.getInstance());
        Person employee = new Person("Peter", "Marian", "xMarian", "14789", PersonType.EMPLOYEE, Calendar.getInstance());

        List<Service> services = Arrays.asList(service1, service2);
        List<Tire> tires1 = Arrays.asList(tire1, tire2);
        List<Tire> tires2 = Arrays.asList(tire1);
        Order order1 = new Order(customer.getId(), services, tires1, "my order", false, false, PaymentType.CARD);
        Order order2 = new Order(customer.getId(), services, tires2, "my order2", false, true, PaymentType.TRANSFER);
    }
}
