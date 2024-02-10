package org.safal.bookify.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue
    private long id;

   @Column(unique=true)
    private String email;

    private String password;

    private String name;

    @OneToMany(mappedBy="user")  
    private List<Book> books;

    @Override  
public String toString()   
{  
    return String.format("User [id=%d, email=%s, password=%s, name = %s]", id, email, password,name);  
}  
    
}
