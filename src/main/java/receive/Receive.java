package receive;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Receive {
    private final Map<Class<?>, Consumer<?>> consumerMap;

    private Receive(Map<Class<?>, Consumer<?>> consumerMap) {
        this.consumerMap = consumerMap;
    }

    public static Builder newBuilder() {
        return new Receive.Builder();
    }

    public void accept(Object message) {
        Map<Integer, Boolean> map = new HashMap<>();
        for (Map.Entry<Integer, Boolean> entry : map.entrySet()) {

        }
        if (message == null) {
            return;
        }
        Consumer<Object> consumer = (Consumer<Object>) consumerMap.get(message.getClass());
        if (consumer == null) {
            return;
        }
        consumer.accept(message);
    }

    public static class Builder {
        private final Map<Class<?>, Consumer<?>> consumerMap = new HashMap<>();

        public <T> Builder match(Class<T> type, Consumer<T> consumer) {
            Consumer<?> prev = consumerMap.put(type, consumer);
            if (prev != null) {
                System.out.println("重复");
                return null;
            }
            return this;
        }

        public Receive build() {
            return new Receive(consumerMap);
        }
    }
}
