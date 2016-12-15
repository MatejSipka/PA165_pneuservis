/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.sampledata;

import cz.fi.muni.pa165.pneuservis.entity.Order;
import cz.fi.muni.pa165.pneuservis.entity.Person;
import cz.fi.muni.pa165.pneuservis.entity.Services;
import cz.fi.muni.pa165.pneuservis.entity.Tire;
import cz.fi.muni.pa165.pneuservis.enums.PaymentType;
import cz.fi.muni.pa165.pneuservis.enums.PersonType;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cz.fi.muni.pa165.pneuservis.services.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Maros Staurovsky
 */
@Service
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

        Services service1 = createService(3, true, "change of oil", BigDecimal.valueOf(1500), "boring", "audi");
        Services service2 = createService(5, true, "change of engine", BigDecimal.valueOf(10000), "yeah", "fiat");
        Services service3 = createService(10, true, "change of transmission", BigDecimal.valueOf(7500), "ou yeah", "bmw");
        Services service4 = createService(2, true, "change of suspension", BigDecimal.valueOf(8500), "bleee", "mercedes");

        Tire tire1 = createTire(TireType.WINTER, 258, 255, 17, TireManufacturer.FIRESTONE, BigDecimal.valueOf(1050), "dsa", "audi");
        Tire tire2 = createTire(TireType.SUMMER, 211, 255, 18, TireManufacturer.FALKEN, BigDecimal.valueOf(1850), "dsa", "bmw");
        Tire tire3 = createTire(TireType.WINTER, 255, 255, 19, TireManufacturer.BRIDGESTONE, BigDecimal.valueOf(2150), "dsa", "fiat");
        Tire tire4 = createTire(TireType.ALL_SEASON, 254, 255, 20, TireManufacturer.BARUM, BigDecimal.valueOf(150), "dsa", "ford");
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(1995, 9, 20);
        Date date1 = calendar.getTime();
        
        calendar.set(1985, 4, 8);
        Date date2 = calendar.getTime();
        
        Person customer = createPerson("Adam", "Fero", "xFero", "12345", PersonType.CLIENT, date1);
        Person employee = createPerson("Peter", "Marian", "xMarian", "14789", PersonType.EMPLOYEE, date2);

        List<Services> services = Arrays.asList(service1, service2);
        List<Tire> tires1 = Arrays.asList(tire1, tire2);
        List<Tire> tires2 = Arrays.asList(tire1);
        Order order1 = createOrder(customer, services, tires1, "my order", false, false, PaymentType.CARD);
//        Order order2 = createOrder(customer, services, tires2, "my order2", false, true, PaymentType.TRANSFER);
    }
    
    private Order createOrder(Person person, List <Services> listOfServices,List<Tire> listOfTires,
            String note, boolean paymentConfirmed,boolean shipped, PaymentType paymentType){     
        return orderService.findOrderById(orderService.create(new Order(person.getId(),listOfServices,listOfTires,note,paymentConfirmed,shipped,paymentType)).getId());
    }
    
    private Tire createTire (TireType type, int catalogSize,int tireSize, int diameter,TireManufacturer manufacturer, BigDecimal price,String description, String typeOfCar){
        return tireService.findById(tireService.create(new Tire(type, catalogSize, tireSize, diameter, manufacturer, price, description, typeOfCar)).getId());
    }
    
    private Services createService(int duration, boolean ownParts,String nameOfService,BigDecimal price,String description, String typeOfCar){
        return serviceService.findById(serviceService.create(new Services(duration, ownParts, nameOfService, price, description, typeOfCar)));
    }
    
    private Person createPerson(String firstname, String surname, String login, String passwordHash, PersonType type, Date dateOfBirth){
        return personService.findById(personService.create(new Person(firstname, surname, login, passwordHash, type, dateOfBirth),passwordHash));
    }
    
}
