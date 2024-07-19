package com.li.auth.config;

import com.li.auth.dto.Result;
import com.li.auth.utils.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final  String BAD_REQUEST_MSG = "错误的请求";
    private static final  String INTERNAL_SERVER_ERROR_MSG = "服务器内部错误";
    private static final  String NOT_FOUND_MSG = "请求地址错误";
    private static final  String UNAUTHORIZED_MSG = "用户验证失败";


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleException(MethodArgumentNotValidException ex) {//参数少
        ToolsUtil.writeLogs(INTERNAL_SERVER_ERROR_MSG,logger,ex);
        return new ResponseEntity<>(Result.getInstance(false,400,BAD_REQUEST_MSG,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // 可以添加其他异常处理方法

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Result> handleException(MethodArgumentTypeMismatchException ex) {//方法参数类型不匹配异常
        ToolsUtil.writeLogs(INTERNAL_SERVER_ERROR_MSG,logger,ex);
        return new ResponseEntity<>(Result.getInstance(false,400,BAD_REQUEST_MSG,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     HttpMessageNotReadableException 是一个由 Spring 框架抛出的异常，通常表示 HTTP 消息不可读。这种异常通常发生在接收客户端发送的数据时，数据的格式不正确或者无法被解析为相应的对象。
     常见原因和解决方法：
     请求正文格式错误：客户端发送的请求正文格式与服务器期望的格式不匹配。检查客户端发送的 Content-Type 头部，确保与服务器端期望的格式一致（例如：application/json）。
     JSON/XML格式错误：如果请求正文是 JSON 或 XML，可能存在格式错误，如缺少引号、花括号不匹配、缺少逗号等。使用 JSON 或 XML 验证工具检查格式是否正确。
     数据类型不匹配：请求正文中的数据类型与后端接收的数据类型不匹配，检查实体类中的字段类型是否与输入数据匹配。
     日期格式错误：如果接收的数据包含日期类型，可能因为日期格式不正确导致解析失败。确保日期格式与后端期望的格式一致，或者提供一个自定义的日期格式解析器。
     数据为空：如果客户端发送了空数据，也可能引发此异常。确保客户端发送的数据非空有效。
     配置问题：检查 Spring 应用中的消息转换器配置是否正确，确保可以正确处理请求正文。
     安全配置：如果使用了安全配置（如 Spring Security），确保认证和授权不会阻止请求到达服务器。
     解决方法通常涉及检查客户端请求的正文、内容类型、以及服务器端对应的实体类定义。修复格式错误或类型不匹配的问题后，异常应该能够被解决。
     * @param ex
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Result> handleException(HttpMessageNotReadableException ex) {//错误的请求
        ToolsUtil.writeLogs(INTERNAL_SERVER_ERROR_MSG,logger,ex);
        return new ResponseEntity<>(Result.getInstance(false,400,BAD_REQUEST_MSG,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * MissingServletRequestParameterException 是Spring MVC框架中处理HTTP请求时抛出的一种异常，它继承自ServletException，表示客户端的HTTP请求缺少了服务器端所期望的一个或多个必需参数。
     * 当一个控制器方法通过@RequestParam注解指定了某个参数是必需的（默认情况下就是必需的），而客户端在发起HTTP请求时未提供这个参数或提供的值为空时，服务器端Spring MVC在尝试将请求参数绑定到方法参数的过程中，就会抛出MissingServletRequestParameterException异常。
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<Result> handleException(MissingServletRequestParameterException ex) {//方法参数类型不匹配异常
        ToolsUtil.writeLogs(INTERNAL_SERVER_ERROR_MSG,logger,ex);
        return new ResponseEntity<>(Result.getInstance(false,400,BAD_REQUEST_MSG,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    /**
     * HttpMediaTypeNotSupportedException
     * 请求头中缺少 Content-Type：
     *     原因： 请求头中缺少 Content-Type 头部，而服务器需要根据这个头部来确定如何处理请求体。
     *     解决方法： 确保请求头中包含正确的 Content-Type 头部，指明请求体的数据类型。例如，如果请求体是 JSON 格式，确保请求头包含 Content-Type: application/json。
     *
     * 不支持的请求方法：
     *     原因： 使用了不支持消息体的请求方法，例如 GET 请求。
     *     解决方法： 对于不支持消息体的请求方法，不应该期望有请求体。如果需要传递数据，使用 POST 或其他支持消息体的方法。
     *
     * 无效的请求体：
     *     原因： 请求体的格式不符合指定的 Content-Type。
     *     解决方法： 确保请求体的格式与指定的 Content-Type 相匹配。例如，如果 Content-Type 为 application/json，确保请求体是合法的 JSON 格式。
     *
     * 请求体包含不受支持的媒体类型：
     *     原因： 请求体的媒体类型不被服务器支持。
     *     解决方法： 确保请求体的媒体类型与服务器支持的消息转换器匹配。例如，如果服务器支持 JSON 数据，确保请求头中的 Content-Type 包含 application/json。
     *
     * 无法找到匹配的消息转换器：
     *     原因： 没有找到适用于请求体的消息转换器。
     *     解决方法： 确保请求体的类型可以被正确转换，并且存在适用于该类型的消息转换器。如果需要自定义消息转换器，可以添加一个自定义的 HttpMessageConverter 实现。

     * @param ex
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Result> handleException(HttpMediaTypeNotSupportedException ex) {//方法参数类型不匹配异常
        ToolsUtil.writeLogs(INTERNAL_SERVER_ERROR_MSG,logger,ex);
        return new ResponseEntity<>(Result.getInstance(false,400,BAD_REQUEST_MSG,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 用户登录错误归->类服务器内部错误
     * @param ex
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Result> handleException(AuthenticationException ex) {
        ToolsUtil.writeLogs(INTERNAL_SERVER_ERROR_MSG,logger,ex);
        return new ResponseEntity<>(Result.getInstance(false,401,UNAUTHORIZED_MSG,ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }







    /**
     * 其它错误归->类服务器内部错误
     * @param ex
     * @return
     */
    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<Result> handleException(NoResourceFoundException ex) {
        ToolsUtil.writeLogs(INTERNAL_SERVER_ERROR_MSG,logger,ex);
        return new ResponseEntity<>(Result.getInstance(false,404,NOT_FOUND_MSG,ex.getMessage()), HttpStatus.NOT_FOUND);
    }


    /**
     * 其它错误归->类服务器内部错误
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Result> handleException(Exception ex) {
        ToolsUtil.writeLogs(INTERNAL_SERVER_ERROR_MSG,logger,ex);
        return new ResponseEntity<>(Result.getInstance(false,500,INTERNAL_SERVER_ERROR_MSG,ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
