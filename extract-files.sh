#!/bin/bash
#
# Copyright (C) 2016 The CyanogenMod Project
# Copyright (C) 2017-2020 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

function blob_fixup() {
    case "${1}" in
        # Add uhid group for fingerprint service
        vendor/etc/init/android.hardware.biometrics.fingerprint@2.1-service.rc)
            sed -i "s/input/uhid input/" "${2}"
            ;;
        # Fix camera recording
        vendor/lib/libmmcamera2_pproc_modules.so)
            sed -i "s/ro.product.manufacturer/ro.product.nopefacturer/" "${2}"
            ;;
        # Load ZAF configs from vendor
        vendor/lib/libzaf_core.so)
            sed -i "s|/system/etc/zaf|/vendor/etc/zaf|g" "${2}"
            ;;
    esac
}

# If we're being sourced by the common script that we called,
# stop right here. No need to go down the rabbit hole.
if [ "${BASH_SOURCE[0]}" != "${0}" ]; then
    return
fi

set -e

AOSP_ROOT="$MY_DIR"/../../..

# Required!
export DEVICE=evert
export DEVICE_COMMON=sdm660-common
export VENDOR=motorola

"./../../${VENDOR}/${DEVICE_COMMON}/extract-files.sh" "$@"

BLOB_ROOT="$AOSP_ROOT"/vendor/"$VENDOR"/"$DEVICE"/proprietary

# Load ZAF configs from vendor
ZAF_CORE="$BLOB_ROOT"/vendor/lib/libzaf_core.so
sed -i "s|/system/etc/zaf|/vendor/etc/zaf|g" "$ZAF_CORE"

# Add uhid group for fingerprint service
FP_SERVICE_RC="$BLOB_ROOT"/vendor/etc/init/android.hardware.biometrics.fingerprint@2.1-service.rc
sed -i "s/input/uhid input/" "$FP_SERVICE_RC"
