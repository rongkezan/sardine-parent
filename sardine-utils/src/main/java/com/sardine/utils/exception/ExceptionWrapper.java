package com.sardine.utils.exception;

import com.sardine.utils.annotation.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Catch exception wrapper, logging when throw exception.
 *
 * @author keith
 */
public class ExceptionWrapper {

    private static Logger log = LoggerFactory.getLogger(ExceptionWrapper.class);

    public static void wrapCatch(Wrapper wrapper) {
        try {
            wrapper.wrap();
        } catch (BusinessException e) {
            log.warn(e.getMessage(), e);
            throw e;
        } catch (SystemException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw SystemException.of("网络错误");
        }
    }

    public static <T> T wrapCatch(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (BusinessException e) {
            log.warn(e.getMessage(), e);
            throw e;
        } catch (SystemException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw SystemException.of("网络错误");
        }
    }
}
