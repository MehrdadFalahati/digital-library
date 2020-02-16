package asadyian.zahra.digitallibrary.domain.entities.feedback;

import asadyian.zahra.digitallibrary.domain.entities.AuditModel;
import asadyian.zahra.digitallibrary.domain.entities.ContentEntity;
import asadyian.zahra.digitallibrary.domain.entities.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DL_EMOTION",
		uniqueConstraints={
				@UniqueConstraint(columnNames = {"FK_USER_ID", "FK_CONTENT_ID"})
		})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", doNotUseGetters=true, callSuper=false)
public class EmotionEntity extends AuditModel {
	public static final String REF = "EmotionEntity";
	public static final String PROP_ID = "id";

	public static final String PROP_EMOTION_TYPE = "emotionType";

	public static final String PROP_USER = "user";
	public static final String PROP_CONTENT = "content";

	// primary key
	@Id
	@Column(name = "EMOTION_ID")
	private String id;

	// fields
	@Basic
	@Column(name = "EMOTION_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private EmotionType emotionType;

	// many to one
	@ManyToOne(targetEntity = UserEntity.class, optional = true, cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_USER_ID", nullable = false)
	private UserEntity user;
	@ManyToOne(targetEntity = ContentEntity.class, optional = true, cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONTENT_ID", nullable = false)
	private ContentEntity content;
}
