package laba8.server;

import laba8.pi.CMethods;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Permission;
import java.util.logging.Logger;

public class WebServer extends JFrame {

    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

    private final JFileChooser fileChooser = new JFileChooser();
    private JPanel rootPanel;
    private JTextField portTextField;
    private JTextArea textArea;
    private Registry registry;
    private RegMember participants;
    private static final  String NAME = "Registerable";
    private JLabel labelCont;

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager(){
                public void checkConnect(String host, int port, Object context) { }
                public void checkConnect(String host,int port) { }
                public void checkPermission(Permission perm) { }
            }); }
        System.setProperty("java.rmi.server.codebase", "http://localhost:80/RMI/ http://localhost:80/RMI/client.jar");

        EventQueue.invokeLater(() -> {
            try {
                WebServer frame = new WebServer();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public WebServer() throws HeadlessException, UnknownHostException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Server");
        setBounds(100, 100, 800, 600);
        fileChooser.setCurrentDirectory(new java.io.File("."));

        CMethodsImpl clientMethods = new CMethodsImpl();
        participants = clientMethods.getParticipants();

        participants.addDRegisteredParticipantChangeListener(e ->{
            textArea.setText(participants.toString());
            labelCont.setText(String.valueOf(participants.size()));
        });


        //root panel
        rootPanel = new JPanel(new BorderLayout(0,0));
        setContentPane(rootPanel);

        //Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JToggleButton startButton = new JToggleButton("Start");
        JToggleButton stopButton = new JToggleButton("Stop");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton exitButton = new JButton("Exit");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(startButton);
        buttonGroup.add(stopButton);
        buttonGroup.setSelected(stopButton.getModel(), true);

        startButton.addActionListener(e-> {
            try {
                CMethods stub = (CMethods) UnicastRemoteObject.exportObject(clientMethods, Integer.parseInt(portTextField.getText()));
                registry = LocateRegistry.createRegistry(Integer.parseInt(portTextField.getText()));
                LOG.info(InetAddress.getLocalHost().toString());
                registry.rebind(NAME, stub);
                LOG.info("Server is ready to work");
                JOptionPane.showMessageDialog(null, "Server is ready to work","Server is ready to work", JOptionPane.INFORMATION_MESSAGE);

            } catch (RemoteException | UnknownHostException ex) {
                ex.printStackTrace();
            }
        });

        stopButton.addActionListener(e->{
            try {
                registry.unbind(NAME);
                UnicastRemoteObject.unexportObject(clientMethods,true);
                LOG.info("server si stopped");
                JOptionPane.showMessageDialog(null, "Server si stopped","Server si stopped", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException | NotBoundException remoteException) {
                remoteException.printStackTrace();
            }
        });

        saveButton.addActionListener(e->save());
        loadButton.addActionListener(e ->load(clientMethods));
        exitButton.addActionListener(e-> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(exitButton);
        rootPanel.add(buttonPanel,BorderLayout.SOUTH);

        //data panel
        FlowLayout dataPanelFlowLayout = new FlowLayout();
        dataPanelFlowLayout.setAlignment(FlowLayout.CENTER);
        dataPanelFlowLayout.setHgap(10);
        dataPanelFlowLayout.setVgap(10);
        JPanel dataPanel = new JPanel(dataPanelFlowLayout);
        portTextField = new JTextField("1049",10);
        JLabel hostLabel = new JLabel("host : " + InetAddress.getLocalHost()+" ");
        dataPanel.add(hostLabel);
        dataPanel.add(new JLabel("port : "));
        dataPanel.add(portTextField);
        dataPanel.add(new JLabel("count : "));
        labelCont = new JLabel(String.valueOf(participants.size()));
        dataPanel.add(labelCont);
        rootPanel.add(dataPanel,BorderLayout.NORTH);

        //add Test area
        textArea = new JTextArea();
        Border border = textArea.getBorder();
        Border margin = new EmptyBorder(20,20,20,20);
        textArea.setBorder(new CompoundBorder(border, margin));
        JScrollPane scrollPane = new JScrollPane(textArea);
        rootPanel.add(scrollPane,BorderLayout.CENTER);
    }

    private void load(CMethodsImpl clientMethods) {
        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
            String fileName = fileChooser.getSelectedFile().getPath();
            try {
                participants = RegMemberSaveToXml.unmarshalOutXMLs(fileName);
                clientMethods.setParticipants(participants);
            } catch (JAXBException jaxbException) {
                jaxbException.printStackTrace();
            }
        }
    }

    private void save() {
        if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
            String path = fileChooser.getSelectedFile().getPath();
            try {
                String fileName = fileChooser.getSelectedFile().getName();
                String[] st = fileName.trim().split("\\.");
                if (st.length < 2){

                    String newFilename = fileName+".xml";
                    path = path.replace(fileName,newFilename);

                }else if(!st[st.length-1].equals("xml")){
                    path = path.replace(st[st.length-1],"xml");
                }
                RegMemberSaveToXml.marshalDataToXML(path,participants);
                JOptionPane.showMessageDialog(null,
                        "File " + path.trim() + " saved!", "Результаты сохранены",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (JAXBException jaxbException) {
                JOptionPane.showMessageDialog(null,
                        "File " + path.trim() + "not saved, error!!", "Результаты не сохранены",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
