# Paxos

A program that implements a Paxos voting protocol

## Scenario

This year, Suburbs Council is holding elections for council president. Any member of its nine person council is eligible to become council president.

* Member M1 – M1 has wanted to be council president for a very long time. M1 is very chatty over social media and responds to emails/texts/calls almost instantly. It is as if M1 has an in-brain connection with their mobile phone!

* Member M2 – M2 has also wanted to be council president for a very long time, except their very long time is longer than everybody else's. M2 lives in a remote part of the Suburbs and thus their internet connection is really poor, almost non-existent. Responses to emails come in very late, and sometimes only to one of the emails in the email thread, so it is unclear whether M2 has read/understood them all. However, M2 sometimes likes to work at Café @ Bottom of the Hill. When that happens, their responses are instant and M2 replies to all emails.

* Member M3 – M3 has also wanted to be council president. M3 is not as responsive as M1, nor as late as M2, however sometimes emails completely do not get to M3. The other councilors suspect that it’s because sometimes M3 goes on retreats in the woods at the top of the Suburbs, completely disconnected from the world.

* Members M4-M9 have no particular ambitions about council presidency and no particular preferences or animosities, so they will try to vote fairly. Their jobs keep them fairly busy and as such their response times  will vary.

How does voting happen: On the day of the vote, one of the councilors will send out an email/message to all councilors with a proposal for a president. A majority (half+1) is required for somebody to be elected president.

### Compling

Compile files using

```
javac *.java
```

### Running

All the commands to run the project and do testing is test.sh. Give permission to the file using 

```
chmod 777 test.sh

```
To run 
```
./test.sh
```
The expected outcome is in ```consensus.txt```

Test 1 
Here all the 3 members propose at the same time but as memeber 3 has the highest id, its value gets accepted.

```

Consenus reached for value : cat with id : 3

```
Test 2 
Here the first member proposes and his value gets accepted. All the other proposal from 2 and 3 doesnt get consenus on their value. It gets consensus on the val accepted which was proposed by memeber 1.

```

Consenus reached for value : ant with id : 3

```
Test 3 
Here member 3 initating voting but gets killed in between. Hence consensus is not reached as memeber 3 fails to respond to acceptors after a while.

```

Consenus not reached

```



### Input

The Server takes one argument (test1 or test2)  

```
java Server test1
```
The Clinet takes four argument (ip, memeber id, val, delay)  

```
java Member 127.0.0.1 3 ant 1000
```
val can be ```nil``` and delay can be ```0``` if not used

**server is acting just as a brodcaster to brodcast message to all members.