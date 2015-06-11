package model.annotations;

import java.lang.annotation.*;

/**
 * Annotation to mark values that should be treated as tags
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Tag {

  boolean excludeFromStringConcatenation() default false;
}
