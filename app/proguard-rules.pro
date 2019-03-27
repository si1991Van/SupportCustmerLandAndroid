# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class com.google.android.** { *; }
-keep class android.support.** { *; }
-keep class com.support.customer.lands.model** { *; }
-keep interface android.support.** { *; }
-keepnames class com.firebase.** { *; }
-keepattributes Signature
-dontobfuscate

-ignorewarnings
-keep class * {
    public private *;
}
-dontwarn it.sephiroth.android.library.widget.**
-dontwarn com.squareup.**
-dontwarn com.soundcloud.**
-dontwarn com.makeramen.**
-dontwarn com.google.**
-dontwarn com.afollestad.**
-dontwarn org.apache.**
-dontwarn javax.**
-dontwarn android.support.v4.**
-dontwarn me.leolin.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}