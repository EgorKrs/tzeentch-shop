package com.loneliness.dto;

import com.loneliness.entity.Role;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Picture;
import com.loneliness.entity.domain.Room;
import com.loneliness.entity.domain.User;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements DTO<User> {
    private Integer id;
    @Length(max = 255,groups = {New.class, Exist.class} )
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
    private Picture picture;
    private Set<Book> books;
    @NotEmpty(groups = {New.class,Exist.class})
    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @PastOrPresent(groups = {New.class,Exist.class})
    private Timestamp lastVisit;
    private Boolean expired;
    private Boolean locked;


    @Override
    public User fromDTO() {
        return new User(id,googleId,name,active,email,activationCode,locale,picture,books,roles,lastVisit,expired,locked);
    }
}
