package in.mymovemanager.imdb.service.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 * imdb annotation for service injection
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
@Qualifier
@Documented
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Imdb {

}
