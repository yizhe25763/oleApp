-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#-libraryjars libs/andfix.jar
#友盟region
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
#/**这里需要修改成对应的包名com.ljf.wyhb**/
-keep public class com.crv.ole.R$*{
  public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#友盟endregion
#jackson
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry
-keep class sun.misc.Unsafe { *; }
-keep class com.fasterxml.jackson.**{*;}
#jackson

#eventbus begin
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode {
      *;
}
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#eventbus end

# volley
-dontwarn com.android.volley.jar.**
-keep class com.android.volley.**{*;}

# pulltorefresh
-dontwarn com.handmark.pulltorefresh.library.**
-keep class com.handmark.pulltorefresh.library.** { *;}
-dontwarn com.handmark.pulltorefresh.library.extras.**
-keep class com.handmark.pulltorefresh.library.extras.** { *;}
-dontwarn com.handmark.pulltorefresh.library.internal.**
-keep class com.handmark.pulltorefresh.library.internal.** { *;}

# tab切换viewpagerindicator
-dontwarn com.viewpagerindicator.**
-keep class com.viewpagerindicator.** { *;}

# waps
-keep public class cn.waps.** {*;}
-keep public interface cn.waps.** {*;}
-dontwarn cn.waps.**

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keepattributes InnerClasses,LineNumberTable

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
# 保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# keep 所有的 javabean[这里需要修改对应的bean包名]
-keep class com.dpydzf.model.** { *; }
# keep 所有的 网络类[这里需要修改对应的bean包名]
-keep class com.dpydzf.net.** { *; }
-keep class com.dpydzf.event.** { *; }
# keep 泛型
-keepattributes Signature

-keep public class * implements java.io.Serializable {
    public *;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public interface org.apache.http.** {*;}
-keep class android.support.v4.** {*;}
-keep class android.support.v7.** {*;}
-keep class com.marswin89.marsdaemon.** {*;}
-keep class android.support.graphics.drawable.** {*;}
-keep class android.support.graphics.drawable.animated.** {*;}
-keep class com.handmark.pulltorefresh.** {*;}
-keep class org.android.agoo.net.** {*;}
-keep class android.util.FloatMath.** {*;}
-keep class  com.ta.utdid2.aid.** {*;}
-keep class  org.android.agoo.** {*;}
-keep class org.apache.http.** {*;}
-keep class android.util.FloatMath {*;}
-keep class com.umeng.** {*;}
-dontwarn org.apache.*
-dontwarn android.util.FloatMath

-dontwarn com.umeng.**
-dontwarn com.igexin.**
-keep class com.igexin.**{*;}

-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**

#ksoap
-ignorewarnings
-keep class org.kobjects.** { *; }
-keep class org.ksoap2.** { *; }
-keep class org.kxml2.** { *; }
-keep class org.xmlpull.** { *; }
-keep class pt.joaocruz04.lib.** { *; }

#百度map
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}

#greendao
-keep class com.dpydzf.dao.** {*;}
-keep class de.greenrobot.dao.** {*;}
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static Java.lang.String TABLENAME;
}
-keep class **$Properties
#环信
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
#sharesdk
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-dontwarn cn.sharesdk.**
-dontwarn **.R$*

-keep class com.dpydzf.utils.AndroidForJs { *;}
-keep class tv.danmaku.ijk.media.** {*;}
-keep class com.google.zxing.** {*;}
-keep class com.nineoldandroids.** {*;}

# OkHttp
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** {*; }
-keep interface okhttp3.* { *; }
-dontwarn okhttp3.*
-keep class okhttp3.ResponseBody

#Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

#alipay
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

# adding this in to preserve line numbers so that the stack traces
# can be remapped
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

#wechatpay
-keep class com.tencent.mm.sdk.** {
   *;
}
#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}
  #3D 地图
    -keep   class com.amap.api.maps.**{*;}
    -keep   class com.autonavi.amap.mapcore.*{*;}
    -keep   class com.amap.api.trace.**{*;}

    #定位
    -keep class com.amap.api.location.**{*;}
    -keep class com.amap.api.fence.**{*;}
    -keep class com.autonavi.aps.amapapi.model.**{*;}

   # 搜索
    -keep   class com.amap.api.services.**{*;}

    #2D地图
    -keep class com.amap.api.maps2d.**{*;}
    -keep class com.amap.api.mapcore2d.**{*;}

    #导航
    -keep class com.amap.api.navi.**{*;}
    -keep class com.autonavi.**{*;}

    #个推
    -dontwarn com.igexin.**
    -keep class com.igexin.** { *; }
    -keep class org.json.** { *; }
    #glide
    -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
      **[] $VALUES;
      public *;
    }
    #-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

    #RxJava RxAndroid
    -dontwarn sun.misc.**
    -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
       long producerIndex;
       long consumerIndex;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
        rx.internal.util.atomic.LinkedQueueNode producerNode;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
        rx.internal.util.atomic.LinkedQueueNode consumerNode;
    }

    ###ucrop
    -dontwarn com.yalantis.ucrop**
    -keep class com.yalantis.ucrop** { *; }
    -keep interface com.yalantis.ucrop** { *; }

    # Fresco
    # Keep our interfaces so they can be used by other ProGuard rules.
    # See http://sourceforge.net/p/proguard/bugs/466/
    -keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

    # Do not strip any method/class that is annotated with @DoNotStrip
    -keep @com.facebook.common.internal.DoNotStrip class *
    -keepclassmembers class * {
        @com.facebook.common.internal.DoNotStrip *;
    }

    # Keep native methods
    -keepclassmembers class * {
        native <methods>;
    }

    #butterknife
      -keep class butterknife.** { *; }
      -dontwarn butterknife.internal.**
      -keep class **$$ViewBinder { *; }
      -keepclasseswithmembernames class * { @butterknife.* <fields>;}
      -keepclasseswithmembernames class * { @butterknife.* <methods>;}

      #环信
      -keep class com.hyphenate.* {*;}
      -keep class com.hyphenate.chat.** {*;}
      -keep class org.jivesoftware.** {*;}
      -keep class org.apache.** {*;}
      #另外，demo中发送表情的时候使用到反射，需要keep SmileUtils,注意前面的包名，
      #不要SmileUtils复制到自己的项目下keep的时候还是写的demo里的包名
      -keep class com.hyphenate.chatuidemo.utils.SmileUtils {*;}
      #2.0.9后加入语音通话功能，如需使用此功能的api，加入以下keep
      -keep class net.java.sip.** {*;}
      -keep class org.webrtc.voiceengine.** {*;}
      -keep class org.bitlet.** {*;}
      -keep class org.slf4j.** {*;}
      -keep class ch.imvs.** {*;}
      -keep class com.superrtc.** {*;}

     #支付宝
     -libraryjars libs/alipaySdk-20170623-proguard.jar
     -keep class com.alipay.android.app.IAlixPay{*;}
     -keep class com.alipay.android.app.IAlixPay$Stub{*;}
     -keep class com.alipay.android.app.IRemoteServiceCallback{*;}
     -keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
     -keep class com.alipay.sdk.app.PayTask{ public *;}
     -keep class com.alipay.sdk.app.AuthTask{ public *;}