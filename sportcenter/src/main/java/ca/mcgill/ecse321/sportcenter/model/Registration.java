package ca.mcgill.ecse321.sportcenter.model;

import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 28 "model.ump"
// line 116 "model.ump"
public class Registration
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Registration Associations
  private List<Customer> customer;
  private List<Session> session;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Registration()
  {
    customer = new ArrayList<Customer>();
    session = new ArrayList<Session>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Customer getCustomer(int index)
  {
    Customer aCustomer = customer.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomer()
  {
    List<Customer> newCustomer = Collections.unmodifiableList(customer);
    return newCustomer;
  }

  public int numberOfCustomer()
  {
    int number = customer.size();
    return number;
  }

  public boolean hasCustomer()
  {
    boolean has = customer.size() > 0;
    return has;
  }

  public int indexOfCustomer(Customer aCustomer)
  {
    int index = customer.indexOf(aCustomer);
    return index;
  }
  /* Code from template association_GetMany */
  public Session getSession(int index)
  {
    Session aSession = session.get(index);
    return aSession;
  }

  public List<Session> getSession()
  {
    List<Session> newSession = Collections.unmodifiableList(session);
    return newSession;
  }

  public int numberOfSession()
  {
    int number = session.size();
    return number;
  }

  public boolean hasSession()
  {
    boolean has = session.size() > 0;
    return has;
  }

  public int indexOfSession(Session aSession)
  {
    int index = session.indexOf(aSession);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomer()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customer.contains(aCustomer)) { return false; }
    customer.add(aCustomer);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomer(Customer aCustomer)
  {
    boolean wasRemoved = false;
    if (customer.contains(aCustomer))
    {
      customer.remove(aCustomer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCustomerAt(Customer aCustomer, int index)
  {  
    boolean wasAdded = false;
    if(addCustomer(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomer()) { index = numberOfCustomer() - 1; }
      customer.remove(aCustomer);
      customer.add(index, aCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerAt(Customer aCustomer, int index)
  {
    boolean wasAdded = false;
    if(customer.contains(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomer()) { index = numberOfCustomer() - 1; }
      customer.remove(aCustomer);
      customer.add(index, aCustomer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerAt(aCustomer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSession()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addSession(Session aSession)
  {
    boolean wasAdded = false;
    if (session.contains(aSession)) { return false; }
    session.add(aSession);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSession(Session aSession)
  {
    boolean wasRemoved = false;
    if (session.contains(aSession))
    {
      session.remove(aSession);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSessionAt(Session aSession, int index)
  {  
    boolean wasAdded = false;
    if(addSession(aSession))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSession()) { index = numberOfSession() - 1; }
      session.remove(aSession);
      session.add(index, aSession);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSessionAt(Session aSession, int index)
  {
    boolean wasAdded = false;
    if(session.contains(aSession))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSession()) { index = numberOfSession() - 1; }
      session.remove(aSession);
      session.add(index, aSession);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSessionAt(aSession, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    customer.clear();
    session.clear();
  }

}