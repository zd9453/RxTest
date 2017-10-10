package com.zd.rxtest.rxretrofit;

/**
 * use to
 * <p>
 * Created by zhangdong on 2017/10/10.
 *
 * @version 2.1
 */

public class CodeMsgModel {

    /**
     * error_code : 0
     * reason : Success
     * result : {"data":[{"content":"昨天下班坐公交车回家，白天上班坐着坐多了想站一会儿，\r\n就把座位让给了一个阿姨，阿姨道谢一番开始和我聊天，聊了挺多的。\r\n后来我要下车了，阿姨热情的和我道别。\r\n下车的一瞬间我回头看了一眼，只见那阿姨对着手机说：\u201c儿子，\r\n刚才遇见一个姑娘特不错，可惜长得不好看，不然我肯定帮你要号码！\u201d\r\n靠，阿姨你下车，我保证不打死你！","hashId":"348e7f933774bcaef3ed3f0ecb8e7b19","unixtime":1418819032,"updatetime":"2014-12-17 20:23:52"}]}
     */

    private int error_code;
    private String reason;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "CodeMsgModel{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                '}';
    }
}
