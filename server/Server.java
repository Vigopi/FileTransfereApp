import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;

public class Server extends JFrame implements Runnable {
    public static final int PORT = 3332;
    public static final int BUFFER_SIZE = 100;
		String path="";
		String file="";
		boolean connected=false;
		boolean closed=true;
		JPanel p1,p2,p3;
		JLabel main,l1,l2,l3,l4,l5,l6,l7;
		JButton browse;
		/*File dir;
		void setFile(File f)
			{
				dir=f;
			}*/
		Server()
		{
			
		String path;
			path=".";
			setTitle("Server");
			setLocation(10,10);
			setSize(400,400);
			setResizable(true);
			p1=new JPanel(new GridLayout(2,1));
			p2=new JPanel();
			p3=new JPanel(new GridLayout(4,2));
			main=new JLabel("Server");
			p2.add(main);
			p1.add(p2);
			l1=new JLabel("Status : ");
			try
			{
			l2=new JLabel("Server running at "+(Inet4Address.getLocalHost().getHostAddress()));
			l3=new JLabel("Server ");
			l4=new JLabel("No client ");
			l5=new JLabel("Files recived");
			l6=new JLabel("No files recived");
			l7=new JLabel("");
			p3.add(l1);
			p3.add(l2);
			p3.add(l3);
			p3.add(l4);
			p3.add(l5);
			p3.add(l6);
			}
			catch(Exception e)
			{}
			
			try{
			Enumeration ee = NetworkInterface.getNetworkInterfaces();
while(ee.hasMoreElements())
{
    NetworkInterface n = (NetworkInterface) ee.nextElement();
    Enumeration eee = n.getInetAddresses();
    while (eee.hasMoreElements())
    {
        InetAddress i = (InetAddress) eee.nextElement();
        System.out.println(i.getHostAddress());
    }
}
		
			}
			catch(Exception e)
			{}
			browse=new JButton("Download Location");
			p3.add(browse);
			p3.add(l7);
			p1.add(p3);
			add(p1);
			
			browse.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					JFileChooser j = new JFileChooser();
					j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					Integer opt = j.showOpenDialog(browse);
					
					if(opt == JFileChooser.APPROVE_OPTION) {
						System.out.println("You chose to open this directory: " +j.getSelectedFile().getAbsolutePath());
					//setFile(j.getSelectedFile());
				final String path2=j.getSelectedFile().getAbsolutePath();
					l7.setText(path2);
					/*String p=path2;
					String path="";
					for(int i=0;i<p.length();i++)
			{
				if(p.charAt(i)=='\\')
					path+="\\";
				path+=p.charAt(i);
				
			}*/
			setPath(path2);
			System.out.println(path2);
			
				}
				}
			});
			
					try
					{
			//Server s=new Server(path);
			
			//s.start();
			/*while(true)
					{
						if(s.isConnected())
							l4.setText("Client connected");
						else
							l4.setText("Client disconnected");
					}*/
					}
					catch(Exception e)
					{}
				
			/*browse.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					JFileChooser j = new JFileChooser();
					j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					Integer opt = j.showOpenDialog(browse);
					
					if(opt == JFileChooser.APPROVE_OPTION) {
						System.out.println("You chose to open this directory: " +j.getSelectedFile().getAbsolutePath());
					path=j.getSelectedFile().getAbsolutePath();
					System.out.println(path);
					
					s.destroy();
					final Server s1=new Server(path);
					s1.start();
				}
			}
			});*/
		}
		private void setPath(String p)
		{
			path=p;
		}

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
						//System.out.println(Inet4Address.getLocalHost().getHostAddress());
            while (true) {
                Socket s = serverSocket.accept();
								if(s.isConnected())
								{
									l4.setText("Client connected");
									saveFile(s);
								}
								//if(s.isClosed())
								else	
								{
									l4.setText("Client disconnected");
									
								} 
            }
        } catch (Exception e) {
            e.printStackTrace();
						
        }
    }
    private void saveFile(Socket socket) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        FileOutputStream fos = null;
        byte [] buffer = new byte[BUFFER_SIZE];
 
        // 1. Read file name.
        Object o = ois.readObject();
 
        if (o instanceof String) {
					file=o.toString();
            //fos = new FileOutputStream(path+"\\"+o.toString());
						fos = new FileOutputStream(path+"/"+o.toString());
						l6.setText(o.toString());
        } else {
            throwException("Something is wrong");
        }
 
        // 2. Read file to the end.
        Integer bytesRead = 0;
 
        do {
            o = ois.readObject();
 
            if (!(o instanceof Integer)) {
                throwException("Something is wrong");
            }
 
            bytesRead = (Integer)o;
 
            o = ois.readObject();
 
            if (!(o instanceof byte[])) {
                throwException("Something is wrong");
            }
 
            buffer = (byte[])o;
 
            // 3. Write data to output file.
            fos.write(buffer, 0, bytesRead);
           
        } while (bytesRead == BUFFER_SIZE);
         
        System.out.println("File transfer success");
         
        fos.close();
 
        ois.close();
        oos.close();
    }
	public String getFile()
	{
		return file;
	}
    public static void throwException(String message) throws Exception {
        throw new Exception(message);
    }
		
		public static void main(String[] args) {
				Server server=new Server();
        server.setVisible(true);
				try{
				Thread th1=new Thread(server);
				th1.start();
				th1.join();
				}
				catch(Exception e)
				{}
				
    }
}  
/*
public class Server1 extends JFrame
{
	public static void main(String[] args) {
				Server server=new Server();
        server.setVisible(true);
				try{
				Thread th1=new Thread(server);
				th1.start();
				th1.join();
				}
				catch(Exception e)
				{}
				
    }
		
		
}*/