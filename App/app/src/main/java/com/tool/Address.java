package com.tool;

/*ip是服务器的地址
 * provingidentitya是身份验证的servlet地址
 * register是用户注册的servlet地址
 * bankcardinformation是银行卡信息查询的servlet地址
 * transferrecord是查询明细表的servlet地址
 * addbankcard是添加银行卡的servlet地址
 * transfer是交易转账的servlet地址
 */

public class Address {
    public static String ip = "http://192.168.1.103:8080";
    public static String provingidentitya="/ImageRecognition/com/servlet/tool/ProvingIdentityServlet?";
    public static String register="/ImageRecognition/com/servlet/tool/RegisterUserServlet?";
    public static String bankcardinformation="/ImageRecognition/com/servlet/user/BankCardInformationServlet?";
    public static String transferrecord="/ImageRecognition/com/servlet/user/QueryTransferRecordServlet?";
    public static String addbankcard="/ImageRecognition/com/servlet/user/AddBankCardServlet?";
    public static String transfer="/ImageRecognition/com/servlet/user/TransferServlet?";
}
