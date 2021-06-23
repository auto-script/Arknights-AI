package com.mlick.mrfzai.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lixiangxin
 * @date 2019/7/22 16:08
 **/
public enum Action {

    INSTANCE("", ""),
    INDEX_TERMINAL("index_zhongduan", "首页终端"),
    START_ACTION("start_action_btn", "开始行动（横）"),
    START_ACTION2("start_action_btn2", "开始行动（竖）"),
    END_ACTION_BTN("end_action_btn", "行动结束"),
    JIE_GUAN_ZUO_ZHAN("jieguanzuozhan", "接管作战"),
    UN_PROXY("un_proxy", "代理指挥【未选中】"),
    YUAN_SHI("yuanshi", "使用至纯源石恢复"),
    YES_1("yes_btn", "红底加白底确认"),
    YES_2("next_btn", "灰底确认"),
    YES_3("next_white_btn", "白底确认"),
    YES_4("next_black", "黑底确认"),
    EXIT_1("exit_btn", "灰底关闭"),
    CLOSE_4("exit_black", "黑底关闭"),
    CONTINUE_SUBMIT("continue_submit", "继续结算"),
    JI_YI_MO_HU("jiyimohu", "记忆已经模糊，请重新输入登录信息"),
    LOGIN_MLICK_INPUT("login_mlick_input", "18321295235"),
    LEVEL_UP_BTN("level_up_btn", "等级提升"),
    RECOVER_WIT("recover_wit", "理智已恢复"),
    START("start", "START"),
    START_WAKE("start_wake2", "开始唤醒"),
    EMAIL_BTN("email_btn", "邮箱"),
    EMAIL_ACCEPT_ALL("email_accept_all", "收取所有邮件"),
    EMAIL_DELETE_BTN("email_delete_btn", "删除已读邮件"),
    BACK_WHITE_BTN("back_white_btn", "白底返回"),
    BACK_BLACK_BTN("back_black_btn", "黑底返回"),
    LOGIN_ACCOUNT_BTN("login_account_btn", "账号登录"),
    LOGIN_ACCOUNT_INPUT("login_account_input", "账号输入"),
    LOGIN_PASSWORD_INPUT("login_password_input", "密码输入"),
    LOGIN_BTN("login_btn", "登录"),
    ENTER_BTN("enter_btn", "确定"),
    GOODS("goods2", "资源收集"),
    FIGHT_MONEY("fight_money2", "货物运送"),
    CE_5("CE-5", "CE-5"),
    GET_CREDIT("get_credit", "收取信用"),
    ;

    private String img;
    private String name;


    public static Map<String, String> DESC = new HashMap<>();

    static {
        for (Action action : Action.values()) {
            DESC.put(action.getImg(), action.getName());
        }
    }

    Action(String img, String name) {
        this.img = img + ".png";
        this.name = name;
    }


    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }
}
