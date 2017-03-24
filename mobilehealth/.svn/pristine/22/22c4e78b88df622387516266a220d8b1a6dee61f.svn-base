package com.cmcc.patient.httprequest;

/**
 * The type Api exception.
 *
 * @author shuyang on 16/3/10.
 */
public class ApiException extends RuntimeException {

    /**
     * The constant USER_NOT_EXIST.
     */
    public static final int USER_NOT_EXIST = 100;
    /**
     * The constant WRONG_PASSWORD.
     */
    public static final int WRONG_PASSWORD = 101;
    /**
     * The constant NO_MESSAGE.
     */
    public static final int NO_MESSAGE = 102;

    /**
     * Instantiates a new Api exception.
     *
     * @param resultCode the result code
     */
    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    /**
     * Instantiates a new Api exception.
     *
     * @param detailMessage the detail message
     */
    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            case NO_MESSAGE:
                message = "没有任何数据";
                break;
            default:
//                message = "未知错误";
                message = "未知";
        }
        return message;
    }
}

