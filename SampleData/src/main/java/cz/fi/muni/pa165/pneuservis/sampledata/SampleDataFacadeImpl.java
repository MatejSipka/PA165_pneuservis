/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.sampledata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.fi.muni.pa165.pneuservis.entity.*;
import cz.fi.muni.pa165.pneuservis.services.*;

import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;
import java.util.Calendar;


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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
