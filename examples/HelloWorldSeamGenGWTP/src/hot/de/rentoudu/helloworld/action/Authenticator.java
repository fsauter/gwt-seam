package de.rentoudu.helloworld.action;

import javax.ejb.Local;

@Local
public interface Authenticator {

	boolean authenticate();

}
