package ca.mcgill.ecse321.sportcenter.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account implements UserDetails
{
  private String email;
  private String password;
  private String name;
  private String imageURL;
  private String pronouns;
  private Authority authority;
  @Id
  @GeneratedValue
  private int id;

  @ManyToOne
  @JoinColumn(name="sport_center_id")
  private SportCenter sport_center;

  public Account()
  {
  }

  public Account(String aEmail, String aPassword, String aName, String aImageURL, String aPronouns, int aId, SportCenter aCenter)
  {
    email = aEmail;
    password = aPassword;
    name = aName;
    imageURL = aImageURL;
    pronouns = aPronouns;
    id = aId;
    boolean didAddCenter = setCenter(aCenter);
    if (!didAddCenter)
    {
      throw new RuntimeException("Unable to create account due to center. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail.toLowerCase();
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setImageURL(String aImageURL)
  {
    boolean wasSet = false;
    imageURL = aImageURL;
    wasSet = true;
    return wasSet;
  }

  public boolean setPronouns(String aPronouns)
  {
    boolean wasSet = false;
    pronouns = aPronouns;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getUsername() {
    return email;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public String getImageURL()
  {
    return imageURL;
  }

  public String getPronouns()
  {
    return pronouns;
  }

  public int getId()
  {
    return id;
  }

  public SportCenter getCenter()
  {
    return sport_center;
  }

  public boolean setCenter(SportCenter aCenter)
  {
    boolean wasSet = false;
    if (aCenter == null)
    {
      return wasSet;
    }

    SportCenter existingCenter = sport_center;
    sport_center = aCenter;
    if (existingCenter != null && !existingCenter.equals(aCenter))
    {
      existingCenter.removeAccount(this);
    }
    sport_center.addAccount(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    SportCenter placeholderCenter = sport_center;
    this.sport_center = null;
    if(placeholderCenter != null)
    {
      placeholderCenter.removeAccount(this);
    }
  }

  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "," +
            "imageURL" + ":" + getImageURL()+ "," +
            "pronouns" + ":" + getPronouns()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "center = "+(getCenter()!=null?Integer.toHexString(System.identityHashCode(getCenter())):"null");
  }

  public void setAuthority(Authority authority) {
      this.authority = authority;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      List<Authority> list = new ArrayList<>();
      list.add(authority);
      return list;
  }

  @Override
  public boolean isAccountNonExpired() {
      return true;
  }

  @Override
  public boolean isAccountNonLocked() {
      return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
      return true;
  }

  @Override
  public boolean isEnabled() {
      return true;
  }
}