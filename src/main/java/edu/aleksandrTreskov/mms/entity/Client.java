package edu.aleksandrTreskov.mms.entity;

import lombok.*;

import javax.persistence.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Table(name = "CLIENT")
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Please provide a name")
    @Column(name = "name", nullable = false)
    private String name;
    @NotEmpty(message = "Please provide a surname")
    @Column(name = "surname", nullable = false)
    private String surname;
    @NotNull
    @Column(name = "birthDate")
    private String birthDate;
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;
    @NotNull
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "password", nullable = false)
    @Size(min = 4)
    private String password;
    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private List<Address> addresses;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Override
    public String toString() {
        return "Client"+id;
    }
}
