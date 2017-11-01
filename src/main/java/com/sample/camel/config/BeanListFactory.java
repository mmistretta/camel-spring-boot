package com.sample.camel.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.primitives.Primitives;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.FactoryBean;
import java.beans.PropertyDescriptor;

public class BeanListFactory <T> implements FactoryBean<List<T>> {

    private String propertyPrefix;
    private Map<String, Object> commonProperties = Collections.emptyMap();
    private Class<T> targetType;
    private Properties properties;

    private List<T> loadedBeans;
   
    public String getPropertyPrefix() {
        return propertyPrefix;
    }

    public void setPropertyPrefix(String propertyPrefix) {
        this.propertyPrefix = propertyPrefix;
    }

    public Map<String, Object> getCommonProperties() {
        return commonProperties;
    }

    public void setCommonProperties(Map<String, Object> commonProperties) {
        this.commonProperties = commonProperties;
    }

    public Class<T> getTargetType() {
        return targetType;
    }

    public void setTargetType(Class<T> targetType) {
        this.targetType = targetType;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public List<T> getObject() throws Exception {

        loadedBeans = new ArrayList<T>();
        int lastIndex = -1;

        T item = null;

        for (String property: prefixFilteredProperties()) {

            // The actual value
            final String propertyValue = properties.getProperty(property);

            // Remove Prefix
            property = property.substring(propertyPrefix.length() + 1);

            // Split by "."
            String tokens[] = property.split("\\.");

            if (tokens.length != 2) {
                throw new IllegalArgumentException("Each list property must be in form of: <prefix>.<index>.<property name>");
            }

            final int index = Integer.valueOf(tokens[0]);
            final String propertyName = tokens[1];

            // New index
            if (lastIndex != index) {
                if (lastIndex !=-1) {
                    loadedBeans.add(item);
                }
                lastIndex = index;

                item = targetType.newInstance();
                setCommonProperties(item, commonProperties);
            }

            // Set the property
            setProperty(item, propertyName, convertIfNecessary(propertyName, propertyValue));
        }

        // Add last item
        if (lastIndex != -1) {
            loadedBeans.add(item);
        }

        return loadedBeans;
    }

    @Override
    public Class<?> getObjectType() {
        return ArrayList.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    private Object convertIfNecessary(String propertyName, String propertyValue) throws Exception {
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(targetType, propertyName);
        Class<?> propertyType = Primitives.wrap(descriptor.getPropertyType());

        if (propertyType == String.class) {
            return propertyValue;
        }

        return propertyType.getDeclaredMethod("valueOf", String.class).invoke(propertyType, propertyValue);
    }

    private Set<String> prefixFilteredProperties() {
        Set<String> filteredProperties = new TreeSet<String>();

        for (String propertyName: properties.stringPropertyNames()) {
            if (propertyName.startsWith(this.propertyPrefix)) {
                filteredProperties.add(propertyName);
            }
        }

        return filteredProperties;
    }

    private void setCommonProperties(T item, Map<String, Object> commonProperties) throws Exception {
        for (Map.Entry<String, Object> commonProperty: commonProperties.entrySet()) {
            setProperty(item, commonProperty.getKey(), commonProperty.getValue());
        }
    }

    private static void setProperty(Object item, String propertyName, Object value) throws Exception {
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(item.getClass(), propertyName);
        descriptor.getWriteMethod().invoke(item, value);
    }
}

