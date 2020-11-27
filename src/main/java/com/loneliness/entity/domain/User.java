package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loneliness.entity.Role;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;


@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor
@AllArgsConstructor
public class User implements Domain, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = New.class)
    @NotNull(groups = Exist.class)
    @Positive(groups = Exist.class)
    private Integer id;
    @Length(max = 255,groups = {New.class,Exist.class} )
    @Column( unique = true)
    private String googleId;
    @Length(max = 255,groups = {New.class,Exist.class} )
    @NotBlank(groups = {New.class,Exist.class})
    @Column( nullable = false,unique = true)
    private String name;
    private boolean active;
    @NotBlank(groups = {New.class,Exist.class})
    @Email(groups = {New.class,Exist.class})
    @Column( unique = true, nullable = false)
    private String email;
    private String activationCode;
    @Length(max = 255,groups = {New.class,Exist.class} )
    private String locale;
    @ManyToOne(cascade = CascadeType.REFRESH )
    private Picture picture;
    @ManyToMany(cascade = {
            CascadeType.REFRESH,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(name = "book_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books;
    @NotEmpty(groups = {New.class,Exist.class})
    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @PastOrPresent(groups = {New.class,Exist.class})
    private Timestamp lastVisit;
    private Boolean expired;
    private Boolean locked;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
       // return expired;
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
       // return locked;
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
