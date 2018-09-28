package com.revature;

class employee extends account implements employeeControls {

    protected employee(String aName, String fName, String lName, int pLevel, double cFunds) {
        super(aName, fName, lName, pLevel, cFunds);
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