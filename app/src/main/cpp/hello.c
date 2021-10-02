//
// Created by jacob on 9/12/21.
//

#include "hello.h"
#include <jni.h>

int getFoo() {
    return 42;
}

JNIEXPORT jint JNICALL
Java_com_jacobarau_smoothptz_HelloJNI_getFoo(JNIEnv *env, jobject thiz) {
    return getFoo();
}