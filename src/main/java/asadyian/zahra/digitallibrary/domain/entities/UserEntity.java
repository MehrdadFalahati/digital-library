package asadyian.zahra.digitallibrary.domain.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DL_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", doNotUseGetters=true, callSuper=false)
public class UserEntity extends AuditModel{

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 20)
    @Column(nullable = false)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    @Column(nullable = false)
    @Builder.Default
    private Boolean emailVerified = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_IMAGE_ID")
    private FileEntity image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dl_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<RoleEntity> roles = new HashSet<>();

    public void addRole(RoleEntity role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    public void removeRole(RoleEntity role){
        this.roles.remove(role);
    }

}
