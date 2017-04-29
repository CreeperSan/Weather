package creeper_san.weather.Exctption;



public class JsonDecodeException extends Exception {
    public static String TYPE_DECODE_ERR = "JSON数据解析出错";
    public static String TYPE_MISS = "JSON缺少键值";
    public static String TYPE_CUSTOM = "JSON其他异常";

    private String type;//错误的类型
    private String reason;//错误的原因
    private String errCode;

    public JsonDecodeException(String type, String reason,String errCode) {
        this.type = type;
        this.reason = reason;
        this.errCode = errCode;
    }

    public String getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    public String getErrCode() {
        return errCode;
    }
}
