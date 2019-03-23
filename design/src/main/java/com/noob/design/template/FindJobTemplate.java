package com.noob.design.template;

public abstract class FindJobTemplate {

    public void findJob(){
        //1.投递简历
        sendInfo();
        //2.去对方公司
        goToCompany();
        //3.笔试
        paperTest();
        //4.面试
        interview();
        //5.hr面
        hrTest();
        //6.是否录用
        if(checkPass()){
            pass();
        }
    }

    abstract boolean checkPass();

    private void hrTest() {
        System.out.println("hr谈");
    }

    private void pass(){
        System.out.println("通过了面试!");
    }

    private void interview() {
        System.out.println("技术面试");
    }

    private void paperTest() {
        System.out.println("笔试");
    }

    private void goToCompany() {
        System.out.println("去公司");
    }

    private void sendInfo() {
        System.out.println("投递简历");
    }
}
