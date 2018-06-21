package edu.bjtu.analysis.constant;

/**
 * Created by baohehe
 * 事件类型定义
 */
public enum EventType {

    //作品播放切换事件,此时发送上一个播放作品的信息
    播放切换("works_play_on_switch"),
    FEED_CARD("feed_card_on_entry"),
    APP_EVENT("app_on_open"),
    LIVING_PV("living_pv"),
    PLAY_VIDEO("play_video"),
    PAGE_VIEW("page_view"),
    PLAY_STUCK("stuck"),
    FANS_CIRCLE("fans_circle_on_entry"),
    FANS_CIRCLE_DETAIL("fans_circle_detail_on_entry");

    public final String name;

    EventType(String name) {
        this.name = name;
    }

    public static EventType getMenuType(String name) {
        for (EventType type : EventType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }

}
