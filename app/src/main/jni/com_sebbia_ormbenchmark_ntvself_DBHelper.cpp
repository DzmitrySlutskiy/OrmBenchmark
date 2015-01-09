/*
 * Copyright (C) 2009 The Android Open Source Project
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
 *
 */
#include <jni.h>
//#include <JNIHelp.h>

#include <android/log.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string.h>
#include <string>
#include "include/sqlite3.h"
#include "include/SQLiteCommon.h"

#include "include/com_sebbia_ormbenchmark_ntvself_DBHelper.h"

using namespace std;
using namespace android;

//#define LOG_TAG    "DBHelperNative"
//#define LOGI(x...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG,x)
//#define ALOG(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
//#define ALOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE,LOG_TAG,__VA_ARGS__)
//#define ALOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
//#define ALOGW(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
//#define LOG_WINDOW(...)  ALOGW(__VA_ARGS__)
// Set to 1 to use UTF16 storage for localized indexes.
#define UTF16_STORAGE 0

/*---------------------------------  ---------------------------------*/
sqlite3* db;
sqlite3_stmt *pStmt = 0;

int bindIndex = 0;
/*com_sebbia_ormbenchmark_ntvself_DBHelper_
/*com_sebbia_ormbenchmark_ntvself_DBHelper_*/

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvself_DBHelper_nativeOpen(JNIEnv* env, jclass clazz, jstring pathStr) {
    const char* pathChars = env->GetStringUTFChars(pathStr, NULL);
    string path(pathChars);
    env->ReleaseStringUTFChars(pathStr, pathChars);

    char receivedStr[1000];
    int err;
    if (err = sqlite3_open(path.c_str(), &db) != SQLITE_OK) {
    	strcat(receivedStr,"Error wile opening db. ");
    	strcat(receivedStr, sqlite3_errmsg(db));
    	throw_sqlite3_exception_errcode(env, err, receivedStr);
    }

    //sqlite3_exec(db,"CREATE TABLE IF NOT EXISTS Tab1 (_id integer primary key autoincrement, Col1 text, Col2 text); ",NULL,NULL,NULL);
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvself_DBHelper_nativeClose(JNIEnv* env, jclass clazz) {
    sqlite3_close(db);
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvself_DBHelper_execSQL(JNIEnv* env, jclass clazz,jstring sqlStr) {
    const char* pathChars = env->GetStringUTFChars(sqlStr, NULL);
    string sqlChars(pathChars);
    env->ReleaseStringUTFChars(sqlStr, pathChars);

    sqlite3_exec(db,sqlChars.c_str(),NULL,NULL,NULL);
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvself_DBHelper_prepareStatement(JNIEnv* env, jclass clazz,jstring sqlStr) {
    const char* valChars = env->GetStringUTFChars(sqlStr, NULL);
    string valueStr(valChars);
    env->ReleaseStringUTFChars(sqlStr, valChars);

    int rc = sqlite3_prepare(db, valueStr.c_str(), -1, &pStmt, 0);
    bindIndex = 0;
//    if( rc!=SQLITE_OK ){
//        return;
//    }
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvself_DBHelper_execStatement(JNIEnv* env, jclass clazz ) {
    sqlite3_step(pStmt);
    sqlite3_finalize(pStmt);
    bindIndex = 0;
}

//char bindings[9216];

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvself_DBHelper_nativeReleaseBinds(JNIEnv* env, jclass clazz) {
    //strcat(bindings,"");
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvself_DBHelper_nativeBindInt(JNIEnv* env, jclass clazz, jint value) {
    /*char str[11];
    sprintf(str, "%d", value);
    if (strlen(bindings) > 0){
        strcat(bindings, ",");
    }
    strcat(bindings, str);*/

    sqlite3_bind_int(pStmt, ++bindIndex, value);
//    sqlite3_bind_int64(pStmt, 1, zKey, -1, SQLITE_STATIC);
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvself_DBHelper_nativeBindLong(JNIEnv* env, jclass clazz, jlong value) {
    /*char str[11];
    sprintf(str, "%d", value);
    if (strlen(bindings) > 0){
        strcat(bindings, ",");
    }
    strcat(bindings, str);*/

    sqlite3_bind_int64(pStmt, ++bindIndex, value);
//    sqlite3_bind_int64(pStmt, 1, zKey, -1, SQLITE_STATIC);
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvself_DBHelper_nativeBindString(JNIEnv* env, jclass clazz, jstring value) {
    /*const char* valChars = env->GetStringUTFChars(value, NULL);
    string valueStr(valChars);
    env->ReleaseStringUTFChars(value, valChars);

    if (strlen(bindings) > 0){
        strcat(bindings, ",");
    }
    strcat(bindings, "'");
    strcat(bindings, valueStr.c_str());
    strcat(bindings, "'");*/
    const char* valChars = env->GetStringUTFChars(value, NULL);
    string valueStr(valChars);
    env->ReleaseStringUTFChars(value, valChars);

    sqlite3_bind_text(pStmt, ++bindIndex, valueStr.c_str(), -1, SQLITE_STATIC);
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvself_DBHelper_nativeBindBlob(JNIEnv* env, jclass clazz, jbyteArray valueArray, jint size) {

//    jsize valueLength = env->GetArrayLength(valueArray);
//    jbyte* value = static_cast<jbyte*>(env->GetPrimitiveArrayCritical(valueArray, NULL));

    jboolean isCopy;
    jbyte* a = env->GetByteArrayElements(valueArray,&isCopy);

    int textLength = strlen((const char*)a);
    char* b = (char*) malloc(textLength + 1);
    memcpy(b, a, textLength);
    env->ReleaseByteArrayElements(valueArray, a, JNI_ABORT);

    b[textLength] = '\0';

    sqlite3_bind_blob(pStmt, ++bindIndex, b, textLength, SQLITE_STATIC);
}
