!/bin/bash

clear
echo 'Test case 1'

javac Server.java && java Server test1 &
sleep 3

javac Member.java
sleep 2

java Member 127.0.0.1 1 ant 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 2 dog 0 >> output.txt& 
sleep 2
java Member 127.0.0.1 3 cat 0 >> output.txt &
sleep 2
java Member 127.0.0.1 4 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 5 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 6 nil 0 >> output.txt &
sleep 2
java Member 127.0.0.1 7 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 8 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 9 nil 0 >> output.txt &
sleep 15



count=$(grep -c 'Consenus' output.txt)

if [ "$count" -eq "0" ]; then
   echo "Consenus not reached";
else
	echo "Consenus reached";
	echo "************Test 1************" >> consensus.txt
	grep 'Consenus' output.txt >> consensus.txt
fi
cp output.txt test1Log.txt
> output.txt
pkill -9 java

echo 'Test case 2'

java Server test2 &
sleep 3


java Member 127.0.0.1 1 ant 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 2 dog 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 3 cat 0 >> output.txt &
sleep 2
java Member 127.0.0.1 4 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 5 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 6 nil 0 >> output.txt &
sleep 2
java Member 127.0.0.1 7 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 8 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 9 nil 0 >> output.txt &
sleep 15



count=$(grep -c 'Consenus' output.txt)
echo "************Test 2************" >> consensus.txt
if [ "$count" -eq "0" ]; then
   echo "Consenus not reached" >> consensus.txt
else
	echo "Consenus reached";
	grep 'Consenus' output.txt >> consensus.txt
fi
cp output.txt test2Log.txt 
> output.txt
pkill -9 java

clear
echo 'Test case 3'

java Server test1 &
sleep 3

javac Member.java
sleep 2

java Member 127.0.0.1 3 ant 1000 >> output.txt & 
P1=$!
sleep 2
java Member 127.0.0.1 1 dog 1000 >> output.txt& 
sleep 2
java Member 127.0.0.1 2 cat 1000 >> output.txt &
sleep 2
java Member 127.0.0.1 4 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 5 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 6 nil 0 >> output.txt &
sleep 2
java Member 127.0.0.1 7 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 8 nil 0 >> output.txt & 
sleep 2
java Member 127.0.0.1 9 nil 0 >> output.txt &
sleep 1

kill ${P1}
sleep 13

count=$(grep -c 'Consenus' output.txt)
echo "************Test 3************" >> consensus.txt
if [ "$count" -eq "0" ]; then
   echo "Consenus not reached" >> consensus.txt
else
	echo "Consenus reached";
	grep 'Consenus' output.txt >> consensus.txt
fi
pkill -9 java
cp output.txt test3Log.txt 
> output.txt
