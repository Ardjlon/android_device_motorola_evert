#
# Copyright (C) 2017 The LineageOS Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Inherit some common EVOLUTION X stuff.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, vendor/evolution/config/common_full_phone.mk)

$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/languages_full.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/product_launched_with_o.mk)

# Device
$(call inherit-product, device/motorola/evert/device.mk)

# A/B updater
AB_OTA_UPDATER := true

AB_OTA_PARTITIONS += \
    boot \
    system \
    vendor

AB_OTA_POSTINSTALL_CONFIG += \
    RUN_POSTINSTALL_system=true \
    POSTINSTALL_PATH_system=system/bin/otapreopt_script \
    FILESYSTEM_TYPE_system=ext4 \
    POSTINSTALL_OPTIONAL_system=true

PRODUCT_PACKAGES += \
    otapreopt_script \
    update_engine \
    update_engine_sideload \
    update_verifier

# The following modules are included in debuggable builds only.
PRODUCT_PACKAGES_DEBUG += \
    bootctl \
    update_engine_client

# Boot control HAL
PRODUCT_PACKAGES += \
    android.hardware.boot@1.0-impl.recovery \
    bootctrl.sdm660 \
    bootctrl.sdm660.recovery

# Recovery
TARGET_RECOVERY_FSTAB := device/motorola/sdm660-common/rootdir/etc/fstab.qcom

# Evolution stuff
TARGET_BOOT_ANIMATION_RES := 1080
TARGET_GAPPS_ARCH := arm64
#EVO_BUILD_TYPE := OFFICIAL
EVO_DONATE_URL := https://paypal.me/Ardjlon
EVO_MAINTAINER := Ardjlon
EVO_SUPPORT_URL := https://t.me/evertlatino

# Device identifiers
PRODUCT_DEVICE := evert
PRODUCT_NAME := evolution_evert
PRODUCT_BRAND := Motorola
PRODUCT_MODEL := Moto G6 Plus
PRODUCT_MANUFACTURER := Motorola
PRODUCT_RELEASE_NAME := evert

PRODUCT_BUILD_PROP_OVERRIDES += \
    PRODUCT_NAME=evert \
    PRIVATE_BUILD_DESC="evert-user 8.0.0 OPW27.113-89 116 release-keys"

BUILD_FINGERPRINT := motorola/evert/evert:8.0.0/OPW27.113-45/53:user/release-keys