package asadyian.zahra.digitallibrary.controller.model.feedback;


import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionType;

public class SaveEmotionRequest {

    private EmotionType type;

    public EmotionType getType() {
        return type;
    }

    public void setType(EmotionType type) {
        this.type = type;
    }
}
