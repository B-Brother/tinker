package org.apache.tomcat.util.threads;
 
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
  
/**
 * Simple task thread factory to use to create threads for an executor
 * implementation.
 */
public class TaskThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private final boolean daemon;
    private final int threadPriority;

    public TaskThreadFactory(String namePrefix, boolean daemon, int priority) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
        this.daemon = daemon;
        this.threadPriority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            // Threads should not be created by the webapp classloader 
            Thread.currentThread().setContextClassLoader(
                    getClass().getClassLoader()); 
            TaskThread t = new TaskThread(group, r, namePrefix + threadNumber.getAndIncrement());
            t.setDaemon(daemon);
            t.setPriority(threadPriority);
            return t;
        } finally { 
            Thread.currentThread().setContextClassLoader(loader); 
        }
    }
}