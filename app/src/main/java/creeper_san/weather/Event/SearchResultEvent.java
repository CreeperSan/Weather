package creeper_san.weather.Event;

import creeper_san.weather.Json.SearchJson;

public class SearchResultEvent {
    private final boolean isSucceed;
    private final SearchJson searchItem;

    public SearchResultEvent(boolean isSucceed, SearchJson searchItem) {
        this.isSucceed = isSucceed;
        this.searchItem = searchItem;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public SearchJson getSearchItem() {
        return searchItem;
    }
}
