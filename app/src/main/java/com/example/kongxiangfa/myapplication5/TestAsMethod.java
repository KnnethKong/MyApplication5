package com.example.kongxiangfa.myapplication5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class TestAsMethod {
    private static final String TAG = "aspect_aby";

    @Pointcut("execution(@com.example.kongxiangfa.myapplication5.TestLoginAnnotation * *(..))")
    public void testLoginPoint() {
    }

    @Pointcut("execution(* com.example.kongxiangfa.myapplication5.TestActivity.aspectClick(android.view.View))")
    public void secondMethodAnnotationBehavior() {
    }

    @Around("testLoginPoint()")
    public void testLoginAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 类名
        String className = methodSignature.getDeclaringType().getSimpleName();
        // 方法名
        String methodName = methodSignature.getName();
        // 功能名
        TestLoginAnnotation behaviorTrace = methodSignature.getMethod()
                .getAnnotation(TestLoginAnnotation.class);
        String value = behaviorTrace.value();
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        Log.e(TAG, String.format("%s类中%s方法执行%s功能,耗时：%dms", className, methodName, value, duration));

        Context context = null;
        final Object object = joinPoint.getThis();
        if (object instanceof Context) {
            context = (Context) object;
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        } else if (object instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) object).getActivity();
        }
        if (context == null) {
            Log.e(TAG, "testLoginAround:  context==null");
            return;
        }
        String mEmail = context.getSharedPreferences("testOne", Context.MODE_PRIVATE).getString("userEmail", null);
        if (TextUtils.isEmpty(mEmail)){
            context.startActivity(new Intent(context,LoginActivity.class));
        }
    }

}
