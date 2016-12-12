/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.sampledata;

import java.io.IOException;

/**
 *
 * @author Maros Staurovsky
 */
public interface SampleDataFacade {
     //Signals that an I/O exception of some sort has occurred - loading of sample data was not succesful
    void loadData() throws IOException;
}
