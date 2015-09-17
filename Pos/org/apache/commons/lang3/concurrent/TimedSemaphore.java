package org.apache.commons.lang3.concurrent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimedSemaphore
{
  public static final int NO_LIMIT = 0;
  private static final int THREAD_POOL_SIZE = 1;
  private int acquireCount;
  private final ScheduledExecutorService executorService;
  private int lastCallsPerPeriod;
  private int limit;
  private final boolean ownExecutor;
  private final long period;
  private long periodCount;
  private boolean shutdown;
  private ScheduledFuture<?> task;
  private long totalAcquireCount;
  private final TimeUnit unit;
  
  public TimedSemaphore(long paramLong, TimeUnit paramTimeUnit, int paramInt)
  {
    this(null, paramLong, paramTimeUnit, paramInt);
  }
  
  public TimedSemaphore(ScheduledExecutorService paramScheduledExecutorService, long paramLong, TimeUnit paramTimeUnit, int paramInt)
  {
    if (paramLong <= 0L) {
      throw new IllegalArgumentException("Time period must be greater 0!");
    }
    this.period = paramLong;
    this.unit = paramTimeUnit;
    if (paramScheduledExecutorService != null) {
      this.executorService = paramScheduledExecutorService;
    }
    for (this.ownExecutor = false;; this.ownExecutor = true)
    {
      setLimit(paramInt);
      return;
      paramScheduledExecutorService = new ScheduledThreadPoolExecutor(1);
      paramScheduledExecutorService.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
      paramScheduledExecutorService.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
      this.executorService = paramScheduledExecutorService;
    }
  }
  
  public void acquire()
    throws InterruptedException
  {
    try
    {
      if (isShutdown()) {
        throw new IllegalStateException("TimedSemaphore is shut down!");
      }
    }
    finally {}
    if (this.task == null)
    {
      this.task = startTimer();
      break label96;
      if (getLimit() <= 0) {
        break label99;
      }
      if (this.acquireCount < getLimit()) {
        break label99;
      }
    }
    for (;;)
    {
      if (i == 0) {
        wait();
      }
      for (;;)
      {
        if (i == 0) {
          break label94;
        }
        return;
        i = 0;
        break;
        this.acquireCount += 1;
      }
      label94:
      break;
      label96:
      break;
      label99:
      int i = 1;
    }
  }
  
  void endOfPeriod()
  {
    try
    {
      this.lastCallsPerPeriod = this.acquireCount;
      this.totalAcquireCount += this.acquireCount;
      this.periodCount += 1L;
      this.acquireCount = 0;
      notifyAll();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getAcquireCount()
  {
    try
    {
      int i = this.acquireCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getAvailablePermits()
  {
    try
    {
      int i = getLimit();
      int j = getAcquireCount();
      return i - j;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public double getAverageCallsPerPeriod()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 102	org/apache/commons/lang3/concurrent/TimedSemaphore:periodCount	J
    //   6: lstore_3
    //   7: lload_3
    //   8: lconst_0
    //   9: lcmp
    //   10: ifne +9 -> 19
    //   13: dconst_0
    //   14: dstore_1
    //   15: aload_0
    //   16: monitorexit
    //   17: dload_1
    //   18: dreturn
    //   19: aload_0
    //   20: getfield 100	org/apache/commons/lang3/concurrent/TimedSemaphore:totalAcquireCount	J
    //   23: l2d
    //   24: dstore_1
    //   25: aload_0
    //   26: getfield 102	org/apache/commons/lang3/concurrent/TimedSemaphore:periodCount	J
    //   29: lstore_3
    //   30: dload_1
    //   31: lload_3
    //   32: l2d
    //   33: ddiv
    //   34: dstore_1
    //   35: goto -20 -> 15
    //   38: astore 5
    //   40: aload_0
    //   41: monitorexit
    //   42: aload 5
    //   44: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	45	0	this	TimedSemaphore
    //   14	21	1	d	double
    //   6	26	3	l	long
    //   38	5	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	7	38	finally
    //   19	30	38	finally
  }
  
  protected ScheduledExecutorService getExecutorService()
  {
    return this.executorService;
  }
  
  public int getLastAcquiresPerPeriod()
  {
    try
    {
      int i = this.lastCallsPerPeriod;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final int getLimit()
  {
    try
    {
      int i = this.limit;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public long getPeriod()
  {
    return this.period;
  }
  
  public TimeUnit getUnit()
  {
    return this.unit;
  }
  
  public boolean isShutdown()
  {
    try
    {
      boolean bool = this.shutdown;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void setLimit(int paramInt)
  {
    try
    {
      this.limit = paramInt;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void shutdown()
  {
    try
    {
      if (!this.shutdown)
      {
        if (this.ownExecutor) {
          getExecutorService().shutdownNow();
        }
        if (this.task != null) {
          this.task.cancel(false);
        }
        this.shutdown = true;
      }
      return;
    }
    finally {}
  }
  
  protected ScheduledFuture<?> startTimer()
  {
    getExecutorService().scheduleAtFixedRate(new Runnable()
    {
      public void run()
      {
        TimedSemaphore.this.endOfPeriod();
      }
    }, getPeriod(), getPeriod(), getUnit());
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\concurrent\TimedSemaphore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */