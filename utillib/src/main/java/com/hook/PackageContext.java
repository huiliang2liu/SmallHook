/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hook;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;

import com.load.ApkResources;
import com.load.Load;
import com.reflect.ClassManager;
import com.reflect.MethodManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * Common implementation of Context API, which provides the base context object
 * for Activity and other application components.
 */
public class PackageContext extends Context {
    Context mBase;
    ApkResources mResources;
    Application mApplication;
    static Class<?> contextImplClass;
    private static Method getThemeResId;
    private static Method getBasePackageName;
    private static Method getOpPackageName;
    private static Method getSharedPreferences;
    private static Method reloadSharedPreferences;
    private static Method moveSharedPreferencesFrom;
    private static Method deleteSharedPreferences;
    private static Method getPreloadsFileCache;
    private static Method getSharedPreferencesPath;
    private static Method moveDatabaseFrom;
    private static Method startActivityAsUser;
    private static Method startActivityAsUser1;
    private static Method startActivityAsUser2;
    private static Method sendBroadcastMultiplePermissions;
    private static Method sendBroadcast;
    private static Method sendBroadcast1;
    private static Method sendOrderedBroadcast;
    private static Method sendOrderedBroadcast1;
    private static Method sendBroadcastAsUser;
    private static Method sendBroadcastAsUser1;
    private static Method sendOrderedBroadcastAsUser;
    private static Method sendStickyBroadcastAsUser;
    private static Method registerReceiverAsUser;
    private static Method startForegroundService;
    private static Method registerReceiver;
    public static Method createPackageContextAsUser;
    public static Method getDisplay;
    public static Method updateDisplay;
    public static Method getUserId;

    static {
        contextImplClass = ClassManager.forName("android.app.ContextImpl");
        getThemeResId = method(contextImplClass, "getThemeResId");
        getDisplay = method(contextImplClass, "getDisplay");
        updateDisplay = method(contextImplClass, "updateDisplay", int.class);
        getUserId = method(contextImplClass, "getUserId");
        getBasePackageName = method(contextImplClass,
                "getBasePackageName");
        getOpPackageName = method(contextImplClass,
                "getOpPackageName");
        getSharedPreferences = method(contextImplClass,
                "getSharedPreferences", new Class[]{File.class, int.class});
        reloadSharedPreferences = method(contextImplClass,
                "reloadSharedPreferences");
        moveSharedPreferencesFrom = method(contextImplClass,
                "moveSharedPreferencesFrom", new Class[]{Context.class,
                        String.class});
        deleteSharedPreferences = method(contextImplClass,
                "deleteSharedPreferences", new Class[]{String.class});
        getPreloadsFileCache = method(contextImplClass,
                "getPreloadsFileCache");
        getSharedPreferencesPath = method(contextImplClass,
                "getSharedPreferencesPath", new Class[]{String.class});
        moveDatabaseFrom = method(contextImplClass, "moveDatabaseFrom", new Class[]{
                Context.class, String.class});
        sendBroadcastMultiplePermissions = method(
                contextImplClass, "sendBroadcastMultiplePermissions",
                new Class[]{Intent.class, String[].class});
        sendBroadcast = method(contextImplClass, "sendBroadcast",
                new Class[]{Intent.class, String.class, Bundle.class});
        sendBroadcast1 = method(contextImplClass,
                "sendBroadcast", new Class[]{Intent.class, String.class,
                        int.class});
        sendOrderedBroadcast = method(contextImplClass,
                "sendOrderedBroadcast", new Class[]{Intent.class,
                        String.class, Bundle.class, BroadcastReceiver.class,
                        Handler.class, int.class, String.class, Bundle.class});
        sendOrderedBroadcast1 = method(contextImplClass,
                "sendOrderedBroadcast", new Class[]{Intent.class,
                        String.class, int.class, BroadcastReceiver.class,
                        Handler.class, int.class, String.class, Bundle.class});
        startForegroundService = method(contextImplClass,
                "startForegroundService", new Class[]{Intent.class});
        registerReceiver = method(contextImplClass,
                "registerReceiver", new Class[]{BroadcastReceiver.class,
                        IntentFilter.class, String.class, Handler.class,
                        int.class});
        if (Build.VERSION.SDK_INT >= 17) {
            createPackageContextAsUser = method(contextImplClass, "createPackageContextAsUser", String.class, int.class, UserHandle.class);
            startActivityAsUser = method(contextImplClass,
                    "startActivityAsUser", new Class[]{Intent.class,
                            UserHandle.class});
            startActivityAsUser1 = method(contextImplClass,
                    "startActivityAsUser", new Class[]{Intent.class,
                            Bundle.class, UserHandle.class});
            startActivityAsUser2 = method(contextImplClass,
                    "startActivityAsUser", new Class[]{Intent[].class,
                            Bundle.class, UserHandle.class});
            sendBroadcastAsUser = method(contextImplClass,
                    "sendBroadcastAsUser", new Class[]{Intent.class,
                            UserHandle.class, String.class, Bundle.class});
            sendBroadcastAsUser1 = method(contextImplClass,
                    "sendBroadcastAsUser", new Class[]{Intent.class,
                            UserHandle.class, String.class, int.class});
            sendOrderedBroadcastAsUser = method(contextImplClass,
                    "sendOrderedBroadcastAsUser", new Class[]{Intent.class,
                            UserHandle.class, String.class, int.class,
                            Bundle.class, BroadcastReceiver.class, Handler.class,
                            int.class, String.class, Bundle.class});
            sendStickyBroadcastAsUser = method(contextImplClass,
                    "sendStickyBroadcastAsUser", new Class[]{Intent.class,
                            UserHandle.class, Bundle.class});
            registerReceiverAsUser = method(contextImplClass,
                    "registerReceiverAsUser", new Class[]{
                            BroadcastReceiver.class, UserHandle.class,
                            IntentFilter.class, String.class, Handler.class});
        }
    }

    public static Method method(Class cl, String name, Class... cls) {
        return MethodManager.method(cl, name, cls);
    }

    public static Object invoke(Method method, Object object, Object... params) {
        return MethodManager.invoke(method, object, params);
    }

    public PackageContext(Context context, Load load, String packageName) {
        // TODO Auto-generated constructor stub
        this(context, load == null ? null : load.name2resources(packageName));
    }

    public PackageContext(Context context, ApkResources resources) {
        // TODO Auto-generated constructor stub
        mBase = context;
        mResources = resources;
        mApplication = (Application) context.getApplicationContext();
    }

    public void setApplication(Application application) {
        mApplication = application;
    }

    @Override
    public AssetManager getAssets() {
        if (mResources != null)
            return mResources.mAssetManager;
        return mBase.getAssets();
    }

    @Override
    public Resources getResources() {
        if (mResources != null)
            return mResources.mResources;
        return mBase.getResources();
    }

    @Override
    public PackageManager getPackageManager() {
        return mBase.getPackageManager();
    }

    @Override
    public ContentResolver getContentResolver() {
        return mBase.getContentResolver();
    }

    @Override
    public Looper getMainLooper() {
        return mBase.getMainLooper();
    }

    @Override
    public Context getApplicationContext() {
        if (mApplication != null)
            return mApplication;
        return mBase.getApplicationContext();
    }

    @Override
    public void setTheme(int resId) {
        mBase.setTheme(resId);
    }

    public int getThemeResId() {
        return (int) invoke(getThemeResId, mBase);
    }

    @Override
    public Resources.Theme getTheme() {
        if (mResources != null)
            return mResources.mTheme;
        return mBase.getTheme();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mBase.getClassLoader();
    }

    @Override
    public String getPackageName() {
        if (mResources != null)
            return mResources.mPackage;
        return mBase.getPackageName();
    }

    /**
     * @hide
     */
    public String getBasePackageName() {
        return (String) invoke(getBasePackageName, mBase);
    }

    /**
     * @hide
     */
    public String getOpPackageName() {
        return (String) invoke(getOpPackageName, mBase);
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return mBase.getApplicationInfo();
    }

    @Override
    public String getPackageResourcePath() {
        return mBase.getPackageResourcePath();
    }

    @Override
    public String getPackageCodePath() {
        return mBase.getPackageCodePath();
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        // At least one application in the world actually passes in a null
        // name. This happened to work because when we generated the file name
        // we would stringify it to "null.xml". Nice.
        return mBase.getSharedPreferences(name, mode);
    }

    public SharedPreferences getSharedPreferences(File file, int mode) {
        return (SharedPreferences) invoke(getSharedPreferences,
                mBase, new Object[]{file, mode});
    }

    public void reloadSharedPreferences() {
        // Build the list of all per-context impls (i.e. caches) we know about
        invoke(reloadSharedPreferences, mBase);
    }

    public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
        return (boolean) invoke(moveSharedPreferencesFrom, mBase,
                new Object[]{sourceContext, name});
    }

    public boolean deleteSharedPreferences(String name) {
        return (boolean) invoke(deleteSharedPreferences, mBase,
                new Object[]{name});
    }

    @Override
    public FileInputStream openFileInput(String name)
            throws FileNotFoundException {
        return mBase.openFileInput(name);
    }

    @Override
    public FileOutputStream openFileOutput(String name, int mode)
            throws FileNotFoundException {
        return mBase.openFileOutput(name, mode);
    }

    @Override
    public boolean deleteFile(String name) {
        return mBase.deleteFile(name);
    }

    @Override
    public File getFilesDir() {
        return mBase.getFilesDir();
    }

    @Override
    public File getNoBackupFilesDir() {
        if (Build.VERSION.SDK_INT >= 21)
            return mBase.getNoBackupFilesDir();
        return null;
    }

    @Override
    public File getExternalFilesDir(String type) {
        // Operates on primary external storage
        return mBase.getExternalFilesDir(type);
    }

    @Override
    public File[] getExternalFilesDirs(String type) {
        return getExternalFilesDirs(type);
    }

    @Override
    public File getObbDir() {
        // Operates on primary external storage
        return mBase.getObbDir();
    }

    @Override
    public File[] getObbDirs() {
        if (Build.VERSION.SDK_INT >= 19)
            return mBase.getObbDirs();
        return null;
    }

    @Override
    public File getCacheDir() {
        return mBase.getCacheDir();
    }

    @Override
    public File getCodeCacheDir() {
        if (Build.VERSION.SDK_INT >= 21)
            return mBase.getCodeCacheDir();
        return null;
    }

    @Override
    public File getExternalCacheDir() {
        // Operates on primary external storage
        return mBase.getExternalCacheDir();
    }

    @Override
    public File[] getExternalCacheDirs() {
        if (Build.VERSION.SDK_INT >= 19)
            return mBase.getExternalCacheDirs();
        return null;
    }

    @Override
    public File[] getExternalMediaDirs() {
        if (Build.VERSION.SDK_INT >= 21)
            return mBase.getExternalMediaDirs();
        return null;
    }

    /**
     * @hide
     */
    public File getPreloadsFileCache() {
        return (File) invoke(getPreloadsFileCache, mBase);
    }

    @Override
    public File getFileStreamPath(String name) {
        return mBase.getFileStreamPath(name);
    }

    public File getSharedPreferencesPath(String name) {
        return (File) invoke(getSharedPreferencesPath, mBase,
                new Object[]{name});
    }

    @Override
    public String[] fileList() {
        return mBase.fileList();
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               CursorFactory factory) {
        return mBase.openOrCreateDatabase(name, mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return mBase.openOrCreateDatabase(name, mode, factory, errorHandler);
    }

    public boolean moveDatabaseFrom(Context sourceContext, String name) {
        return (boolean) invoke(moveDatabaseFrom, mBase,
                new Object[]{sourceContext, name});
    }

    @Override
    public boolean deleteDatabase(String name) {
        return mBase.deleteDatabase(name);
    }

    @Override
    public File getDatabasePath(String name) {
        return mBase.getDatabasePath(name);
    }

    @Override
    public String[] databaseList() {
        return mBase.databaseList();
    }

    @Override
    @Deprecated
    public Drawable getWallpaper() {
        return mBase.getWallpaper();
    }

    @Override
    @Deprecated
    public Drawable peekWallpaper() {
        return mBase.peekWallpaper();
    }

    @Override
    @Deprecated
    public int getWallpaperDesiredMinimumWidth() {
        return mBase.getWallpaperDesiredMinimumWidth();
    }

    @Override
    @Deprecated
    public int getWallpaperDesiredMinimumHeight() {
        return mBase.getWallpaperDesiredMinimumHeight();
    }

    @Override
    @Deprecated
    public void setWallpaper(Bitmap bitmap) throws IOException {
        mBase.setWallpaper(bitmap);
    }

    @Override
    @Deprecated
    public void setWallpaper(InputStream data) throws IOException {
        mBase.setWallpaper(data);
    }

    @Override
    @Deprecated
    public void clearWallpaper() throws IOException {
        mBase.clearWallpaper();
    }

    @Override
    public void startActivity(Intent intent) {
        mBase.startActivity(intent);
    }

    /**
     * @hide
     */
    public void startActivityAsUser(Intent intent, UserHandle user) {
        // mBase.startActivityAsUser;
        invoke(startActivityAsUser, mBase, new Object[]{intent,
                user});
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        if (Build.VERSION.SDK_INT >= 16)
            mBase.startActivity(intent, options);
    }

    /**
     * @hide
     */
    public void startActivityAsUser(Intent intent, Bundle options,
                                    UserHandle user) {
        invoke(startActivityAsUser1, mBase, new Object[]{
                intent, options, user});
    }

    @Override
    public void startActivities(Intent[] intents) {
        mBase.startActivities(intents);
    }

    /**
     * @hide
     */
    public void startActivitiesAsUser(Intent[] intents, Bundle options,
                                      UserHandle userHandle) {
        invoke(startActivityAsUser2, mBase, new Object[]{
                intents, options, userHandle});
    }

    @Override
    public void startActivities(Intent[] intents, Bundle options) {
        if (Build.VERSION.SDK_INT >= 16)
            mBase.startActivities(intents, options);
    }

    @Override
    public void startIntentSender(IntentSender intent, Intent fillInIntent,
                                  int flagsMask, int flagsValues, int extraFlags)
            throws IntentSender.SendIntentException {
        mBase.startIntentSender(intent, fillInIntent, flagsMask, flagsValues,
                extraFlags);
    }

    @Override
    public void startIntentSender(IntentSender intent, Intent fillInIntent,
                                  int flagsMask, int flagsValues, int extraFlags, Bundle options)
            throws IntentSender.SendIntentException {
        if (Build.VERSION.SDK_INT >= 16)
            mBase.startIntentSender(intent, fillInIntent, flagsMask, flagsValues,
                    extraFlags, options);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        mBase.sendBroadcast(intent);
    }

    @Override
    public void sendBroadcast(Intent intent, String receiverPermission) {
        mBase.sendBroadcast(intent, receiverPermission);
    }

    public void sendBroadcastMultiplePermissions(Intent intent,
                                                 String[] receiverPermissions) {
        invoke(sendBroadcastMultiplePermissions, mBase,
                new Object[]{intent, receiverPermissions});
    }

    public void sendBroadcast(Intent intent, String receiverPermission,
                              Bundle options) {
        invoke(sendBroadcast, mBase, new Object[]{intent,
                receiverPermission, options});
    }

    public void sendBroadcast(Intent intent, String receiverPermission,
                              int appOp) {
        invoke(sendBroadcast1, mBase, new Object[]{intent,
                receiverPermission, appOp});
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
        mBase.sendOrderedBroadcast(intent, receiverPermission);
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String receiverPermission,
                                     BroadcastReceiver resultReceiver, Handler scheduler,
                                     int initialCode, String initialData, Bundle initialExtras) {
        mBase.sendOrderedBroadcast(intent, receiverPermission, resultReceiver,
                scheduler, initialCode, initialData, initialExtras);
    }

    public void sendOrderedBroadcast(Intent intent, String receiverPermission,
                                     Bundle options, BroadcastReceiver resultReceiver,
                                     Handler scheduler, int initialCode, String initialData,
                                     Bundle initialExtras) {
        invoke(sendOrderedBroadcast, mBase, new Object[]{
                intent, receiverPermission, options, resultReceiver, scheduler,
                initialCode, initialData, initialExtras});
    }

    public void sendOrderedBroadcast(Intent intent, String receiverPermission,
                                     int appOp, BroadcastReceiver resultReceiver, Handler scheduler,
                                     int initialCode, String initialData, Bundle initialExtras) {
        invoke(sendOrderedBroadcast1, mBase, new Object[]{
                intent, receiverPermission, appOp, resultReceiver, scheduler,
                initialCode, initialData, initialExtras});
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle user) {
        if (Build.VERSION.SDK_INT >= 17)
            mBase.sendBroadcastAsUser(intent, user);
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle user,
                                    String receiverPermission) {
        if (Build.VERSION.SDK_INT >= 17)
            mBase.sendBroadcastAsUser(intent, user, receiverPermission);
    }

    public void sendBroadcastAsUser(Intent intent, UserHandle user,
                                    String receiverPermission, Bundle options) {
        invoke(sendBroadcastAsUser, mBase, new Object[]{intent,
                user, receiverPermission, options});
    }

    public void sendBroadcastAsUser(Intent intent, UserHandle user,
                                    String receiverPermission, int appOp) {
        invoke(sendBroadcastAsUser, mBase, new Object[]{intent,
                user, receiverPermission, appOp});
    }

    @Override
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user,
                                           String receiverPermission, BroadcastReceiver resultReceiver,
                                           Handler scheduler, int initialCode, String initialData,
                                           Bundle initialExtras) {
        if (Build.VERSION.SDK_INT >= 17)
            mBase.sendOrderedBroadcastAsUser(intent, user, receiverPermission,
                    resultReceiver, scheduler, initialCode, initialData,
                    initialExtras);
    }

    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user,
                                           String receiverPermission, int appOp,
                                           BroadcastReceiver resultReceiver, Handler scheduler,
                                           int initialCode, String initialData, Bundle initialExtras) {
        sendOrderedBroadcastAsUser(intent, user, receiverPermission, appOp,
                null, resultReceiver, scheduler, initialCode, initialData,
                initialExtras);
    }

    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user,
                                           String receiverPermission, int appOp, Bundle options,
                                           BroadcastReceiver resultReceiver, Handler scheduler,
                                           int initialCode, String initialData, Bundle initialExtras) {
        invoke(sendOrderedBroadcastAsUser, mBase, new Object[]{
                intent, user, receiverPermission, appOp, options,
                resultReceiver, scheduler, initialCode, initialData,
                initialExtras});
    }

    @Override
    @Deprecated
    public void sendStickyBroadcast(Intent intent) {
        mBase.sendStickyBroadcast(intent);
    }

    @Override
    @Deprecated
    public void sendStickyOrderedBroadcast(Intent intent,
                                           BroadcastReceiver resultReceiver, Handler scheduler,
                                           int initialCode, String initialData, Bundle initialExtras) {
        mBase.sendStickyOrderedBroadcast(intent, resultReceiver, scheduler,
                initialCode, initialData, initialExtras);
    }

    @Override
    @Deprecated
    public void removeStickyBroadcast(Intent intent) {
        mBase.removeStickyBroadcast(intent);
    }

    @Override
    @Deprecated
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
        if (Build.VERSION.SDK_INT >= 17)
            mBase.sendStickyBroadcastAsUser(intent, user);
    }

    @Deprecated
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user,
                                          Bundle options) {
        invoke(sendStickyBroadcastAsUser, mBase, new Object[]{
                intent, user, options});
    }

    @Override
    @Deprecated
    public void sendStickyOrderedBroadcastAsUser(Intent intent,
                                                 UserHandle user, BroadcastReceiver resultReceiver,
                                                 Handler scheduler, int initialCode, String initialData,
                                                 Bundle initialExtras) {
        if (Build.VERSION.SDK_INT >= 17)
            mBase.sendStickyOrderedBroadcastAsUser(intent, user, resultReceiver,
                    scheduler, initialCode, initialData, initialExtras);
    }

    @Override
    @Deprecated
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
        if (Build.VERSION.SDK_INT >= 17)
            mBase.removeStickyBroadcastAsUser(intent, user);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver,
                                   IntentFilter filter) {
        return registerReceiver(receiver, filter, null, null);
    }

    public Intent registerReceiver(BroadcastReceiver receiver,
                                   IntentFilter filter, int flags) {
        return registerReceiver(receiver, filter, null, null, flags);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver,
                                   IntentFilter filter, String broadcastPermission, Handler scheduler) {
        return mBase.registerReceiver(receiver, filter, broadcastPermission,
                scheduler);
    }

    public Intent registerReceiver(BroadcastReceiver receiver,
                                   IntentFilter filter, String broadcastPermission, Handler scheduler,
                                   int flags) {
        return (Intent) invoke(registerReceiver, mBase,
                new Object[]{receiver, filter, broadcastPermission,
                        scheduler, flags});
    }

    public Intent registerReceiverAsUser(BroadcastReceiver receiver,
                                         UserHandle user, IntentFilter filter, String broadcastPermission,
                                         Handler scheduler) {
        return (Intent) invoke(registerReceiverAsUser, mBase,
                new Object[]{receiver, user, filter, broadcastPermission,
                        scheduler});
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        mBase.unregisterReceiver(receiver);
    }

    @Override
    public ComponentName startService(Intent service) {
        return mBase.startService(service);
    }

    public ComponentName startForegroundService(Intent service) {
        return (ComponentName) invoke(startForegroundService,
                mBase, new Object[]{service});
    }

    @Override
    public boolean stopService(Intent service) {
        return mBase.stopService(service);
    }


    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return mBase.bindService(service, conn, flags);
    }


    @Override
    public void unbindService(ServiceConnection conn) {
        mBase.unbindService(conn);
    }

    @Override
    public boolean startInstrumentation(ComponentName className,
                                        String profileFile, Bundle arguments) {
        return mBase.startInstrumentation(className, profileFile, arguments);
    }

    @Override
    public Object getSystemService(String name) {
        return mBase.getSystemService(name);
    }

    @Override
    public String getSystemServiceName(Class<?> serviceClass) {
        if (Build.VERSION.SDK_INT >= 23)
            return mBase.getSystemServiceName(serviceClass);
        return "";
    }

    @Override
    public int checkPermission(String permission, int pid, int uid) {
        return mBase.checkPermission(permission, pid, uid);
    }


    @Override
    public int checkCallingPermission(String permission) {
        return mBase.checkCallingPermission(permission);
    }

    @Override
    public int checkCallingOrSelfPermission(String permission) {

        return mBase.checkCallingOrSelfPermission(permission);
    }

    @Override
    public int checkSelfPermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23)
            return mBase.checkSelfPermission(permission);
        return PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void enforcePermission(String permission, int pid, int uid,
                                  String message) {
        mBase.enforcePermission(permission, pid, uid, message);
    }

    @Override
    public void enforceCallingPermission(String permission, String message) {
        mBase.enforceCallingPermission(permission, message);
    }

    @Override
    public void enforceCallingOrSelfPermission(String permission, String message) {
        mBase.enforceCallingOrSelfPermission(permission, message);
    }

    @Override
    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        mBase.grantUriPermission(toPackage, uri, modeFlags);
    }

    @Override
    public void revokeUriPermission(Uri uri, int modeFlags) {
        mBase.revokeUriPermission(uri, modeFlags);
    }

    // @Override
    // public void revokeUriPermission(String targetPackage, Uri uri, int
    // modeFlags) {
    // try {
    // ActivityManager.getService().revokeUriPermission(
    // mMainThread.getApplicationThread(), targetPackage,
    // ContentProvider.getUriWithoutUserId(uri), modeFlags,
    // resolveUserId(uri));
    // } catch (RemoteException e) {
    // throw e.rethrowFromSystemServer();
    // }
    // }

    @Override
    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        return mBase.checkUriPermission(uri, pid, uid, modeFlags);
    }

    // /** @hide */
    // @Override
    // public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags,
    // IBinder callerToken) {
    // try {
    // return ActivityManager.getService().checkUriPermission(
    // ContentProvider.getUriWithoutUserId(uri), pid, uid,
    // modeFlags, resolveUserId(uri), callerToken);
    // } catch (RemoteException e) {
    // throw e.rethrowFromSystemServer();
    // }
    // }

    @Override
    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        return mBase.checkCallingUriPermission(uri, modeFlags);
    }

    @Override
    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        return mBase.checkCallingOrSelfUriPermission(uri, modeFlags);
    }

    @Override
    public int checkUriPermission(Uri uri, String readPermission,
                                  String writePermission, int pid, int uid, int modeFlags) {
        return mBase.checkUriPermission(uri, pid, uid, modeFlags);
    }

    @Override
    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags,
                                     String message) {
        mBase.enforceUriPermission(uri, pid, uid, modeFlags, message);
    }

    @Override
    public void enforceCallingUriPermission(Uri uri, int modeFlags,
                                            String message) {
        mBase.enforceCallingUriPermission(uri, modeFlags, message);
    }

    @Override
    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags,
                                                  String message) {
        mBase.enforceCallingOrSelfUriPermission(uri, modeFlags, message);
    }

    @Override
    public void enforceUriPermission(Uri uri, String readPermission,
                                     String writePermission, int pid, int uid, int modeFlags,
                                     String message) {
        mBase.enforceUriPermission(uri, readPermission, writePermission, pid,
                uid, modeFlags, message);
    }

    // @Override
    // public Context createApplicationContext(ApplicationInfo application,
    // int flags) throws NameNotFoundException {
    // return mBase.cA;
    // }

    @Override
    public Context createPackageContext(String packageName, int flags)
            throws NameNotFoundException {
        return mBase.createPackageContext(packageName, flags);
    }


    public Context createPackageContextAsUser(String packageName, int flags,
                                              UserHandle user) throws NameNotFoundException {
        return (Context) invoke(createPackageContextAsUser, mBase, packageName, flags, user);
    }


    @Override
    public Context createConfigurationContext(
            Configuration overrideConfiguration) {
        if (Build.VERSION.SDK_INT >= 17)
            return mBase.createConfigurationContext(overrideConfiguration);
        return this;
    }

    @Override
    public Context createDisplayContext(Display display) {
        if (Build.VERSION.SDK_INT >= 17)
            return mBase.createDisplayContext(display);
        return this;
    }

    @Override
    public boolean isDeviceProtectedStorage() {
        if (Build.VERSION.SDK_INT >= 24)
            return mBase.isDeviceProtectedStorage();
        return true;
    }

    @Override
    public Context createContextForSplit(String splitName) throws NameNotFoundException {
        if (Build.VERSION.SDK_INT >= 26)
            return mBase.createContextForSplit(splitName);
        return this;
    }

    @Override
    public File getDataDir() {
        if (Build.VERSION.SDK_INT >= 24)
            return mBase.getDataDir();
        return null;
    }

    @Override
    public Context createDeviceProtectedStorageContext() {
        if (Build.VERSION.SDK_INT >= 24)
            return mBase.createDeviceProtectedStorageContext();
        return this;
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        mBase.registerComponentCallbacks(callback);
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        mBase.unregisterComponentCallbacks(callback);
    }

    @Override
    public void revokeUriPermission(String toPackage, Uri uri, int modeFlags) {
        if (Build.VERSION.SDK_INT >= 26)
            mBase.revokeUriPermission(toPackage, uri, modeFlags);
    }

    public Display getDisplay() {
        return (Display) invoke(getDisplay, mBase);
    }

    public void updateDisplay(int display) {
        invoke(updateDisplay, mBase, display);
    }
    public int getUserId() {
        return (int) invoke(getUserId, mBase);
    }
    @Override
    public boolean isRestricted() {
        return mBase.isRestricted();
    }


    @Override
    public File getDir(String name, int mode) {
        return mBase.getDir(name, mode);
    }

}
