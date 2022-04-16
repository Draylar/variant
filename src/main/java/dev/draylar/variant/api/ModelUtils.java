package dev.draylar.variant.api;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ModelUtils {

    public static <T extends Entity> EntityModel<T> duplicate(EntityModel<T> model, ModelPart part) {
        Constructor<?>[] constructors = model.getClass().getConstructors();
        for (Constructor<?> constructor : constructors) {
            if(constructor.getParameters().length == 1) {
                try {
                    Object o = constructor.newInstance(part);
                    return (EntityModel<T>) o;
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("couldn't load");
        return null;
    }
}
