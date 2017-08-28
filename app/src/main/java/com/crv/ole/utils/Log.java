package com.crv.ole.utils;


import com.crv.ole.BuildConfig;

public class Log
{
	protected static final String TAG = "oles";

    private Log() 
    {
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String msg) 
    {
        if (BuildConfig.DEBUG)
            android.util.Log.v(TAG, buildMessage(msg));
    }
    
    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String tag,String msg) 
    {
        if (BuildConfig.DEBUG)
            android.util.Log.v(tag, buildMessage(msg));
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void v(String msg, Throwable thr) 
    {
        if (BuildConfig.DEBUG)
        {
            android.util.Log.v(TAG, buildMessage(msg), thr);
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    public static void d(String msg) 
    {
        if (BuildConfig.DEBUG)
        {
            android.util.Log.d(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void d(String msg, Throwable thr) 
    {
        if (BuildConfig.DEBUG)
        {
            android.util.Log.d(TAG, buildMessage(msg), thr);
        }
    }
   
    public static void d(String tag, String msg) 
    {
    	if (BuildConfig.DEBUG)
    	{
    		android.util.Log.d(tag, buildMessage(msg));
    	}
    }

    /**
     * Send an INFO log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String msg) 
    {
        if (BuildConfig.DEBUG)
        {
            android.util.Log.i(TAG, buildMessage(msg));
        }
    }
    
    public static void i(String tag, String msg) 
    {
    	if (BuildConfig.DEBUG)
    	{
    		android.util.Log.i(tag, buildMessage(msg));
    	}
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void i(String msg, Throwable thr) 
    {
        if (BuildConfig.DEBUG)
        {
            android.util.Log.i(TAG, buildMessage(msg), thr);
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String msg) 
    {
        if (BuildConfig.DEBUG)
        {
            android.util.Log.e(TAG, buildMessage(msg));
        }
    }
    
    public static void e(String tag, String msg) 
    {
    	if (BuildConfig.DEBUG)
    	{
    		android.util.Log.e(tag, buildMessage(msg));
    	}
    }

    /**
     * Send a WARN log message
     *
     * @param msg The message you would like logged.
     */
    public static void w(String msg)
    {
        if (BuildConfig.DEBUG)
        {
            android.util.Log.w(TAG, buildMessage(msg));
        }
    }
    
    public static void w(String tag, String msg) 
    {
    	if (BuildConfig.DEBUG)
    	{
    		android.util.Log.w(tag, buildMessage(msg));
    	}
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void w(String msg, Throwable thr)
    {
        if (BuildConfig.DEBUG)
        {
            android.util.Log.w(TAG, buildMessage(msg), thr);
        }
    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr An exception to log
     */
    public static void w(Throwable thr)
    {
        if (BuildConfig.DEBUG)
        {
            android.util.Log.w(TAG, buildMessage(""), thr);
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void e(String msg, Throwable thr) 
    {
        if (BuildConfig.DEBUG)
        {
            android.util.Log.e(TAG, buildMessage(msg), thr);
        }
    }

    /**
     * Building Message
     *
     * @param msg The message you would like logged.
     * @return Message String
     */
    protected static String buildMessage(String msg) 
    {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];

        return new StringBuilder()
                .append(caller.getClassName())
                .append(".")
                .append(caller.getMethodName())
                .append("().")
                .append(caller.getLineNumber())
                .append(": ")
                .append(msg).toString();
    }

}
