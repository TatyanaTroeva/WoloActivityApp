package com.woloactivityapp.camera;

import java.util.Locale;

import android.os.Build;

public class FragmentationManager
{
  public static void LogDeviceInformation()
  {

  }

  public static boolean doesDeviceNotPreserveAspectRatio()
  {
    return (Devices.CurrentDevice.equalsIgnoreCase("GT-N7100")) || (Devices.CurrentDevice.equalsIgnoreCase("GT-I9300"));
  }

  public static boolean flashlightDoesNotWorkForDevice()
  {
    return (Devices.CurrentDevice.equalsIgnoreCase("HTC One V")) || (Devices.CurrentDevice.equalsIgnoreCase("GT-I9100"));
  }

  public static boolean isSamsungDevice()
  {
    return "samsung".equalsIgnoreCase(Build.BRAND);
  }

  public static boolean isXiaomiDevice()
  {
    return "Xiaomi".equalsIgnoreCase(Build.BRAND);
  }

  public boolean isHTCDevice()
  {
    return Build.MANUFACTURER.toLowerCase(Locale.getDefault()).contains("htc");
  }

  public static class Devices
  {
    public static final String CurrentDevice = Build.MODEL;
    public static final String HTC_1V = "HTC One V";
    public static final String HTC_1XPlus = "HTC One X+";
    public static final String MI_ONE_PLUS = "MI-ONE C1";
    public static final String Nexus4 = "Nexus 4";
    public static final String Note2 = "GT-N7100";
    public static String[] RecentFastDevices = { "Nexus 4", "GT-I9300", "SGH-T999", "HTC One X+", "GT-N7100" };
    public static final String S2_INTERNATIONAL = "GT-I9100";
    public static final String S3_A = "GT-I9300";
    public static final String S3_B = "SGH-T999";
  }

  public static class Feed
  {
    public static int GetOpenDelayMillisec()
    {
      return 50;
    }

    public static int GetPrepareDelayMillisec()
    {
      return 100;
    }

    public static boolean SupportsMultiplePlayers()
    {
      return false;
    }
  }
}