import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.List;


public class Server {
    int i=0;
    
    private static List<String> names = new ArrayList<String>();
    private static List<PrintWriter> writers =  new ArrayList<PrintWriter>();


    public static void main(String[] args) throws Exception {

        // assigning threads to each members
        System.out.println("The server is running...");
        var pool = Executors.newFixedThreadPool(500);
        try (var listener = new ServerSocket(59001)) {
            while (true) {
                pool.execute(new Handler(listener.accept(), args));
            }
        }
    }

    // handler for memebers
    private static class Handler implements Runnable {
        private String name;
        private Socket socket;
        private Scanner in;
        private PrintWriter out;
        private String[] args;

        // constrcutor
        public Handler(Socket socket, String[] args) {
            this.socket = socket;
            this.args = args;
        }

        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                // Keep requesting a name until we get a unique one.
                while (true) {
                    out.println("SUBMITNAME");
                    name = in.nextLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!name.isBlank() && !names.contains(name)) {
                            names.add(name);
                            break;
                        }
                    }
                }

                // add writers to list
                writers.add(out);
                // if all memebers have joined
                if(names.size()==9){

                    int k =0;
                    if(args[0].equals("test1")){

                        for(String name : names){
                            if(name.equals("1") || name.equals("2")|| name.equals("3")){
                                //senting ready msg to proposers
                                writers.get(k).println("READY");
                            }
                            k++;

                        }  
                    }

                    else if(args[0].equals("test2")){
                        for(String name : names){
                            if(name.equals("1") || name.equals("2")|| name.equals("3")){
                                //senting ready msg to proposers
                                writers.get(k).println("READY");
                                // delaying 
                                Thread.sleep(5000); 
                            }
                            k++;

                        }  
                    }
                    
                }

                // Accept messages from this client and broadcast them.
                while (true) {

                    String input = in.nextLine();
                    for (PrintWriter writer : writers) {
                        writer.println(input);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (out != null) {
                    writers.remove(out);
                }
                if (name != null) {
                    System.out.println(name + " is leaving");
                    names.remove(name);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}