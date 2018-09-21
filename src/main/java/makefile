make:
	find -name "*.java" > sources.txt && javac -cp \* @sources.txt

run:
	java -cp .:\* Initiator

clean:
	rm *.class && rm sources.txt && find -name "*.lck" > lock.txt && rm -rf @lock.txt

