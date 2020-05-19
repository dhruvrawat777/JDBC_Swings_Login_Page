import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
	
	JLabel l1=new JLabel("User name");
	JLabel l2 =new JLabel("Password");
	JTextField tf1=new JTextField(20);
	JPasswordField pf1=new JPasswordField(20);
	JButton b1=new JButton("Login");
	JButton b2=new JButton("New User");
	
	public Login()
	{
		setTitle("Login Page");
		add(l1);
		add(tf1);
		add(l2);
		add(pf1);
		add(b1);
		add(b2);
		setSize(300,300);
		setLayout(new FlowLayout());
		setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e)
	{
		String usr=tf1.getText();
		String pswd=new String(pf1.getPassword());
		if(e.getSource()==b1)
		{
			if(dbcheck(usr,pswd)==1)
			{
				JOptionPane.showMessageDialog(this,"Login successful");
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Login Failed");
			}
			
		}
		else if(e.getSource()==b2)
		{
			if(adduser(usr,pswd)==1)
			{
				JOptionPane.showMessageDialog(this,"New User created successfully");
			}
			else
			{
				JOptionPane.showMessageDialog(this,"User not created");
			}
		}
	}
	public int adduser(String usr,String pswd)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","root");
			Statement st=con.createStatement();
			//String q="insert into usrpass values('"+usr+"','"+pswd+"')";
			String q="insert into usrpass(username,password) "+"values(?,?)";
			PreparedStatement mystm=con.prepareStatement(q);
			mystm.setString(1,usr);
			mystm.setString(2,pswd);
			int rowaffected=mystm.executeUpdate();
			if(rowaffected==1)
				return 1;
			else
				return 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	public int dbcheck(String usr,String pswd)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","root");
			Statement st=con.createStatement();
			String q="Select * from usrpass where username='"+usr+"' and password='"+pswd+"'";
			ResultSet rs=st.executeQuery(q);
			if(rs.next())
			{
				return 1;
			}
			else
			{
				return 0;
			}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	public static void main(String[] args) {
		
		Login l1=new Login();
		
	}

	
	
}
