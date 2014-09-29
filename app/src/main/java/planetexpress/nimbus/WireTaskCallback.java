package planetexpress.nimbus;


public interface WireTaskCallback<T> {
    void onFinished(T result);
}