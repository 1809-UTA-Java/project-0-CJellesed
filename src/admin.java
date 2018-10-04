package com.revature;

class admin extends account implements employeeControls {

    protected admin(String aName, String fName, int pLevel, double cFunds) {
        super(aName, fName, pLevel, cFunds);
    }
    protected void depositeFunds() {

    }
    protected void withdrawFunds() {

    }
    protected void transferFunds() {

    }

    @Override
    public void gAccInfo() {

    }
        
    @Override
    public void gAccBalance() {

    }
        
    @Override
    public void gPInfo() {

    }
        
    @Override
    public void gCApplication() {

    }

    protected void sAccInfo() {

    }
    protected void sAccBalance() {

    }
    protected void sPInfo() {
        System.out.println("test");
    }
    protected void sCApplication() {

    }
}