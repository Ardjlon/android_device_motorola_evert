/*
 * Copyright (c) 2015 The CyanogenMod Project
 * Copyright (c) 2017 The LineageOS Project
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

package com.moto.actions;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.provider.Settings;

import android.util.Log;

import com.moto.actions.actions.Constants;
import com.moto.actions.actions.UpdatedStateNotifier;
import com.moto.actions.actions.CameraActivationAction;
import com.moto.actions.actions.TorchAction;

import static android.provider.Settings.Secure.DOZE_ALWAYS_ON;
import static android.provider.Settings.Secure.DOZE_ENABLED;

public class MotoActionsSettings implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "MotoActions";

    protected static final String GESTURE_CAMERA_ACTION_KEY = "gesture_camera_action";
    protected static final String GESTURE_CHOP_CHOP_KEY = "gesture_chop_chop";
    protected static final String GESTURE_PICK_UP_KEY = "gesture_pick_up";
    protected static final String GESTURE_IR_WAKEUP_KEY = "gesture_hand_wave";
    protected static final String GESTURE_IR_SILENCER_KEY = "gesture_ir_silencer";
    protected static final String GESTURE_FLIP_TO_MUTE_KEY = "gesture_flip_to_mute";
    protected static final String GESTURE_LIFT_TO_SILENCE_KEY = "gesture_lift_to_silence";

    protected static final String ALWAYS_ON_DISPLAY = "always_on_display";

    protected static final String CATEG_PICKUP_SENSOR = "pickup_sensor";
    protected static final String CATEG_PROX_SENSOR = "proximity_sensor";

    private final Context mContext;
    private final UpdatedStateNotifier mUpdatedStateNotifier;

    private boolean mCameraGestureEnabled;
    private boolean mChopChopEnabled;
    private boolean mPickUpGestureEnabled;
    private boolean mIrWakeUpEnabled;
    private boolean mIrSilencerEnabled;
    private boolean mFlipToMuteEnabled;
    private boolean mLiftToSilenceEnabled;

    public MotoActionsSettings(Context context, UpdatedStateNotifier updatedStateNotifier) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        loadPreferences(sharedPrefs);
        sharedPrefs.registerOnSharedPreferenceChangeListener(this);
        mContext = context;
        mUpdatedStateNotifier = updatedStateNotifier;
    }

    public boolean isCameraGestureEnabled() {
        return mCameraGestureEnabled;
    }

    public boolean isChopChopGestureEnabled() {
        return mChopChopEnabled;
    }

    public static boolean isAlwaysOnEnabled(Context context) {
        final boolean enabledByDefault = context.getResources()
                .getBoolean(com.android.internal.R.bool.config_dozeAlwaysOnEnabled);

        return Settings.Secure.getIntForUser(context.getContentResolver(),
                DOZE_ALWAYS_ON, alwaysOnDisplayAvailable(context) && enabledByDefault ? 1 : 0,
                UserHandle.USER_CURRENT) != 0;
    }

    public static boolean alwaysOnDisplayAvailable(Context context) {
        return new AmbientDisplayConfiguration(context).alwaysOnAvailable();
    }

    public static boolean enableAlwaysOn(Context context, boolean enable) {
        return Settings.Secure.putIntForUser(context.getContentResolver(),
                DOZE_ALWAYS_ON, enable ? 1 : 0, UserHandle.USER_CURRENT);
    }

    public static boolean enableDoze(Context context, boolean enable) {
        return Settings.Secure.putInt(context.getContentResolver(),
                DOZE_ENABLED, enable ? 1 : 0);
    }

    public static boolean isDozeEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(),
                DOZE_ENABLED, 1) != 0;
    }

    public static boolean getProxCheckBeforePulse(Context context) {
        try {
            Context con = context.createPackageContext("com.android.systemui", 0);
            int id = con.getResources().getIdentifier("doze_proximity_check_before_pulse",
                    "bool", "com.android.systemui");
            return con.getResources().getBoolean(id);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    public boolean isDozeEnabled() {
        return isDozeEnabled(mContext);
    }

    public boolean isIrWakeupEnabled() {
        return isDozeEnabled() && !isAlwaysOnEnabled(mContext) && mIrWakeUpEnabled;
    }

    public boolean isPickUpEnabled() {
        return isDozeEnabled() && !isAlwaysOnEnabled(mContext) && mPickUpGestureEnabled;
    }

    public boolean isIrSilencerEnabled() {
        return mIrSilencerEnabled;
    }

    public boolean isFlipToMuteEnabled() {
        return mFlipToMuteEnabled;
    }

    public boolean isLiftToSilenceEnabled() {
        return mLiftToSilenceEnabled;
    }

    public void cameraAction() {
        new CameraActivationAction(mContext).action();
    }

    public void chopChopAction() {
        new TorchAction(mContext).action();
    }

    private void loadPreferences(SharedPreferences sharedPreferences) {
        mCameraGestureEnabled = sharedPreferences.getBoolean(GESTURE_CAMERA_ACTION_KEY, true);
        mChopChopEnabled = sharedPreferences.getBoolean(GESTURE_CHOP_CHOP_KEY, true);
        mIrWakeUpEnabled = sharedPreferences.getBoolean(GESTURE_IR_WAKEUP_KEY, false);
        mPickUpGestureEnabled = sharedPreferences.getBoolean(GESTURE_PICK_UP_KEY, true);
        mIrSilencerEnabled = sharedPreferences.getBoolean(GESTURE_IR_SILENCER_KEY, false);
        mFlipToMuteEnabled = sharedPreferences.getBoolean(GESTURE_FLIP_TO_MUTE_KEY, false);
        mLiftToSilenceEnabled = sharedPreferences.getBoolean(GESTURE_LIFT_TO_SILENCE_KEY, false);
    }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            boolean updated = true;

            if (GESTURE_CAMERA_ACTION_KEY.equals(key)) {
                mCameraGestureEnabled = sharedPreferences.getBoolean(GESTURE_CAMERA_ACTION_KEY, true);
            } else if (GESTURE_CHOP_CHOP_KEY.equals(key)) {
                mChopChopEnabled = sharedPreferences.getBoolean(GESTURE_CHOP_CHOP_KEY, true);
            } else if (GESTURE_IR_WAKEUP_KEY.equals(key)) {
                mIrWakeUpEnabled = sharedPreferences.getBoolean(GESTURE_IR_WAKEUP_KEY, false);
            } else if (GESTURE_PICK_UP_KEY.equals(key)) {
                mPickUpGestureEnabled = sharedPreferences.getBoolean(GESTURE_PICK_UP_KEY, true);
            } else if (GESTURE_IR_SILENCER_KEY.equals(key)) {
                mIrSilencerEnabled = sharedPreferences.getBoolean(GESTURE_IR_SILENCER_KEY, false);
            } else if (GESTURE_FLIP_TO_MUTE_KEY.equals(key)) {
                mFlipToMuteEnabled = sharedPreferences.getBoolean(GESTURE_FLIP_TO_MUTE_KEY, false);
            } else if (GESTURE_LIFT_TO_SILENCE_KEY.equals(key)) {
                mLiftToSilenceEnabled = sharedPreferences.getBoolean(GESTURE_LIFT_TO_SILENCE_KEY, false);
            } else if (Constants.FP_HOME_KEY.equals(key) || Constants.FP_HAPTIC_KEY.equals(key) || Constants.FP_HOME_KEY_OFF.equals(key) || Constants.FP_HAPTIC_SCREENOFF_KEY.equals(key) || Constants.FP_KEYS.equals(key) || Constants.FP_KEY_DBLTAP.equals(key) || Constants.FP_KEY_HOLD.equals(key) ||  Constants.FP_KEY_LEFT.equals(key) || Constants.FP_KEY_RIGHT.equals(key)
                || Constants.FP_KEYS_OFF.equals(key) || Constants.FP_KEY_DBLTAP_OFF.equals(key) || Constants.FP_KEY_HOLD_OFF.equals(key) ||  Constants.FP_KEY_LEFT_OFF.equals(key) || Constants.FP_KEY_RIGHT_OFF.equals(key)) {
                Constants.writePreference(mContext, key);
                updated = false;
            } else {
                updated = false;
            }

            if (updated) {
                mUpdatedStateNotifier.updateState();
            }
        }
}
