LOCAL_PATH := $(call my-dir)

##########build share library############
include $(CLEAR_VARS)

LOCAL_LDLIBS+=-llog

LOCAL_MODULE := gpuimage-library

#LOCAL_C_INCLUDES := \
$(LOCAL_PATH)/include/ \
$(LOCAL_PATH)/frontend/ \
$(LOCAL_PATH)/libmp3lame/ \
$(LOCAL_PATH)/mpglib

LOCAL_SRC_FILES := yuv-decoder.c
#LOCAL_CFLAGS += -DSTDC_HEADERS -DHAVE_MPGLIB -DHAVE_STDINT_H -DHAVE_LIMITS_H
#LOCAL_MODULE_TAGS := eng
#LOCAL_PRELINK_MODULE := false

include $(BUILD_SHARED_LIBRARY)