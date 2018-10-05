package com.revature;

class employee extends account implements employeeControls {

    protected employee(String aName, String password, String fName, int pLevel, int aNum) {
        super(aName, password, fName, pLevel, aNum);
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
        System.out.println("test");
    }
        
    @Override
    public void gCApplication() {

    }
}