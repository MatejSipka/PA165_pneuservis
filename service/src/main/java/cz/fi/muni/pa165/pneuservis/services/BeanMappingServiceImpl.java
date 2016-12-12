/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Maros Staurovsky
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService{
    
    @Autowired
    private Mapper dozer;

    @Override
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {

        if (objects == null){
            throw new PneuservisPortalDataAccessException("Object returned by called method is null");
        }

        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public  <T> T mapTo(Object u, Class<T> mapToClass) {
        if (u == null){
            throw new PneuservisPortalDataAccessException("Object returned by called method is null");
        }
        return dozer.map(u,mapToClass);
    }
    
    @Override
    public Mapper getMapper(){
    	return dozer;
    }
}
