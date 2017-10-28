import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.lang.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; 
 
public class Client extends JFrame{
	JPanel main,p1,p2;
	//JProgressBar progress;
		JLabel l1,l2,sended;
		JTextField textField,ip;
		JButton browseBtn,sendBtn,Connect,Disconnect;
		Socket socket;
		Client()
		{
			sended=new JLabel("No progress");
			//sended.setText("Process going on");
			setTitle("Client");
			setLocation(300,10);
			setSize(400,400);
			setResizable(false);
			main=new JPanel(new GridLayout(2,1));
			p1=new JPanel();
			l1=new JLabel("File");
			ip=new JTextField(10);
			l2=new JLabel("IP address ");
			textField=new JTextField(20);
			browseBtn=new JButton("Browse");
			//Connect=new JButton("Connect");
			//Disconnect=new JButton("Disconnect");
			p1.add(l1);
			p1.add(textField);
			p1.add(browseBtn);
			//p1.add(Connect);
			//p1.add(Disconnect);
			p1.add(new JLabel("Progress : "));
			p1.add(sended);
			main.add(p1);
			sendBtn=new JButton("Send");
			p2=new JPanel();
			p2.add(l2);
			p2.add(ip);
			p2.add(sendBtn);
			/*			progress=new JProgressBar(0,100);
				progress.setValue(0);
				progress.setStringPainted(true);
				progress.setBounds(40,40,160,30);
main.add(progress);   */     
			main.add(p2);
			add(main);

			
			/*Connect.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					try{
					socket = new Socket(ip.getText(),3332);
					}
					catch(Exception e)
					{}
				}
			});
			
			Disconnect.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					try{
					//socket.close();
					}
					catch(Exception e)
					{}
				}
			});
			*/
			browseBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				//send("localhost",3332,textField.getText());
				JFileChooser fileChooser=new JFileChooser();
        fileChooser.showOpenDialog(browseBtn);
        String str=fileChooser.getSelectedFile().getAbsolutePath();
        textField.setText(str);
			}
		});
			//sendBtn=new JButton("Send");
			sendBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				System.out.println("Send started");
				String file_name=textField.getText();
				try{
			String fileName = null;
        //String file_name = scanner.nextLine();
          
        File file = new File(file_name);
        socket = new Socket(ip.getText(),3332);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
 
        oos.writeObject(file.getName());
 
        FileInputStream fis = new FileInputStream(file);
        byte [] buffer = new byte[100];
        Integer bytesRead = 0;
				int size=(int)file.length();
				int cur=0;
				int percentage=0;
				sended.setText("0");
        while ((bytesRead = fis.read(buffer)) > 0) {
						cur+=bytesRead;
						percentage=(cur*100)/size;
						System.out.println("Current read "+cur+ "Size "+size+"  Completed "+percentage);
            //progress.setValue(percentage);
						//progress.setStringPainted(true);
						//if(percentage==25||percentage==50||percentage==75)
						sended.setText(Integer.toString(percentage));
						oos.writeObject(bytesRead);
            oos.writeObject(Arrays.copyOf(buffer, buffer.length));
        }
				sended.setText("Completed");
				oos.close();
        ois.close();
				 //progress.setValue(0);
					//	progress.setStringPainted(true);
				System.out.println("Send completed");
				Thread.sleep(2000);
				sended.setText("No Progress");
				//System.exit(0);
			}
			catch(Exception e)
			{
				
			}
			}
			});
			
		}
		/*public static void send(String file_name)
		{
			
			finally
			{
        
       //     
			}
			}
	*/
    public static void main(String[] args) throws Exception {
        Client client=new Client();
        client.setVisible(true);
 
}
}  