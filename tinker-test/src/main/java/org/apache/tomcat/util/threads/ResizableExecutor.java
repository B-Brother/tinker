package org.apache.tomcat.util.threads;

import java.util.concurrent.Executor;

public interface ResizableExecutor extends Executor {

    /**
     * Returns the current number of threads in the pool.
     *
     * @return the number of threads
     */
    public int getPoolSize();

    public int getMaxThreads();

    /**
     * Returns the approximate number of threads that are actively executing
     * tasks.
     *
     * @return the number of threads
     */
    public int getActiveCount();

    public boolean resizePool(int corePoolSize, int maximumPoolSize);

    public boolean resizeQueue(int capacity);

}