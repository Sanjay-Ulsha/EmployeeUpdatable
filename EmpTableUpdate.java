import java.awt.*;
import java.awt.event.*;
import java.sql.*;
class EmpTableUpdate implements ActionListener
{ 
 TextField t1,t2,t3,t4;
 Button b1,b2,b3,b4,b5; 
 Frame f; Label l;
 ResultSet rs; Connection con; Statement st;
 EmpTableUpdate()
  {
		f=new Frame("Dept NutShell");
		f.setVisible(true);
		f.setSize(288,351);
		f.setBackground(Color.yellow);
		f.setLayout(new FlowLayout());
		
	    t1=new TextField(18); //t1.addTextListener(this);
	 	t2=new TextField(18); //t2.addTextListener(this);
		t3=new TextField(18); //t3.addTextListener(this);
		t4=new TextField(18);
		f.add(new Label("DeptNo*:")); f.add(t1);
		f.add(new Label("DName*:")); f.add(t2); 
		f.add(new Label("   Dloc*: ")); f.add(t3);
		f.add(new Label("RecMark:")); f.add(t4);
		b1=new Button(" Insert"); b1.addActionListener(this);
		b2=new Button("Update"); b2.addActionListener(this);
		b3=new Button("Delete"); b3.addActionListener(this);
		b4=new Button("Find"); b4.addActionListener(this);
		f.add(b1); f.add(b2); f.add(b3); f.add(b4);
		b5=new Button("Clear"); b5.addActionListener(this); f.add(b5);
	
		f.add(new Label("'*'->MandatoryFields for Insert"));
		f.add(new Label("DeptNo->Mandatory for Update,Delete,Find"));
	        l=new Label("Welcome"); f.add(l);
	f.addWindowListener(new WindowAdapter()
                                { 
                                   public void windowClosing(WindowEvent w)
                                   { 
                                     System.exit(1); 
                                     try
		                      {
		                        if(con!=null) con.close();
		                        if(st!=null)  st.close();
	                              }
                                      catch(Exception e3)
      	                              {  e3.printStackTrace();  System.out.println("In addWindowListener \n caught_3"); }
                                   }//windowClosing
                                 }//WindowAdapter
                            );
                 
    }//sanju()
	
public static void main(String...n)
{ 	
     EmpTableUpdate N=new EmpTableUpdate(); 
      try
      { 
       Class.forName("oracle.jdbc.driver.OracleDriver");
	   N.con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
	   N.st=N.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       N.rs=N.st.executeQuery("select deptno,dname,loc from dept");
      }
      catch(Exception e1)
      { e1.printStackTrace(); System.out.println("In Main \n caught_1"); }	 
}//main

  /*public void textValueChanged(TextEvent a){  }*/

  public void actionPerformed(ActionEvent b)
 {  f.remove(l); 
 	 try
	 { rs=st.executeQuery("select deptno,dname,loc from dept");
        if(b.getActionCommand().equals(" Insert"))
	    {  //t4.setText(""); 
	       /*if((t2.getText().equals("No_Value") || t2.getText().equals("")) && (t3.getText().equals("No_Value") || t3.getText().equals(""))) 
		  { rs.moveToInsertRow();
		    rs.updateInt(1,Integer.parseInt(t1.getText())); 
		   t4.setText("Rec Can't Be Inserted"); l.setText("Coz->No Dname,Dloc"); f.add(l); 
		   } */
          	  rs.moveToInsertRow();
		      rs.updateInt(1,Integer.parseInt(t1.getText())); rs.updateString(2,t2.getText()); 
		      rs.updateString(3,t3.getText()); 
		      rs.insertRow(); t4.setText("Rec Created");
		      l.setText("Rec->>Inserted"); f.add(l);
	    }
        
	   else if(b.getActionCommand().equals("Update"))
	    {  //t4.setText("");
		  int i=Integer.parseInt(t1.getText()); //rs=st.executeQuery("select deptno,dname,loc from dept");
		   while(rs.next())
		  {
			if(rs.getInt(1)==i)
		    { 
		      if(!t2.getText().equals("")) rs.updateString(2,t2.getText()); 
			  if(!t3.getText().equals("")) rs.updateString(3,t3.getText()); 
			  rs.updateRow(); t4.setText("Rec Updated"); l.setText("Coz->>Rec present"); f.add(l); break; 
			}
		  }
		  if(rs.isAfterLast())
		  { t2.setText("No_Value"); t3.setText("No_Value"); t4.setText("Rec Not Found"); l.setText("Coz->>No Such Rec's present"); f.add(l); }
	    }
       
  	    else if(b.getActionCommand().equals("Delete"))
	    {  //t4.setText("");
			int i=Integer.parseInt(t1.getText()); //rs=st.executeQuery("select deptno,dname,loc from dept");
	      while(rs.next())
		  {
			  if(rs.getInt(1)==i)
			  { t2.setText(rs.getString(2)); t3.setText(rs.getString(3)); rs.deleteRow(); t4.setText("RecDeleted"); l.setText("Coz->>Rec present"); f.add(l); break; }
		  }
		  if(rs.isAfterLast())
		  { t2.setText("No_Value"); t3.setText("No_Value"); t4.setText("Rec Not Found"); l.setText("Coz->>No Such Rec's present"); f.add(l); }
		}
	   
	    else if(b.getActionCommand().equals("Find"))
	    { // t4.setText(""); 
	       int i=Integer.parseInt(t1.getText()); //rs=st.executeQuery("select deptno,dname,loc from dept"); 
	      while(rs.next())
		  {
			  if(rs.getInt(1)==i)
			  { t2.setText(rs.getString(2)); t3.setText(rs.getString(3)); t4.setText("Rec Found"); l.setText("Coz->>Rec present"); f.add(l); break;}
		  }
		  if(rs.isAfterLast())
		  { t2.setText("No_Value"); t3.setText("No_Value"); t4.setText("Rec Not Found"); l.setText("Coz->>No Such Rec's present"); f.add(l); }
	    }
        
		else if(b.getActionCommand().equals("Clear"))
	    {
		  t1.setText(""); t2.setText(""); t3.setText(""); t4.setText(""); //rs=st.executeQuery("select deptno,dname,loc from dept");
	    }
	 }//try
      catch(SQLException e2)
	 { l.setText("SQLException->UniqueConstraint");
        e2.printStackTrace();	t4.setText("can'tBeInserted->AlreadyExists"); f.add(l); System.out.println("In actionPerformed \n caught_2");
	 }//catch
	 catch(NumberFormatException e2)
	 { l.setText("NumberFormatException->NoDeptno");
        e2.printStackTrace();	t4.setText("Enter Valid Deptno"); f.add(l); System.out.println("In actionPerformed \n caught_2");
	 }//catch
	  catch(Exception e2)
	 { 
        e2.printStackTrace(); System.out.println("In actionPerformed \n caught_2");
	 }//catch
 }//actionPerformed
}//class