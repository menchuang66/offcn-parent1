package enums;

public enum ResponseCodeEnume {

    SUCCESS(0,"�����ɹ�"),
    FAIL(1,"�������쳣"),
    NOT_FOUND(404,"��Դδ�ҵ�"),
    NOT_AUTHED(403,"��Ȩ�ޣ����ʾܾ�"),
    PARAM_INVAILD(400,"�ύ�����Ƿ�");

    private Integer code;
    private String msg;

    private ResponseCodeEnume(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}