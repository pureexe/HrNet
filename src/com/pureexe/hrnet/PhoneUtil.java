package com.pureexe.hrnet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

public class PhoneUtil {
	public static void setMobileDataEnabled(Context context, boolean enabled) {
		if(Build.VERSION.SDK_INT>=19){
			setMobileDataEbabled_postkitkat(context,enabled);
		} else {
			setMobileDataEbabled_prekitkat(context,enabled);
		}
	}
	private static void setMobileDataEbabled_postkitkat(Context context, boolean enabled){
		final ConnectivityManager conman = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		Class conmanClass;

		Class[] cArg = new Class[2];
		cArg[0] = String.class;
		cArg[1] = Boolean.TYPE;
		Method setMobileDataEnabledMethod;

		try {
			conmanClass = Class.forName(conman.getClass().getName());

			final Field iConnectivityManagerField = conmanClass
					.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			final Object iConnectivityManager = iConnectivityManagerField
					.get(conman);
			final Class iConnectivityManagerClass = Class
					.forName(iConnectivityManager.getClass().getName());
			setMobileDataEnabledMethod = iConnectivityManagerClass
					.getDeclaredMethod("setMobileDataEnabled", cArg);

			Object[] pArg = new Object[2];
			pArg[0] = context.getPackageName();
			pArg[1] = enabled;
			setMobileDataEnabledMethod.setAccessible(true);
			setMobileDataEnabledMethod.invoke(iConnectivityManager, pArg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	private static void setMobileDataEbabled_prekitkat(Context context, boolean enabled){

        try {
         final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       final Class conmanClass = Class.forName(conman.getClass().getName());
       final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
       iConnectivityManagerField.setAccessible(true);
       final Object iConnectivityManager = iConnectivityManagerField.get(conman);
       final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
       final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
       setMobileDataEnabledMethod.setAccessible(true);
       setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        finally {
        }
    }
	
}
