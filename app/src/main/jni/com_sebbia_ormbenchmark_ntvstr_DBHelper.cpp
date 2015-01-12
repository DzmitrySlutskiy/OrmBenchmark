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

#include "include/com_sebbia_ormbenchmark_ntvstr_DBHelper.h"

using namespace std;
using namespace android;

#define LOG_NTV_STR    "ntvstr"
//#define LOGI(x...) __android_log_print(ANDROID_LOG_DEBUG, LOG_NTV_STR,x)
#define A_LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_NTV_STR,__VA_ARGS__)
//#define ALOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE,LOG_NTV_STR,__VA_ARGS__)
//#define ALOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_NTV_STR,__VA_ARGS__)
//#define ALOGW(...)  __android_log_print(ANDROID_LOG_INFO,LOG_NTV_STR,__VA_ARGS__)
//#define LOG_WINDOW(...)  ALOGW(__VA_ARGS__)
// Set to 1 to use UTF16 storage for localized indexes.
#define UTF16_STORAGE 0

char sql_statement[65535];
string *sql_string;
/*---------------------------------  ---------------------------------*/
sqlite3* dataBase;

int bindingIndex = 0;
/*com_sebbia_ormbenchmark_ntvstr_DBHelper_
/*com_sebbia_ormbenchmark_ntvstr_DBHelper_*/

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvstr_DBHelper_nativeOpen(JNIEnv* env, jclass clazz, jstring pathStr) {
    const char* pathChars = env->GetStringUTFChars(pathStr, NULL);
    string path(pathChars);
    env->ReleaseStringUTFChars(pathStr, pathChars);

    char receivedStr[1000];
    int err;
    if (err = sqlite3_open(path.c_str(), &dataBase) != SQLITE_OK) {
    	strcat(receivedStr,"Error wile opening db. ");
    	strcat(receivedStr, sqlite3_errmsg(dataBase));
    	throw_sqlite3_exception_errcode(env, err, receivedStr);
    }

    //sqlite3_exec(db,"CREATE TABLE IF NOT EXISTS Tab1 (_id integer primary key autoincrement, Col1 text, Col2 text); ",NULL,NULL,NULL);
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvstr_DBHelper_nativeClose(JNIEnv* env, jclass clazz) {
    sqlite3_close(dataBase);
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvstr_DBHelper_execSQL(JNIEnv* env, jclass clazz,jstring sqlStr) {
    const char* pathChars = env->GetStringUTFChars(sqlStr, NULL);
    string sqlChars(pathChars);
    env->ReleaseStringUTFChars(sqlStr, pathChars);

    sqlite3_exec(dataBase,sqlChars.c_str(),NULL,NULL,NULL);

}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvstr_DBHelper_prepareStatement(JNIEnv* env, jclass clazz,jstring sqlStr) {
    const char* valChars = env->GetStringUTFChars(sqlStr, NULL);
    sql_string = new string(valChars);
    env->ReleaseStringUTFChars(sqlStr, valChars);

    bindingIndex = 0;
}
bool flag = false;
JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvstr_DBHelper_execStatement(JNIEnv* env, jclass clazz ) {
    const char *sql_query = sql_string->c_str();
    int result = sqlite3_exec(dataBase, sql_query,NULL,NULL,NULL);
    if (!flag){
        A_LOGI("%s",sql_query);
        flag = true;
    }
    if (result != SQLITE_OK){
            A_LOGI("ERROR NTV STR");
    }
//    A_LOGI("query: %s",sql_query);

    delete sql_string;
    sql_string = NULL;
    bindingIndex = 0;
}

//char bindings[9216];

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvstr_DBHelper_nativeReleaseBinds(JNIEnv* env, jclass clazz) {
    //strcat(bindings,"");
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvstr_DBHelper_nativeBindInt(JNIEnv* env, jclass clazz, jint value) {
    size_t pos = sql_string->find("?");
    if (pos>0){
        char str[100];
        sprintf(str, "%d", value);
        sql_string->replace(pos,1,str);
    }
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvstr_DBHelper_nativeBindLong(JNIEnv* env, jclass clazz, jlong value) {
    size_t pos = sql_string->find("?");
    if (pos > 0){
        char str[100];
        sprintf(str, "%d", value);
        sql_string->replace(pos,1,str);
    }
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvstr_DBHelper_nativeBindString(JNIEnv* env, jclass clazz, jstring valueString) {
    const char* paramChars = env->GetStringUTFChars(valueString, NULL);
    string sqlParam(paramChars);
    env->ReleaseStringUTFChars(valueString, paramChars);

    string res("'");
    res.append(sqlParam);
    res.append("'");

    size_t pos = sql_string->find("?");
    if (pos>0){
        sql_string->replace(pos,1,res);
    }
}
bool flag3 = true;
unsigned char* as_unsigned_char_array(JNIEnv* env, jbyteArray array) {
    int len = env->GetArrayLength (array);
    if (flag3){
        A_LOGI("byte arr size: %d",len);
        flag3=false;
    }

    unsigned char* buf = new unsigned char[len];
    env->GetByteArrayRegion (array, 0, len, reinterpret_cast<jbyte*>(buf));
    return buf;
}

JNIEXPORT void JNICALL Java_com_sebbia_ormbenchmark_ntvstr_DBHelper_nativeBindBlob(JNIEnv* env, jclass clazz, jbyteArray valueArray, jint size) {
//    unsigned char *array = as_unsigned_char_array(env,valueArray);

//    jbyte* array = env->GetByteArrayElements(valueArray, NULL);
    bool isCopy;

    int len = env->GetArrayLength(valueArray);
    char buffer[65535];

    jbyte* data = env->GetByteArrayElements(valueArray, NULL);
    if (data != NULL) {
       memcpy(buffer, data, len);
       env->ReleaseByteArrayElements(valueArray, data, JNI_ABORT);
    }
    buffer[len]=0;
    size_t pos = sql_string->find("?");

    string res("'");
    res.append(buffer, len);
    res.append("'");

    if (pos>0){
        sql_string->replace(pos,1,res.c_str());
    }
}

