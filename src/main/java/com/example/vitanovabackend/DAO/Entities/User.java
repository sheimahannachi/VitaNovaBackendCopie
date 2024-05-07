package com.example.vitanovabackend.DAO.Entities;

import com.example.vitanovabackend.Security.config.GrantedAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Table(name = "User")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;
    @Column(name = "username")
    private String username;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "dateOfBirth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "email")
    private String email;
    @Column(name = "weight")
    private float weight;
    @Column(name = "height")
    private float height;
    @Column(name = "password")
    private String password;
    @Column(name = "archive")
    private Boolean archive=false;

    private String picture;
    @Column(name = "verified")
    private Boolean verified=false;


    @Column(name = "Community_Activity")
    long comunityActivity=0;


    @Column(name = "score")
    private int score=0;
    @Column(name = "phone")
    private String phone;
    @OneToOne
    PersonalGoals personalGoals;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    PeriodTracker periodTracker;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    Cart cart;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)


    List<Food>foods=new ArrayList<>();

    @JsonBackReference("notifBack")
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    List<Notification> notifications = new ArrayList<>();


    @JsonBackReference("communityBackRef")
    @ManyToOne( cascade = CascadeType.ALL )
    Community community;

    @OneToMany( mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LikeProduct> likedProducts = new ArrayList<>();



    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ERole role ;

    @Column(name = "plan")
    @Enumerated(EnumType.STRING)
    private Plan plan ;



    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

  /*  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   private List<IPAdresses> ipAdresses= new ArrayList<>();*/



    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.name());
        return Collections.singleton(authority);
    }



    // Custom deserializer for GrantedAuthority

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }




}
