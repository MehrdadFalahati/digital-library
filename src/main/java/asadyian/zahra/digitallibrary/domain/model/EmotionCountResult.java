package asadyian.zahra.digitallibrary.domain.model;


import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionType;

public class EmotionCountResult {
    public static final String PROP_COUNT = "count";
    public static final String PROP_TYPE = "type";

    private EmotionType type;
    private long count;

    public EmotionType getType() {
        return type;
    }

    public void setType(EmotionType type) {
        this.type = type;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
