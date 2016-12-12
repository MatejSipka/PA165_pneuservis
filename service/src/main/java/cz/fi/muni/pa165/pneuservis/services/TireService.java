/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.services;

import cz.fi.muni.pa165.pneuservis.entity.Tire;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import java.util.List;

/**
 *
 * @author Matej Sipka
 */
public interface TireService {

    /**
     * Creates a new Tire
     *
     * @param tire Tire to create
     * @return created Tire
     * @throws IllegalArgumentException
     */
    Tire create(Tire tire) throws IllegalArgumentException;

    /**
     * Updates an existing Tire
     *
     * @param tire Tire to update
     * @return updated Tire
     */
    Tire update(Tire tire);

    /**
     * Deletes an existing Tire
     *
     * @param tire Existing tire to delete
     */
    void delete(Tire tire);

    /**
     * Finds a Tire according to its Id
     *
     * @param id a Tire Id
     * @return Found Tire or null if not found
     */
    Tire findById(Long id);

    /**
     * Finds all stored Tires
     *
     * @return List of tires found
     */
    List<Tire> findAll();

    /**
     * Finds all tires according to a catalog number
     *
     * @param catalogNumber A number of good from the catalog
     * @return List of found tires
     */
    List<Tire> findByCatalogNumber(int catalogNumber);

    /**
     * Finds all tires according to a profile
     *
     * @param profile a Tire profile
     * @return List of found tires
     */
    List<Tire> findByProfile(int profile);

    /**
     * Finds all tires according to a diameter
     *
     * @param diameter a Tire diameter
     * @return List of found tires
     */
    List<Tire> findByDiameter(int diameter);

    /**
     * Finds all tires according to a size
     *
     * @param size a Tire size
     * @return List of found tires
     */
    List<Tire> findBySize(int size);

    /**
     * Finds all tires according to a type
     *
     * @param type a Tire type
     * @return List of found tires
     */
    List<Tire> findByType(TireType type);

    /**
     * Finds all tires according to a manufacturer
     *
     * @param manufacturer a Tire manufacturer
     * @return List of found tires
     */
    List<Tire> findByManufacturer(TireManufacturer manufacturer);

}
