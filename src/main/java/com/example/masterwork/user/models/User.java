package com.example.masterwork.user.models;

import com.example.masterwork.order.models.Order;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Column(name = "first_name")
  private String firstName;

  @NotNull
  @Column(name = "last_name")
  private String lastName;

  @NotNull
  @Column(name = "username", unique = true)
  private String userName;

  @NotNull
  @Column(unique = true)
  private String email;

  @NotNull
  private String password;

  @NotNull
  @Column(name = "phone_number")
  private String phoneNumber;

  @NotNull
  private String address;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Order> orders = new ArrayList<>();

  public User(String firstName, String lastName, String userName,
              String email, String password, String phoneNumber, String address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
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

