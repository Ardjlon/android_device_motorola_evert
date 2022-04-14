LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := RemovePkgs
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_TAGS := optional
LOCAL_OVERRIDES_PACKAGES := \
        AmbientSensePrebuilt \
        AndroidAutoStubPrebuilt \
        AndroidAutoStubPrebuilt \
        AppDirectedSMSService \
        CalculatorGooglePrebuilt \
        CalendarGooglePrebuilt \
        CalendarProvider \
        Camera2 \
        ConnMO \
        DCMO \
        DMService \
        DevicePolicyPrebuilt \
        DocumentsUIGoogle \
        Drive \
        FM2 \
        FilesPrebuilt \
        Flipendo \
        GCS \
        Launcher3QuickStep \
        LiveWallpapersPicker \
        MaestroPrebuilt \
        Maps \
        MicropaperPrebuilt \
        MotCamera2 \
        Music \
        MusicFX \
        MyVerizonServices \
	NexusLauncherRelease \
        OBDM_Permissions \
        Ornament \
        Papers \
        PixelLiveWallpaperPrebuilt \
        PixelWallpapers2020 \
        PrebuiltDeskClockGoogle \
        PrebuiltGmail \
        RecorderPrebuilt \
        SCONE \
        SafetyHubPrebuilt \
        ScribePrebuilt \
        Showcase \
        Snap \
        SoundAmplifierPrebuilt \
        SprintDM \
        SprintHM \
        TurboAdapter \
        TurboPrebuilt \
        Tycho \
        USCCDM \
        VZWAPNLib \
        Velvet \
        VzwOmaTrigger \
        WallpapersBReel2020 \
        YouTube \
        YouTubeMusicPrebuilt \
        arcore \
        obdm_stub
LOCAL_UNINSTALLABLE_MODULE := true
LOCAL_CERTIFICATE := platform
LOCAL_SRC_FILES := /dev/null
include $(BUILD_PREBUILT)
