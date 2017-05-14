package creeper_san.weather.Event;

public class PartEditEvent {
    public final static int EDIT_ADD = 1;
    public final static int EDIT_REMOVE = 2;
    public final static int EDIT_REORDER = 3;

    private final int editType;

    public PartEditEvent(int editType) {
        this.editType = editType;
    }

    public int getEditType() {
        return editType;
    }
}
