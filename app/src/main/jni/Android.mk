##############################################################################
#               SQLite3 module
##############################################################################
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

include $(CLEAR_VARS)
LOCAL_MODULE    := sqlite3
LOCAL_SRC_FILES := ../jnilibs_/$(TARGET_ARCH_ABI)/libsqlite3.so
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/../include
include $(PREBUILT_SHARED_LIBRARY)

##############################################################################
#               NativeSQLite module
##############################################################################

include $(CLEAR_VARS)
LOCAL_PATH := $(call my-dir)
LOCAL_MODULE := nativesqlite
LOCAL_SRC_FILES := \
	D:\work\projects\OrmBench\app\src\main\jni\com_epam_database_NativeSQLiteConnection.cpp \
	D:\work\projects\OrmBench\app\src\main\jni\com_sebbia_ormbenchmark_ntvself_DBHelper.cpp \
	D:\work\projects\OrmBench\app\src\main\jni\com_sebbia_ormbenchmark_ntvstr_DBHelper.cpp \
	D:\work\projects\OrmBench\app\src\main\jni\empty.c \
	D:\work\projects\OrmBench\app\src\main\jni\JNIHelp.c \
	D:\work\projects\OrmBench\app\src\main\jni\SQLiteCommon.cpp \

LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/../include

LOCAL_LDFLAGS := -L$(LOCAL_PATH)/../jnilibs_/$(TARGET_ARCH_ABI)/
LOCAL_C_INCLUDES += $(LOCAL_PATH)/../include
LOCAL_LDLIBS := -ldl -llog -lz -lm -landroid
LOCAL_SHARED_LIBRARIES := sqlite3

#LOCAL_CFLAGS += -DHAVE_SYS_UIO_H
#LOCAL_CPP_FEATURES += exceptions


#LOCAL_STATIC_LIBRARIES :=  libutils \
#                    	libcutils
include $(BUILD_SHARED_LIBRARY)