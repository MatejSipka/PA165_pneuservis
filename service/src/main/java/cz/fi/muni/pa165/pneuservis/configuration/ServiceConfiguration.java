package cz.fi.muni.pa165.pneuservis.configuration;

import cz.fi.muni.pa165.pneuservis.dto.*;
import cz.fi.muni.pa165.pneuservis.entity.Order;
import cz.fi.muni.pa165.pneuservis.services.BillingItem;
import cz.fi.muni.pa165.pneuservis.services.OrderBilling;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import cz.fi.muni.pa165.pneuservis.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.pneuservis.entity.Person;
import cz.fi.muni.pa165.pneuservis.entity.Service;
import cz.fi.muni.pa165.pneuservis.entity.Tire;
import org.dozer.loader.api.BeanMappingBuilder;

/**
 * @author  Ivan Moscovic on 23.11.2016.
 */

@Configuration
@Import(PersistenceSampleApplicationContext.class)
@ComponentScan(basePackages = "cz.fi.muni.pa165.pneuservis")
public class ServiceConfiguration {

    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {

        @Override
        protected void configure() {
            mapping(Person.class, PersonDTO.class);
            mapping(Service.class, ServiceDTO.class);
            mapping(Tire.class, TireDTO.class);
            mapping(Order.class, CreateOrderDTO.class);
            mapping(Order.class, UpdateOrderDTO.class);
            mapping(Order.class, OrderDTO.class);
            mapping(OrderBilling.class, OrderBillingDTO.class);
            mapping(BillingItem.class, OrderBillingDTO.class);
        }
    }
}
