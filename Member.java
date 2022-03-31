import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Member {
    public static void main(String[] args) throws Exception {
        if (args.length <2 ) {
            System.err.println("Pass the server IP memberId val delay");
            return;
        }
        try (var socket = new Socket(args[0], 59001)) {
            
            var in = new Scanner(socket.getInputStream());
            var out = new PrintWriter(socket.getOutputStream(), true);
            boolean readyFlag = false, proposal_accepted = false;
            int memberId = Integer.parseInt(args[1]), maxId=0, promiseCount=0,acceptedId=0, consensusCounter=0;
            String val = args[2], acceptedVal="";
            
            System.out.println("Member "+memberId+" is running");
            
           
            while(in.hasNextLine()) {

                var line = in.nextLine();

                // registering member with name to distinguish between proposer and acceptor at server
                if (line.startsWith("SUBMITNAME")) {
                    out.println(args[1]);
                }

                //proposers get READY signal 
                else if (line.startsWith("READY")) {
                    readyFlag = true;
                }

                // when PREPARE msg is recieved by acceptors
                else if (line.startsWith("PREPARE")&& memberId!=Integer.parseInt(line.split("~")[1])){
                    
                    int id = Integer.parseInt(line.split("~")[1]);
                    System.out.println("Prepare recieved  from : "+id+" @ member"+memberId);

                    if(id<=maxId){
                        System.out.println("fail"+" @ member"+memberId);
                    }

                    else{

                        maxId=id;
                        if(proposal_accepted==true){

                            out.println("PROMISE~"+maxId+"~"+acceptedId+"~"+acceptedVal);
                            System.out.println("PROMISE~"+maxId+"~"+acceptedId+"~"+acceptedVal+" @ member"+memberId);

                        }

                        else{

                            // inducing delay for proposers
                            Thread.sleep(Integer.parseInt(args[3]));
                            out.println("PROMISE~"+maxId);   
                            System.out.println("PROMISE~"+maxId+" @ member"+memberId);                         
                        }
                        
                    }
                }

                // when PROMISE msg is recieved by proposers
                else if (line.startsWith("PROMISE")&& memberId==Integer.parseInt(line.split("~")[1])){                    
                    promiseCount++;

                    if(line.split("~").length>2){
                        val = line.split("~")[3];
                    }

                    // if promise is attained from majority
                    if(promiseCount>=5){
                            System.out.println("majority promises recieved"+" @ member"+memberId);
                            System.out.println("PROPOSE~"+memberId+"~"+val);
                            out.println("PROPOSE~"+memberId+"~"+val);
                            promiseCount=0;
                    }
                }

                // when PORPOSE msg is recieved by acceptors
                else if (line.startsWith("PROPOSE")&& memberId!=Integer.parseInt(line.split("~")[1])){
                    int id = Integer.parseInt(line.split("~")[1]);
                    System.out.println("Propose recieved  from : "+id+" @ member"+memberId);

                    if(id==maxId){

                        proposal_accepted=true;
                        acceptedId = id;
                        acceptedVal = line.split("~")[2];
                        System.out.println("accepted : "+acceptedId+" : "+acceptedVal+" @ member"+memberId);
                        out.println("ACCEPTED~"+acceptedId+"~"+acceptedVal);

                    }

                    else{

                        System.out.println("fail"+" @ member"+memberId);
                    }

                }

                // when ACCEPTED msg is recieve by proposers
                else if(line.startsWith("ACCEPTED")&& memberId==Integer.parseInt(line.split("~")[1])){

                    consensusCounter++;
                    if(consensusCounter>=5){

                        System.out.println("Consenus reached for value : "+ line.split("~")[2]+" with id : "+Integer.parseInt(line.split("~")[1])+"\n");
                        consensusCounter=0;
                    }
                }

                // initialting proposal with PREPARE msg
                if(readyFlag){
                out.println("PREPARE~"+memberId);
                readyFlag=false;
                maxId=0;
                System.out.println("Prepare msg sent from : "+memberId);
                }
            }
        }
    }
}

