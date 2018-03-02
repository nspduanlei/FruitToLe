package com.ap88.yg.fruittole.ui.fragments.web.event;

import android.support.annotation.NonNull;

import java.util.HashMap;


/**
 * Created by duanlei on 2018/1/8.
 */

public class EventManager {
  private static final HashMap<String, Event> EVENTS = new HashMap<>();

  private EventManager() {}

  private static class Holder {
    private static final EventManager INSTANCE = new EventManager();
  }

  public static EventManager getInstance() {
    return Holder.INSTANCE;
  }

  public EventManager addEvent(@NonNull String name, @NonNull Event event) {
    EVENTS.put(name, event);
    return this;
  }

  public Event createEvent(@NonNull String action) {
    final Event event = EVENTS.get(action);
    if (event == null) {
      return new UndefinedEvent();
    }
    return event;
  }
}
