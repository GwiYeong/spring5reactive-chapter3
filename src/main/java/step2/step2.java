package step2;


import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class step2 {

    public static void main(String[] args) {
        NewsServicePublisher newsService = new NewsServicePublisher();
        NewsServiceSubscriber subscriber = new NewsServiceSubscriber(5);
        newsService.subscribe(subscriber);

        Optional<NewsLetter> letter = subscriber.eventuallyReadDigest();
    }
}

class NewsLetter {}

class NewsServicePublisher implements Publisher<NewsLetter> {
    @Override
    public void subscribe(Subscriber<? super NewsLetter> subscriber) {

    }
}
//TODO
class NewsServiceSubscriber implements Subscriber<NewsLetter> {
    final Queue<NewsLetter> mailbox = new ConcurrentLinkedQueue<NewsLetter>();
    final int take;
    final AtomicInteger remaining = new AtomicInteger();
    Subscription subscription;

    public NewsServiceSubscriber(int take) {
        this.take = take;
    }



    Optional<NewsLetter> eventuallyReadDigest() {
        NewsLetter letter = mailbox.poll();
        if (letter != null) {
            if (remaining.decrementAndGet() == 0) {
                subscription.request(take);
                remaining.set(take);
            }
            return Optional.of(letter);
        }
        return Optional.empty();
    };

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(take);
    }

    @Override
    public void onError(Throwable throwable) {
        // Nothing to do
    }

    @Override
    public void onComplete() {
        // Nothing to do
    }

    @Override
    public void onNext(NewsLetter newsLetter) {
        mailbox.offer(newsLetter);
    }
}