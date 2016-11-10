package com.kish.staz;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.DatatypeConverter;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;

public class StaxEventTest
{

    /**
     * @param args
     */
    
 public static Logger log =Logger.getLogger(StaxEventTest.class);
    public static void main(String[] args) throws Exception
    {
        List<String> raNumberList = new ArrayList<String>();
        raNumberList.add("91993109");
        raNumberList.add("91993110");
        raNumberList.add("91993111");
        raNumberList.add("91993112");
        raNumberList.add("91993113");
        raNumberList.add("91993115");
        raNumberList.add("91993116");
        
        List<RemittanceExtractVo> edi835FileList = new ArrayList<RemittanceExtractVo>();
        Date date=new Date();
        log.info("Job Started"+log.getLevel());
        log.info("My Main");
        System.out.println(date.toString());
            Map<String,Integer> tradingPartnerIDMap = new ConcurrentHashMap<String,Integer>();
            Map<String,Integer> ediIDSequenceMap = new ConcurrentHashMap<String,Integer>();
            String pathToScan = "E:\\RA\\Electronic\\";
            String targetLocation="E:\\RA\\Electronic";
            File folderToScan = new File(pathToScan); 
            for( File ediFile : folderToScan.listFiles( new FilenameFilter()
            {
                
                @Override
                public boolean accept( File dir, String name ) { 
                    System.out.println("Name --->"+name);
                    return name.startsWith("EDI_835_" );
                }
             }))
            {
                System.out.println( ediFile.getName() );
                StringTokenizer splitFileName = new StringTokenizer(ediFile.getName(), "_.");
                String FILE_TYPE = splitFileName.nextToken();
                String FILE_TXN = splitFileName.nextToken();
                String RA_NUMBER = splitFileName.nextToken();
                String tradingPartnerID = splitFileName.nextToken();
                
                
                if(raNumberList.contains(RA_NUMBER))
               {
                    RemittanceExtractVo remittanceExtractVo=new RemittanceExtractVo();
                    remittanceExtractVo.setFileName(ediFile.getName() );
                    remittanceExtractVo.setTradingPartnerId(tradingPartnerID);
                    remittanceExtractVo.setRaNumber(RA_NUMBER);
                    remittanceExtractVo.setTxnType("835");
                    edi835FileList.add(remittanceExtractVo);
                    if(tradingPartnerIDMap.containsKey(tradingPartnerID)){                      
                        int retCount = (Integer) tradingPartnerIDMap.get(tradingPartnerID);                         
                        retCount++;
                        tradingPartnerIDMap.put(tradingPartnerID,retCount);     
                        System.out.println("RA_NUMBER-->"+RA_NUMBER);
                        System.out.println("TP_ID-->"+tradingPartnerID);
                        System.out.println("retCount-->"+retCount);
                   }else{
                       tradingPartnerIDMap.put(tradingPartnerID,1);
                       System.out.println("RA_NUMBER-->"+RA_NUMBER);
                       System.out.println("TP_ID-->"+tradingPartnerID);
                       System.out.println("retCount-->Constant"+1);
                   }  
               }
            }
            for (RemittanceExtractVo remittanceExtractVo : edi835FileList) {
                SimpleDateFormat simDateFormat  = new SimpleDateFormat("yyyyMMddHHmmSSS");
                    
                      String tradingPartnerId=remittanceExtractVo.getTradingPartnerId();
                        System.out.println("&&&&&&&&&&"+tradingPartnerId.length());
                        String str1=tradingPartnerId;
                         int temp = 15 - str1.length();               
                         for (int i = 0; i < temp; i++) {
                             //str = "0" + str;
                             StringBuilder sb1 = new StringBuilder();
                             str1 = sb1.append('0').append(str1).toString();
                             
                         }
                         int payeeCount=tradingPartnerIDMap.get(tradingPartnerId);
                         String str2=String.valueOf(payeeCount).trim();
                         int temp2 = 7 - str2.length();         
                         for (int i = 0; i < temp2; i++) {
                             //str2 = "0" + str2;
                             StringBuilder sb2 = new StringBuilder();
                             str2 = sb2.append('0').append(str2).toString();
                         }
                         System.out.println("EDI ID"+tradingPartnerId.concat(str2));
                         if(ediIDSequenceMap.containsKey(tradingPartnerId)){                      
                            int retCount= (Integer) ediIDSequenceMap.get(tradingPartnerId);                         
                             retCount++;
                             ediIDSequenceMap.put(tradingPartnerId,retCount);                         
                        }else{
                            ediIDSequenceMap.put(tradingPartnerId,1);
                        } 
                         int sequenceCount=ediIDSequenceMap.get(tradingPartnerId);
                         String str3=String.valueOf(sequenceCount).trim();
                         int temp3 = 7 - str3.length();         
                         for (int i = 0; i < temp3; i++) {
                             //str2 = "0" + str2;
                             StringBuilder sb2 = new StringBuilder();
                             str3 = sb2.append('0').append(str3).toString();
                         }
                         StringBuilder ediId = new StringBuilder();
                         ediId.append(str1).append(str2).append(str3);
                         StringBuilder fileName = new StringBuilder();
                         fileName.append(targetLocation).append("/").append(str1).append("_").append(str2).append("_").append(str3).append("_").append(simDateFormat.format(new Date())).append(".xml");
                         
                         
                XMLEventFactory m_eventFactory = XMLEventFactory.newInstance();
                XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(new java.io.FileInputStream(pathToScan+remittanceExtractVo.getFileName()));
               XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(new java.io.FileOutputStream(fileName.toString()));
        
               while (reader.hasNext()) {
                   XMLEvent event = (XMLEvent) reader.next();
                   
//                   if(event.getEventType() == event.CHARACTERS)
//                   {
//                       System.out.println(event.asCharacters());
//                       System.out.println(event.asCharacters().getData());
//                       System.out.println(event.);
//                   }
                   if (event.getEventType() == XMLEvent.START_ELEMENT && event.asStartElement().getName().getLocalPart().equals("applicationNumber")) 
                   {
                       writer.add(event);
                       event = (XMLEvent) reader.next();
                       writer.add(event);
                       event = (XMLEvent) reader.next();
                       writer.add(event);
                   StartElement codeSE = m_eventFactory.createStartElement("", "", "ediID");
                   writer.add(codeSE);
                 Characters codeChars = m_eventFactory.createCharacters(ediId.toString());
                 writer.add(codeChars);
                   
                    EndElement codeEE = m_eventFactory.createEndElement("", "", "ediID");
                 writer.add(codeEE); 
                   System.out.println("end element");
                        
              }
                   else   if (event.getEventType() == XMLEvent.START_ELEMENT && event.asStartElement().getName().getLocalPart().equals("checkOrEFTDate")) 
                       {
                       StartElement codeSE = m_eventFactory.createStartElement("", "", "checkOrEFTDate");
                       writer.add(codeSE);
                       Calendar c = Calendar.getInstance();
                       c.setTime(new Date());
                       String xmlDateTime = DatatypeConverter.printDate(c);
                     Characters codeChars = m_eventFactory.createCharacters(xmlDateTime);
                     writer.add(codeChars);
                       
                        EndElement codeEE = m_eventFactory.createEndElement("", "", "checkOrEFTDate");
                     writer.add(codeEE); 
                       System.out.println("end element");
                       event = (XMLEvent) reader.next();
                       event = (XMLEvent) reader.next();     
                  }
                  else
                    {
                          
                       writer.add(event);
                       }
               }
               writer.flush();
                }

            
       
    }
   
}
