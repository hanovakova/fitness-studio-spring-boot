package com.fitnessstudiospringboot.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;

public class BeanCopyUtils {
    public static void copyNonNullProperties(Object source, Object target) {
        BeanWrapper src = new BeanWrapperImpl(source);
        BeanWrapper dest = new BeanWrapperImpl(target);

        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            String propName = pd.getName();
            // Ignore "class" property and check if property is writable on target
            if (!propName.equals("class") && dest.isWritableProperty(propName)) {
                Object srcValue = src.getPropertyValue(propName);
                // Only copy if the source value is not null
                if (srcValue != null) {
                    dest.setPropertyValue(propName, srcValue);
                }
            }
        }
    }
}
