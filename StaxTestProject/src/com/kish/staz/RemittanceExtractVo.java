package com.kish.staz;

public class RemittanceExtractVo
{
    private String raNumber=null;
    private String tradingPartnerId=null;
    private String fileName=null;
    private String txnType=null;
    public String getRaNumber()
    {
        return raNumber;
    }
    public void setRaNumber(String raNumber)
    {
        this.raNumber = raNumber;
    }
    public String getTradingPartnerId()
    {
        return tradingPartnerId;
    }
    public void setTradingPartnerId(String tradingPartnerId)
    {
        this.tradingPartnerId = tradingPartnerId;
    }
    public String getFileName()
    {
        return fileName;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    public String getTxnType()
    {
        return txnType;
    }
    public void setTxnType(String txnType)
    {
        this.txnType = txnType;
    }

}
