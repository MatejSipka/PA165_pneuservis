/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.facade;

import cz.fi.muni.pa165.pneuservis.dto.TireDTO;
import cz.fi.muni.pa165.pneuservis.entity.Tire;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import cz.fi.muni.pa165.pneuservis.facade.TireFacade;
import cz.fi.muni.pa165.pneuservis.services.BeanMappingService;
import cz.fi.muni.pa165.pneuservis.services.TireService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Matej Sipka
 */
@org.springframework.stereotype.Service
@Transactional
public class TireFacadeImpl implements TireFacade {
    
    @Autowired
    private TireService tireService;
    
    @Autowired
    private BeanMappingService beanMappingService;
    
    @Override
    public void create(TireDTO tire) throws IllegalArgumentException {
        Tire tireEntity = beanMappingService.mapTo(tire, Tire.class);
        tireService.create(tireEntity);
    }
    
    @Override
    public void update(TireDTO tire) {
        tireService.update(beanMappingService.mapTo(tire, Tire.class));
    }
    
    @Override
    public void delete(TireDTO tire) {
        tireService.delete(beanMappingService.mapTo(tire, Tire.class));
    }
    
    @Override
    public TireDTO findById(Long id) {
        return beanMappingService.mapTo(tireService.findById(id), TireDTO.class);
    }
    
    @Override
    public List<TireDTO> findAll() {
        return beanMappingService.mapTo(tireService.findAll(), TireDTO.class);
    }
    
    @Override
    public List<TireDTO> findByCatalogNumber(int catalogNumber) {
        List<Tire> tires = tireService.findByCatalogNumber(catalogNumber);
        return (tires == null) ? null : beanMappingService.mapTo(tires, TireDTO.class);
    }
    
    @Override
    public List<TireDTO> findByProfile(int profile) {
        List<Tire> tires = tireService.findByProfile(profile);
        return (tires == null) ? null : beanMappingService.mapTo(tires, TireDTO.class);
    }
    
    @Override
    public List<TireDTO> findByDiameter(int diameter) {
        List<Tire> tires = tireService.findByDiameter(diameter);
        return (tires == null) ? null : beanMappingService.mapTo(tires, TireDTO.class);
    }
    
    @Override
    public List<TireDTO> findBySize(int size) {
        List<Tire> tires = tireService.findBySize(size);
        return (tires == null) ? null : beanMappingService.mapTo(tires, TireDTO.class);
    }
    
    @Override
    public List<TireDTO> findByType(TireType type) {
        List<Tire> tires = tireService.findByType(type);
        return (tires == null) ? null : beanMappingService.mapTo(tires, TireDTO.class);
    }
    
    @Override
    public List<TireDTO> findByManufacturer(TireManufacturer manufacturer) {
        List<Tire> tires = tireService.findByManufacturer(manufacturer);
        return (tires == null) ? null : beanMappingService.mapTo(tires, TireDTO.class);
    }
    
}
