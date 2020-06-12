package com.cvinicius.springcomaws.domain.user;

import com.cvinicius.springcomaws.domain.user.auth.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="tbl_permission")
public class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    public Permission(Roles roles){
        this.authority = roles.name();
    }
}
