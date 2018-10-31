package com.xyls.enums;

/**
 * 系统处理或者输出时的枚举类型
 */
public enum ResponseEnum {
    SYSTEM_STATUS("S00000", "成功！"),
    SYSTEM_ERROR("FF0001", "系统异常"),
    LOGIN_USER_NOT_EXIST("F00001", "用户不存在！"),
    LOGIN_USER_PASSWORD_ERROR("F00002", "密码输入有误！"),
    LOGIN_USER_TYPE_ERROR("F00004", "登陆类型有误！"),
    REGISTER_USER_EXIST("F00003", "注册用户已存在！"),
    REGISTER_USER_TYPE_ERROR("F00005", "注册用户类型有误！"),
    ILLEGAL_PARAMS("F00006", "参数非法！"),
    SYSTEM_VALIDATE_ERROR("F00007", "登录验证码输入有误！"),
    SYSTEM_USER_CODE_FAILED("F00008", "获取验证码的值失败"),
    SYSTEM_USER_CODE_EMPTY("F00009", "验证码的值不能为空"),
    SYSTEM_CODE_NOT_EXIST("F00010", "验证码不存在"),
    SYSTEM_CODE_EXPIRE("F00011", "验证码已过期"),
    SYSTEM_CODE_NOT_EQUAL("F00012", "验证码不匹配"),
    SYSTEM_CODE_GENERATOR_NOT_EXIT("F00013", "验证码生成器不存在【系统错误】！"),
    SYSTEM_CODE_PARAM_ERROR("F00014", "请在请求头中携带deviceId参数"),
    USER_SIGN_ALREADY("F000015", "已签到"),
    NEED_LOGIN("F00016", "需要登录！"),
    UPLOAD_FAIL("F00018", "文件上传失败！"),
    UPLOAD_EMPTY("F00017", "文件不能为空！"),
    UPLOAD_FILE_SIZE("F000019", "单个文件上传最大限制！");

    private final String code;
    private final String desc;


    ResponseEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
