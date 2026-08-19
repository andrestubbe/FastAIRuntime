package fastairuntime;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class FastAIEventBus {

    private static final FastAIEventBus INSTANCE = new FastAIEventBus();
    private final Map<String, List<Consumer<Object>>> listeners = new ConcurrentHashMap<>();

    private FastAIEventBus() {}

    public static FastAIEventBus getInstance() {
        return INSTANCE;
    }

    public synchronized void subscribe(String eventType, Consumer<Object> listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public void emit(String eventType, Object eventData) {
        List<Consumer<Object>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (Consumer<Object> listener : eventListeners) {
                try {
                    listener.accept(eventData);
                } catch (Exception ignored) {}
            }
        }
    }
}
