package asadyian.zahra.digitallibrary.domain.entities;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "DL_ROLE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", doNotUseGetters=true, callSuper=false)
public class RoleEntity {

    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "ROLE_NAME",length = 60)
    private RoleName name;
}
