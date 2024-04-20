package com.example.h2_shop;

public final class Constant {

    public static String getContentForgotPassword(String toName, String codeReset){
        StringBuilder content = new StringBuilder();


        content.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body style=\"font-family: Inter, mySymbol !important;\">\n" +
                "    <h1>H2Shop</h1>\n" +
                "    <div>Kính gửi quý khách hàng:") ;
        content.append(      "<span style=\"color:black; font-weight:600;\">"+ toName+" </span> ");
        content.append("</div>\n") ;
        content.append("<div> H2Shop trân trọng thông báo mã OTP lấy lại của quý khách là " );
        content.append(    "<span class=\"opt\">&nbsp;"+codeReset+"&nbsp;</span> vui lòng nhập mã sau để thực hiện khôi phục mật khẩu. Trân trọng!\n" );
        content.append(                "</div>\n" );
        content.append(         "</body>\n" +
                 "</html>");
        return content.toString();
    }

}
