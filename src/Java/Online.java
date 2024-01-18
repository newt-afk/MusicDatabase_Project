package Java;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Online {
    private static final Logger LOGGER = Logger.getLogger(Online.class.getName());
    private static final List<Thread> allRunningServer = new LinkedList<>();
    private static Thread genServerThread(Music m) {
        return new Thread(() -> {
            try (ServerSocket serv = new ServerSocket(900)) {
                LOGGER.info("Server listening to port " + serv.getLocalPort() + " at address " + serv.getInetAddress());
                serv.setSoTimeout(1000);
                while (!Thread.interrupted()) {
                    try {
                        new ServerHandler(m, serv.accept()).start();
                    }catch (SocketTimeoutException ignored) {}
                }
            }catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Could not serve music", e);
            }
        });
    }
    private static class ServerHandler extends Thread {
        private static final Logger LOGGER = Logger.getLogger(ServerHandler.class.getName());
        Music music;
        DataOutputStream dataout;
        ServerHandler(Music music, DataOutputStream dataout) {
            this.music = music;
            this.dataout = dataout;
        }
        ServerHandler(Music music, Socket sock) {
            this.music = music;
            try {this.dataout = new DataOutputStream(sock.getOutputStream());}
            catch (IOException io) {
                LOGGER.log(Level.SEVERE, "Could not connect to client", io);
                this.dataout = null;
            }
        }
        public void run() {
            int bytes;
            File file = music.getFile();
            try (FileInputStream fileIn = new FileInputStream(file)) {
                if (dataout == null) return;
                dataout.writeUTF(music.getName());
                dataout.writeUTF(music.getArtist());
                dataout.writeUTF(music.getGenre());
                dataout.writeLong(file.length());
                dataout.flush();
                byte[] buffer = new byte[4096];
                while ((bytes = fileIn.read(buffer)) != -1) {
                    dataout.write(buffer, 0, bytes);
                    dataout.flush();
                }
            }catch (IOException io) {
                LOGGER.log(Level.SEVERE, "Couldn't serve file", io);
            }
        }
    }
    public static void serveMusic(Music music) {
        allRunningServer.add(genServerThread(music));
    }
    public static void getMusic(String hostName, int portNumber) {
        try (Socket sock = new Socket(hostName, portNumber)){
            int bytes;
            DataInputStream dataIn = new DataInputStream(sock.getInputStream());
            String musicName = dataIn.readUTF(), artist = dataIn.readUTF(), genre = dataIn.readUTF();
            try (FileOutputStream fileOut = new FileOutputStream(FileManager.musicPath + musicName + ".mp3")){
                long size = dataIn.readLong();
                byte[] buffer = new byte[4096];
                while (size > 0 && (bytes = dataIn.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                    fileOut.write(buffer, 0, bytes);
                    size -= bytes;
                }
            }
        } catch (UnknownHostException e) {
            LOGGER.log(Level.SEVERE, "IP address of " + hostName + " could not be determined.", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not get music from " + hostName, e);
        }
    }
    public static void kill() {
        for (Thread t: allRunningServer) t.interrupt();
    }
}
