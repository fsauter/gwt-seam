package de.rentoudu.seamgwt.action;

import javax.ejb.Local;

@Local
public interface Authenticator {

    boolean authenticate();

}
