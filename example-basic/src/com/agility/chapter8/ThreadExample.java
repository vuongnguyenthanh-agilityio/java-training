package com.agility.chapter8;

import java.util.List;
import java.util.concurrent.*;

public class ThreadExample {
  public void testRunnable() {
    ExecutorService pool = Executors.newCachedThreadPool();
    String[] names = {"One", "Two", "Three"};
    for (int i = 0, size = names.length; i < size; i++) {
      pool.execute(new RunnableOne(names[i]));
    }

    System.out.println("Before shutdown: isShutdown()=" + pool.isShutdown()
        + ", isTerminated()=" + pool.isTerminated());
    pool.shutdown(); // New threads cannot be added to the pool
    //pool.execute(new MyRunnable("Four"));    //RejectedExecutionException
    System.out.println("After shutdown: isShutdown()=" + pool.isShutdown()
        + ", isTerminated()=" + pool.isTerminated());

    long timeout = 100;
    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    System.out.println("Waiting all threads completion for "
        + timeout + " " + timeUnit + "...");

    boolean isTerminated = false;
    try {
      isTerminated = pool.awaitTermination(timeout, timeUnit);
      if (!isTerminated) {
        System.out.println("Calling shutdownNow()...");
        List<Runnable> list = pool.shutdownNow();
        System.out.println(list.size() + " threads running");
        isTerminated = pool.awaitTermination(timeout, timeUnit);
        if (!isTerminated) {
          System.out.println("Some threads are still running");
        }
        System.out.println("Exiting");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("isTerminated()=" + isTerminated);
  }

  public void testGetResult() {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    List<Callable<String>> list = List.of(new CallableOne(2), new CallableOne(10));
    try {
      List<Future<String>> futures = executorService.invokeAll(list);
      for (Future<String> future : futures) {
        try {
          String result = future.get();
          System.out.println("Result: " + result);
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
      }
      executorService.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  private class RunnableOne implements Runnable {

    private String name;

    public RunnableOne(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      try {
        while (true) {
          System.out.println(this.name + " is working...");
          TimeUnit.SECONDS.sleep(1);
        }
      } catch (InterruptedException e) {
        System.out.println(this.name + " was interrupted\n" +
            this.name + " Thread.currentThread().isInterrupted()="
            + Thread.currentThread().isInterrupted());
      }
    }
  }

  private class CallableOne implements Callable<String> {
    private int timeSleep;

    public CallableOne(int timeSleep) {
      this.timeSleep = timeSleep;
    }

    @Override
    public String call() throws Exception {
      System.out.println("Callable One is working...");
      TimeUnit.SECONDS.sleep(this.timeSleep);
      return "Callable: " + this.timeSleep;
    }
  }
}
