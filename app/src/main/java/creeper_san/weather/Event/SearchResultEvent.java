package creeper_san.weather.Event;

import creeper_san.weather.Json.SearchItem;

public class SearchResultEvent {
    private final boolean isSucceed;
    private final SearchItem searchItem;

    public SearchResultEvent(boolean isSucceed, SearchItem searchItem) {
        this.isSucceed = isSucceed;
        this.searchItem = searchItem;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public SearchItem getSearchItem() {
        return searchItem;
    }
}
