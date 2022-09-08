package com.rodrigo.foodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "person", uniqueConstraints = {@UniqueConstraint(name = "unq_email", columnNames = {"email"})})
public class User {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "create_at", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createAt;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "person_team",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Group> groups = new ArrayList<>();

    public boolean validPassword(String password) {
        return getPassword().equals(password);
    }

    public void removeGroup(Group group) {
        getGroups().remove(group);
    }

    public void addGroup(Group group) {
        getGroups().add(group);
    }
}
