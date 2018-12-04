package ECB18S1;
import java.io.*;
import java.util.*;

public class ProcessFile {

	 private File inputFile;
	 private File instruction; 
	 private File outputFile;
	 private File reportFile;
	  
	 private RecordFile recordList;
	   
	 public ProcessFile(String[]f){
	 inputFile = new File(f[0]);
	 instruction = new File(f[1]);
	 outputFile = new File(f[2]);
	 reportFile = new File(f[3]);
	  
	 recordList = new RecordFile();
	  }	  
	   
	public void readContact(){ // reading existing contact record file
	  try{
	   Scanner fileScan = new Scanner(inputFile);
	   String dataStr = "" ;
	     while(fileScan.hasNextLine()) { // Reading a line a time.
			String ContactLine = fileScan.nextLine();
			 
			  if(!ContactLine.isEmpty()){
				  Scanner lineScan = new Scanner(ContactLine);
					 String keyWordIn = lineScan.next();
			   if(keyWordIn.equals("name")||keyWordIn.equals("birthday")
				||keyWordIn.equals("phone")||keyWordIn.equals("address")
				||keyWordIn.equals("email")){
				    if(dataStr.equals("")){
				    	dataStr = ContactLine;
				      }else{
			    	  dataStr += ";"+ContactLine;}
			        }else {
			        	dataStr += ContactLine;}
			      lineScan.close();
		         }else{ 
		            	// once line is empty, print out the whole dataStream
		           recordList.addRec(dataStr);
		           dataStr="";//clean the dataStream to read next record
		         } 
			  
	       }
	       //System.out.println(dataStream); // print out the last record(has no empty line at the end)
	       recordList.addRec(dataStr);
	      fileScan.close();    
      }catch(FileNotFoundException a){
    	  a.printStackTrace();
     }
   }
	
	public void readInst(){  // reading instruction file.
		  try{
		   Scanner instructionScan = new Scanner(instruction);
		   boolean hasReport = false;
		   
		     while(instructionScan.hasNextLine()) { // reading one line at a time
				String instruLine = instructionScan.nextLine();
				Scanner sc = new Scanner(instruLine);
				String keyword, queryword, param;
				
				  if(sc.hasNext()){
					 keyword = sc.next();
				
			           if(sc.hasNextLine()){
					       			            
					       if (keyword.equals("add")){  // addcontact
					    	   param= sc.nextLine();
					    	   recordList.addRec(param); 
					       } else if (keyword.equals("delete")){
					    	   param= sc.nextLine();
					    	   recordList.delRec(param); // deleteContact
					       } else if (keyword.equals("query")){
					    	   queryword = sc.next();
					    	   param =sc.nextLine();
					    	   recordList.searchRec(queryword,param);
					    	   hasReport = true;
					       }
			            }else{ 
			            	if (keyword.equals("save")){
			            		saveResult();
			            		
			            		if(hasReport==true){
			            		saveReport();
			            		}
			            	}
			            	continue;} //No parameters
			           sc.close();
		           }else { // When the line is empty.
	                     continue;
	                	}
	                }
		      instructionScan.close();    
	      }catch(FileNotFoundException c){
	    	  c.printStackTrace();
	      }
	   }
	
	public void saveResult(){ // outputs to file
		try{
			PrintWriter output = new PrintWriter(new FileOutputStream(outputFile));
		    for(ContactInfo r: recordList.getRecordList()){
		    	
			    	if (r.getName().isValidName()){
			    	output.print(" name:"+ r.getName().getFullName() + "\n");}
			    	if (r.getBirthDay().isValidBirthday()){
			    		output.print(" birthday:"+r.getBirthDay().dateString()+ "\n");}
			    	if(r.getAddress()!=null){
			    		output.print(" address:" + r.getAddress()+ "\n");}
			    	if(r.getPhoneNumber()!=null){
			    		output.print(" phone:"+ r.getPhoneNumber()+ "\n");}
				    if(r.getEmail()!=null){
				    	output.print(" email:"+r.getEmail()+ "\n");}  	
			        
				    if(r.getQDiv()!=null){
				    	output.print(r.getQDiv());
				    }else{output.println("");}		    
		    }	 	    
		    	    	
			output.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public void saveReport(){ // output to file
		try{
			PrintWriter reportOut = new PrintWriter(new FileOutputStream(reportFile));
			for(ContactInfo r: recordList.getReportList()){
		    	
		    	if (r.getName().isValidName()){
		    	reportOut.print(" name:"+ r.getName().getFullName()+ "\n");}
		    	if (r.getBirthDay().isValidBirthday()){
		    	reportOut.print(" birthday:"+r.getBirthDay().dateString()+ "\n");}
		    	if(r.getAddress()!=null){
		    	reportOut.print(" address:" + r.getAddress()+ "\n");}
		    	if(r.getPhoneNumber()!=null){
		    	reportOut.print(" phone:"+ r.getPhoneNumber()+ "\n");}
			    if(r.getEmail()!=null){
			    reportOut.print(" email:"+r.getEmail()+ "\n");}  	
		        
			    if(r.getQDiv()!=null){
			    reportOut.print(r.getQDiv());
			    }else{reportOut.println("");}		    
	    }	    	
			reportOut.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public RecordFile getRecordList(){
		return recordList;
	}
	
}
