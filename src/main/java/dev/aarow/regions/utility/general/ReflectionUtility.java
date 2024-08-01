package dev.aarow.regions.utility.general;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtility {

    public static Object multiInvoke(Object mainClass, String path){
        Object current = mainClass;

        for(String methodName : path.split("\\.")){
            try {
                current = current.getClass().getMethod(methodName).invoke(current);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return current;
    }
}
