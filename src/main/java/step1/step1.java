package step1;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class step1 {

    public ListenableFuture<?> requestData() {

        AsyncRestTemplate<String> httpClient;
        AsyncDatabaseClient<String> databaseClient;

        // api 호출 후 db에 저장하고 사용자에게 데이터 리턴.
        CompletionStage<String> completionStage = AsyncAdapters.toCompletion(
                httpClient.execute()
        );
        return AsyncAdapters.toListenable(
                databaseClient.store(completionStage)
        );

    }
}

interface AsyncRestTemplate<T> {
    <T> ListenableFuture<T> execute();
}
interface AsyncDatabaseClient<T> {
    <T> CompletionStage<T> store(CompletionStage<T> stage);
}

final class AsyncAdapters {
    public static <T> CompletionStage<T> toCompletion(ListenableFuture<T> future) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        future.addCallback(
                completableFuture::complete,
                completableFuture::completeExceptionally
        );
        return completableFuture;
    }

    public static <T> ListenableFuture<T>  toListenable(CompletionStage<T> stage) {
        SettableListenableFuture<T> future = new SettableListenableFuture<>();
        stage.whenComplete((v, t) -> {
            if (t == null) {
                future.set(v);
            } else {
                future.setException(t);
            }
        });
        return future;
    }
}
