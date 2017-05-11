package creeper_san.weather.Item;

public class PartItem {
    private int order;
    private int type;

    public PartItem() {
    }

    public PartItem(int order, int type) {
        this.order = order;
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
