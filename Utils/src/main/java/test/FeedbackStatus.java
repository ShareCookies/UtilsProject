package test;

public enum FeedbackStatus {
    //yqs("已签收"),//已签收
    jdfk("阶段反馈"),//阶段反馈
    bjfksh("报结反馈审核")//报结反馈审核
    ,fksh("反馈审核")//反馈审核
    ,shtg ("审核通过")//审核通过
    ,jxfk("继续反馈"); //继续反馈
    String cname;
    FeedbackStatus(String cname){
        this.cname = cname;
    }

}