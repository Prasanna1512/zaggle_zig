package com.zaggle.zig.travel.common.helper;

import org.springframework.beans.BeanUtils;

public class GenericConverter<Source, Target> {
    private final Class<Source> sourceType;
    private final Class<Target> targetType;

    public GenericConverter(Class<Source> sourceType, Class<Target> targetType) {
        this.sourceType = sourceType;
        this.targetType = targetType;
    }

    public Target convertToDTO(Source source) {
        Target target = null;
        try {
            target = targetType.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            // Handle exceptions as needed
        }
        return target;
    }

    public Source convertToEntity(Target target) {
        Source source = null;
        try {
            source = sourceType.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(target, source);
        } catch (Exception e) {
            // Handle exceptions as needed
        }
        return source;
    }
}
