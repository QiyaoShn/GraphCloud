package com.yupi.yupicture.infrastructure.aop;

import com.yupi.yupicture.infrastructure.annotation.AuthCheck;
import com.yupi.yupicture.infrastructure.exception.BusinessException;
import com.yupi.yupicture.infrastructure.exception.ErrorCode;
import com.yupi.yupicture.domain.user.entity.User;
import com.yupi.yupicture.domain.user.valueobject.UserRoleEnum;
import com.yupi.yupicture.application.service.UserApplicationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserApplicationService userApplicationService;

    /**
     * 执行拦截
     * 所有的权限校验逻辑都在这里集中管理
     *
     * @param joinPoint 切入点(在哪个方法之后执行doInterceptor方法)
     * @param authCheck 权限校验注解
     * @return
     * @throws Throwable
     * @Around 环绕切面，方法上有authCheck注解，才能作为切入点，才会应用权限校验
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        //通过全局请求上下文存储器获取 请求对象，获取当前对象所有属性
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //获取当前登录用户
        User loginUser = userApplicationService.getLoginUser(request);
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        //如果没有指定必须具有的权限，就继续执行原方法(放行)
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }
        // 以下代码都是必须有权限，才会通过
        // 获取当前用户权限
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        // 当前用户没有任何权限
        if (userRoleEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //要求有管理员权限，但是当前用户没有管理员权限，拒绝
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum) && !UserRoleEnum.ADMIN.equals(userRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //通过权限校验，放行
        return joinPoint.proceed();
    }
}
