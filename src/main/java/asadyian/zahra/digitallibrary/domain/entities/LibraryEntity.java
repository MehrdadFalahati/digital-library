package asadyian.zahra.digitallibrary.domain.entities;

import asadyian.zahra.digitallibrary.controller.model.library.LibraryResponse;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DL_LIBRARY")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", doNotUseGetters=true, callSuper=false)
public class LibraryEntity extends AuditModel{
    @Id
    @Column(name = "LIBRARY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "CODE_", nullable = false)
    private String code;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CONTENT_TYPE_ID")
    private ContentTypeEntity contentType;

    public static LibraryResponse convert2Responce(LibraryEntity libraryEntity) {
        return LibraryResponse.builder()
                .id(libraryEntity.getId())
                .code(libraryEntity.getCode())
                .title(libraryEntity.getTitle())
                .contentType(new LibraryResponse.ContentType(libraryEntity.contentType.getId(), libraryEntity.contentType.getTitle()))
                .build();
    }
}
