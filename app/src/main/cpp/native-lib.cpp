#include <jni.h>
#include <string>
#include "demo_test01.h"
#include "android/log.h"
#include "pthread.h"
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>


#define APPNAME "FridaDetectionTest"
#define  TAG    "HEXL"

// 定义info信息

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)

// 定义debug信息

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)

// 定义error信息

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)


JavaVM *global_vm;
JNIEnv *global_env;

extern "C" JNIEXPORT jstring JNICALL
Java_com_hexl_lessontest_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    int result = add(1, 2);
    char str[6];
    sprintf(str, " add %d", result);
    LOGI("val=%s,len=%d,address=%p", str, strlen(str), &str);
    return env->NewStringUTF(strcat(const_cast<char *>(hello.c_str()), str));
}

void onCreate(JNIEnv *env, jobject thiz, jobject saved_instance_state) {
    // TODO: implement onCreate()
    /*
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }
     */

    jclass thisClass = env->GetObjectClass(thiz);
//    jclass superClsss1 = env->GetSuperclass(thisClass);

    //super.onCreate(savedInstanceState);
    jclass superClsss1 = env->FindClass("androidx/appcompat/app/AppCompatActivity");
    jmethodID onCreate_mid = env->GetMethodID(superClsss1, "onCreate", "(Landroid/os/Bundle;)V");
    env->CallNonvirtualVoidMethod(thiz, superClsss1, onCreate_mid, saved_instance_state);

    //getLayoutInflater()
    jmethodID getLayoutInflater_mid = env->GetMethodID(thisClass, "getLayoutInflater", "()Landroid/view/LayoutInflater;");
    jobject LayoutInflater_object = env->CallObjectMethod(thiz, getLayoutInflater_mid);

    // ActivityMainBinding.inflate()
    jclass ActivityMainBindingClass = env->FindClass("com/hexl/lessontest/databinding/ActivityMainBinding");
    jmethodID inflate_mid = env->GetStaticMethodID(ActivityMainBindingClass, "inflate", "(Landroid/view/LayoutInflater;)Lcom/hexl/lessontest/databinding/ActivityMainBinding;");
    jobject binding_object = env->CallStaticObjectMethod(ActivityMainBindingClass, inflate_mid, LayoutInflater_object);

    // binding = xxx
    jfieldID binding_fid = env->GetFieldID(thisClass, "binding", "Lcom/hexl/lessontest/databinding/ActivityMainBinding;");
    env->SetObjectField(thiz, binding_fid, binding_object);

    // binding.getRoot()
    jmethodID getRoot_mid = env->GetMethodID(ActivityMainBindingClass, "getRoot", "()Landroid/view/View;");
    jobject root_object = env->CallObjectMethod(binding_object, getRoot_mid);

    // setContentView(xxx)
    jmethodID setContentView_mid = env->GetMethodID(thisClass, "setContentView", "(Landroid/view/View;)V");
    env->CallVoidMethod(thiz, setContentView_mid, root_object);

    //initView()
    jmethodID initView_mid = env->GetMethodID(thisClass, "initView", "()V");
    env->CallVoidMethod(thiz, initView_mid);

}


__attribute__ ((visibility ("hidden"))) void *detect_frida_loop(void *) {
    struct sockaddr_in sa;
    memset(&sa, 0, sizeof(sa));
    sa.sin_family = AF_INET;
    inet_aton("0.0.0.0", &(sa.sin_addr));
    int sock;
    int i;
    int ret;
    char res[7];
    while(1){
        /*
         * 1:Frida Server Detection
         */
        //LOGI("entering frida server detect loop started");
        for(i=20000;i<30000;i++){
            sock = socket(AF_INET,SOCK_STREAM,0);
            sa.sin_port = htons(i);
            LOGI("entering frida server detect loop started,now i is %d",i);

            if (connect(sock , (struct sockaddr*)&sa , sizeof sa) != -1) {
                memset(res, 0 , 7);
                send(sock, "\x00", 1, NULL);
                send(sock, "AUTH\r\n", 6, NULL);
                usleep(500); // Give it some time to answer
                if ((ret = recv(sock, res, 6, MSG_DONTWAIT)) != -1) {
                    if (strcmp(res, "REJECT") == 0) {
                        LOGI("FOUND FRIDA SERVER: %s,FRIDA DETECTED [1] - frida server running on port %d!",APPNAME,i);
                    }else{
                        LOGI("not FOUND FRIDA SERVER");
                    }
                }
            }
            close(sock);
        }
    }
}


extern "C" JNIEXPORT void JNICALL
Java_com_hexl_lessontest_MainActivity_init(
        JNIEnv* env,
        jclass clazz) {

    pthread_t t;
    pthread_create(&t,NULL,detect_frida_loop,(void*)NULL);
    LOGI("frida server detect loop started");
}


struct Data{
    JNIEnv *env;
    char *str;
};

extern "C" __attribute__ ((visibility ("hidden"))) void not_thread_test(void *arg){
    Data *data = static_cast<Data *>(arg);
    char *str = data->str;
    JNIEnv *data_env = data->env;

    JNIEnv *vm_env;
    global_vm->AttachCurrentThread(&vm_env, nullptr);

    __android_log_print(ANDROID_LOG_INFO, "HEXL-not_thread_test", "global_env-->%p", global_env);
    __android_log_print(ANDROID_LOG_INFO, "HEXL-not_thread_test", "data_env-->%p", data_env);
    __android_log_print(ANDROID_LOG_INFO, "HEXL-not_thread_test", "vm_env-->%p", vm_env);


    jstring jstring_test = global_env->NewStringUTF("not threadtest jstring");
    const char *content = global_env->GetStringUTFChars(jstring_test, nullptr);

    for(int i=0;i<10;i++){
        __android_log_print(ANDROID_LOG_INFO, "HEXL-not_thread_test", "%s-->%d-->%s", str, i, content);
    }
}

void * thread_test(void *arg){
    Data *data = static_cast<Data *>(arg);
    char *str = data->str;
    JNIEnv *data_env = data->env;

    JNIEnv *vm_env;
    global_vm->AttachCurrentThread(&vm_env, nullptr);

    LOGI("global_env-->%p", global_env);
    LOGI("data_env-->%p", data_env);
    LOGI("vm_env-->%p", vm_env);


    jstring jstring_test = vm_env->NewStringUTF("threadtest jstring");
    const char *content = vm_env->GetStringUTFChars(jstring_test, nullptr);

    for(int i=0;i<10;i++){
        LOGI("%s-->%d-->%s", str, i, content);
    }
    pthread_exit(0);
}

char * get_cat_string(char *tmp, char *tmp2){
    return strcat(tmp, tmp2);
}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved){

    JNIEnv *env = nullptr;
    vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6);

    global_vm = vm;
    global_env = env;

    JNINativeMethod nativeMethod[] = {
            {"onCreate", "(Landroid/os/Bundle;)V", (void *)onCreate}
    };
    jclass MainActivityClass = env->FindClass("com/hexl/lessontest/MainActivity");
    env->RegisterNatives(MainActivityClass, nativeMethod, sizeof(nativeMethod)/ sizeof(JNINativeMethod));

    char tmp1[30]= "huaerxiela->";
    char *tmp2 = "nihao";

    char *str_cat_result = get_cat_string(tmp1, tmp2);
//    char *str_cat_result = strcat(tmp1, tmp2);

    LOGI("JNI_OnLoad env-->%p", env);
    LOGI("str_cat_result-->%s", str_cat_result);

    //    Data *data = new Data;
    Data *data = static_cast<Data *>(malloc(sizeof(Data)));
    data->env = env;
    data->str = str_cat_result;

    pthread_t tid;
    pthread_create(&tid, nullptr, thread_test, data);
    pthread_join(tid, nullptr);


    not_thread_test(data);

    return JNI_VERSION_1_6;
}

// 编译生成后在.init段 [名字不可更改]
extern "C" void _init(void){
    LOGI("go into _init");
}

// 编译生成后在.init_array段 [名字可以更改]
__attribute__((__constructor__)) static void pp_init1() {
    LOGI("Enter pp_init1......");
}
__attribute__((__constructor__)) static void pp_init2() {
    LOGI("Enter pp_init2......");
}