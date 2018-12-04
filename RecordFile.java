package ECB18S1;
import java.util.*;

public class RecordFile {
	private ArrayList<ContactInfo> rec_file;
	private ArrayList<ContactInfo> rep_file;
	
	public RecordFile(){
		rec_file = new ArrayList<ContactInfo>();
		rep_file = new ArrayList<ContactInfo>();
	}
	
    public void addRecord(ContactInfo record){
		rec_file.add(record);
	}
    
    public void addRec(String s){
       ContactInfo m = new ContactInfo(s);
       
       if(m.getName().isValidName()&&m.getBirthDay().isValidBirthday()){
   
    	    int matchCount = 0;
    	    String n = m.getName().getFullName();
    	    String bd = m.getBirthDay().dateString();
    	    
    	    for(ContactInfo r: rec_file){
    	    	
				if(r.getName().getFullName().equals(n)
						&&r.getBirthDay().dateString().equals(bd)){
				matchCount++;		
				r.update(m);
				}
			}
    	   if (matchCount==0&&m.getAddress()!=null){
    		   rec_file.add(m);} 
       }   		  
    }
	
	public void delRec(String s){
		String[] dStr = s.trim().split("\\s*;\\s*");
		if (dStr.length==1)
		{
			  for(ContactInfo r: rec_file){
					if(r.getName().getFullName().equals(dStr[0]))
					{
						rec_file.remove(r);
					}
				}		
		}else if(dStr.length==2)
		{			
			BirthDay birthdelete = new BirthDay(dStr[1]); 
			for(int i=0;i<rec_file.size();i++){
				String tN= rec_file.get(i).getName().getFullName();
				String tB= rec_file.get(i).getBirthDay().dateString();
					if(tN.equals( dStr[0])&& tB.equals( birthdelete.dateString()))
					{
						rec_file.remove(i);
					}
				}		
		}	
	}
		
	public void searchRec(String a, String b){
		b=b.trim();
		ContactInfo d = new ContactInfo();
        d.setQDiv(a, b);
        rep_file.add(d);
		if(a.equals("name"))
		{        
			 for(ContactInfo r: rec_file)
			 {		 
	    	    	if(r.getName().getFullName().equals(b))
	    	    	{
	    	    		rep_file.add(r);
					} 
	    	    }	 
			 }
		if(a.equals("birthday"))
		{
			 for(ContactInfo r: rec_file){		 
	    	    	if(r.getBirthDay().dateString().equals(b))
	    	    	{
	    	    		rep_file.add(r);
					} 
	    	    }	
		}
		if(a.equals("phone")){
			 for(ContactInfo r: rec_file){
				 if(r.getPhoneNumber()!=null)
				 {
	    	    	if(r.getPhoneNumber().equals(b))
	    	    	{
	    	    		rep_file.add(r);
					} 
	    	    }
			 }
		}
		ContactInfo e = new ContactInfo();
		e.setEndQDiv(a, b);
		rep_file.add(e);
	}
	
	
	public ArrayList<ContactInfo> getRecordList(){
		return rec_file;
	}
	
	public ArrayList<ContactInfo> getReportList(){
		return rep_file;
	}
	
    public String toString(){
    	String a="";
    	for(ContactInfo r: rec_file){
	    	if (r.getName().isValidName())
	    	{
	    	a +="name:"+ r.getName().getFullName();
	    	}
	    	if (r.getBirthDay().isValidBirthday())
	    	{
	    		a+=" birthday:"+r.getBirthDay().dateString();
	    	}
	    	if(r.getAddress()!=null)
	    	{
	    		a+=" address:" + r.getAddress();
	    	}
	    	if(r.getPhoneNumber()!=null)
	    	{
	    		a+=" phone:"+ r.getPhoneNumber();
			}
		    if(r.getEmail()!=null)
		    {
		    	a+=" email:"+r.getEmail();
	    	}  	
	    }	 
    	return a;
    }
    
}
