package com.china.hcg.utils.validate;

import com.china.hcg.ApplicationConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @autor hecaigui
 * @date 2023/4/26
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfiguration.class)
public class ValidateTest {
    //Validator 是一个接口，它的实现类可以是 Hibernate Validator、Spring Validator 或者其他第三方实现。具体的实例化方式取决于您要使用哪个实现。
//    @Autowired
//    private Validator validator;

    @Test
    public void test(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        BO bo = new BO();
        Set<ConstraintViolation<BO>> violations = validator.validate(bo);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<BO> violation : violations) {
                System.err.println(violation.getPropertyPath()+violation.getMessage());
            }
            System.err.println(violations);
        }
    }
    /**
     https://blog.csdn.net/huanglu0314/article/details/118605808
     除了使用 Spring 框架提供的注解进行参数校验外，还可以使用 Java 标准库中的 Validation API 进行校验。

    1. 在 UserDto 类上使用 Validation API 进行校验.
    2. 使用 Validator 对象对 UserDto 对象进行了校验。如果校验失败，将会抛出 ConstraintViolationException 异常，并返回给客户端相应的错误信息。
        @Autowired
        private Validator validator;
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
                if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    附：
        Java标准库中的Validation API可以与groups一起使用：
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<MyClass>> violations = validator.validate(myObject, Group1.class, Group2.class);
            请注意，如果未指定组，则所有注释都将被验证。
        自定义校验注解：
            https://blog.csdn.net/huanglu0314/article/details/118605808
    */
    @Test
    public void test2(){
        BO fc = new BO();
        validateParam(fc);
    }
    public static <T> String validateParam(T t, Class<?>... groups){
        String err = null;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(t,groups);
        if (!violations.isEmpty()) {
            err = "";
            for (ConstraintViolation<T> violation : violations) {
                err = violation.getPropertyPath()+violation.getMessage();
            }
            return err;
        }
        return null;
    }
}
