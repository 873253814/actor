package actor;

import mailbox.MailBox;
import receive.Receive;

import java.util.concurrent.*;

public abstract class TypeActor<T> {
    protected MailBox mailBox;
    private Receive receive;

    protected TypeActor() {
        mailBox = createMailBox();
        receive = receive();
    }

    public void tell(T message) {
        mailBox.enqueue(() -> receive.accept(message));
    }

    public void tell(T message, Executor executor) {
        mailBox.enqueue(executor, () -> receive.accept(message));
    }

    public void tell(Runnable task) {
        mailBox.enqueue(task);
    }

    public void tell(Runnable task, Executor executor) {
        mailBox.enqueue(executor, task);
    }


    protected MailBox createMailBox() {
        return new MailBox(ForkJoinPool.commonPool());
    }

    protected abstract Receive receive();
}
