import gui.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CpuUsageServiceClient {

    //Frame dataFrame = new Frame();
    private class settingServerConnection implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Socket socket = new Socket("192.168.1.13", 9999); //IP local just to test - Change to the IP Server
                DataOutputStream outputCpuData = new DataOutputStream(socket.getOutputStream()); //Output data Stream from CPU SERVER
                //outputCpuData.writeUTF(dataFrame.get);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


}
