package step1;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class step1 {

    public ListenableFuture<?> requestData() {

        AsyncRestTemplate httpClient;
        AsyncDatabaseClient databaseClient;

        // api 호출 후 db에 저장하고 사용자에게 데이터 리턴.
        //TODO

    }
}

interface AsyncRestTemplate {
    <T> ListenableFuture<T> execute();
}
interface AsyncDatabaseClient {
    <T> CompletionStage<T> store(CompletionStage<T> stage);
}

final class AsyncAdapters {
    public static <T> CompletionStage<T> toCompletion(ListenableFuture<T> future) {
        //TODO
    }

    public static <T> ListenableFuture<T>  toListenable(CompletionStage<T> stage) {
        //TODO
    }
}
