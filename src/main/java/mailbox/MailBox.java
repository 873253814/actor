package mailbox;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class MailBox {
    private Executor defaultExecutor;
    private ConcurrentLinkedQueue<Envelope> queue = new ConcurrentLinkedQueue<Envelope>();
    private AtomicInteger counter = new AtomicInteger();

    public MailBox(Executor defaultExecutor) {
        this.defaultExecutor = defaultExecutor;
    }

    public void enqueue(Runnable runnable) {
        enqueue(defaultExecutor, runnable);
    }

    public void enqueue(Executor executor, Runnable task) {
        Envelope envelope = new Envelope(task, executor);
        queue.add(envelope);
        int count = counter.incrementAndGet();
        if (count == 1) {
            Envelope next = queue.poll();
            assert next != null;
            next.executor.execute(next);
        }
    }


    private class Envelope implements Runnable{
        private Runnable t;
        private Executor executor;

        Envelope(Runnable t, Executor executor) {
            this.t = t;
            this.executor = executor;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    t.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int count = counter.decrementAndGet();
                if (count == 0) {
                    break;
                }
                Envelope next = queue.poll();
                assert next != null;
                if (next.executor == executor) {
                    t = next.t;
                } else {
                    next.executor.execute(next);
                    break;
                }
            }
        }
    }
}
