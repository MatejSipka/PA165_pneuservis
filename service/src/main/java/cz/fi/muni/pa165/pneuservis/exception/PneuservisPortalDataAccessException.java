/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.exception;

import org.springframework.dao.DataAccessException;

/**
 *
 * @author Maros Staurovsky
 */
public class PneuservisPortalDataAccessException extends DataAccessException {
    public PneuservisPortalDataAccessException(String msg) {
        super(msg);
    }

    public PneuservisPortalDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
