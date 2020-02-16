package asadyian.zahra.digitallibrary.domain.model;

import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.EnumMap;


@Data
@AllArgsConstructor
public class EmotionResult {
    EnumMap<EmotionType, Long> item;
    private EmotionType currentType;

    public EmotionResult() {
        item = new EnumMap<>(EmotionType.class);
        for (EmotionType type:EmotionType.values()){
            item.put(type, 0L);
        }
    }
}
