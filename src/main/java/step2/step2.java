package step2;


import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class step2 {

    public static void main(String[] args) {
        NewsServicePublisher newsService = new NewsServicePublisher();
        NewsServiceSubscriber subscriber = new NewsServiceSubscriber(5);
        newsService.subscribe(subscriber);

        subscriber.eventuallyReadDigest();
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

    void eventuallyReadDigest() {
        //TODO
    };
}