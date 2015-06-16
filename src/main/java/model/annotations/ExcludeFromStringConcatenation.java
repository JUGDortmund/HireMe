package model.annotations;

import java.lang.annotation.*;

/**
 * Annotation to mark values to NOT be transformed into a String during the PDF-Export
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcludeFromStringConcatenation {
}
