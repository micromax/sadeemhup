package com.github.sadeemhup.system.utils;


import com.github.sadeemhup.system.core.CG_Controller;
import com.github.sadeemhup.system.core.DataBase.CGModel;
import com.github.sadeemhup.system.core.authentication.Iauthentication;
import io.vertx.ext.web.RoutingContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class LuncherHelper {

    private volatile static  LuncherHelper ourInstance = new LuncherHelper();


    public static  LuncherHelper getInstance() {

        if (null == ourInstance)
        {

            synchronized(LuncherHelper.class)
            {
                if (null == ourInstance)
                {

                    ourInstance = new LuncherHelper();
                }
            }

        }

        return ourInstance;
    }


    public static boolean isClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException | NullPointerException e) {
            return false;
        }

    }

    public static boolean isClasskotlin(String className) {
        try {



            ClassLoader cl = null;
            Class c = cl.loadClass(className);
            if(c == null)
            {
                return false;
            }else {
                return true;
            }
        } catch (ClassNotFoundException | NullPointerException e) {
            return false;
        }

    }



    public CG_Controller GetController(String whichClass, RoutingContext Parm) {
        CG_Controller UC = null;

        try {
            if (isClass(whichClass) == true) {

                Class clazz = Class.forName(whichClass);
                Object annotion = null;
                Annotation[] annotations = clazz.getAnnotations();
                for (Annotation an:
                annotations) {


                    switch (an.annotationType().getSimpleName()){
                        case "Iauthentication":
                            annotion =  ((Iauthentication) an).AuthModel().getDeclaredConstructor(RoutingContext.class).newInstance(Parm);
                                    break;
                    }



                }

                if(annotion instanceof CGModel)
                {
                    boolean isl = ((CGModel) annotion).isLook();
                    if(isl == true){
                        UC = (CG_Controller) clazz.getDeclaredConstructor(RoutingContext.class).newInstance(Parm);
                        UC.setRunError(true);
                    }else {
                        UC = (CG_Controller) clazz.getDeclaredConstructor(RoutingContext.class).newInstance(Parm);
                    }

                }else {

                    UC = (CG_Controller) clazz.getDeclaredConstructor(RoutingContext.class).newInstance(Parm);
                }
            } else {
                    if(isClasskotlin(whichClass))
                    {

                    }
                    Class clazz = Class.forName("com.github.sadeemhup.system.core.controller.Error");

                    UC = (CG_Controller) clazz.getDeclaredConstructor(RoutingContext.class).newInstance(Parm);
            }


            //return UC;


        } catch (ClassNotFoundException | NullPointerException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return UC;
    }



}
