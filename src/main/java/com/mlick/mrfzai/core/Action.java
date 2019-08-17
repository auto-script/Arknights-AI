package com.mlick.mrfzai.core;

/**
 * @author lixiangxin
 * @date 2019/7/22 16:08
 **/
public enum Action {

  INSTANCE("", ""),
  START_ACTION("start_action_btn", "开始行动（横）"),
  START_ACTION2("start_action_btn2", "开始行动（竖）"),
  YES_1("yes_btn", "红底加白底确认"),
  YES_2("next_btn", "灰底确认"),
  YES_3("next_white_btn", "白底确认"),
  EXIT_1("exit_btn", "灰底关闭"),
  CONTINUE_SUBMIT("continue_submit", "继续结算"),
  END_ACTION_BTN("end_action_btn", "行动结束"),
  LEVEL_UP_BTN("level_up_btn", "等级提升"),
  START("START", "START"),
  START_WAKE("start_wake", "开始唤醒"),
  EMAIL_BTN("email_btn", "邮箱"),
  EMAIL_ACCEPT_ALL("email_accept_all", "收取所有邮件"),
  EMAIL_DELETE_BTN("email_delete_btn", "删除已读邮件"),
  BACK_WHITE_BTN("back_white_btn", "白底返回"),
  BACK_BLACK_BTN("back_black_btn", "黑底返回"),
  LOGIN_ACCOUNT_BTN("login_account_btn", "账号登录"),

  ;

  private String img;
  private String name;

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

  public Action getAction(String img) {
    for (Action action : Action.values()) {
      if (action.img.equals(img)) {
        return action;
      }
    }
    Action instance = Action.INSTANCE;
    instance.img = img;
    instance.name = "待添加ENUM";
    return instance;
  }

}
